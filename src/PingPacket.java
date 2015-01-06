
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tranq
 */
public class PingPacket 
{
    private InetAddress hostAddr;
    private int port;
    private String payload;
    public PingPacket(InetAddress hostadd, int port, String payload)
    {
        this.hostAddr = hostadd;
        this.port = port;
        this.payload = payload;
    }
    public InetAddress getHost()
    {
        return hostAddr;
    }
    public int getPort()
    {
        return port;
    }
    public String getPayload()
    {
        return payload;
    }
    
}
