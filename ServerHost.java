import java.net.*;
import java.io.*;
public class ServerHost {
    public static void main(String[] args) throws Exception {
        String waitingIP = "";
        boolean isWaiting = false;
        boolean online=true;
        ServerSocket serv = new ServerSocket(45454);
        while(online) {
            Socket s = serv.accept();
            if (isWaiting) {
                connect(waitingIP,s.getInetAddress().toString());
                System.out.println("Server has matched " + waitingIP + " with " + s.getInetAddress().toString());
            } else {
                waitingIP=s.getInetAddress().toString();
                isWaiting=true;
                System.out.println("Server received waiting player at IP: " + waitingIP);
            }
        }
    }
    public static void connect(String ip1, String ip2) {

        
    }
}