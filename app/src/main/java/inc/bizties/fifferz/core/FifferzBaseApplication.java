package inc.bizties.fifferz.core;

import android.app.Application;

import java.io.File;

public abstract class FifferzBaseApplication extends Application {

    private static FifferzBaseApplication app;

    public static FifferzBaseApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public void clearCache() {
        try {
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (Exception ignored) {
        }
    }

    public abstract void restartApplication();

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else
            return dir != null && dir.isFile() && dir.delete();
    }

    public abstract void clearUserDataOnLogout();

}
