package feec.vutbr.cz.multimediatesting.Contract;


import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

public interface MainActivityContract {
    public interface View extends BaseView {
        void beginTransition(int position);
    }

    public interface Presenter extends BasePresenter<View> {
        void onTransitionRequest(int currentPosition);
    }

    public interface Model extends BaseModel {

    }
}
