package inc.bizties.fifferz.core;

import inc.bizties.fifferz.core.config.AppConfig;
import inc.bizties.fifferz.core.locale.LocaleConfig;

public enum FifferzConfig {

    INSTANCE;

    private AppConfig appConfig;

    private LocaleConfig localeConfig;

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public LocaleConfig getLocaleConfig() {
        return localeConfig;
    }

    public void setLocaleConfig(LocaleConfig localeConfig) {
        this.localeConfig = localeConfig;
    }

}
