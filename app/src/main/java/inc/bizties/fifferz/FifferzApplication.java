package inc.bizties.fifferz;

import android.app.Application;
import android.util.Log;

import inc.bizties.fifferz.data.cache.DataRepository;
import inc.bizties.fifferz.data.cache.DiskCache;

public class FifferzApplication extends Application {

    private static final String TAG = "FifferzApplication";

    private static FifferzApplication mApp;

    public static FifferzApplication getApp() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initialise();
    }

    private void initialise() {
        initialiseCache();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Initialization is complete!");
        }
    }

    private void initialiseCache() {
        DataRepository.getInstance().setDiskCache(new DiskCache(this));
    }
}
