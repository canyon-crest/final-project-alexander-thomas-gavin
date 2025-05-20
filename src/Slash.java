import java.awt.*;
import java.util.ArrayList;

public class Slash extends Projectile{
    private Player player;
    private int offsetX;
    private int offsetY;
    private boolean hit;
    public Slash(double xLocation, double yLocation, int width, int height, Player player,int offsetX,int offsetY){
        super(xLocation,yLocation,0,width,height,1,10,10);
        this.player = player;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        hit = false;
        setColor(Color.WHITE);
    }
    public boolean tick(){

        move(player.getX()+ player.getWidth()/2-getWidth()/2+offsetX,player.getY()+ player.getHeight()/2-getHeight()/2+offsetY);
        return super.tick();

    }
    public boolean registerHit(){
        setColor(Color.WHITE);
        ArrayList<Entity> nearbyEntities = checkHitboxes();
        for (Entity e : nearbyEntities) {
            if (e instanceof Enemy) {
                setColor(Color.BLUE);
                e.setColor(Color.RED);
                if(!hit) {
                    ((Enemy) e).takeDamage(getDamage());
                    player.stun(2);
                    player.setRectVelocity(player.getXCenter()-e.getXCenter(),player.getYCenter()-e.getYCenter());
                    player.setVelocity(player.getAngle(),4);
                    hit = true;
                }
            }
        }

        return false;
    }

}
