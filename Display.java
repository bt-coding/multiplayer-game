import javax.swing.*;
import java.awt.*;
public class Display extends JComponent{
    int[][] map;
    double yScale;
    double xScale;
    Game game;
    public Display(Game g){
        game = g;
        map = g.getMap();
        yScale = 1070/map.length;
        xScale = 1920/map[0].length;
        game.setScales(new double[]{xScale,yScale});
    }
    public void draw(){
        super.repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                if(map[y][x] == 1){
                    g.fillRect((int)(x*xScale),(int)(y*yScale),(int)xScale,(int)yScale);
                }
            }
        }
        g.setColor(Color.BLUE);
        double[] p1loc = game.getP1loc();
        double[] p2loc = game.getP2loc();
        g.fillRect((int)(p1loc[0]*xScale),(int)(p1loc[1]*yScale),(int)xScale,(int)(2*yScale));
        g.setColor(new Color(100,100,255));
        g.fillRect((int)(p2loc[0]*xScale),(int)(p2loc[1]*yScale),(int)xScale,(int)(2*yScale));
        g.setColor(Color.YELLOW);
        for(Bullet b: game.getBullets()){
            g.fillRect((int)(b.getLoc()[0]*xScale),(int)(b.getLoc()[1]*yScale),(int)(xScale/4),(int)(yScale/4));
        }
    }
}