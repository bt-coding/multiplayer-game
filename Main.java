import javax.swing.*;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Game Window");
        Game game = new Game(0);
        Display screen = new Display(game);
        KeyboardThread keyboard = new KeyboardThread(game);
        Mouse mouse = new Mouse(game);
        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.add(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(0,0,1920,1080);
        screen.draw();
        game.spawn(1);
        while(true){
            try{
                Thread.sleep(1000/60);
                game.update();
                screen.draw();
                game.dealDamage(0.5);
            }
            catch(Exception e){
                System.out.println(e);
            }   
        }
    }
}