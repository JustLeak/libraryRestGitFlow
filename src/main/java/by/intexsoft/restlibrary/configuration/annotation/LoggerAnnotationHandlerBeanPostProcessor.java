package by.intexsoft.restlibrary.configuration.annotation;

import by.intexsoft.restlibrary.controller.CardController;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class LoggerAnnotationHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> beanNameClassMap = new HashMap<>();
    private Map<Class, List<Method>> classMethodsMap = new LinkedHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        List<Method> methods = new ArrayList<>();
        Arrays.stream(beanClass.getMethods())
                .parallel()
                .filter(method -> method.isAnnotationPresent(ExceptionLogging.class))
                .forEach(methods::add);
        if (!methods.isEmpty()) {
            beanNameClassMap.put(beanName, beanClass);
            classMethodsMap.put(beanClass, methods);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beanNameClassMap.get(beanName);
        if (beanClass != null) {
            MethodInterceptor handler = (obj, method, args, proxy) -> {
                if (classMethodsMap.get(beanClass).contains(method)) {
                    final Logger logger = Logger.getLogger(beanClass);
                    try {
                        //Тут вовзвращается результат оригинального метода
                        return proxy.invoke(bean, args);
                    }catch (Exception ex){
                        //Здесь можно обработать как хочешь и вернуть тоже что хочешь
                        logger.error(ex.getMessage(), ex);
                        System.out.println("Exception logged.");
                    }
                }
                return proxy.invoke(bean, args);
            };

            return Enhancer.create(beanClass, handler);
        }

        return bean;
    }
}
