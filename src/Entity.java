import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Entity {
	public static final int SCALE = TitleScreen.SCALE;
	private double xLocation;
	private double yLocation;
	private double friction;
	private double[] velocity;
	private	int width;
	private int height;
	private int damages;
	private Color color;
	
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	/**
	 *  constructor method for the entity class
	 * @param xLocation x of the entity
	 * @param yLocation y of the entity
	 * @param friction friction of the entity
	 * @param width width of the entity
	 * @param height height of the entity
	 * @param damages what the entity damages
	 */
	public Entity(double xLocation, double yLocation, double friction, int width, int height, int damages){
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.friction = friction;
		this.width = width;
		this.height = height;
		this.velocity = new double[]{0,0};
		this.damages = damages;
		entities.add(this);
		color = Color.CYAN;
	}
	/**
	 *  moves the player to another location
	 * @param xLocation the new x
	 * @param yLocation the new y
	 */
	public void move(double xLocation, double yLocation) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		
	}
	/**
	 *  method that gets the x
	 * @return x location
	 */
	public double getX() {
		return xLocation;
		
	}
	/**
	 *  method that gets y
	 * @return y location
	 */
	public double getY() {
		return yLocation;
	}
	/**
	 *  method that gets x but centered
	 * @return x location centered
	 */
	public double getXCenter() {
		return xLocation + width/2;
	}
	/**
	 *  method that gets y but centered
	 * @return y location centered
	 */
	public double getYCenter() {
		return yLocation + height/2;
	}
	/**
	 *  method that sets centered location
	 * @param x new x location of center
	 * @param y new y location of center
	 */
	public void moveCentered(double x, double y){
		move(x-(double)getWidth()/2,y-(double)getHeight()/2);
	}
	/**
	 *  method that gets x velocity
	 * @return x velocity
	 */
	public double getXVel() {
		return Math.cos(velocity[0]) * velocity[1];
	}
	/**
	 *  method that gets y velocity
	 * @return y velocity
	 */
	public double getYVel() {
		return Math.sin(velocity[0]) * velocity[1];
		
	}
	/**
	 *  method that gets width of the entity
	 * @return width of the entity
	 */
	public int getWidth(){
		return width;
	}
	/**
	 *  method that gets height of the entity
	 * @return height of the entity
	 */
	public int getHeight(){
		return height;
	}
	/**
	 *  method that returns angle of velocity
	 * @return angle of the velocity of the entity
	 */
	public double getAngle() {
		return velocity[0];
	}
	/**
	 *  method that returns magnitude of velocity
	 * @return magnitude of the velocity
	 */
	public double getMagnitude(){
		return velocity[1];
	}
	/**
	 *  method that sets the velocity of the entity
	 * @param angle angle of the velocity
	 * @param magnitude magnitude of the velocity
	 */
	public void setVelocity(double angle, double magnitude) {
		velocity[0] = angle;
		velocity[1] = Math.abs(magnitude);
		if(magnitude < 0){
			velocity[0] += Math.PI;
		}
		while(velocity[0] > 2*Math.PI){
			velocity[0] -= 2*Math.PI;
		}
		while(velocity[0] < 0){
			velocity[0] += 2*Math.PI;
		}
	}
	/**
	 *  method that adds velocity
	 * @param angle angle of the velocity to add
	 * @param magnitude magnitude of the velocity to add
	 */
	public void addVelocity(double angle, double magnitude) {
		double newX = getXVel()+Math.cos(angle)*magnitude;
		double newY = getYVel()+Math.sin(angle)*magnitude;
		if(-0.001 < newX && newX < 0.001){
			newX = 0.0001;
		}
		velocity[0] = Math.atan(newY/newX);
		if(newX < 0 || newY < 0){
			velocity[0] += Math.PI;
		}
		if(newX >= 0 && newY < 0){
			velocity[0] += Math.PI;
		}
		velocity[1] = Math.sqrt(Math.pow(newX,2)+Math.pow(newY,2));


	}
	/**
	 *  method that sets the rectangular velocity
	 * @param x x component of the velocity
	 * @param y y component of the velocity
	 */
	public void setRectVelocity(double x, double y){
		if(-0.001 < x && x < 0.001){
			x = 0.0001;
		}
		velocity[0] = Math.atan(y/x);
		if(x < 0 || y < 0){
			velocity[0] += Math.PI;
		}
		if(x >= 0 && y < 0){
			velocity[0] += Math.PI;
		}
		velocity[1] = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	/**
	 *  method sets the color
	 * @param color the color to set the entity's hitbox to
	 */
	public void setColor(Color color){
		this.color = color;
	}
	/**
	 *  method gets the color of the hitbox
	 * @return color of the hitbox
	 */
	public Color getColor(){
		return color;
	}
	/**
	 *  method that runs every frame
	 * @return whether the entity is deleted
	 */
	public boolean tick() {
		//manages velocities

		if(velocity[1] != 0){
			move(xLocation+getXVel(),yLocation+getYVel());
		}
		if(velocity[1] > 0) {
			addVelocity(velocity[0], -1 * friction);
		}
		if(velocity[1] <= friction) {
			velocity[1] = 0;
		}

		return false;
	}
	/**
	 *  method that gets all entities
	 * @return arraylist of all entities
	 */
	public static ArrayList<Entity> getAllEntities() {
		return entities;
		
		
	}
	/**
	 *  method that checks to see other entities in the hitbox
	 * @return arraylist of all entities in the hitbox
	 */
	public ArrayList<Entity> checkHitboxes() {
		ArrayList<Entity> nearbyEntities = new ArrayList<Entity>();
		for(Entity e: entities){
			if(isInHitBox(e)){
				nearbyEntities.add(e);
			}
		}
		return nearbyEntities;
	}
	/**
	 *  method that removes an entity
	 * @param entity entity to remove
	 */
	public static void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	/**
	 *  method that deletes all entities
	 */
	public static void clearEntities() {
		entities = new ArrayList<Entity>();
	}
	/**
	 *  method that deletes this entity
	 */
	public void destroy(){
		entities.remove(this);
	}
	/**
	 *  method that checks if another entity is in the hitbox
	 * @param other the other entity to check
	 */
	public boolean isInHitBox(Entity other) {
		boolean xCorrect = false;
		boolean yCorrect = false;
		if(other.getX()>=getX()) {
			xCorrect = other.getX()<=getX()+getWidth();
		}
		else if(other.getX()<getX()) {
			xCorrect = other.getX()+other.getWidth()>=getX();
		}
		if(other.getY()>=getY()) {
			yCorrect = other.getY()<=getY()+getHeight();
		}
		else if(other.getY()<getY()) {
			yCorrect = other.getY()+other.getHeight()>=getY();
		}
		return xCorrect && yCorrect;
		
	}
	
}
