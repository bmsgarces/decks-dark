package inc.bizties.fifferz.core.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import inc.bizties.fifferz.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        initialiseToolbar();
    }

    @LayoutRes
    public abstract int getLayoutId();

    public void setToolbarTitle(@StringRes int title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    public void initialiseToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
