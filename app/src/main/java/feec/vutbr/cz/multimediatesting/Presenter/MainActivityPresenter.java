package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import feec.vutbr.cz.multimediatesting.Contract.MainActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.ModelFactory;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;

/**
 * Created by alda on 1.3.17.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;


    public MainActivityPresenter() {

    }

    @Override
    public void onAttachView(@NonNull MainActivityContract.View view) {
        mView = view;
        mView.beginTransition(1);
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onTransitionRequest(int currentPosition) {
        mView.beginTransition(currentPosition + 1);
    }


    public static class Factory implements PresenterFactory<MainActivityPresenter> {

        @Override
        public MainActivityPresenter create() {
            return new MainActivityPresenter();
        }
    }
}
