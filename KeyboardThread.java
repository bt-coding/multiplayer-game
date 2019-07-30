import java.awt.event.*;
public class KeyboardThread extends KeyAdapter{
    Game game;
    Client connection;
    public KeyboardThread(Game g, Client c){
        game = g;
        connection = c;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE){
            if (game.getGrounded()) {
                game.jump();
                connection.sendData("k:j:p");
            }
        }
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            if (!game.getRight()) {
                game.setRight(true);
                connection.sendData("k:r:t");
            }
        }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
            
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            if (!game.getLeft()) {
                game.setLeft(true);
                connection.sendData("k:l:t");
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            game.setRight(false);
            connection.sendData("k:r:f");
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            game.setLeft(false);
            connection.sendData("k:l:f");
        }
    }
}