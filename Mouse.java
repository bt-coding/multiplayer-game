import java.awt.event.*;
public class Mouse implements MouseListener{
    Game game;
    public Mouse(Game g){
        game = g;
    }
    public void mousePressed(MouseEvent e){
         game.shoot();
    }
    public void mouseReleased(MouseEvent e){
        
    }
    public void mouseClicked(MouseEvent e){
    
    }
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
    
    }
}