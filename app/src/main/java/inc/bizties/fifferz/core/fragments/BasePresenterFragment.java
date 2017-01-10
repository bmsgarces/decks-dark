package inc.bizties.fifferz.core.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import inc.bizties.fifferz.core.presenters.BasePresenter;
import inc.bizties.fifferz.core.utils.SystemUtils;

public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment {

    private T presenter;
    private boolean isTablet;

    /**
     * @return provides new instance of presenter
     */
    public abstract T initializePresenter();

    /**
     * This function can be overridden to setup presenter. It is being called in onCreate after
     * initializing presenter
     *
     * @param presenter presenter to setup
     */
    @CallSuper
    public void setUpPresenter(T presenter) {
        isTablet = SystemUtils.isTablet(getActivity());
        presenter.setIsTablet(isTablet);
    }

    public T getPresenter() {
        return presenter;
    }

    private void setPresenter() {
        this.presenter = initializePresenter();
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        setUpPresenter(presenter);
        getPresenter().onAttach(this);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
        presenter = null;
    }
}
