import java.awt.*;

public class Indicator extends Projectile{
    /**
     *  constructor for indicator
     * @param xLocation x location of the indicator
     * @param yLocation y location of the indicator
     * @param width width of the indicator
     * @param height height of the indicator
     * @param timeLeft time left of the indicator
     */
    public Indicator(double xLocation, double yLocation, int width, int height,int timeLeft){
        super(xLocation,yLocation,0,width,height,0,0,timeLeft);

    }
    /**
     *  method that gets all entities damaged (none)
     * @return always returns false
     */
    public boolean registerHit() {
        return false;
    }
    /**
     *  method that runs every frame
     * @return whether the indicator is deleted
     */
    public boolean tick(){
        this.setColor(Color.PINK);
        setVelocity(0,0);
        return super.tick();
    }
}
