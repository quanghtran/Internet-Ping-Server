/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tranq
 */
import java.io.*;
import java.net.*;
import java.util.*; 
public class PingServer 
{
    private static final double LOSS_RATE = 0.3;
    private static final int DOUBLE = 2;
    private static final int AVERAGE_DELAY = 100; //millisecons
    private static final int PACKET_LENGTH = 1024;
    public static void main(String[] args)
    {
        try
        {
            PingServer server = new PingServer();
            server.run();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
//...
    public void run()
    {
        DatagramSocket serverSocket = null;
        System.out.println("Ping Server running....");
        try
        {
            Random random = new Random(new Date().getTime());
            serverSocket = new DatagramSocket(5976);
        while (true)
        {
            try
            {
                System.out.println("Waiting for UDPpacket....");
                byte[] buff = new byte[PACKET_LENGTH];
                DatagramPacket packet = new DatagramPacket(buff, PACKET_LENGTH);
                //write codes for receiving the UDP packet
                //write codes for displaying the packet content on the system output 
                serverSocket.receive(packet);
                System.out.println("Received packet from " + packet.getAddress() + " " + new String(packet.getData()));
                             
                if ( random.nextDouble() < LOSS_RATE ) //simulate packet loss
                {
                    System.out.println("Packet loss...., reply not sent.");
                    continue;
                }
                //simulate network delay
                Thread.sleep((int)(random.nextDouble() * DOUBLE * AVERAGE_DELAY));
                //write codes for making packet and sending the packet
                //write codes for displaying the packet sent message on the system output
                DatagramPacket sendPacket = new DatagramPacket(buff,buff.length,  packet.getAddress(), packet.getPort() );
                serverSocket.send(sendPacket);
                System.out.println("Replied Packet");
                
            }
            catch (IOException e)
            {
                System.out.println("Datagram packeterror...." + e);
            }
            catch (InterruptedException e)
            {
                continue;
            }
        } //end while
    }
    catch (SocketException e)
    {
        System.out.print("DatagramSocket error..."+ e);
    }
    serverSocket.close(); 
}
}