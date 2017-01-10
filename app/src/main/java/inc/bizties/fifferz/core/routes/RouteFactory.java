package inc.bizties.fifferz.core.routes;

import android.content.Context;
import android.net.Uri;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.core.locale.LocaleHelper;

public class RouteFactory {

    private final Context context;
    private Uri uri;

    public RouteFactory(Context context) {
        this.context = context;
    }

    public RouteFactory start() {
        Uri.Builder builder = RouteFactoryUtils.buildPath(LocaleHelper.getAndroidString(R.string.app_config_scheme),
                LocaleHelper.getAndroidString(R.string.module_config_name_startpage));
        uri = builder.build();
        return this;
    }

    public void launch() {
        RouteFactoryUtils.resolveIntent(RouteFactoryUtils.getRouteIntent(uri), context);
    }
}
