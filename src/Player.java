import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Character{
	public static final int SCALE = TitleScreen.SCALE;
	public static final int W = 288;
	public static final int H = 288;
	
	private int dashCooldown = 0;
	private int swordCooldown = 0;
	private int iFrames = 0;
	private int stun = 0;
	private double speedMult = 1;
	private double speedMultTime = 0;
	private double currentDirection;
	//Player Move Directions
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
	//Slash
	//Right
	final String SLASHR1 = "images/player/slash/right/slashR1.png";
	final String SLASHR2 = "images/player/slash/right/slashR2.png";
	final String SLASHR3 = "images/player/slash/right/slashR3.png";
	final String SLASHR4 = "images/player/slash/right/slashR4.png";
	private Image SR1;
	private Image SR2;
	private Image SR3;
	private Image SR4;
	private Animation SR;
	//Left
	final String SLASHL1 = "images/player/slash/left/slashL1.png";
	final String SLASHL2 = "images/player/slash/left/slashL2.png";
	final String SLASHL3 = "images/player/slash/left/slashL3.png";
	final String SLASHL4 = "images/player/slash/left/slashL4.png";
	private Image SL1;
	private Image SL2;
	private Image SL3;
	private Image SL4;
	private Animation SL;
	
	private int hurt = 0;
	private int regen = 60;
	public Player(double xLocation, double yLocation, double friction, int width, int height, int health) {
		super(xLocation, yLocation, friction, width, height, health);
		loadImages();
		createAnimations();

	}
	private void createAnimations(){
		ArrayList<Image> animationImages = new ArrayList<>();
		animationImages.add(SL1);
		animationImages.add(SL2);
		animationImages.add(SL3);
		animationImages.add(SL4);
		ArrayList<Integer> timings = new ArrayList<>();
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SL = new Animation(animationImages,timings);
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SR1);
		animationImages.add(SR2);
		animationImages.add(SR3);
		animationImages.add(SR4);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SR = new Animation(animationImages,timings);
	}
	public void changeVelocity(double direction, double amount){
		if(stun <= 0){
			setVelocity(direction,amount*speedMult);
		}
	}
	public void setCurrentDirection(double currentDirection){
		this.currentDirection = currentDirection;
	}
	public void swordAttack(double direction, int width, int height){
		if(swordCooldown == 0){
			new Slash(getX()+getWidth()*Math.cos(direction)/2,getY()+getHeight()*Math.sin(direction)/2 ,
					width ,height,this,(int)((width*Math.cos(direction))/2+(getWidth()*Math.cos(direction))/2),
					(int)((height*Math.sin(direction))/2+(getHeight()*Math.sin(direction))/2));
			swordCooldown = 10;
			if (Math.PI/8 > direction && -Math.PI/8 < direction){
				SR.startAnimation();
			}
			if (7*Math.PI/8 < direction &&  9*Math.PI/8 > direction){
				SL.startAnimation();
			}
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
		SL.tick();
		SR.tick();
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
		double angle = currentDirection;
		
		
		//8 DIRECTIONS WALK ANIMATION 1
		
		//diagonal down right
		if(SR.animationStarted()) {
			return SR.getCurrentImage();
		}
		else if(SL.animationStarted()){
			return SL.getCurrentImage();
		}
		else if ( Math.PI/8 <= angle && angle <= Math.PI/4+Math.PI/8) {
			return DDR;
		}
		//right
		else if (Math.PI/8 > angle && -Math.PI/8 < angle) {
			//SWING RIGHT ANIMATION

			return R;
		}
		//down
		else if (3*Math.PI/8 < angle && 5*Math.PI/8 > angle) {
			return D;
		}
		//diagonal down left
		else if (5*Math.PI/8 <= angle &&  7*Math.PI/8 >= angle) {
			return DDL;
		}
		//left
		else if (7*Math.PI/8 < angle &&  9*Math.PI/8 > angle) {
			//SWING LEFT ANIMATION
			return L;
		}
		//diagonal up left
		else if (9*Math.PI/8 <= angle &&  11*Math.PI/8 >= angle) {
			return DUL;
		}
		//up
		else if (11*Math.PI/8 < angle &&  13*Math.PI/8 > angle) {
			return U;
		}
		//diagonal up right
		else if (13*Math.PI/8 <= angle &&  15*Math.PI/8 >= angle) {
			return DUR;
		}

		return D;
		
		//starts at pi/8 with pi/4 intervals for each direction
		
	}
	public int getImageX(){
		return (int)getXCenter()-W/(SCALE*2);
	}
	public int getImageY(){
		return (int)getYCenter()-H/(SCALE*2);
	}
	  private void loadImages(){
		  //LEFT SLASH
		  try{
	            SL1 = ImageIO.read(ArenaPanel.class.getResource(SLASHL1)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SL1 = null;
	        }
		  try{
	            SL2 = ImageIO.read(ArenaPanel.class.getResource(SLASHL2)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SL2 = null;
	        }
		  try{
	            SL3 = ImageIO.read(ArenaPanel.class.getResource(SLASHL3)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SL3 = null;
	        }
		  try{
	            SL4 = ImageIO.read(ArenaPanel.class.getResource(SLASHL4)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SL4 = null;
	        }
		  //RIGHT SLASH
		  try{
	            SR1 = ImageIO.read(ArenaPanel.class.getResource(SLASHR1)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SR1 = null;
	        }
		  try{
	            SR2 = ImageIO.read(ArenaPanel.class.getResource(SLASHR2)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SR2 = null;
	        }
		  try{
	            SR3 = ImageIO.read(ArenaPanel.class.getResource(SLASHR3)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SR3 = null;
	        }
		  try{
	            SR4 = ImageIO.read(ArenaPanel.class.getResource(SLASHR4)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SR4 = null;
	        }
		  
		  //8 DIRECTIONS
	    	try{
	            U = ImageIO.read(ArenaPanel.class.getResource(PLAYERU)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            U = null;
	        }
	    	try{
	            DUR = ImageIO.read(ArenaPanel.class.getResource(PLAYERDUR)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
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
	            DUL = ImageIO.read(ArenaPanel.class.getResource(PLAYERDUL)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            DUL = null;
	        }
	    }
}
