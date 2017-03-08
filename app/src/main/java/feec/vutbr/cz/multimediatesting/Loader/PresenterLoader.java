package feec.vutbr.cz.multimediatesting.Loader;

import android.content.Context;

import android.support.v4.content.Loader;
import feec.vutbr.cz.multimediatesting.Contract.MainActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;

/**
 * Created by alda on 1.3.17.
 */
public class PresenterLoader<T extends BasePresenter> extends Loader<T> {

    private final PresenterFactory<T> mFactory;
    private T mPresenter;

    public PresenterLoader(Context context, PresenterFactory factory) {
        super(context);
        mFactory = factory;
    }

    @Override
    protected void onStartLoading() {
        if (mPresenter != null) {
            deliverResult(mPresenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        mPresenter = mFactory.create();
        deliverResult(mPresenter);
    }

    @Override
    protected void onReset() {
        mPresenter.onDestroy();
        mPresenter = null;
    }
}
