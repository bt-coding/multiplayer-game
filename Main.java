import javax.swing.*;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Game Window");
        Game game = new Game(0);
        Display screen = new Display(game);
        KeyboardThread keyboard = new KeyboardThread(game);
        frame.addKeyListener(keyboard);
        frame.add(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(0,0,1920,1080);
        screen.draw();
        while(true){
            try{
                Thread.sleep(1000/60);
                game.update();
                screen.draw();
            }
            catch(Exception e){
                System.out.println(e);
            }   
        }
    }
}