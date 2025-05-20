import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Character{

    private Entity mortalEnemy;
    private int cooldown = 0;
    private int noAction = 0;
    public Enemy(double xLocation, double yLocation, double friction, int width, int height, int health, Entity mortalEnemy) {
        super(xLocation, yLocation, friction, width, height, health);
        this.mortalEnemy = mortalEnemy;
        setColor(Color.YELLOW);
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
                e.setVelocity(e.getAngle(),4);
                e.setColor(Color.ORANGE);


            }
        }
        if(cooldown <= 0){
            double attackChance = Math.random();
            if(attackChance <= 0.05){
                dash();
                cooldown = 100;
                noAction = 20;
            }
        }
        else{
            cooldown--;
        }
        return false;
    }
    public void dash(){
        setRectVelocity(Math.random()*TitleScreen.WIDTH/SCALE-getX(),Math.random()*TitleScreen.HEIGHT/SCALE-getY());
        setVelocity(getAngle(),Math.sqrt(getMagnitude()));
    }


}
