package feec.vutbr.cz.multimediatesting.Contract;

import feec.vutbr.cz.multimediatesting.Model.BaseModel;
import feec.vutbr.cz.multimediatesting.Presenter.BasePresenter;
import feec.vutbr.cz.multimediatesting.View.BaseView;

public interface SettingsActivityContract {
    interface View extends BaseView {
        void closeView();

        void setPacketSize(String packetSize);

        void setPacketCount(String packetCount);

        void setServerAddress(String address);
    }

    interface Presenter extends BasePresenter<View> {
        void onHomeClick();

        void onPacketSizeChange(String packetSize);

        void onPacketCountChange(String packetCount);

        void onServerAddressChange(String address);

        void setSavedSettings(Settings settings);
    }

    interface Settings extends BaseModel {
        void savePacketSize(int packetSize);

        void savePacketCount(int packetCount);

        void saveServerAddress(String serverAddress);

        String getServerAddress();

        int getPacketSize();

        int getPacketCount();
    }
}
