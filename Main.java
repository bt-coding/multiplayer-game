import javax.swing.*;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Game Window");
        Display screen = new Display();
        frame.add(screen);
        frame.setVisible(true);
        screen.draw();
    }
}