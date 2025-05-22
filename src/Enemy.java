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
    private int shield = 0;
    private double nextShield = 0.5;

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
            }
            else {
                double attackChance = Math.random();
                if (attackChance <= 0.025) {
                    initiateDash();

                } else if (attackChance <= 0.035) {
                    initiateEntropy();
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

        }
        if(entropyProgress > 0){
            entropyProgress--;
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
