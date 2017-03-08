package feec.vutbr.cz.multimediatesting.Listener;

/**
 * Created by alda on 3.3.17.
 */
public interface DataActionListener extends BaseActionListener {
    void onData(byte[] buffer);
}
