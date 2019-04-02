package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.service.api.ILocalizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@PropertySource("classpath:localization/localization.properties")
public class LocalizationService implements ILocalizationService {
    @Value("${language_default}")
    private String defaultLang;
    private String currentLang;
    private ResourceBundle resourceBundle;

    @PostConstruct
    public void init() {
        setCurrentLang(defaultLang);
    }

    public String getString(String key, String language) {
        setCurrentLang(language);
        return resourceBundle.getString(key);
    }

    public void setCurrentLang(String lang) {
        if (resourceBundle == null || lang == null) {
            currentLang = defaultLang;
            refresh();
        } else if (!currentLang.equals(lang)) {
            currentLang = lang;
            refresh();
        }
    }

    public void refresh() {
        resourceBundle = ResourceBundle.getBundle("localization/l10n_user_messages", new Locale(currentLang));
    }
}