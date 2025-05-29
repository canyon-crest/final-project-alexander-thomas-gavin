import java.awt.*;
import java.util.ArrayList;

public class Slash extends Projectile{
    private Player player;
    private int offsetX;
    private int offsetY;
    private boolean hit;
    /**
     *  constructor method for slash
     * @param xLocation the x of the slash
     * @param yLocation the y of the slash
     * @param width the width of the slash
     * @param height the height of the slash
     * @param player the player who dealt the attack
     * @param offsetX the x offset of the attack
     * @param offsetY the y offset of the attack
     */
    public Slash(double xLocation, double yLocation, int width, int height, Player player,int offsetX,int offsetY){
        super(xLocation,yLocation,0,width,height,1,15,8);
        this.player = player;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        hit = false;
        setColor(Color.WHITE);
    }
    /**
     *  method that runs every frame
     * @return whether the attack should be deleted
     */
    public boolean tick(){
        //sets the color and moves the player
        setColor(Color.WHITE);
        move(player.getX()+ player.getWidth()/2d-getWidth()/2d+offsetX,player.getY()+ player.getHeight()/2d-getHeight()/2d+offsetY);
        return super.tick();

    }
    /**
     * registers hits and deals damage to nearby enemies
     * @return whether the attack should be deleted
     */
    public boolean registerHit(){

        ArrayList<Entity> nearbyEntities = checkHitboxes();
        for (Entity e : nearbyEntities) {
            if (e instanceof Enemy) {
                setColor(Color.BLUE);
                e.setColor(Color.RED);
                if(!hit) {
                    ((Enemy) e).takeDamage(getDamage());
                    player.stun(3);
                    player.setRectVelocity(player.getXCenter()-e.getXCenter(),player.getYCenter()-e.getYCenter());
                    player.setVelocity(player.getAngle(),2/SCALE);
                    hit = true;
                }
            }
        }

        return false;
    }

}
