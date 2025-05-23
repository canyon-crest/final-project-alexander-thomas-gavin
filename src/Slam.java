import java.awt.Color;
import java.util.ArrayList;

public class Slam extends Projectile{
	public Slam(double xLocation, double yLocation, int radius){
		super(xLocation,yLocation,0,radius,radius,2,15,35);
		
	}
	public double getXCenter() {
		return getX();
	}
	public double getYCenter() {
		return getY();
	}

	public boolean isInHitBox(Entity other){
        return(Math.sqrt(Math.pow(other.getXCenter()-getX(),2)+Math.pow(other.getYCenter()-getY(),2)) < getWidth()/2+(other.getWidth()+other.getHeight())/4);
    }
	
	public boolean registerHit() {
		if(getTimeLeft() < 16) {
			setColor(Color.RED);
		}
		if(getTimeLeft() != 15) {
			return false;
		}
		 ArrayList<Entity> nearbyEntities = checkHitboxes();
	        for (Entity e : nearbyEntities) {
	            if (e instanceof Player) {
	                Player p = (Player) e;
	                p.hurt();
	                if(p.getiFrames() <= 0) {
	                	p.takeDamage(getDamage());
	                	p.setRectVelocity(p.getXCenter()-getX(), p.getYCenter()-getY());
	                	p.setVelocity(p.getAngle(), 20/SCALE);
	                	p.setIFrames(6);
	                	p.stun(6);
	                	
	                }
	            }
	        }
	    return false;       
	            
	}
	public boolean tick() {
		setColor(Color.CYAN);
		return super.tick();
	}

}
