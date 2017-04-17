package feec.vutbr.cz.multimediatesting.Model;

import android.content.Context;
import android.content.SharedPreferences;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Contract.SettingsActivityContract;

public class SavedSettings implements SettingsActivityContract.Settings, ConnectionFragmentContract.Settings {

    private final static String PREFS_NAME = "Settings";
    private final static String PACKET_SIZE_KEY = "packet_size";
    private final static String PACKET_COUNT_KEY = "packet_count";
    private final static String SERVER_ADDRESS = "server_address";

    private Context mContext;
    private SharedPreferences mSettings;

    public SavedSettings(Context context) {
        mContext = context;
        mSettings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void savePacketSize(int packetSize) {
        mSettings.edit().putInt(PACKET_SIZE_KEY, packetSize).apply();
    }

    @Override
    public void savePacketCount(int packetCount) {
        mSettings.edit().putInt(PACKET_COUNT_KEY, packetCount).apply();
    }

    @Override
    public void saveServerAddress(String serverAddress) {
        mSettings.edit().putString(SERVER_ADDRESS, serverAddress).apply();
    }

    @Override
    public String getServerAddress() {
        return mSettings.getString(SERVER_ADDRESS, "127.0.0.1");
    }

    @Override
    public int getPacketSize() {
        return mSettings.getInt(PACKET_SIZE_KEY, 256);
    }

    @Override
    public int getPacketCount() {
        return mSettings.getInt(PACKET_COUNT_KEY, 50);
    }
}
