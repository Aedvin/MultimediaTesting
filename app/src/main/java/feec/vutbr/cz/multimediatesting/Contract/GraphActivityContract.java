package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

import java.util.Map;

public interface GraphActivityContract {
    interface View extends BaseView {
        void closeView();

        void showToast(String message);

        void setInfo(Map<String, String> info);

        void setDelay(long[] delay);

        void setJitter(long[] jitter);
    }

    interface Presenter extends BasePresenter<View> {
        void onHomeClick();

        void setDatabaseConnection(Database database);

        void setId(long id);

        void onPageChange(int position);

        void setStrings(Strings strings);
    }

    interface Database extends BaseModel {
        Map<String, String> getInfo(long id);

        long[][] getDelayAndJitter(long id);

        void close();
    }

    interface Info extends BaseView {
        void setInfo(Map<String, String> info);
    }

    interface Delay extends BaseView {
        void setDelay(long[] delay);
    }

    interface Jitter extends BaseView {
        void setJitter(long[] jitter);
    }

    interface Strings extends BaseModel {
        String getString(int code);
    }

}
