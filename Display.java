import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
public class Display extends JComponent{
    int[][] map;
    double yScale;
    double xScale;
    Game game;
    BufferedImage playerRight;
    BufferedImage playerLeft;
    BufferedImage ground;
    BufferedImage background;
    ArrayList<BufferedImage> annimateRightWalk;
    ArrayList<BufferedImage> annimateLeftWalk;
    int annimateCounter;
    public Display(Game g){
        game = g;
        map = g.getMap();
        yScale = 1070/map.length;
        xScale = 1920/map[0].length;
        game.setScales(new double[]{xScale,yScale});
        annimateCounter = 0;
        annimateRightWalk = new ArrayList<BufferedImage>();
        annimateLeftWalk = new ArrayList<BufferedImage>();
        try{
            playerRight = ImageIO.read(new File("Player-Right.png"));
            playerLeft = ImageIO.read(new File("Player-Left.png"));
            ground = ImageIO.read(new File("ground.png"));
            background = ImageIO.read(new File("background.png"));
            for(int count = 1; count <= 3; count++){
                try{
                    annimateRightWalk.add(ImageIO.read(new File("Player-Right-Walking("+count+").png")));
                    annimateLeftWalk.add(ImageIO.read(new File("Player-Left-Walking("+count+").png")));
                }
                catch(Exception e){
                    System.out.println(e+" walking annimation");
                }
            }
        }
        catch(Exception e){
            System.out.println(e+" loading textures");
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
        if(game.getRight() || game.getLeft()){
            annimateCounter = (annimateCounter+1)%20;
        }
        else{
            annimateCounter = 0;
        }
        double[] p1loc = game.getP1loc();
        double[] p2loc = game.getP2loc();
        if(game.getRight()){
            g.drawImage(annimateRightWalk.get((int)(annimateCounter/7)),(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        else if(game.getLeft()){
            g.drawImage(annimateLeftWalk.get((int)(annimateCounter/7)),(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        else if(MouseInfo.getPointerInfo().getLocation().x-((p1loc[0]+0.5)*xScale) > 0){
            g.drawImage(playerRight,(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        else{
            g.drawImage(playerLeft,(int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        }
        g.setColor(new Color(100,100,255));
        g.drawImage(playerRight,(int)(p2loc[0]*xScale),(int)(p2loc[1]*yScale),(int)xScale,(int)(2*yScale),this);
        //draws the health bar
        g.setColor(Color.BLACK);
        g.drawRect((int)(p1loc[0]*xScale-1),(int)(p1loc[1]*yScale-10),(int)(xScale+1),11);
        g.drawRect((int)(p2loc[0]*xScale-1),(int)(p2loc[1]*yScale-10),(int)(xScale+1),11);
        g.setColor(Color.RED);
        g.fillRect((int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale-9),(int)xScale,10);
        g.fillRect((int)(p2loc[0]*xScale),(int)(p2loc[1]*yScale-9),(int)xScale,10);
        g.setColor(Color.GREEN);
        g.fillRect((int)(p1loc[0]*xScale-1),(int)(p1loc[1]*yScale-9),(int)(xScale*(game.getPlayerHealth()/game.getMaxPlayerHealth())),10);
        g.setColor(Color.BLACK);
        g.fillRect((int)(p2loc[0]*xScale-1),(int)(p2loc[1]*yScale-9),(int)(xScale*(game.getP2Health()/game.getMaxPlayerHealth())),10);
        g.setColor(Color.YELLOW);
        for(Bullet b: game.getBullets()){
            g.fillRect((int)(b.getLoc()[0]*xScale),(int)(b.getLoc()[1]*yScale),(int)(xScale/4),(int)(yScale/4));
        }
        for(Bullet b: game.getP2Bullets()){
            g.fillRect((int)(b.getLoc()[0]*xScale),(int)(b.getLoc()[1]*yScale),(int)(xScale/4),(int)(yScale/4));
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
        g.drawString(game.getName()+": "+game.getScores()[0],20,50);
        g.drawString(game.getP2Name()+": "+game.getScores()[1],1700,50);
    }
}