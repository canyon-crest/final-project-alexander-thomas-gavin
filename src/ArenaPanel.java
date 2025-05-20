import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ArenaPanel extends GamePanel {
	public static int SCALE = TitleScreen.SCALE;
    private Player player;
    private Enemy enemy;
    private ArrayList<Integer> prevKeys;
    private double prevDirection;
    public ArenaPanel(GameManager manager, JFrame frame){
        super(manager, frame);
        setBackground(Color.cyan);
        prevKeys = new ArrayList<Integer>();
        prevDirection = 0;
        player = new Player(100/SCALE,100/SCALE,0.3*SCALE,60/SCALE,60/SCALE,100);
        enemy = new Enemy(400/SCALE,400/SCALE,0.8*SCALE,60/SCALE,60/SCALE,100,player);


    }
    @Override
    public void click(int x, int y) {
        player.swordAttack(prevDirection,(int)(player.getWidth()),(int)(player.getHeight()));
    }

    @Override
    public void update(ArrayList<Integer> keys) {
        for(int i = 0; i < Entity.getAllEntities().size(); i++) {
            if(Entity.getAllEntities().get(i).tick()){
                i--;
            }
        }
        //directions
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        //dash
        boolean space = false;
        boolean prevSpace = false;
        double direction = prevDirection;
        for(int i: keys){
            //directions
            if(i == KeyEvent.VK_UP || i == KeyEvent.VK_W){
                up = true;
            }
            if(i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S){
                down = true;
            }
            if(i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A){
                left = true;
            }
            if(i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D){
                right = true;
            }
            //dash
            if(i == KeyEvent.VK_SPACE){
                space = true;


            }
        }
        for(int i: prevKeys){
            if(i == KeyEvent.VK_SPACE){
                prevSpace = true;

            }
        }
        //moving directions
        if(up){
            direction = 3*Math.PI/2;
        }
        if(down){
            direction = Math.PI/2;
        }
        if(left){
            direction = Math.PI;
        }
        if(right){
            direction = 0;
        }


        if(up && right){
            direction = 7*Math.PI/4;
        }
        if(down && right){
            direction = Math.PI/4;
        }
        if(up && left){
            direction = 5*Math.PI/4;
        }
        if(down && left){
            direction = 3*Math.PI/4;
        }


        if(up||down||left||right) {
            player.changeVelocity(direction, 8/SCALE);
        }
        //dashing
        if(space && !prevSpace){
            player.dash(direction,24/SCALE);
        }
        prevDirection = direction;


        prevKeys = new ArrayList<Integer>(keys);

    }
    public void paintComponent(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(0,0,TitleScreen.WIDTH/SCALE,TitleScreen.HEIGHT/SCALE);

        for(Entity e: Entity.getAllEntities()) {
            g.setColor(e.getColor());
            g.drawRect((int) e.getX(), (int) e.getY(), e.getWidth(), e.getHeight());
        }
        g.setColor(Color.CYAN);

    }
}
