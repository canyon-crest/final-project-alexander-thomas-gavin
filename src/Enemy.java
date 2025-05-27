import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Character{

    private Entity mortalEnemy;
    private int cooldown = 0;
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
    

    public Enemy(double xLocation, double yLocation, double friction, int width, int height, int health, Entity mortalEnemy) {
        super(xLocation, yLocation, friction, width, height, health);
        this.mortalEnemy = mortalEnemy;
        setColor(Color.YELLOW);
    }
    public int getShield(){
        return shield;
    }
    public void takeDamage(int amount){
        if(shield <= 0 || amount < 0){
            super.takeDamage(amount);
        }
        else{
            shield -= amount;
            if(shield < 0){
                shield = 0;
            }
        }
    }
    public boolean tick(){
    	dashChance = 0.02-0.01*((getMaxHealth()-getHealth())/(double)getMaxHealth());
    	slamOrDashChance = 0.02+0.02*((getMaxHealth()-getHealth())/(double)getMaxHealth());
    	double distance = Math.sqrt(Math.pow(mortalEnemy.getXCenter()-getXCenter(), 2)+Math.pow(mortalEnemy.getYCenter()-getYCenter(), 2));
        super.tick();
        setColor(Color.YELLOW);
        if(noAction<=0) {
            setRectVelocity(getX() - mortalEnemy.getX(), getY() - mortalEnemy.getY());
            setVelocity(getAngle(), (double) 2 / SCALE);
        }
        else{
            noAction--;
        }
        if(getHealth() == 0){
            destroy();
            return true;
        }
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
        if(dashProgress > 0){
            if(dashProgress == 20){
                setRectVelocity(dashX-getXCenter(),dashY-getYCenter());
                setVelocity(getAngle(),(getMagnitude()+120)/15);

            }
            dashProgress--;
            if(dashProgress == 0 && enraged) {
            	initiateSlam();
            }

        }
        if(entropyProgress > 0){
            entropyProgress--;
        }
        if(slamProgress > 0) {
        	slamProgress --;
        }
        return false;
    }
    public void initiateDash(){
        dashX = Math.random()*(TitleScreen.WIDTH-84)/SCALE+(double)42/SCALE;
        dashY = Math.random()*(TitleScreen.HEIGHT-230)/SCALE+120d/SCALE;
        Indicator i = new Indicator(dashX,dashY,50/SCALE,50/SCALE,25);
        i.moveCentered(dashX,dashY);
        dashProgress = 40;
        cooldown = 60;
        noAction = 35;
    }
    public void initiateDash2(){
        dashX = mortalEnemy.getXCenter();
        dashY = mortalEnemy.getYCenter();
        Indicator i = new Indicator(dashX,dashY,50/SCALE,50/SCALE,25);
        i.moveCentered(dashX,dashY);
        dashProgress = 40;
        cooldown = 50;
        noAction = 35;
    }
    public void initiateSlam() {
    	Slam s = new Slam(getXCenter(),getYCenter(),300/SCALE);
    	noAction = 40;
    	slamProgress = 35;
    	cooldown = 80;
    	if(enraged) {
    		double direction = 0;
    		for(int i = 0; i < 12; i++){
                new Debris(getXCenter(),getYCenter(),40/SCALE,40/SCALE,direction);
                direction += Math.PI/6;
            }
    	}
    }
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

        entropyProgress = 30;
        cooldown = 200;
        noAction = 30;
    }
    public void initiateShield(){
        shield += getMaxHealth()/4;
        cooldown = 90;
        noAction = 60;
        nextShield -= 0.25;

    }


}
