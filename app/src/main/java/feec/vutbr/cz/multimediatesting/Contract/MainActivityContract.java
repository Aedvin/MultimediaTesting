package feec.vutbr.cz.multimediatesting.Contract;


import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

public interface MainActivityContract {
    interface View extends BaseView {
        void beginTransition(int position);

        void showConfig();

        void showHistory();

    }

    interface Presenter extends BasePresenter<View> {
        void onConfigClick();

        void onHistoryClick();

    }

    interface Model extends BaseModel {

    }
}
