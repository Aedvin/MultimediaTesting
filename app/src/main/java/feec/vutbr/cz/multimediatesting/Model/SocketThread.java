package feec.vutbr.cz.multimediatesting.Model;

import feec.vutbr.cz.multimediatesting.Listener.DataActionListener;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by alda on 1.3.17.
 */
public class SocketThread extends Thread {

    private DatagramSocket mSocket;
    private boolean mRunning;
    private DataActionListener mListener;
    private boolean mLastPacket;

    private String mHost;
    private int mPort;

    private final Object mLock = new Object();

    private final static int NORMAL_TIMEOUT = 500;
    private final static int LAST_TIMEOUT = 1000;
    private final static int MAX_TRY_COUNT = 5;
    private final static int MAX_PACKET_SIZE = 1024;

    public SocketThread(String host, int serverPort, DataActionListener listener) {
        mListener = listener;
        mRunning = false;
        mHost = host;
        mPort = serverPort;
        mLastPacket = false;
    }

    public void sendData(byte[] data) {
        synchronized (mLock) {
            if (!mSocket.isClosed()) {
                try {
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    packet.setAddress(InetAddress.getByName(mHost));
                    packet.setPort(mPort);
                    mSocket.send(packet);
                } catch (IOException e) {
                    publishError(e.getMessage());
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        mRunning = true;
        super.start();
    }

    @Override
    public void run() {

        try {
            mSocket = new DatagramSocket();
            mSocket.connect(InetAddress.getByName(mHost), mPort);
            mSocket.setSoTimeout(NORMAL_TIMEOUT);
            publishSuccess();
        } catch (IOException e) {
            mRunning = false;
            publishError(e.getMessage());
        }

        int tries = 0;

        while (mRunning) {
            byte[] buffer = new byte[MAX_PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, MAX_PACKET_SIZE);
            try {
                mSocket.receive(packet);
                tries = 0;
                publishData(Arrays.copyOf(packet.getData(), packet.getLength()));
            } catch (SocketTimeoutException e) {
                if (mLastPacket) {
                    tries++;
                }
            } catch (IOException e) {
                publishError(e.getMessage());
            }

            if (tries >= MAX_TRY_COUNT) {
                mRunning = false;
            }

        }

        try {
            mSocket.disconnect();
            mSocket.close();
        } catch (Exception e) {
            mRunning = false;
        }

    }

    public boolean isRunning() {
        return mRunning;
    }

    public void close() {
        mRunning = false;
    }

    public int getServePort() {
        return mSocket.getPort();
    }

    public void setLastPacket() {
        mLastPacket = true;
        try {
            mSocket.setSoTimeout(LAST_TIMEOUT);
        } catch (SocketException e) {
            publishError(e.getMessage());
        }
    }

    private void publishError(String message) {
        if (mListener != null) {
            mListener.onError(message);
        }
    }

    private void publishData(byte[] buffer) {
        if (mListener != null) {
            mListener.onData(buffer);
        }
    }

    private void publishSuccess() {
        if (mListener != null) {
            mListener.onSuccess();
        }
    }
}
