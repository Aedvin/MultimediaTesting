package feec.vutbr.cz.multimediatesting.Model;

import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;
import feec.vutbr.cz.multimediatesting.Listener.DataActionListener;


public class ConnectionFragmentModel extends Thread implements ConnectionFragmentContract.Model, DataActionListener {


    private ConnectionFragmentContract.ActionListener mListener;
    private SocketThread mThread;
    private String mServerAddress;

    private static final int PORT = 49558;


    @Override
    public void addListener(ConnectionFragmentContract.ActionListener listener) {
        mListener = listener;
    }

    @Override
    public void removeListener() {
        mListener = null;
    }

    @Override
    public void onStart() {
        if (mThread == null) {
            mThread = new SocketThread(mServerAddress, PORT, this);
            if (!mThread.isRunning()) {
                mThread.start();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mThread != null) {
            if (mThread.isRunning()) {
                mThread.close();
                try {
                    mThread.join();
                } catch (InterruptedException e) {
                    if (mListener != null) {
                        mListener.onError(e.getMessage());
                    }
                }
            }
            mThread = null;
        }
    }

    @Override
    public void sendData(byte[] buffer) {
        if (mThread != null) {
            if (mThread.isRunning()) {
                mThread.sendData(buffer);
            }
        }
    }

    @Override
    public void setLastPacket() {
        if (mThread != null) {
            mThread.setLastPacket();
        }
    }

    @Override
    public void setServerAddress(String serverAddress) {
        mServerAddress = serverAddress;
    }


    @Override
    public void onError(String message) {
        if (mListener != null) {
            mListener.onError(message);
        }
    }

    @Override
    public void onSuccess() {
        if (mListener != null) {
            mListener.onSuccess();
        }
    }

    @Override
    public void onData(byte[] buffer) {
        if (mListener != null) {
            mListener.onData(buffer);
        }
    }

    @Override
    public void onFinish() {
        if (mListener != null) {
            mListener.onFinish();
        }
    }

}
