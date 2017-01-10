package inc.bizties.fifferz.core.locale;

import android.content.Context;

import java.util.Locale;

public class ApplicationLocaleConfig extends LocaleConfig {

    public ApplicationLocaleConfig(Context context) {
        super(context);
    }

    @Override
    public Locale createLocale() {
        return new Locale("pt", "PT");
    }

    @Override
    public LocaleEngine createLocaleEngine() {
        return new LocaleEngine(getContext());
    }
}
