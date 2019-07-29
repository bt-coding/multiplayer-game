import java.awt.*;
import java.util.*;
public class Game{
    private int[][] map;
    private double[] p1loc;
    private double[] p2loc;
    private boolean right;
    private boolean left;
    private boolean grounded;
    private double yVelocity;
    private double gravity;
    private double[] scales;
    private ArrayList<Bullet> bullets;
    private final double[][] spawnPoints = new double[][]{{1,0},{14,0},{7,0}};
    private double bulletDamage;
    private double maxPlayerHealth;
    private double health;
    private boolean p2Right;
    private boolean p2Left;
    private boolean p2Grounded;
    private double p2YVelocity;
    private ArrayList<Bullet> p2Bullets;
    private double p2Health;
    private int[] scores;
    private String name;
    private String p2Name;
    public Game(int mn){
        map = getMap(mn);
        p1loc = new double[2];
        p2loc = new double[2];
        yVelocity = 0;
        gravity = 0.01;
        bullets = new ArrayList<Bullet>();
        bulletDamage = 10;
        maxPlayerHealth = 200;
        p2Bullets = new ArrayList<Bullet>();
        name = "Player";
        p2Name = "Player 2";
        scores = new int[2];
    }
    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }
    public void setP2Name(String n){
        p2Name = n;
    }
    public String getP2Name(){
        return p2Name;
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
    public void spawn(int playerNum){
        health = maxPlayerHealth;
        p1loc = new double[]{spawnPoints[playerNum][0],spawnPoints[playerNum][1]};
    }
    public boolean getRight(){
        return right;
    }
    public boolean getLeft(){
        return left;
    }
    public boolean getP2Left(){
        return p2Left;
    }
    public boolean getP2Right(){
        return p2Right;
    }
    public int checkBulletCollion(){
       Rectangle p2 = new Rectangle((int)(p2loc[0]*scales[0]),(int)(p2loc[1]*scales[1]),(int)scales[0],(int)(2*scales[1])); 
       int count = 0;
       for(int b = 0; b < bullets.size(); b++){
           Rectangle bullet = new Rectangle((int)(bullets.get(b).getLoc()[0]*scales[0]),(int)(bullets.get(b).getLoc()[1]*scales[1]),(int)(scales[0]/4),(int)(scales[1]/4));
           if(bullet.intersects(p2)){
               count++;
               bullets.remove(b);
           }
       }
       return count;
    }
    public int checkP2BulletCollion(){
        Rectangle p1 = new Rectangle((int)(p1loc[0]*scales[0]),(int)(p1loc[1]*scales[1]),(int)scales[0],(int)(2*scales[1]));
        int count = 0;
        for(int b = 0; b < p2Bullets.size(); b++){
            Rectangle bullet = new Rectangle((int)(p2Bullets.get(b).getLoc()[0]*scales[0]),(int)(p2Bullets.get(b).getLoc()[1]*scales[1]),(int)(scales[0]/4),(int)(scales[1]/4));
            if(bullet.intersects(p1)){
                count++;
                p2Bullets.remove(b);
            }
        }
        return count;
    }   
    public int[] getScores(){
        return scores;
    }
    public boolean isWalking(){
        if(left || right)
            return true;
        return false;
    }
    public void respawn(){
        scores[1] ++;
        health = maxPlayerHealth;
        int spawnPointNum = (int)(Math.random()*spawnPoints.length);
        p1loc = new double[]{spawnPoints[spawnPointNum][0],spawnPoints[spawnPointNum][1]};
        //System.out.println("X: "+p1loc[0] +" Y: "+p1loc[1]+" SpawnPointCords- X: "+spawnPoints[spawnPointNum][0]+" Y: "+spawnPoints[spawnPointNum][1]);
    }
    public void p2Respawn(){
        scores[0] ++;
        p2Health = maxPlayerHealth;
        int spawnPointNum = (int)(Math.random()*spawnPoints.length);
        p2loc = new double[]{spawnPoints[spawnPointNum][0],spawnPoints[spawnPointNum][1]};
    }
    public void dealDamage(double damage){
        health -= damage;
    }
    public void dealP2Damage(double damage){
        p2Health -= damage;
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
        if(p2Right && p2loc[0] < map[0].length-1){
            p2loc[0] += 0.1;
        }
        if(p2Left && p2loc[1] > 0){
            p2loc[0] -= 0.1;
        }
        if(p2Grounded){
            p2loc[1] += gravity;
            p2Grounded = p2Landed();
        }
        if(!p2Grounded){
            if(p2YVelocity > -0.2)
                p2YVelocity -= gravity;
            p2loc[1] -= p2YVelocity;
            if(yVelocity <= 0 && p2Landed()){
                p2Grounded = true;
            }
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
        for(int b = 0; b < bullets.size(); b++){
            bullets.get(b).move();
            if(bullets.get(b).getLoc()[0] < 0 || bullets.get(b).getLoc()[0] > 15 || bullets.get(b).getLoc()[1] < -3 || bullets.get(b).getLoc()[1] > 10){
                bullets.remove(b);
                b--;
            }
        }
        for(int b = 0; b < p2Bullets.size(); b++){
            p2Bullets.get(b).move();
            if(p2Bullets.get(b).getLoc()[0] < 0 || p2Bullets.get(b).getLoc()[0] > 15 || p2Bullets.get(b).getLoc()[1] < -3 || p2Bullets.get(b).getLoc()[1] > 10){
                p2Bullets.remove(b);
                b--;
            }
        }
        dealP2Damage(checkBulletCollion()*bulletDamage);
        dealDamage(checkP2BulletCollion()*bulletDamage);
        if(p2Health <= 0){
            p2Respawn();
        }
        if(health <= 0){
            respawn();
        }
    }
    public void shoot(){
        bullets.add(new Bullet(new double[]{p1loc[0]+0.5,p1loc[1]+1},getSlope()));
    }
    public void p2Shoot(double[] ml){
       p2Bullets.add(new Bullet(new double[]{p2loc[0]+0.5,p2loc[1]+1},getP2Slope(ml))); 
    }
    public double[] getSlope(){
        double h2 = 0.3;
        double[] p1 = new double[]{(p1loc[0]+0.5)*scales[0],(p1loc[1]+1)*scales[1]};
        double[] p2 = new double[]{MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y};
        //System.out.println(p1[0] + " " + p1[1]);
        double h1 = Math.sqrt(((p2[0]-p1[0])*(p2[0]-p1[0]))+((p2[1]-p1[1])*(p2[1]-p1[1])));
        double y1 = p2[1]-p1[1];
        double x1 = p2[0]-p1[0];
        return new double[]{h2/h1*x1,h2/h1*y1};
    }
    public double[] getP2Slope(double[] mouseLoc){
        double h2 = 0.3;
        double[] p1 = new double[]{(p2loc[0]+0.5)*scales[0],(p2loc[1]+1)*scales[1]};
        //System.out.println(p1[0] + " " + p1[1]);
        double h1 = Math.sqrt(((mouseLoc[0]-p1[0])*(mouseLoc[0]-p1[0]))+((mouseLoc[1]-p1[1])*(mouseLoc[1]-p1[1])));
        double y1 = mouseLoc[1]-p1[1];
        double x1 = mouseLoc[0]-p1[0];
        return new double[]{h2/h1*x1,h2/h1*y1};
    }
    public double getPlayerHealth(){
        return health;
    }
    public double getP2Health(){
        return p2Health;
    }
    public double getMaxPlayerHealth(){
        return maxPlayerHealth;
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
    public boolean p2Landed(){
        Rectangle player = new Rectangle((int)(p2loc[0]*scales[0]),(int)((p2loc[1]*scales[1])+(scales[1]*2)),(int)(scales[0]),1);
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                if(map[y][x] == 1){
                    Rectangle block = new Rectangle((int)(x*scales[0]),(int)(y*scales[1]),(int)scales[0],(int)scales[1]);
                    if(player.intersects(block)){
                        p2loc[1] = (block.getY()-(scales[1]*2))/scales[1];
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
    public void p2Jump(){
        if(p2Grounded){
            p2YVelocity = 0.03;
            grounded = false;
        }
    }
    public void setP2Right(boolean r){
        p2Right = r;
    }
    public void setP2Left(boolean l){
        p2Left = l;
    }
    public void setLeft(boolean l){
        left = l;
    }
    public void setRight(boolean r){
        right = r;
    }
}