package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Listener.DataActionListener;
import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Model.Packet;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

import java.util.ArrayList;

public interface ConnectionFragmentContract {

    interface View extends BaseView {
        void hideStartButton();

        void showStartButton();

        void hideLoading();

        void showLoading();

        void postInfo(final String info);

        void startTimer();

        void stopTimer();

        void showResultButton();

        void hideResultButton();

        void initView();

        void showResults(long id);
    }

    interface Presenter extends BasePresenter<View> {
        void onStartMeasure();

        void onSendNewPacket();

        void setSavedSettings(ConnectionFragmentContract.Settings settings);

        void setDatabaseConnection(ConnectionFragmentContract.DatabaseModel database);

        void onViewRequest();

        void onShowResultsClick();
    }

    interface Model extends BaseModel {
        void addListener(ActionListener listener);

        void removeListener();

        void onStart();

        void onDestroy();

        void sendData(byte[] buffer);

        void setLastPacket();

        void setServerAddress(String serverAddress);
    }

    interface PacketModel extends BaseModel {
        byte[] getSend(int position);

        void addReceived(byte[] buffer);

        ArrayList<Packet> getSent();

        ArrayList<Packet> getReceived();

        int getPercentSent(int packetCount);

        int getPercentReceived(int packetCount);

    }

    interface DatabaseModel extends BaseModel {
        long insertData(ConnectionFragmentContract.PacketModel packets);

        void close();
    }

    interface Settings extends BaseModel {
        int getPacketSize();

        int getPacketCount();

        String getServerAddress();
    }


    interface ActionListener extends DataActionListener {
    }
}
