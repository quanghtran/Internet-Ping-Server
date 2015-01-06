
import java.net.InetAddress;
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
public class UDPClient extends UDPPinger implements Runnable
{
    static final int NUM_PINGS = 10;
    static final int TIMEOUT = 1000; 
    static final int REPLY_TIMEOUT = 5000; 
    static boolean[] replies = new boolean[NUM_PINGS]; 
    static long[] rtt = new long[NUM_PINGS]; 
    static final private int port = 8888;
    static final private String destinationHost = "137.104.120.197";
    private int numReplies = 0;
    private static String local = "localhost";
/**
 * 
 * @param host
 * @param port 
 */
public UDPClient(String host, int port) //constructor
{
    super(host,port);
}
/**
 * 
 * @param arg 
 */
public static void main(String arg[])
{
    try
    {
        UDPClient client = new UDPClient(local, port);
        client.run();
    }
    catch(Exception ex)
    {
    
    }
}
/**
 * 
 */
@Override
public void run()
{
    try
    {
        createSocket();
        DataSocket.setSoTimeout(TIMEOUT);
        for(int i = 0; i < 10; i++)
        {
            Date d = new Date();
            String PayLoad = "PING " + i + " " + d.getTime();
            PingPacket ping = new PingPacket(
                         InetAddress.getByName(destinationHost), 5976, PayLoad);
            sendPing(ping);
            try
            {
                PingPacket recieve = receivePing();
                handleRTT(recieve.getPayload());
            }
            catch( Exception e)
            {
                System.out.println("recievePing..." + e);
            }
        }
        if (numReplies < NUM_PINGS)
            DataSocket.setSoTimeout(REPLY_TIMEOUT);
        printStatistics();
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    }
} 
/**
* Given the payload of a UDP packet, this helper method calculates the RTT,
* and uses the ping number as the index to store the RTT in the array.
* RTT = (current timestamp) – (previous timestamp when sending the packet)
* A counter is used to keep track of the number of valid replies from the
* Server.
*/
private void handleRTT(String payload)
{
    Date d = new Date();
    int index = Integer.parseInt(payload.split(" ")[1]);
    String t = payload.split(" ")[2];
    long temp = Long.parseLong(t.trim());
    Long RoundTripTime = d.getTime() - temp;
    rtt[index] = RoundTripTime;
    replies[index] = true; 
    numReplies++;
}
/**
 * 
 */
private void printStatistics()
{
    long sum = 0;
    long minimum = 1000;
    long maximum = 0;
    for(int i = 0; i < 10; i++)
    {
        if(replies[i] ==  false)
        {
            rtt[i] = 1000;
            System.out.println("PING " + i + " false RTT: " + rtt[i] );
        }
        else
        {
            System.out.println("PING " + i + " true RTT: " + rtt[i] );  
        }
        if (rtt[i] <= minimum )
            minimum = rtt[i];
        if(rtt[i] >= maximum)
            maximum = rtt[i];
       sum += rtt[i];
    }
    long ave = sum / 10;
    System.out.println("Minumum = " + minimum + "ms, Maximum = " 
                                      + maximum + "ms, Average = " + ave +"ms");
}
}