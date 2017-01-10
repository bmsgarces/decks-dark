package inc.bizties.fifferz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import inc.bizties.fifferz.activities.LauncherActivity;
import inc.bizties.fifferz.core.FifferzBaseApplication;
import inc.bizties.fifferz.core.FifferzConfig;
import inc.bizties.fifferz.core.config.AppConfig;
import inc.bizties.fifferz.core.locale.ApplicationLocaleConfig;
import inc.bizties.fifferz.core.locale.LocaleEngine;
import inc.bizties.fifferz.core.locale.LocaleHelper;
import inc.bizties.fifferz.data.cache.DataRepository;
import inc.bizties.fifferz.data.cache.DiskCache;
import inc.bizties.fifferz.data.cache.Preferences;
import inc.bizties.fifferz.data.provider.DataProvider;

public class FifferzApplication extends FifferzBaseApplication {

    private static final String TAG = "FifferzApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LocaleEngine localeEngine = FifferzConfig.INSTANCE.getLocaleConfig().getLocaleEngine();
        localeEngine.setLocale(super.getResources(), localeEngine.getLocale());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialise();
    }

    @Override
    public void restartApplication() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12313, new Intent(this, LauncherActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);
    }

    private void initialise() {
        initialiseLocale();
        initialiseConfig();
        initialiseCache();
    }

    private void initialiseLocale() {
        FifferzConfig.INSTANCE.setLocaleConfig(new ApplicationLocaleConfig(this));
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Locale(Language): " + LocaleHelper.getAppLanguage());
        }
    }

    private void initialiseConfig() {
        AppConfig appConfig = new AppConfig.Builder()
                .setAuthorityName(getPackageName())
                .build();
        FifferzConfig.INSTANCE.setAppConfig(appConfig);
    }

    private void initialiseCache() {
        DataRepository.INSTANCE.setDiskCache(new DiskCache(this));
    }

    @Override
    public void clearUserDataOnLogout() {
        DataProvider.clear(this);
        Preferences.clear(this);
        initialiseLocale();
    }
}
