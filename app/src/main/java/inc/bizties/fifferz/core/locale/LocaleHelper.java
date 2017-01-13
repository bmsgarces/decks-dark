package inc.bizties.fifferz.core.locale;

import inc.bizties.fifferz.core.FifferzConfig;

public class LocaleHelper {

    public static String getAndroidString(int id) {
        return FifferzConfig.INSTANCE.getAppConfig().getAndroidResources().getString(id);
    }
}
