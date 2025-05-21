import java.awt.*;

public class Indicator extends Projectile{
    public Indicator(double xLocation, double yLocation, int width, int height,int timeLeft){
        super(xLocation,yLocation,0,width,height,0,0,timeLeft);

    }
    public boolean registerHit() {
        return false;
    }
    public boolean tick(){
        this.setColor(Color.PINK);
        setVelocity(0,0);
        return super.tick();
    }
}
