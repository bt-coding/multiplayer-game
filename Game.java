import java.awt.*;
import java.util.*;
public class Game{
    private int[][] map;
    private double[] p1loc;
    private double[] p2loc;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean grounded;
    private double yVelocity;
    private double gravity;
    private double[] scales;
    private ArrayList<Bullet> bullets;
    public Game(int mn){
        map = getMap(mn);
        p1loc = new double[2];
        p2loc = new double[2];
        yVelocity = 0;
        gravity = 0.01;
        bullets = new ArrayList<Bullet>();
    }
    public void setScales(double[] s){
        scales = s;
    }
    public int[][] getMap(int mapNum){
        if(mapNum == 0){
            return new int[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {1,1,1,0,0,0,0,0,0,0,0,0,1,1,1},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
                               
        }
        return new int[10][15];
    }
    public double[] getP1loc(){
        return p1loc;
    }
    public double[] getP2loc(){
        return p2loc;
    }
    public int[][] getMap(){
        return map;
    }
    public void update(){
        if(right && p1loc[0] < map[0].length-1){
            p1loc[0] += 0.1;
        }
        if(left && p1loc[0] > 0){
            p1loc[0] -= 0.1;
        }
        if(grounded){
            p1loc[1] += gravity;
            grounded = landed();
        }
        if(!grounded){
            if(yVelocity > -0.2)
                yVelocity -= gravity;
            p1loc[1] -= yVelocity;
            if(yVelocity <= 0 && landed()){
                grounded = true;
            }
        }
        for(Bullet b: bullets){
            b.move();
            if(b.getLoc()[0] < 0 || b.getLoc()[0] > 15 || b.getLoc()[1] < 0 || b.getLoc()[1] > 10){
                bullets.remove(b);
            }
        }
    }
    public void shoot(){
        bullets.add(new Bullet(new double[]{p1loc[0]+0.5,p1loc[1]+1},getSlope()));
    }
    public double[] getSlope(){
        double h2 = 0.5;
        double[] p1 = new double[]{(p1loc[0]+0.5)*scales[0],(p1loc[1]+1)*scales[1]};
        double[] p2 = new double[]{MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y};
        System.out.println(p1[0] + " " + p1[1]);
        double h1 = Math.sqrt(((p2[0]-p1[0])*(p2[0]-p1[0]))+((p2[1]-p1[1])*(p2[1]-p1[1])));
        double y1 = p2[1]-p1[1];
        double x1 = p2[0]-p1[0];
        return new double[]{h2/h1*x1,h2/h1*y1};
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    public boolean landed(){
        Rectangle player = new Rectangle((int)(p1loc[0]*scales[0]),(int)((p1loc[1]*scales[1])+(scales[1]*2)),(int)(scales[0]),1);
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                if(map[y][x] == 1){
                    Rectangle block = new Rectangle((int)(x*scales[0]),(int)(y*scales[1]),(int)scales[0],(int)scales[1]);
                    if(player.intersects(block)){
                        p1loc[1] = (block.getY()-(scales[1]*2))/scales[1];
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void jump(){
        if(grounded){
            yVelocity = 0.3;
            grounded = false;
        }
    }
    public void setLeft(boolean l){
        left = l;
    }
    public void setRight(boolean r){
        right = r;
    }
}