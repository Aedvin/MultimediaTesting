package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.HistoryActivityContract;
import feec.vutbr.cz.multimediatesting.Contract.SettingsActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.View.HistoryActivity;

public class HistoryPresenter implements HistoryActivityContract.Presenter {

    private HistoryActivityContract.View mView;
    private HistoryActivityContract.Database mModel;

    @Override
    public void onAttachView(@NonNull HistoryActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mView = null;
        mModel = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onHomeClick() {
        if (mView != null) {
            mView.closeView();
        }
    }

    @Override
    public void setDatabaseConnection(HistoryActivityContract.Database database) {
        mModel = database;
        mView.setHistoryData(mModel.getMeasurements());
    }

    @Override
    public void onItemClick(long id) {

    }

    @Override
    public void onDeleteClick(long id) {
        mModel.deleteMeasurement(id);
        mView.setHistoryData(mModel.getMeasurements());
    }


    public static class Factory implements PresenterFactory<HistoryPresenter> {

        @Override
        public HistoryPresenter create() {
            return new HistoryPresenter();
        }
    }
}
