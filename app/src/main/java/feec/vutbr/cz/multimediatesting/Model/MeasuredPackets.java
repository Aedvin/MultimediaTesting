package feec.vutbr.cz.multimediatesting.Model;


import android.util.Log;
import feec.vutbr.cz.multimediatesting.Contract.ConnectionFragmentContract;

import java.util.ArrayList;
import java.util.Collections;
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

    /*@Override
    public void countDelay() {
        Collections.sort(mReceived);
        mPacketDelay = new long[mSent.size()];
        for (int i = 0; i < mReceived.size(); i++) {
            Packet received = mReceived.get(i);
            Packet sent = mSent.get(received.getSeqNum());
            mPacketDelay[received.getSeqNum()] = received.getTimeStamp() - sent.getTimeStamp();
        }
    }

    @Override
    public void countJitter() {
        mPacketJitter = new long[mPacketDelay.length - 1];
        for (int i = 1; i < mPacketDelay.length; i++) {
            mPacketJitter[i - 1] = Math.abs(mPacketDelay[i] - mPacketDelay[i - 1]);
        }
    } */


}
