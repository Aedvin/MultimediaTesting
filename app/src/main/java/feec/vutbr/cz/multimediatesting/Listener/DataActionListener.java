package feec.vutbr.cz.multimediatesting.Listener;


public interface DataActionListener extends BaseActionListener {
    void onData(byte[] buffer);

    void onFinish();
}
