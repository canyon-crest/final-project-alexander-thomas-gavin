import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends Character{
    public static final int W = 300;
    public static final int H = 300;

    private Entity mortalEnemy;
    private int cooldown = 60;
    private int noAction = 0;
    private double dashX;
    private double dashY;
    private int dashProgress;
    private int entropyProgress;
    private double slamProgress;
    private double dashChance = 0.02;
    private double entropyChance = 0.01;
    private double slamOrDashChance = 0.02;
    private int shield = 0;
    private double nextShield = 0.5;
    private boolean enraged = false;

    private String BOSS_IMAGE_PATH = "images/arena/boss.png";
    private Image bossImage;
    private boolean hide;
    /**
     *  constructor method for the enemy class
     * @param xLocation the x location of the boss
     * @param yLocation the y location of the boss
     * @param friction the friction of the boss
     * @param width the width of the boss
     * @param height the height of the boss
     * @param health the current health of the boss
     * @param mortalEnemy the player the boss targets
     */
    public Enemy(double xLocation, double yLocation, double friction, int width, int height, int health, Entity mortalEnemy) {
        super(xLocation, yLocation, friction, width, height, health);
        this.mortalEnemy = mortalEnemy;
        loadImages();
        setColor(Color.YELLOW);
        hide = false;

    }
    /**
     *  method that loads all the images
     */
    //loads the images
    public void loadImages(){
        try {
            bossImage = ImageIO.read(ArenaPanel.class.getResource(BOSS_IMAGE_PATH)).getScaledInstance(W/SCALE, H/SCALE, Image.SCALE_SMOOTH);
        } catch (IOException e) { bossImage = null; }
    }
    /**
     *  method gets the current image of the boss
     * @return current image of the boss
     */
    public Image getBossImage(){
        return bossImage;
    }
    /**
     *  method that gets the X location of the image
     * @return x of the image
     */
    //returns the X location image should be
    public int getImageX(){
        return (int)getXCenter()-W/(SCALE*2);
    }
    /**
     *  method that gets the Y location of the image
     * @return y of the image
     */
    //returns Y location image should be
    public int getImageY(){
        return (int)getYCenter()-(H-30)/(SCALE*2);
    }
    //returns the amount of shield the enemy has
    /**
     *  method gets the shield of the boss
     * @return amount of shield
     */
    public int getShield(){
        return shield;
    }
    //handles the boss taking damage by either losing health or losing shield
    /**
     *  method that handles taking damage/shield
     * @param amount amount of damage
     */
    public void takeDamage(int amount){
        if(shield <= 0 || amount < 0){
            super.takeDamage(amount);
            hide = true;
        }
        else{
            shield -= amount;
            if(shield < 0){
                shield = 0;
                hide = true;
            }
        }
    }
    /**
     *  method that decides if the boss image should be hidden or not
     * @return boolean representing if the boss image should be hidden
     */
    //gets whether the image should be hidden
    public boolean isHidden(){
        return hide;
    }
    //runs every frame
    /**
     *  method that runs every frame
     * @return whether the boss was deleted or not
     */
    public boolean tick(){
    	dashChance = 0.02-0.02*((getMaxHealth()-getHealth())/(double)getMaxHealth());
    	entropyChance = 0.01+0.02*((getMaxHealth()-getHealth())/(double)getMaxHealth());
    	slamOrDashChance = 0.01+0.01*((getMaxHealth()-getHealth())/(double)getMaxHealth());
    	double distance = Math.sqrt(Math.pow(mortalEnemy.getXCenter()-getXCenter(), 2)+Math.pow(mortalEnemy.getYCenter()-getYCenter(), 2));
        super.tick();
        setColor(Color.YELLOW);
        //handles boss movement
        if(noAction<=0) {

            setRectVelocity(getX() - mortalEnemy.getX(), getY() - mortalEnemy.getY());
            setVelocity(getAngle(), (double) 2 / SCALE);
            if(enraged){
                setVelocity(getAngle(),getMagnitude()*-1);
            }
        }
        else{
            noAction--;
        }
        if(getHealth() == 0){
            destroy();
            return true;
        }
        //checks for collision with the player
        ArrayList<Entity> nearbyEntities = checkHitboxes();
        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                e.setColor(Color.RED);
                ((Player) e).takeDamage(10);
                ((Player) e).setIFrames(5);
                ((Player) e).stun((5));
                e.setRectVelocity(e.getXCenter()-getXCenter(),e.getYCenter()-getYCenter());
                e.setVelocity(e.getAngle(),8/SCALE);
                e.setColor(Color.ORANGE);


            }
        }
        //handles boss abilities based on either health or random chance
        if(cooldown <= 0){
            if((double)getHealth()/getMaxHealth() <= nextShield){
                initiateShield();
                enraged = true;
            }
            else {
                double attackChance = Math.random();
                if (attackChance <= dashChance) {
                    initiateDash();

                } else if (attackChance <= dashChance+entropyChance) {
                    initiateEntropy();
                }
                else if(attackChance <= dashChance+entropyChance+slamOrDashChance){
                	if(distance < 200/SCALE+(getWidth()+getHeight())/(4*SCALE)) {
                		initiateSlam();
                	}
                	else {
                		initiateDash2();
                	}
                }
                
            }
        }
        else{
            cooldown--;
        }
        //handles things that happen after the ability starts
        if(dashProgress > 0){
            if(dashProgress == 20){
                setRectVelocity(dashX-getXCenter(),dashY-getYCenter());
                setVelocity(getAngle(),(getMagnitude()+120)/15);

            }
            dashProgress--;
            if(dashProgress == 1 && enraged) {
            	double direction = getAngle()-Math.PI/2;
                for (int i = 0; i < 6; i++) {
                    new Debris(getXCenter(), getYCenter(), 20 / SCALE, 20 / SCALE, direction);
                    direction += Math.PI / 6;
                }
            }

        }
        if(entropyProgress > 0){
        	if((entropyProgress == 50 || entropyProgress == 40)&&getHealth()<=250) {
        		double angleDiff = 0;
        		double x = 100;
        		if(enraged) {
                	x = Math.random()*((double)TitleScreen.WIDTH/SCALE-86d/SCALE)+43d/SCALE;

                    if(mortalEnemy.getXCenter() != x) {
                        angleDiff = Math.atan(mortalEnemy.getYCenter() / (mortalEnemy.getXCenter()-x));
                    }
                    else{
                        angleDiff = Math.PI/2;
                    }
                    if(angleDiff<0){
                        angleDiff+=Math.PI;
                    }
                    EntropyBeam b = new EntropyBeam(x,0,30/SCALE,angleDiff);
                    b.setTimeLeft(70);
                    b.setNumProjectiles(3);
                }
        	}
            entropyProgress--;
        }
        if(slamProgress > 0) {
        	slamProgress --;

            if(enraged) {
                if(slamProgress == 45){
                    new Slam(getXCenter(),getYCenter(),500/SCALE);
                }
                if (slamProgress == 25){
                    double direction = Math.random()*Math.PI/3;
                    for (int i = 0; i < 6; i++) {
                        new Debris(getXCenter(), getYCenter(), 60 / SCALE, 60 / SCALE, direction);
                        direction += Math.PI / 3;
                    }
                }
            }
        }
        if(hide){
            hide = false;
        }
        return false;
    }
    //starts the dash ability
    /**
     *  method starts the boss's dash ability
     */
    public void initiateDash(){
        dashX = Math.random()*(TitleScreen.WIDTH-84)/SCALE+(double)42/SCALE;
        dashY = Math.random()*(TitleScreen.HEIGHT-230)/SCALE+120d/SCALE;
        Indicator i = new Indicator(dashX,dashY,50/SCALE,50/SCALE,25);
        i.moveCentered(dashX,dashY);
        dashProgress = 40;
        cooldown = 80;
        if(enraged) {
        	cooldown += 40;
        }
        noAction = 35;
    }
    /**
     *  method that starts a boss's dash ability
     */
    //starts a different dash ability toward the player
    public void initiateDash2(){
        dashX = mortalEnemy.getXCenter();
        dashY = mortalEnemy.getYCenter();
        Indicator i = new Indicator(dashX,dashY,50/SCALE,50/SCALE,25);
        i.moveCentered(dashX,dashY);
        dashProgress = 40;
        cooldown = 50;
        noAction = 35;
    }
    /**
     *  method that starts the boss's slam ability
     */
    //starts the slam ability
    public void initiateSlam() {
    	Slam s = new Slam(getXCenter(),getYCenter(),300/SCALE);
        if(enraged){
            noAction = 70;
        }
        else {
            noAction = 40;
        }
    	slamProgress = 70;
    	cooldown = 80;
        if(enraged){
            cooldown = 120;
        }

    }
    /**
     *  method that starts the boss's entropy beam ability
     */
    //starts the entropy beam ability
    public void initiateEntropy(){
        double angleDiff = 0;
        double x = Math.random()*((double)TitleScreen.WIDTH/SCALE-86d/SCALE)+43d/SCALE;

        if(mortalEnemy.getXCenter() != x) {
            angleDiff = Math.atan(mortalEnemy.getYCenter() / (mortalEnemy.getXCenter()-x));
        }
        else{
            angleDiff = Math.PI/2;
        }
        if(angleDiff<0){
            angleDiff+=Math.PI;
        }
        EntropyBeam a = new EntropyBeam(x,0,30/SCALE,angleDiff);
        if(getHealth() <= 250) {
        	a.setTimeLeft(70);
        	a.setNumProjectiles(3);
        }
        

        entropyProgress = 60;
        cooldown = 120;
        noAction = 30;
        if(getHealth() <= 250) {
        	noAction = 60;
        }
    }
    /**
     *  method that starts the boss's shield ability
     */
    //starts the shield ability
    public void initiateShield(){
        shield += getMaxHealth()/4;
        cooldown = 90;
        noAction = 60;
        nextShield -= 0.25;

    }


}
