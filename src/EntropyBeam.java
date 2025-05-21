import java.awt.*;
import java.util.ArrayList;

public class EntropyBeam extends Projectile{
    private int endX;
    private int endY;
    private int startX;
    private int startY;
    private boolean active;
    int axis;

    public EntropyBeam(double xLocation, double yLocation,int width,double direction){
        super(xLocation,yLocation,0,width,0,2,10,50);
        setVelocity(direction,1);
        calculateEnd();
        startX = (int)xLocation;
        startY = (int)yLocation;
        active = false;

    }
    public void calculateEnd(){
        double x = getX();
        double y = getY();
        while(x > 42d/SCALE && x < (double)TitleScreen.WIDTH/SCALE-42d/SCALE && y < (double)TitleScreen.HEIGHT/SCALE - 110d/SCALE){
            x+=getXVel();
            y+=getYVel();
        }
        endX = (int)x;
        endY = (int)y;
        if(x <= 42d/SCALE){
            axis = 0;
        }
        else if(y >= (double)TitleScreen.HEIGHT/SCALE - 110d/SCALE){
            axis = 1;
        }
        else if(x >= (double)TitleScreen.WIDTH/SCALE-42d/SCALE){
            axis = 2;
        }

    }
    public boolean isInHitBox(Entity other){
        double angleDiff = 0;
        if(other.getXCenter() != getX()) {
            angleDiff = getAngle() - Math.atan((getY() - other.getYCenter()) / (getX() - other.getXCenter()));
        }
        else{
            angleDiff = getAngle() - Math.PI/2;
        }
        double distDiff = Math.sqrt(Math.pow((other.getXCenter()-this.getX()),2)+Math.pow((other.getYCenter()-this.getY()),2));
        double distance = Math.abs(Math.sin(angleDiff)*distDiff);
        return distance <= 5 + (other.getHeight()+other.getWidth())/2d;
    }
    public double getXCenter(){
        return getX();
    }
    public double getYCenter(){
        return getY();
    }
    public void moveCentered(double x, double y){
        move(x,y);
    }
    public int getEndX(){
        return endX;
    }
    public int getEndY(){
        return endY;
    }
    public boolean tick(){
        if(getTimeLeft() == 30){
            active = true;
            double direction = -Math.PI/2+(Math.PI/2)*axis;
            for(int i = 0; i < 7; i++){
                new Debris(getEndX(),getEndY(),40/SCALE,40/SCALE,direction);
                direction += Math.PI/6;
            }

        }
        move(startX,startY);
        return super.tick();
    }
    public boolean isActive(){
        return active;
    }

    @Override
    public boolean registerHit() {
        if(!active){
            return false;
        }
        ArrayList<Entity> nearbyEntities = checkHitboxes();
        for (Entity e : nearbyEntities) {
            if (e instanceof Player) {
                Player p = (Player) e;
                p.hurt();
                p.takeDamage(getDamage());
                if(p.getiFrames()<=0) {
                    p.stun(3);
                    p.setVelocity(0,0);
                    p.setIFrames(10);
                }
            }
        }
        return false;
    }
}
