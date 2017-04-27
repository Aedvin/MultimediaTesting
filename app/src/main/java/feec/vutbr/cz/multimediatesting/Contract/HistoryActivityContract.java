package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Listener.BaseActionListener;
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

        void showLoading();

        void hideLoading();

        void showError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void onHomeClick();

        void setDatabaseConnection(Database database);

        void setFileWriter(File file);

        void onItemClick(long id);

        void onDeleteClick(long id);

        void onSaveClick(long id);

    }

    interface Database extends BaseModel {
        ArrayList<HistoryItem> getMeasurements();

        void deleteMeasurement(long id);

        String[] getFileExport(long id);

        String getFileName(long id);

        void close();
    }

    interface File extends BaseModel {
        void setDatabase(Database database);

        void removeDatabase();

        void addListener(BaseActionListener listener);

        void removeListener();

        String canWrite();

        void close();

        void start(long id);
    }
}
