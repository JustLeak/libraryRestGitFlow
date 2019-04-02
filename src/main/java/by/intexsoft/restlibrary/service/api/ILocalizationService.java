package by.intexsoft.restlibrary.service.api;

public interface ILocalizationService {
    void init();

    String getString(String key, String language);

    void setCurrentLang(String lang);

    void refresh();
}
