import java.awt.*;

public class Player extends Character{
	private int dashCooldown = 0;
	private int swordCooldown = 0;
	private int iFrames = 0;
	private int stun = 0;
	private double speedMult = 1;
	private double speedMultTime = 0;
	private int hurt = 0;
	public Player(double xLocation, double yLocation, double friction, int width, int height, int health) {
		super(xLocation, yLocation, friction, width, height, health);

	}
	public void changeVelocity(double direction, double amount){
		if(stun <= 0){
			setVelocity(direction,amount*speedMult);
		}
	}
	public void swordAttack(double direction, int width, int height){
		if(swordCooldown == 0){
			new Slash(getX()+getWidth()*Math.cos(direction)/2,getY()+getHeight()*Math.sin(direction)/2 ,
					width ,height,this,(int)((width*Math.cos(direction))/2+(getWidth()*Math.cos(direction))/2),
					(int)((height*Math.sin(direction))/2+(getHeight()*Math.sin(direction))/2));
			swordCooldown = 10;
			setSpeedMult(0.5,10);
		}
	}
	public void dash(double direction,double amount){
		if(dashCooldown == 0){
			setVelocity(direction, amount);
			dashCooldown = 80;
			iFrames = 15;
			if(stun < 18) {
				stun = 18;
			}
		}


	}
	public void hurt(){
		hurt = 1;
	}
	public void takeDamage(int amount){
		if(iFrames == 0) {
			super.takeDamage(amount);
		}
	}
	public void setIFrames(int amount){
		if(amount > iFrames){
			iFrames = amount;
		}

	}
	public int getiFrames(){
		return iFrames;
	}
	public void setSpeedMult(double amount, double time) {
		speedMult *= amount;
		if(speedMultTime < time) {
			speedMultTime = time;
		}
	}
	public void stun(int time){
		if(time > stun) {
			stun = time;
		}
	}
	public boolean tick(){
		super.tick();
		if(getHealth() == 0){
			destroy();
			return true;
		}
		if(hurt == 0) {
			setColor(Color.GREEN);
		}
		else{
			setColor(Color.ORANGE);
			hurt = 0;
		}
		if(speedMultTime <= 0) {
			speedMult = 1;
		}
		else {
			speedMultTime--;
		}
		if(dashCooldown > 0) {
			dashCooldown--;
		}
		if(swordCooldown > 0){
			swordCooldown--;
		}
		if(iFrames > 0){
			iFrames--;
		}
		if(stun > 0){
			stun--;
		}
		return false;
	}
}
