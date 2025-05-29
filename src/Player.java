import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Character{
	public static final int SCALE = TitleScreen.SCALE;
	public static final int W = 288;
	public static final int H = 288;
	private ArenaPanel panel;
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
	//DDR
	final String SLASHDDR1 = "images/player/slash/DDR/slashDDR1.png";
	final String SLASHDDR2 = "images/player/slash/DDR/slashDDR2.png";
	final String SLASHDDR3 = "images/player/slash/DDR/slashDDR3.png";
	private Image SDDR1;
	private Image SDDR2;
	private Image SDDR3;
	private Animation SDDR;
	//DDL 
	final String SLASHDDL1 = "images/player/slash/DDL/slashDDL1.png";
	final String SLASHDDL2 = "images/player/slash/DDL/slashDDL2.png";
	final String SLASHDDL3 = "images/player/slash/DDL/slashDDL3.png";
	private Image SDDL1;
	private Image SDDL2;
	private Image SDDL3;
	private Animation SDDL;
	//D
	final String SLASHD1 = "images/player/slash/D/slashD1.png";
	final String SLASHD2 = "images/player/slash/D/slashD2.png";
	final String SLASHD3 = "images/player/slash/D/slashD3.png";
	private Image SD1;
	private Image SD2;
	private Image SD3;
	private Animation SD;
	// DUR (Diagonal Up Right)
	final String SLASHDUR1 = "images/player/slash/DUR/slashDUR1.png";
	final String SLASHDUR2 = "images/player/slash/DUR/slashDUR2.png";
	final String SLASHDUR3 = "images/player/slash/DUR/slashDUR3.png";
	private Image SDUR1, SDUR2, SDUR3;
	private Animation SDUR;

	// DUL (Diagonal Up Left)
	final String SLASHDUL1 = "images/player/slash/DUL/slashDUL1.png";
	final String SLASHDUL2 = "images/player/slash/DUL/slashDUL2.png";
	final String SLASHDUL3 = "images/player/slash/DUL/slashDUL3.png";
	private Image SDUL1, SDUL2, SDUL3;
	private Animation SDUL;

	// UP
	final String SLASHU1 = "images/player/slash/U/slashU1.png";
	final String SLASHU2 = "images/player/slash/U/slashU2.png";
	final String SLASHU3 = "images/player/slash/U/slashU3.png";
	private Image SU1, SU2, SU3;
	private Animation SU;

	
	
	private int hurt = 0;
	private int regen = 60;
	/**
	 *  constructor method of the player
	 * @param xLocation the x of the player
	 * @param yLocation the y of the player
	 * @param friction the friction of the player
	 * @param width the width of the player
	 * @param height the height of the player
	 * @param panel the current ArenaPanel
	 */
	public Player(double xLocation, double yLocation, double friction, int width, int height, int health,ArenaPanel panel) {
		super(xLocation, yLocation, friction, width, height, health);
		loadImages();
		createAnimations();
		this.panel = panel;

	}
	/**
	 *  method that creates all animations
	 */
	private void createAnimations(){
		//Swing Left
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
		SL = new Animation(animationImages,timings,false);
		//Swing Right
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
		SR = new Animation(animationImages,timings,false);
		//Swing Diagonal Down Right
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SDDR1);
		animationImages.add(SDDR2);
		animationImages.add(SDDR3);
		animationImages.add(SDDR3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SDDR = new Animation(animationImages, timings,false);
		//Swing Diagonal Down Left
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SDDL1);
		animationImages.add(SDDL2);
		animationImages.add(SDDL3);
		animationImages.add(SDDL3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SDDL = new Animation(animationImages, timings,false);
		//Down
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SD1);
		animationImages.add(SD2);
		animationImages.add(SD3);
		animationImages.add(SD3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SD = new Animation(animationImages, timings,false);
		// Swing DUR
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SDUR1);
		animationImages.add(SDUR2);
		animationImages.add(SDUR3);
		animationImages.add(SDUR3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SDUR = new Animation(animationImages, timings, false);
		// Swing DUL
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SDUL1);
		animationImages.add(SDUL2);
		animationImages.add(SDUL3);
		animationImages.add(SDUL3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SDUL = new Animation(animationImages, timings, false);
		// Swing UP
		animationImages = new ArrayList<>();
		timings = new ArrayList<>();
		animationImages.add(SU1);
		animationImages.add(SU2);
		animationImages.add(SU3);
		animationImages.add(SU3);
		timings.add(2);
		timings.add(1);
		timings.add(4);
		timings.add(3);
		SU = new Animation(animationImages, timings, false);

	}
	/**
	 *  method changes velocity of the player
	 * @param direction the direction
	 * @param amount the magnitude
	 */
	public void changeVelocity(double direction, double amount){
		if(stun <= 0){
			setVelocity(direction,amount*speedMult);
		}
	}
	/**
	 *  method that sets the direction pressed
	 * @param currentDirection the direction
	 */
	public void setCurrentDirection(double currentDirection){
		this.currentDirection = currentDirection;
	}
	/**
	 *  method that starts a slash
	 * @param direction the direction
	 * @param width the width of the slash
	 * @param height the height of the slash
	 */
	public void swordAttack(double direction, int width, int height){
		if(swordCooldown == 0){
			new Slash(getX()+getWidth()*Math.cos(direction)/2,getY()+getHeight()*Math.sin(direction)/2 ,
					width ,height,this,(int)((width*Math.cos(direction))/2+(getWidth()*Math.cos(direction))/2),
					(int)((height*Math.sin(direction))/2+(getHeight()*Math.sin(direction))/2));
			swordCooldown = 10;
			setSpeedMult(0.5,10);
			if (Math.PI/8 > direction && -Math.PI/8 < direction){
				SR.startAnimation();
			}
			if (7*Math.PI/8 < direction &&  9*Math.PI/8 > direction){
				SL.startAnimation();
			}
			if ( Math.PI/8 <= direction && direction <= Math.PI/4+Math.PI/8) {
				SDDR.startAnimation();
			}
			if (5*Math.PI/8 <= direction &&  7*Math.PI/8 >= direction) {
				SDDL.startAnimation();
			}
			if (3*Math.PI/8 < direction && 5*Math.PI/8 > direction) {
			
				SD.startAnimation();
			}
			if (13*Math.PI/8 <= direction &&  15*Math.PI/8 >= direction) {
			    SDUR.startAnimation();
			}
			if (9*Math.PI/8 <= direction &&  11*Math.PI/8 >= direction) {
			    SDUL.startAnimation();
			}
			if (11*Math.PI/8 < direction &&  13*Math.PI/8 > direction) {
			    SU.startAnimation();
			}

		}
	}
	/**
	 *  method that starts a dash
	 * @param direction the direction
	 * @param amount the magnitude
	 */
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
	/**
	 *  method that is run when a player is hurt
	 */
	public void hurt(){
		hurt = 1;
	}
	/**
	 *  method that is run when a player takes damage and reduces health
	 * @param amount the amount of damage
	 */
	public void takeDamage(int amount){
		if(iFrames == 0) {
			super.takeDamage(amount);
			panel.reduceScore();
		}
	}
	/**
	 *  method that sets iframe of the player
	 * @param amount the time of the iframes
	 */
	public void setIFrames(int amount){
		if(amount > iFrames){
			iFrames = amount;
		}

	}
	/**
	 *  method that returns iframes
	 * @return iframes
	 */
	public int getiFrames(){
		return iFrames;
	}
	/**
	 *  method that sets speed multipliers
	 * @param amount the amount of multiplication
	 * @param time the time that speed is multiplied
	 */
	public void setSpeedMult(double amount, double time) {
		speedMult *= amount;
		if(speedMultTime < time) {
			speedMultTime = time;
		}
	}
	/**
	 *  method that sets stun duration
	 * @param time stun duration
	 */
	public void stun(int time){
		if(time > stun) {
			stun = time;
		}
	}
	/**
	 *  method that is run every frame
	 * @return whether the player is deleted
	 */
	public boolean tick(){
		//Tick Each Animation of the Player
		SL.tick();
		SR.tick();
		SDDR.tick();
		SDDL.tick();
		SD.tick();
		SDUR.tick();
		SDUL.tick();
		SU.tick();
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
	/**
	 *  method that returns the current image
	 * @return current image
	 */
	public Image getImage() {
		double angle = currentDirection;
		
		
		//8 DIRECTIONS WALK ANIMATION 1
		
		
		if(SR.animationStarted()) {
			return SR.getCurrentImage();
		}
		else if(SL.animationStarted()){
			return SL.getCurrentImage();
		}
		else if (SDDR.animationStarted()) {
			return SDDR.getCurrentImage();
		}
		else if (SDDL.animationStarted()) {
			return SDDL.getCurrentImage();
		}
		else if(SD.animationStarted()) {
			return SD.getCurrentImage();
		}
		else if(SDUR.animationStarted()) {
		    return SDUR.getCurrentImage();
		}
		else if(SDUL.animationStarted()) {
		    return SDUL.getCurrentImage();
		}
		else if(SU.animationStarted()) {
		    return SU.getCurrentImage();
		}

		//diagonal down right
		else if ( Math.PI/8 <= angle && angle <= Math.PI/4+Math.PI/8) {
			return DDR;
		}
		//right
		else if (Math.PI/8 > angle && -Math.PI/8 < angle) {

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
	/**
	 *  method that returns the image X
	 * @return image X
	 */
	//returns the X location image should be
	public int getImageX(){
		return (int)getXCenter()-W/(SCALE*2);
	}
	/**
	 *  method that returns the image Y
	 * @return image Y
	 */
	//returns Y location image should be
	public int getImageY(){
		return (int)getYCenter()-H/(SCALE*2);
	}
	/**
	 *  method that loads all the images
	 */
	private void loadImages(){
		//Slash DUR
		  try {
		      SDUR1 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUR1)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUR1 = null; }
		  try {
		      SDUR2 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUR2)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUR2 = null; }
		  try {
		      SDUR3 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUR3)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUR3 = null; }

		  //Slash DUL
		  try {
		      SDUL1 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUL1)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUL1 = null; }
		  try {
		      SDUL2 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUL2)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUL2 = null; }
		  try {
		      SDUL3 = ImageIO.read(ArenaPanel.class.getResource(SLASHDUL3)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SDUL3 = null; }

		  //Slash UP
		  try {
		      SU1 = ImageIO.read(ArenaPanel.class.getResource(SLASHU1)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SU1 = null; }
		  try {
		      SU2 = ImageIO.read(ArenaPanel.class.getResource(SLASHU2)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SU2 = null; }
		  try {
		      SU3 = ImageIO.read(ArenaPanel.class.getResource(SLASHU3)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
		  } catch (IOException e) { SU3 = null; }

		  //DOWN
		  try{
	            SD1 = ImageIO.read(ArenaPanel.class.getResource(SLASHD1)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SD1 = null;
	        }
		  try{
	            SD2 = ImageIO.read(ArenaPanel.class.getResource(SLASHD2)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SD2 = null;
	        }
		  try{
	            SD3 = ImageIO.read(ArenaPanel.class.getResource(SLASHD3)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SD3 = null;
	        }
		  
		  //DDL SLASH
		  try{
	            SDDL1 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDL1)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDL1 = null;
	        }
		  try{
	            SDDL2 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDL2)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDL2 = null;
	        }
		  try{
	            SDDL3 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDL3)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDL3 = null;
	        }
		  //DDR SLASH
		  try{
	            SDDR1 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDR1)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDR1 = null;
	        }
		  try{
	            SDDR2 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDR2)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDR2 = null;
	        }
		  try{
	            SDDR3 = ImageIO.read(ArenaPanel.class.getResource(SLASHDDR3)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
	        }
	        catch(IOException e) {
	            SDDR3 = null;
	        }
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
