package inc.bizties.fifferz.core.routes;

import android.content.Context;
import android.net.Uri;

import inc.bizties.fifferz.R;

public class RouteFactory {

    private final Context mContext;
    private Uri mUri;

    public RouteFactory(Context context) {
        this.mContext = context;
    }

    public RouteFactory start() {
        Uri.Builder builder = RouteFactoryUtils
                .buildPath(mContext.getString(R.string.app_config_scheme),
                        mContext.getString(R.string.module_config_name_startpage));
        mUri = builder.build();
        return this;
    }

    public void launch() {
        RouteFactoryUtils.resolveIntent(RouteFactoryUtils.getRouteIntent(mUri), mContext);
    }
}
