import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Display extends JComponent{
    int[][] map;
    double yScale;
    double xScale;
    Game game;
    BufferedImage playerRight;
    BufferedImage playerLeft;
    BufferedImage ground;
    BufferedImage background;
    public Display(Game g){
        game = g;
        map = g.getMap();
        yScale = 1070/map.length;
        xScale = 1920/map[0].length;
        game.setScales(new double[]{xScale,yScale});
        try{
            playerRight = ImageIO.read(new File("Player-Right.png"));
            playerLeft = ImageIO.read(new File("Player-Left.png"));
            ground = ImageIO.read(new File("ground.png"));
            background = ImageIO.read(new File("background.png"));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void draw(){
        super.repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,1920,1080,this);
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                if(map[y][x] == 1){
                    g.drawImage(ground,(int)(x*xScale),(int)(y*yScale),(int)xScale,(int)yScale,this);
                }
            }
        }
        double[] p1loc = game.getP1loc();
        double[] p2loc = game.getP2loc();
        if(MouseInfo.getPointerInfo().getLocation().x-((p1loc[0]+0.5)*xScale) > 0){
            g.drawImage(playerRight,(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        else{
            g.drawImage(playerLeft,(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        g.setColor(new Color(100,100,255));
        g.fillRect((int)(p2loc[0]*xScale),(int)(p2loc[1]*yScale),(int)xScale,(int)(2*yScale));
        g.setColor(Color.YELLOW);
        for(Bullet b: game.getBullets()){
            g.fillRect((int)(b.getLoc()[0]*xScale),(int)(b.getLoc()[1]*yScale),(int)(xScale/4),(int)(yScale/4));
        }
    }
}