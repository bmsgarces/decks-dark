package inc.bizties.fifferz.core.locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

public class LocaleEngine {

    private final Context context;
    private Locale locale;
    private Resources androidResources;

    protected LocaleEngine(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setDefaultLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public void setLocale(@NonNull Locale locale) {
        setLocale(null, locale);
    }

    public void setLocale(@Nullable Resources resources, @NonNull Locale locale) {
        this.locale = locale;
        setAndroidResources(resources);
    }

    public Resources getAndroidResources() {
        return androidResources;
    }

    public void setAndroidResources(Resources resources) {
        if (resources == null) {
            resources = getContext().getResources();
        }
        this.androidResources = resources;
    }

    protected void updateConfigurationWithLocale(Configuration configuration, Locale locale) {
        Resources resources = getContext().getResources();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}
