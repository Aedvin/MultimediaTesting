package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Model.ConnectionFragmentModel;
import feec.vutbr.cz.multimediatesting.Model.MeasuredPackets;

import java.util.Locale;

public class ConnectionFragmentPresenter implements ConnectionFragmentContract.Presenter, ConnectionFragmentContract.ActionListener {

    private ConnectionFragmentContract.View mView;
    private ConnectionFragmentContract.Model mModel;
    private ConnectionFragmentContract.PacketModel mPackets;
    private ConnectionFragmentContract.Settings mSettings;
    private ConnectionFragmentContract.DatabaseModel mDatabase;

    private boolean mFinished;
    private volatile boolean mError;
    private String mLastError;
    private int mPacketSeqNum;
    private int mPacketCount;
    private int mPacketSize;
    private long mLastMeasureId;
    private String mServerAddress;

    public ConnectionFragmentPresenter() {
        ConnectionFragmentModel.Factory factory = new ConnectionFragmentModel.Factory();
        mModel = factory.create();
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
        mView.postInfo("Connection success...");
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
            mView.postInfo(String.format(Locale.getDefault(), "Sent %d%%\n  Received %d%%", mPackets.getPercentSent(mPacketCount), mPackets.getPercentReceived(mPacketCount)));
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
            mView.postInfo("Saving results...");
        }
        if (mDatabase != null && !mError) {
            mLastMeasureId = mDatabase.insertData(mPackets);
        }
        if (mView != null) {
            mView.initView();
            mView.postInfo("Done");
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
