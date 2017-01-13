package inc.bizties.fifferz;

import android.app.Application;
import android.util.Log;

import inc.bizties.fifferz.core.FifferzConfig;
import inc.bizties.fifferz.core.config.AppConfig;
import inc.bizties.fifferz.data.cache.DataRepository;
import inc.bizties.fifferz.data.cache.DiskCache;

public class FifferzApplication extends Application {

    private static final String TAG = "FifferzApplication";

    private static FifferzApplication app;

    public static FifferzApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initialise();
    }

    private void initialise() {
        initialiseConfig();
        initialiseCache();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Initialization is complete!");
        }
    }

    private void initialiseConfig() {
        AppConfig appConfig = new AppConfig.Builder()
                .setResources(this)
                .setAuthorityName(getPackageName())
                .build();
        FifferzConfig.INSTANCE.setAppConfig(appConfig);
    }

    private void initialiseCache() {
        DataRepository.INSTANCE.setDiskCache(new DiskCache(this));
    }
}
