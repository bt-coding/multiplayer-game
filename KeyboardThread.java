import java.awt.event.*;
public class KeyboardThread extends KeyAdapter{
    Game game;
    public KeyboardThread(Game g){
        game = g;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE){
            game.jump();
        }
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            game.setRight(true);
        }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
            
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            game.setLeft(true);
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            game.setRight(false);
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            game.setLeft(false);
        }
    }
}