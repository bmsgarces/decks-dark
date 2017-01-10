package inc.bizties.fifferz.core.routes;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RouteFactoryUtils {

    static void resolveIntent(Intent intent, Context context) {
        if (context instanceof Application) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

    public static Intent getRouteIntent(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        return intent;
    }

    static Uri.Builder buildPath(String scheme, String host) {
        return new Uri.Builder().scheme(scheme.replace("/", ""))
                .authority(host.replace("/", ""));
    }

}
