import javax.swing.*;
import java.awt.*;
import java.io.*;
public class Main{
    public static void main(String[] args){
        Client connection = null;
        try {
            connection = new Client();
        } catch (IOException e) {
            System.out.println("Fatal error establishing network session, exiting");
            e.printStackTrace();
            System.exit(1);
        }
        JFrame frame = new JFrame("Game Window");
        Game game = new Game(0,connection,frame);
        connection.setGame(game);
        (new Thread(connection)).start();
        Display screen = new Display(game);
        KeyboardThread keyboard = new KeyboardThread(game,connection);
        Mouse mouse = new Mouse(game);
        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.add(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setBounds(0,0,1920,1080);
        screen.draw();
        game.spawn(1);
        while(true){
            try{
                Thread.sleep(1000/60);
            }
            catch(Exception e){
                System.out.println(e + " main class");
            }   
            game.update();
            screen.draw();
        }
    }
}