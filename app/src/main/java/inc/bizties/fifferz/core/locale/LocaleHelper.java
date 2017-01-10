package inc.bizties.fifferz.core.locale;

import java.util.Locale;

import inc.bizties.fifferz.core.FifferzConfig;

public class LocaleHelper {

    public static String getAndroidString(int id) {
        return FifferzConfig.INSTANCE.getLocaleConfig().getLocaleEngine().getAndroidResources().getString(id);
    }

    public static String getAppLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
