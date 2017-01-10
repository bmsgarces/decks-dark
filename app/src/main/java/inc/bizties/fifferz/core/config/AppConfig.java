package inc.bizties.fifferz.core.config;

public class AppConfig {

    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public static class Builder {
        private AppConfig config;

        public Builder() {
            config = new AppConfig();
        }

        public AppConfig build() {
            return config;
        }

        public Builder setAuthorityName(String packageName) {
            config.packageName = packageName;
            return this;
        }
    }
}
