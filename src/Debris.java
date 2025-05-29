import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Debris extends Projectile{
    private String DEBRIS_IMAGE_PATH = "images/arena/debris.png";
    private Image debrisImage;
    public Debris(double xLocation, double yLocation, int width, int height,double direction) {
        super(xLocation, yLocation, (0.4+Math.random()*0.4)/SCALE, width, height, 2, 10, (int)(Math.random()*20)+30);
        loadImages();
        setVelocity(direction + Math.random()*Math.PI/30-Math.PI/15, (Math.random()*10+30)/SCALE);

        //makes sure the debris is in bounds
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
    //loads images
    public void loadImages(){
        try {
            debrisImage = ImageIO.read(ArenaPanel.class.getResource(DEBRIS_IMAGE_PATH)).getScaledInstance((int)(getWidth()*1.2), (int)(getHeight()*1.2), Image.SCALE_SMOOTH);
        } catch (IOException e) { debrisImage = null; }
    }
    //returns image
    public Image getDebrisImage(){
        return debrisImage;
    }
    //returns the X location the image should be at
    public int getImageX(){
        return (int)getXCenter()-(int)((getWidth()*1.2)/2);
    }
    //returns the Y location image should be at
    public int getImageY(){
        return (int)getYCenter()-(int)((getHeight()*1.2)/2);
    }
    //runs every frame
    public boolean tick(){
        //handles bouncing off of walls
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
    //handles damaging and colliding with players
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
