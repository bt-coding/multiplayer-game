import java.net.*;
import java.io.*;
import java.util.*;
public class Client implements Runnable {
    double[] otherloc;
    BufferedReader din;
    PrintStream ps;
    Socket p2p;
    Socket matchmaking;
    Game game;
    public Client() throws IOException {
        Socket matchmaking = new Socket("71.115.226.213",45454);
        BufferedReader din = new BufferedReader(new InputStreamReader(matchmaking.getInputStream()));
        String role = din.readLine();
        if (role.equals("c")) {
            System.out.println("Assigned connector role");
            String otherIP = din.readLine();
            System.out.println("host IP is: " + otherIP);
            p2p = new Socket(otherIP.substring(1),50001);
            System.out.println("Successfully connected to host");
        } else if (role.equals("h")) {
            System.out.println("Assigned host role");
            ServerSocket host = new ServerSocket(50001);
            System.out.println("Waiting for connector to connect");
            p2p = host.accept();
            System.out.println("Connection from client received");
        }
    }
    public void setGame(Game g) {
        game=g;
    }
    public void run() {
        try {
            din = new BufferedReader(new InputStreamReader(p2p.getInputStream()));
            ps = new PrintStream(p2p.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                String recv = din.readLine();
                if (recv.toCharArray()[0]=='k') {
                    if (recv.toCharArray()[2]=='l') {
                        boolean value = false;
                        if (recv.toCharArray()[4]=='t') {
                            value=true;
                        }
                        game.setP2Left(value);
                    } else if (recv.toCharArray()[2]=='r') {
                        boolean value = false;
                        if (recv.toCharArray()[4]=='t') {
                            value=true;
                        }
                        game.setP2Right(value);
                    } else if (recv.toCharArray()[2]=='j') {
                        game.p2Jump();
                    }
                } else if (recv.toCharArray()[0]=='m') {
                    if (recv.toCharArray()[2]=='c') {
                        double[] mloc = new double[]{Double.parseDouble(recv.substring(4,recv.indexOf(","))),Double.parseDouble(recv.substring(recv.indexOf(",")+1))};
                        game.p2Shoot(mloc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void sendData(String dat) {
        ps.println(dat);
    }
    public static void main(String[] args) {
        //Used for isolated testing
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * SOCKET DATA FORMAT 
     * CATEGORY:TYPE:VALUE
     * categories - k(keyboard),m(mouse)
     * keyboard types - l(left), r(right), j(jump)
     * mouse types - c(click)
     * keyboard l/r values: t/f
     * keyborad j values: p
     * mouse c values: xloc,yloc
     */
}