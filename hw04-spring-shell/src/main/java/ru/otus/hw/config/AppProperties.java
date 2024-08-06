package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Locale;
import java.util.Map;

@Setter
@ConfigurationProperties(prefix = "test")
// Использовать @ConfigurationProperties.
// Сейчас класс соответствует файлу настроек. Чтобы они сюда отобразились нужно только правильно разместить аннотации
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {

    @Getter
    private int rightAnswersCountToPass;

    @Getter
    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    public AppProperties(Locale locale, int rightAnswersCountToPass, Map<String, String> fileNameByLocaleTag) {
        this.locale = locale;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        System.out.println(fileNameByLocaleTag.get(locale.toLanguageTag()));
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
