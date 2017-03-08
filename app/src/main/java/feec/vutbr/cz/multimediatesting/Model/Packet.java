package feec.vutbr.cz.multimediatesting.Model;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by alda on 3.3.17.
 */
public class Packet implements Comparable<Packet> {
    private int mSeqNum;
    private long mTimeStamp;
    private byte[] mData;

    public Packet(int seqNum) {
        mSeqNum = seqNum;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public byte[] getData() {
        return mData;
    }

    public int getSeqNum() {
        return mSeqNum;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timestamp) {
        mTimeStamp = timestamp;
    }

    @Override
    public int compareTo(Packet o) {
        if (mSeqNum < o.mSeqNum) {
            return -1;
        } else if (mSeqNum > o.mSeqNum) {
            return 1;
        } else {
            return 0;
        }
    }

    public static class PacketFactory {
        public static byte[] pack(Packet p) {
            ByteBuffer buffer = ByteBuffer.wrap(p.mData);
            buffer.putInt(0, p.mSeqNum);
            return buffer.array();
        }

        public static Packet unpack(byte[] data) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            Packet p = new Packet(buffer.getInt(0));
            p.setData(buffer.array());
            return p;
        }
    }
}
