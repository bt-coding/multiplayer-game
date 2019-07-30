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
    public static void main(String[] args) throws IOException {
        String otherIP = "";
        boolean isWaiting = false;
        BufferedReader din = null;
        PrintStream ps = null;
        ServerSocket serv = new ServerSocket(45454);
        Socket waitinghost = null;
        while(true) {
            Socket s = null;
            if (isWaiting) {
                s = serv.accept();
            } else {
                waitinghost=serv.accept();
                s=waitinghost;
            }
            din = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
            System.out.println("server has received a connection from " + s.getInetAddress());
            boolean tempfix=true;
            if (isWaiting) {
                if (waitinghost.getInetAddress().isReachable(5) || tempfix) {
                    System.out.println("both connections online, transmitting connection data");
                    ps.println("c");
                    ps.println(otherIP);
                    isWaiting=false;
                } else {
                    System.out.println("Waiting host disconnected, though a new host has connected");
                    ps.println("h");
                    otherIP=s.getInetAddress().toString();
                }
            } else {
                ps.println("h");
                otherIP=s.getInetAddress().toString();
                isWaiting=true;
            }
        }
    }
}