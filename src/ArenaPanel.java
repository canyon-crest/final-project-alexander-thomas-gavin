import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ArenaPanel extends GamePanel {
    Entity player = new Entity(100,100,0.3,30,30);
    public ArenaPanel(GameManager manager, JFrame frame){
        super(manager, frame);
        setBackground(Color.cyan);

    }
    @Override
    public void click(int x, int y) {

    }

    @Override
    public void update(ArrayList<Integer> keys) {
        player.tick();
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        double direction = 0;
        for(int i: keys){
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
        }
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
            player.setVelocity(direction, 4);
        }
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.cyan);
        g.clearRect(0,0, TitleScreen.WIDTH,TitleScreen.HEIGHT);
        g.drawRect((int)player.getX(),(int)player.getY(),player.getWidth(),player.getHeight());

    }
}
