package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Listener.BaseActionListener;
import feec.vutbr.cz.multimediatesting.Listener.DataActionListener;
import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

/**
 * Created by alda on 1.3.17.
 */
public interface ConnectionFragmentContract {

    interface View extends BaseView {
        void hideButton();

        void showButton();

        void hideLoading();

        void showLoading();

        void postInfo(final String info);

        void startTimer();

        void stopTimer();
    }

    interface Presenter extends BasePresenter<View> {
        void onStartMeasure();

        void onSendNewPacket();
    }

    interface Model extends BaseModel {
        void addListener(ActionListener listener);

        void removeListener();

        void onStart();

        void onDestroy();

        void sendData(byte[] buffer);

        void setLastPacket();
    }

    interface PacketModel extends BaseModel {
        byte[] getSend(int position);

        void addReceived(byte[] buffer);

        int getNumOfSent();

        int getNumOfReceived();

        int getPercentSent(int packetCount);

        int getPercentReceived(int packetCount);
    }


    interface ActionListener extends DataActionListener {
    }
}
