package by.intexsoft.restlibrary.service.api;

public interface ILocalizationService {

    String getString(String key, String language);

    void setCurrentLang(String lang);
}
