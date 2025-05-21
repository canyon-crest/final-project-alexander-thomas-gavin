import java.awt.*;
import java.util.ArrayList;

public class Debris extends Projectile{
    public Debris(double xLocation, double yLocation, int width, int height,double direction) {
        super(xLocation, yLocation, 0.2+Math.random()*0.2, width, height, 2, 10, (int)(Math.random()*20)+30);
        setVelocity(direction + Math.random()*Math.PI/30-Math.PI/15, (Math.random()*10+30)/SCALE);
        if(getX() > (double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE){
            move((double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE,getY());
        }
        if(getX() < 42d/SCALE){
            move(42d/SCALE,getY());
        }
        if(getY() < 120d/SCALE){
            move(getX(),120d/SCALE);
        }
        if(getY() > (double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE){
            move(getX(),(double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE);
        }
    }
    public boolean tick(){
        if(getX() > (double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE){
            move((double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE,getY());
            setVelocity(2*getAngle()-Math.PI,-1*getMagnitude());
        }
        if(getX() < 42d/SCALE){
            move(42d/SCALE,getY());
            setVelocity(2*getAngle()-Math.PI,getMagnitude());
        }
        if(getY() < 120d/SCALE){
            move(getX(),120d/SCALE);
            setVelocity(getAngle(),-1*getMagnitude());
        }
        if(getY() > (double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE){
            move(getX(),(double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE);
            setRectVelocity(getXVel(),-1*getYVel());

        }
        return super.tick();
    }
    @Override
    public boolean registerHit() {
        ArrayList<Entity> nearbyEntities = checkHitboxes();
        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                Player p = (Player)e;
                p.takeDamage(getDamage());
                p.hurt();
                if(p.getiFrames()<= 0) {
                    p.stun(5);
                    p.setRectVelocity(p.getXCenter() - getXCenter(), p.getYCenter() - getYCenter());
                    p.setVelocity(p.getAngle(), 8/SCALE);
                    destroy();
                    return true;
                }
            }
        }
        return false;
    }
}
