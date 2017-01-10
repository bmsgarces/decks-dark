package inc.bizties.fifferz.core.presenters;

import android.support.annotation.CallSuper;

public abstract class BasePresenter<T> {

    protected boolean isTablet;
    T view;

    @CallSuper
    public void onAttach(T view) {
        this.view = view;
    }

    @CallSuper
    public void onDetach() {
        this.view = null;
    }

    public void onStart() {
    }

    protected T getView() {
        return view;
    }

    public void setIsTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }
}
