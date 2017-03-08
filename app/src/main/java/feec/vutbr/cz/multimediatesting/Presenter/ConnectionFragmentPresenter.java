package feec.vutbr.cz.multimediatesting.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Contract.MainActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Model.ConnectionFragmentModel;
import feec.vutbr.cz.multimediatesting.Model.MeasuredPackets;

public class ConnectionFragmentPresenter implements ConnectionFragmentContract.Presenter, ConnectionFragmentContract.ActionListener {

    private ConnectionFragmentContract.View mView;
    private ConnectionFragmentContract.Model mModel;
    private ConnectionFragmentContract.PacketModel mPackets;

    private int mPacketSeqNum;
    private int mPacketCount = 1000;
    private int mPacketSize = 1024;

    public ConnectionFragmentPresenter() {
        ConnectionFragmentModel.Factory factory = new ConnectionFragmentModel.Factory();
        mModel = factory.create();
    }

    @Override
    public void onAttachView(@NonNull ConnectionFragmentContract.View view) {
        mView = view;
        mModel.addListener(this);
        mPacketSeqNum = 0;
    }

    @Override
    public void onDetachView() {
        mView = null;
        mModel.removeListener();
        mModel.onDestroy();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }


    @Override
    public void onError(String message) {
        mModel.onDestroy();
        mView.postInfo(message);
        mView.hideLoading();
        mView.showButton();
        mView.stopTimer();
    }

    @Override
    public void onSuccess() {
        mView.postInfo("Connection success...");
        mPackets = new MeasuredPackets(mPacketSize);
        mView.startTimer();
    }


    @Override
    public void onStartMeasure() {
        mModel.onStart();
        mPacketSeqNum = 0;
        mView.hideButton();
        mView.showLoading();
    }

    @Override
    public void onSendNewPacket() {
        mModel.sendData(mPackets.getSend(mPacketSeqNum));
        mPacketSeqNum++;
        if (mView != null && mPacketCount % 10 == 0) {
            mView.postInfo(String.format("Sent %d%%\n  Received %d%%", mPackets.getPercentSent(mPacketCount), mPackets.getPercentReceived(mPacketCount)));
        }
        if (mPacketSeqNum >= mPacketCount) {
            if (mView != null) {
                mView.stopTimer();
            }
            mModel.setLastPacket();
        }
    }

    @Override
    public void onData(byte[] buffer) {
        mPackets.addReceived(buffer);
    }


    public static class Factory implements PresenterFactory<ConnectionFragmentPresenter> {

        @Override
        public ConnectionFragmentPresenter create() {
            return new ConnectionFragmentPresenter();
        }
    }
}
