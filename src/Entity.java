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
	
	public void move(double xLocation, double yLocation) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		
	}
	public double getX() {
		return xLocation;
		
	}
	public double getY() {
		return yLocation;
	}
	public double getXCenter() {
		return xLocation + width/2;
	}
	public double getYCenter() {
		return yLocation + height/2;
	}
	public void moveCentered(double x, double y){
		move(x-(double)getWidth()/2,y-(double)getHeight()/2);
	}
	public double getXVel() {
		return Math.cos(velocity[0]) * velocity[1];
	}
	public double getYVel() {
		return Math.sin(velocity[0]) * velocity[1];
		
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public double getAngle() {
		return velocity[0];
	}
	public double getMagnitude(){
		return velocity[1];
	}
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
	public void setColor(Color color){
		this.color = color;
	}
	public Color getColor(){
		return color;
	}
	public boolean tick() {

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
	public static ArrayList<Entity> getAllEntities() {
		return entities;
		
		
	}
	public ArrayList<Entity> checkHitboxes() {
		ArrayList<Entity> nearbyEntities = new ArrayList<Entity>();
		for(Entity e: entities){
			if(isInHitBox(e)){
				nearbyEntities.add(e);
			}
		}
		return nearbyEntities;
	}
	public static void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	public void destroy(){
		entities.remove(this);
	}
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
			yCorrect = other.getY()<=getY()+getWidth();
		}
		else if(other.getY()<getY()) {
			yCorrect = other.getY()+other.getWidth()>=getY();
		}
		return xCorrect && yCorrect;
		
	}
	
}
