/*
 * Server finds one connection, Server finds second connection
 * Server sends host packet to first connection, first connection opens server socket
 * Server ip of first server to second server
 * Second server connects to first server
 * Two way socket connection is established
 */
import java.net.*;
import java.io.*;
public class ServerHost {
    public static void main(String[] args) throws Exception {
        Socket waitingSock = null;
        Socket newsock = null;
        boolean isWaiting = false;
        boolean online=true;
        ServerSocket serv = new ServerSocket(45454);
        while(online) {
            if (waitingSock == null) {
                Socket s = serv.accept();
            } else {
                
            }
            if (isWaiting) {
                //connect(waitingSock,s);
                //System.out.println("Server has matched " + waitingSock.getInetAddress() + " with " + s.getInetAddress().toString());
            } else {
                //waitingSock=(Socket)((Socket)s).clone();
                isWaiting=true;
                //System.out.println("Server received waiting player at IP: " + waitingIP.getInetAddress());
            }
        }
    }
    public static void connect(Socket s1, Socket s2) {
        //String primeip = ip1;
        //String secondip = ip2;
        
        
        
    }
}