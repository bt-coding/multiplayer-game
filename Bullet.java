public class Bullet{
    private double[] loc;
    private double[] slope;
    public Bullet(double[] l, double[] s){
        loc = l;
        slope = s;
    }
    public void move(){
        loc[0] += slope[0];
        loc[1] += slope[1];
    }
    public double[] getLoc(){
        return loc;
    }
}