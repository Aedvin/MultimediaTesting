package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.MainActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private boolean mLoaded;

    public MainActivityPresenter() {
        mLoaded = false;
    }

    @Override
    public void onAttachView(@NonNull MainActivityContract.View view) {
        mView = view;
        if (!mLoaded) {
            mView.beginTransition(1);
            mLoaded = true;
        }
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void onConfigClick() {
        if (mView != null) {
            mView.showConfig();
        }
    }

    @Override
    public void onHistoryClick() {
        if (mView != null) {
            mView.showHistory();
        }
    }


    public static class Factory implements PresenterFactory<MainActivityPresenter> {

        @Override
        public MainActivityPresenter create() {
            return new MainActivityPresenter();
        }
    }
}
