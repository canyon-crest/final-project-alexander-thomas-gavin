import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Character{
	public static final int SCALE = TitleScreen.SCALE;
	public static final int W = 96;
	public static final int H = 96;
	
	private int dashCooldown = 0;
	private int swordCooldown = 0;
	private int iFrames = 0;
	private int stun = 0;
	private double speedMult = 1;
	private double speedMultTime = 0;
	final String PLAYERU = "images/player/PlayerU.png";
	final String PLAYERDUR = "images/player/PlayerDUR.png";
	final String PLAYERR = "images/player/PlayerR.png";
	final String PLAYERDDR = "images/player/PlayerDDR.png";
	final String PLAYERD = "images/player/PlayerD.png";
	final String PLAYERDDL = "images/player/PlayerDDL.png";
	final String PLAYERL = "images/player/PlayerL.png";
	final String PLAYERDUL = "images/player/PlayerDUL.png";
	private Image U;
	private Image DUR;
	private Image R;
	private Image DDR;
	private Image D;
	private Image DDL;
	private Image L;
	private Image DUL;
	
	
	private int hurt = 0;
	private int regen = 60;
	public Player(double xLocation, double yLocation, double friction, int width, int height, int health) {
		super(xLocation, yLocation, friction, width, height, health);
		loadImages();
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
		if(regen<=0){
			super.takeDamage(-1);
			regen = 60;
		}
		else{
			regen--;
		}
		return false;
	}
	public Image getImage() {
		if ( Math.PI/8 <= getAngle() && getAngle() <= Math.PI/4+Math.PI/8) {
			return DDR;
		}
		else if (Math.PI/4 > getAngle() && -Math.PI/4 < getAngle()) {
			return R;
		}
		
		return D;
		
		//pi/8 intervals
	}
	  private void loadImages(){
	    	try{
	            U = ImageIO.read(ArenaPanel.class.getResource(PLAYERU)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            U = null;
	        }
	    	try{
	            DUR = ImageIO.read(ArenaPanel.class.getResource(PLAYERU)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            DUR = null;
	        }
	    	try{
	            R = ImageIO.read(ArenaPanel.class.getResource(PLAYERR)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            R = null;
	        }
	    	try{
	            DDR = ImageIO.read(ArenaPanel.class.getResource(PLAYERDDR)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            DDR = null;
	        }
	    	try{
	            D = ImageIO.read(ArenaPanel.class.getResource(PLAYERD)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            D = null;
	        }
	    	try{
	            DDL = ImageIO.read(ArenaPanel.class.getResource(PLAYERDDL)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            DDL = null;
	        }
	    	try{
	            L = ImageIO.read(ArenaPanel.class.getResource(PLAYERL)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            L = null;
	        }
	    	try{
	            DUL = ImageIO.read(ArenaPanel.class.getResource(PLAYERU)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            DUL = null;
	        }
	    }
}
