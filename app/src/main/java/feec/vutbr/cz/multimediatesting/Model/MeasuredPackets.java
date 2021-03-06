package feec.vutbr.cz.multimediatesting.Model;


import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;

import java.util.ArrayList;
import java.util.Random;

public class MeasuredPackets implements ConnectionFragmentContract.PacketModel {

    private Random mRandom;
    private ArrayList<Packet> mSent;
    private ArrayList<Packet> mReceived;
    private int mDataSize;


    public MeasuredPackets(int dataSize) {
        mRandom = new Random();
        mSent = new ArrayList<>();
        mReceived = new ArrayList<>();
        mDataSize = dataSize + 4;
    }

    @Override
    public byte[] getSend(int position) {
        byte[] buffer = new byte[mDataSize];
        mRandom.nextBytes(buffer);
        Packet p = new Packet(position);
        p.setData(buffer);
        p.setTimeStamp(System.currentTimeMillis());
        mSent.add(p);
        return Packet.PacketFactory.pack(p);
    }

    @Override
    public void addReceived(byte[] buffer) {
        Packet p = Packet.PacketFactory.unpack(buffer);
        p.setTimeStamp(System.currentTimeMillis());
        mReceived.add(p);
    }

    @Override
    public ArrayList<Packet> getSent() {
        return mSent;
    }

    @Override
    public ArrayList<Packet> getReceived() {
        return mReceived;
    }


    @Override
    public int getPercentSent(int packetCount) {
        return (int) ((double) mSent.size() / (double) packetCount * 100);
    }

    @Override
    public int getPercentReceived(int packetCount) {
        return (int) ((double) mReceived.size() / (double) packetCount * 100);
    }

}
