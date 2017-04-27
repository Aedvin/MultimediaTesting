package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Model.ConnectionFragmentModel;
import feec.vutbr.cz.multimediatesting.Model.MeasuredPackets;
import feec.vutbr.cz.multimediatesting.Model.Strings;

import java.util.Locale;

public class ConnectionFragmentPresenter implements ConnectionFragmentContract.Presenter, ConnectionFragmentContract.ActionListener {

    private ConnectionFragmentContract.View mView;
    private ConnectionFragmentContract.Model mModel;
    private ConnectionFragmentContract.PacketModel mPackets;
    private ConnectionFragmentContract.Settings mSettings;
    private ConnectionFragmentContract.DatabaseModel mDatabase;
    private ConnectionFragmentContract.Strings mStrings;

    private boolean mFinished;
    private volatile boolean mError;
    private String mLastError;
    private int mPacketSeqNum;
    private int mPacketCount;
    private int mPacketSize;
    private long mLastMeasureId;
    private String mServerAddress;

    public ConnectionFragmentPresenter() {
        mModel = new ConnectionFragmentModel();
        mPacketSeqNum = 0;
        mFinished = false;
        mLastMeasureId = 0;
    }

    @Override
    public void onAttachView(@NonNull ConnectionFragmentContract.View view) {
        mView = view;
        mModel.addListener(this);
        initView();
    }

    @Override
    public void onDetachView() {
        mView = null;
        mModel.removeListener();
        mModel.onDestroy();
        mDatabase.close();
        mSettings = null;
        mDatabase = null;
        mStrings = null;
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }


    @Override
    public void onError(String message) {
        mError = true;
        mModel.onDestroy();
        mView.stopTimer();
        mView.initView();
        mLastError = message;
    }

    @Override
    public void onSuccess() {
        mView.postInfo(mStrings.getString(Strings.CONNECTION_CODE));
        mPackets = new MeasuredPackets(mPacketSize);
        mView.startTimer();
    }


    @Override
    public void onStartMeasure() {
        mModel.setServerAddress(mServerAddress);
        mModel.onStart();
        mPacketSeqNum = 0;
        mFinished = false;
        mError = false;
        mPackets = new MeasuredPackets(mPacketSize);
        mView.initView();
        mView.showLoading();
        mView.hideStartButton();
    }

    @Override
    public void onSendNewPacket() {
        mModel.sendData(mPackets.getSend(mPacketSeqNum));
        mPacketSeqNum++;
        if (mView != null && mPacketCount % 10 == 0 && !mError) {
            mView.postInfo(String.format(Locale.getDefault(), mStrings.getString(Strings.SENT_CODE) + " %d%%\n  " + mStrings.getString(Strings.RECEIVED_CODE) + " %d%%", mPackets.getPercentSent(mPacketCount), mPackets.getPercentReceived(mPacketCount)));
        }
        if (mPacketSeqNum >= mPacketCount) {
            if (mView != null) {
                mView.stopTimer();
            }
            mModel.setLastPacket();
        }
    }

    @Override
    public void setSavedSettings(ConnectionFragmentContract.Settings settings) {
        mSettings = settings;
        mPacketCount = mSettings.getPacketCount();
        mPacketSize = mSettings.getPacketSize();
        mServerAddress = mSettings.getServerAddress();
    }

    @Override
    public void setDatabaseConnection(ConnectionFragmentContract.DatabaseModel database) {
        mDatabase = database;
    }

    @Override
    public void setStrings(ConnectionFragmentContract.Strings strings) {
        mStrings = strings;
    }


    @Override
    public void onViewRequest() {
        initView();
    }


    @Override
    public void onShowResultsClick() {
        mView.showResults(mLastMeasureId);
    }

    @Override
    public void onData(byte[] buffer) {
        mPackets.addReceived(buffer);
    }

    @Override
    public void onFinish() {
        mFinished = true;
        if (mView != null) {
            mView.postInfo(mStrings.getString(Strings.SAVING_CODE));
        }
        if (mDatabase != null && !mError) {
            mLastMeasureId = mDatabase.insertData(mPackets);
        }
        if (mView != null) {
            mView.initView();
            mView.postInfo(mStrings.getString(Strings.DONE_CODE));
        }
        mModel.onDestroy();
    }


    private void initView() {
        if (mView != null) {
            if (mFinished && !mError) {
                mView.hideLoading();
                mView.showStartButton();
                mView.showResultButton();
            } else if (mError) {
                mView.showStartButton();
                mView.postInfo(mLastError);
                mView.hideLoading();
            } else {
                mView.hideResultButton();
                mView.hideLoading();
                mView.showStartButton();
                mView.postInfo("");
            }
        }
    }


    public static class Factory implements PresenterFactory<ConnectionFragmentPresenter> {

        @Override
        public ConnectionFragmentPresenter create() {
            return new ConnectionFragmentPresenter();
        }
    }
}
