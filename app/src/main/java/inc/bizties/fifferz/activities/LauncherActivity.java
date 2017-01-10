package inc.bizties.fifferz.activities;

import android.os.Bundle;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.core.activities.BaseActivity;
import inc.bizties.fifferz.core.routes.RouteFactory;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolvePaths();
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launcher;
    }

    private void resolvePaths() {
        new RouteFactory(this).start().launch();
    }
}
