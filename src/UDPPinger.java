
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tranq
 */
public class UDPPinger {
    protected DatagramSocket DataSocket;
    protected String host;
    protected int port;
    /**
* This method creates a Datagram socket.
     * @param host
     * @param port
*/
protected UDPPinger(String host, int port)
{
    this.host = host;
    this.port = port;
}
public void createSocket()
{
    try
    {
        DataSocket = new DatagramSocket(port);
    }
    catch(Exception ex)
    {
        
    }
}
/**
* Given a PingPacket, this method makes an UDP pack
et using the payload, the
* size, the destination address and the destination
port, and sends the packet. 
*/
public void sendPing(PingPacket pm)
{
    try
    {
        DatagramPacket packet = new DatagramPacket(pm.getPayload().getBytes(),pm.getPayload().length(), pm.getHost(),pm.getPort());
        DataSocket.send(packet);
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    }
}

/**
* This method allocates a buffer with MAX_PACKET_LENGTH, receives the response
* from the Server (socket), and displays the response on the system output.
* size, the destination address and the destination port, and sends the packet.
     * @return 
     * @throws java.net.SocketTimeoutException
*/
public PingPacket receivePing() throws SocketTimeoutException
{
        Date d = new Date();
        byte[] buff = new byte[1024];
        PingPacket ping = null;
        try
        {
            DatagramPacket packet = new DatagramPacket(buff,1024)  ;
            DataSocket.receive(packet);
            System.out.println("Recieved packet from " + packet.getAddress() + " at port " + packet.getPort() + " " + d.toString() );
            ping = new PingPacket(packet.getAddress(), packet.getPort(),new String(packet.getData()) );   
        }
        catch(SocketTimeoutException ex)
        {
            throw ex;
        }
        catch(IOException ex)
        {
            System.out.println("recievePing..." + ex);
    }
    return ping;
}
}
