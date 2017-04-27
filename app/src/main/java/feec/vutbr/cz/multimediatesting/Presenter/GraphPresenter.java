package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.GraphActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Model.Strings;

import java.util.Map;

public class GraphPresenter implements GraphActivityContract.Presenter {

    private GraphActivityContract.View mView;
    private GraphActivityContract.Database mModel;
    private GraphActivityContract.Strings mStrings;
    private long mId;

    private Map<String, String> mInfo;
    private long[] mDelay;
    private long[] mJitter;

    @Override
    public void onAttachView(@NonNull GraphActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mModel.close();
        mView = null;
        mModel = null;
        mStrings = null;
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
    public void setDatabaseConnection(GraphActivityContract.Database database) {
        mModel = database;
    }

    @Override
    public void setId(long id) {
        if (mId != id) {
            if (id != 0) {
                mId = id;
            } else {
                showError();
            }
            loadDataFromDatabase();
        }
    }

    private void loadDataFromDatabase() {
        mInfo = mModel.getInfo(mId);
        long[][] delayAndJitter = mModel.getDelayAndJitter(mId);
        mDelay = delayAndJitter[0];
        mJitter = delayAndJitter[1];
    }

    private void showError() {
        mView.showToast(mStrings.getString(Strings.NON_EXISTANT_MEASURE));
        mView.closeView();
    }

    @Override
    public void onPageChange(int position) {
        if (mId != 0) {
            switch (position) {
                case 0:
                    if (mInfo.size() == 0) {
                        showError();
                        break;
                    }
                    mView.setInfo(mInfo);
                    break;
                case 1:
                    if (mDelay.length == 0) {
                        showError();
                        break;
                    }
                    mView.setDelay(mDelay);
                    break;
                case 2:
                    if (mJitter.length == 0) {
                        showError();
                        break;
                    }
                    mView.setJitter(mJitter);
                    break;
            }
        }
    }

    @Override
    public void setStrings(GraphActivityContract.Strings strings) {

    }



    public static class Factory implements PresenterFactory<GraphPresenter> {

        @Override
        public GraphPresenter create() {
            return new GraphPresenter();
        }
    }
}
