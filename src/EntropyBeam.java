import java.awt.*;
import java.util.ArrayList;

public class EntropyBeam extends Projectile{
    private int endX;
    private int endY;
    private int startX;
    private int startY;
    private boolean active;
    int axis;
    int numProjectiles;
    /**
     *  constructor method for entropybeam
     * @param xLocation starting x of the beam
     * @param yLocation starting y of the beam
     * @param width width of the beam
     * @param direction direction of the beam
     */

    public EntropyBeam(double xLocation, double yLocation,int width,double direction){
        super(xLocation,yLocation,0,width,0,2,10,50);
        setVelocity(direction,1);
        calculateEnd();
        startX = (int)xLocation;
        startY = (int)yLocation;
        active = false;
        numProjectiles = 6;

    }
    /**
     *  method that calculates the end of the beam
     */
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
    /**
     *  method that checks if another entity is in the hitbox
     * @param other the entity to check
     */
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
    /**
     *  method that gets the x center location
     * @return x location
     */
    public double getXCenter(){
        return getX();
    }
    /**
     *  method that gets the y center location
     * @return y location
     */
    public double getYCenter(){
        return getY();
    }
    /**
     *  method that moves the beams center
     * @param x the x location to move to
     * @param y the y location to move to
     */
    public void moveCentered(double x, double y){
        move(x,y);
    }
    /**
     *  method that gets the ending x point
     * @return ending x point
     */
    public int getEndX(){
        return endX;
    }
    /**
     *  method that gets the ending y point
     * @return ending y point
     */
    public int getEndY(){
        return endY;
    }
    /**
     *  a method that sets the number of beam projectiles
     * @param numProjectiles number of projectiles
     */
    public void setNumProjectiles(int numProjectiles) {
    	this.numProjectiles = numProjectiles;
    }
    /**
     *  method that runs every frame
     * @return whether the beam is deleted
     */
    public boolean tick(){
        //checks how much time is left and when to deal damage to the player
        if(getTimeLeft() == 30){
            active = true;
            double direction = -Math.PI/2+(Math.PI/2)*axis;
            for(int i = 0; i < numProjectiles+1; i++){
                new Debris(getEndX(),getEndY(),40/SCALE,40/SCALE,direction);
                direction += Math.PI/numProjectiles;
            }

        }
        move(startX,startY);
        return super.tick();
    }
    /**
     *  method that gets if the beam is active
     * @return boolean if the beam is active
     */
    public boolean isActive(){
        return active;
    }
    /**
     *  method that hits all nearby entities
     * @return whether the beam should be deleted
     */
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
