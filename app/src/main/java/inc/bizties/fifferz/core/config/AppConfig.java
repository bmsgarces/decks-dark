package inc.bizties.fifferz.core.config;

import android.content.Context;
import android.content.res.Resources;

public class AppConfig {

    private String packageName;
    private Resources androidResources;

    public String getPackageName() {
        return packageName;
    }

    public Resources getAndroidResources() {
        return androidResources;
    }

    public static class Builder {
        private AppConfig config;

        public Builder() {
            config = new AppConfig();
        }

        public AppConfig build() {
            return config;
        }

        public Builder setResources(Context context) {
            config.androidResources = context.getResources();
            return this;
        }

        public Builder setAuthorityName(String packageName) {
            config.packageName = packageName;
            return this;
        }
    }
}
