package inc.bizties.fifferz.core.locale;

import android.content.Context;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class LocaleConfig {

    private Context context;
    private Locale locale;
    private LocaleEngine localeEngine;

    protected LocaleConfig(Context context) {
        this.context = context;
        locale = createLocale();
        localeEngine = createLocaleEngine();
        localeEngine.setLocale(locale);
    }

    public abstract Locale createLocale();

    public List<Locale> getAvailableLocales() {
        return Collections.emptyList();
    }

    public boolean hasAvailableLocales() {
        return !getAvailableLocales().isEmpty();
    }

    public abstract LocaleEngine createLocaleEngine();

    public Context getContext() {
        return context;
    }

    public Locale getLocale() {
        return locale;
    }

    public LocaleEngine getLocaleEngine() {
        return localeEngine;
    }

}
