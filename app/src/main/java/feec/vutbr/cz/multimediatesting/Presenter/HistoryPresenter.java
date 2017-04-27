package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.HistoryActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Listener.BaseActionListener;

public class HistoryPresenter implements HistoryActivityContract.Presenter, BaseActionListener {

    private HistoryActivityContract.View mView;
    private HistoryActivityContract.Database mModel;
    private HistoryActivityContract.File mFile;

    @Override
    public void onAttachView(@NonNull HistoryActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mView.hideLoading();
        mFile.removeListener();
        mFile.close();
        mFile.removeDatabase();
        mModel.close();
        mFile = null;
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
    public void setFileWriter(HistoryActivityContract.File file) {
        mFile = file;
        mFile.addListener(this);
        mFile.setDatabase(mModel);
    }

    @Override
    public void onItemClick(long id) {
        mView.showResults(id);
    }

    @Override
    public void onDeleteClick(long id) {
        mModel.deleteMeasurement(id);
        mView.setHistoryData(mModel.getMeasurements());
    }

    @Override
    public void onSaveClick(long id) {
        mView.showLoading();
        if (mFile.canWrite().equals("")) {
            mFile.start(id);
        }

    }

    @Override
    public void onError(String message) {
        mFile.close();
        mView.showError(message);
    }

    @Override
    public void onSuccess() {
        mFile.close();
        mView.hideLoading();
    }


    public static class Factory implements PresenterFactory<HistoryPresenter> {

        @Override
        public HistoryPresenter create() {
            return new HistoryPresenter();
        }
    }
}
