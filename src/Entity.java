import java.util.ArrayList;

public class Entity {
	private double xLocation;
	private double yLocation;
	private double friction;
	private double[] velocity;
	private	int width;
	private int height;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void move(int x, int y) {
		
		
	}
	public double getX() {
		return xLocation;
		
	}
	public double getY() {
		return yLocation;
	}
	public double getXVel() {
		return Math.cos(velocity[0]) * velocity[1];
	}
	public double getYVel() {
		return Math.sin(velocity[0]) * velocity[1];
		
	}
	public double getVelocity() {
		return friction;
		
	}
	public double getAngle() {
		return velocity[0];
	}
	public void setVelocity(double angle, double magnitude) {
		velocity[0] = angle;
		velocity[1] = magnitude;
	}
	public void addVelocity(double angle, double magnitude) {
		
	}
	public void tick() {
		
	}
	public ArrayList<Entity> getAllEntities() {
		return entities;
		
		
	}
	public void checkHitboxes() {
		
	}
	public void removeEntity() {
		
	}
	
}
