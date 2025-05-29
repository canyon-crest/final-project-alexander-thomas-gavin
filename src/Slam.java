import java.awt.Color;
import java.util.ArrayList;

public class Slam extends Projectile{
	/**
	 *  constructor method for slam
	 * @param xLocation x of the slam
	 * @param yLocation y of the slam
	 * @param radius radius of the attack
	 */
	public Slam(double xLocation, double yLocation, int radius){
		super(xLocation,yLocation,0,radius,radius,2,15,35);
		
	}
	/**
	 *  method that gets the x center
	 * @return x center location
	 */
	public double getXCenter() {
		return getX();
	}
	/**
	 *  method that gets the y center
	 * @return y center location
	 */
	public double getYCenter() {
		return getY();
	}
	/**
	 *  method that gets if a player is in the hitbox
	 * @param other the entity to check if it's in the hitbox
	 * @return whether other is within the radius
	 */

	public boolean isInHitBox(Entity other){
        return(Math.sqrt(Math.pow(other.getXCenter()-getX(),2)+Math.pow(other.getYCenter()-getY(),2)) < getWidth()/2+(other.getWidth()+other.getHeight())/4);
    }
	/**
	 *  method that registers hits and deals damage to nearby players
	 * @return whether the attack should be deleted
	 */
	
	public boolean registerHit() {
		//gets the time that the attack should register
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
	/**
	 *  method that runs every frame
	 * @return whether the attack should be deleted
	 */
	public boolean tick() {
		setColor(Color.CYAN);
		return super.tick();
	}

}
