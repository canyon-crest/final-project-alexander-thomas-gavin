
public abstract class Projectile extends Entity{
    private int damage;
    private int timeLeft;
    /**
     *  constructor method for a projectile
     * @param xLocation x of the projectile
     * @param yLocation y of the projectile
     * @param friction friction of the projectile
     * @param width width of the projectile
     * @param height height of the projectile
     * @param damages what the projectile damages
     * @param damage amount of damage
     * @param timeLeft time left
     */
    public Projectile(double xLocation, double yLocation, double friction, int width, int height, int damages,int damage,int timeLeft){
        super(xLocation,yLocation,friction,width,height,damages);
        this.damage = damage;
        this.timeLeft = timeLeft;
    }
    /**
     *  method that runs every frame
     * @return whether the projectile is destroyed
     */
    public boolean tick(){
        //checks if the projectile hits something
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
    /**
     *  method that sets the time left
     * @param timeLeft new time left
     */
    public void setTimeLeft(int timeLeft) {
    	this.timeLeft = timeLeft;
    }
    /**
     *  method that gets the damage
     * @return damage value
     */
    public int getDamage(){
        return damage;
    }
    /**
     *  method gets time left
     * @return time left
     */
    public int getTimeLeft(){
        return  timeLeft;
    }
    /**
     *  method that registers hits on nearby entities
     */
    public abstract boolean registerHit();
}
