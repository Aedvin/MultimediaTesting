package feec.vutbr.cz.multimediatesting.Model;

import android.content.Context;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Contract.GraphActivityContract;
import feec.vutbr.cz.multimediatesting.R;

public class Strings implements ConnectionFragmentContract.Strings, GraphActivityContract.Strings {

    private static Context mCtx;
    public static final int CONNECTION_CODE = 1;
    public static final int SENT_CODE = 2;
    public static final int RECEIVED_CODE = 3;
    public static final int SAVING_CODE = 4;
    public static final int DONE_CODE = 5;
    public static final int NON_EXISTANT_MEASURE = 6;

    public Strings(Context context) {
        mCtx = context;
    }

    @Override
    public String getString(int code) {
        switch (code) {
            case CONNECTION_CODE:
                return mCtx.getString(R.string.connection_success);
            case SENT_CODE:
                return mCtx.getString(R.string.sent);
            case RECEIVED_CODE:
                return mCtx.getString(R.string.received);
            case SAVING_CODE:
                return mCtx.getString(R.string.saving_results);
            case DONE_CODE:
                return mCtx.getString(R.string.done);
            case NON_EXISTANT_MEASURE:
                return getString(R.string.graph_measure_non_existant);
            default:
                return "";
        }
    }
}
