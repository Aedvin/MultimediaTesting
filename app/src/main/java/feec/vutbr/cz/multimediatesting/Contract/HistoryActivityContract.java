package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Model.HistoryItem;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

import java.util.ArrayList;

public interface HistoryActivityContract {
    interface View extends BaseView {
        void closeView();

        void showResults(long id);

        void setHistoryData(ArrayList<HistoryItem> list);
    }

    interface Presenter extends BasePresenter<View> {
        void onHomeClick();

        void setDatabaseConnection(Database database);

        void onItemClick(long id);

        void onDeleteClick(long id);

        void onSaveClick(long id);
    }

    interface Database extends BaseModel {
        ArrayList<HistoryItem> getMeasurements();

        void deleteMeasurement(long id);

        void close();
    }
}
