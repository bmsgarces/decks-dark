package inc.bizties.fifferz.core;

import inc.bizties.fifferz.core.config.AppConfig;

public enum FifferzConfig {

    INSTANCE;

    private AppConfig appConfig;

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

}
