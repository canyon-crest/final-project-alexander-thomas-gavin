
public abstract class Projectile extends Entity{
    private int damage;
    private int timeLeft;
    public Projectile(double xLocation, double yLocation, double friction, int width, int height, int damages,int damage,int timeLeft){
        super(xLocation,yLocation,friction,width,height,damages);
        this.damage = damage;
        this.timeLeft = timeLeft;
    }
    public boolean tick(){
        super.tick();
        if(registerHit()){
            return true;
        }
        timeLeft--;
        if(timeLeft == 0){
            destroy();
            return true;
        }
        return false;
    }
    public void setTimeLeft(int timeLeft) {
    	this.timeLeft = timeLeft;
    }
    public int getDamage(){
        return damage;
    }
    public int getTimeLeft(){
        return  timeLeft;
    }
    public abstract boolean registerHit();
}
