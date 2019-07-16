import java.awt.*;
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
    public Game(int mn){
        map = getMap(mn);
        p1loc = new double[2];
        p2loc = new double[2];
        yVelocity = 0;
        gravity = 0.01;
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
            if(yVelocity > -0.1)
                yVelocity -= gravity;
            System.out.println(yVelocity+" - "+p1loc[1]);
            p1loc[1] -= yVelocity;
            if(yVelocity <= 0 && landed()){
                grounded = true;
            }
        }
    }
    public boolean landed(){
        Rectangle player = new Rectangle((int)(p1loc[0]*scales[0]),(int)((p1loc[1]*scales[1])+(scales[1]*2)),(int)(scales[0]),1);
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                if(map[y][x] == 1){
                    Rectangle block = new Rectangle((int)(x*scales[0]),(int)(y*scales[1]),(int)scales[0],(int)scales[1]);
                    if(player.intersects(block)){
                        p1loc[1] = (block.getY()-(scales[1]*2))/scales[1];
                        System.out.println(p1loc[1]);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void jump(){
        System.out.println(grounded);
        if(grounded){
            yVelocity = 0.3;
            grounded = false;
        }
    }
    public void setLeft(boolean l){
        System.out.println("left");
        left = l;
    }
    public void setRight(boolean r){
        System.out.println("Right");
        right = r;
    }
}