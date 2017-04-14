package feec.vutbr.cz.multimediatesting.Presenter;

import android.databinding.repacked.treelayout.internal.util.java.lang.string.StringUtil;
import android.databinding.tool.util.StringUtils;
import android.support.annotation.NonNull;
import feec.vutbr.cz.multimediatesting.Contract.SettingsActivityContract;
import feec.vutbr.cz.multimediatesting.Factory.PresenterFactory;
import feec.vutbr.cz.multimediatesting.Model.SavedSettings;

public class SettingsPresenter implements SettingsActivityContract.Presenter {

    private SettingsActivityContract.View mView;
    private SettingsActivityContract.Settings mModel;

    @Override
    public void onAttachView(@NonNull SettingsActivityContract.View view) {
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
    public void onPacketSizeChange(String packetSize) {
        int size;
        try {
            size = Integer.parseInt(packetSize);

            if (size > 1024) {
                mView.setPacketSize(String.valueOf(1024));
                return;
            }
            if (size <= 0) {
                mView.setPacketSize(String.valueOf(1));
                return;
            }


            if (mModel.getPacketSize() != size) {
                mModel.savePacketSize(size);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onPacketCountChange(String packetCount) {
        int count;
        try {
            count = Integer.parseInt(packetCount);

            if (count <= 0) {
                mView.setPacketCount(String.valueOf(1));
                return;
            }
            if (mModel.getPacketCount() != count) {
                mModel.savePacketCount(count);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void setSavedSettings(SettingsActivityContract.Settings settings) {
        mModel = settings;
        if (mView != null) {
            mView.setPacketSize(String.valueOf(mModel.getPacketSize()));
            mView.setPacketCount(String.valueOf(mModel.getPacketCount()));
        }
    }

    public static class Factory implements PresenterFactory<SettingsPresenter> {

        @Override
        public SettingsPresenter create() {
            return new SettingsPresenter();
        }
    }
}
