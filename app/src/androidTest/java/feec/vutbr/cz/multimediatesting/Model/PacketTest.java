package feec.vutbr.cz.multimediatesting.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by alda on 3.3.17.
 */
public class PacketTest {
    Packet p;
    Packet r;

    @Before
    public void setUp() throws Exception {

        p = new Packet(2);
        byte[] buffer = new byte[]{0, 0, 0, 0, 5};
        p.setData(buffer);
        byte[] data = Packet.PacketFactory.pack(p);

        r = Packet.PacketFactory.unpack(data);
    }


    @Test
    public void setData() throws Exception {

        Assert.assertEquals("SeqNum same", p.getSeqNum(), r.getSeqNum());
    }

    @Test
    public void getData() throws Exception {
        Assert.assertArrayEquals("getData same", p.getData(), r.getData());
    }

    @Test
    public void compareTo() throws Exception {
        Assert.assertEquals("Compare same", p.compareTo(r), 0);
    }

}