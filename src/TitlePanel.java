import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TitlePanel extends GamePanel {
    final String TITLE_IMAGE_PATH = "images/titleScreen.png";
    BufferedImage background;
    int x;
    int y;

    public TitlePanel(GameManager manager){
        super(manager);
        background = null;
        x=0;
        y=0;
        try{
            background = ImageIO.read(TitlePanel.class.getResource(TITLE_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.print("AAAAAA");
        }

        setBackground(Color.cyan);

    }
    public void tick(ArrayList<Integer> keys){
        super.tick(keys);
    }
    public void click(){

    }
    public void update(ArrayList<Integer> keys){
        for(int i: keys){
            if(i==KeyEvent.VK_DOWN){
                y+=2;
            }
            if(i==KeyEvent.VK_UP){
                y-=2;
            }
            if(i==KeyEvent.VK_LEFT){
                x-=2;
            }
            if(i==KeyEvent.VK_RIGHT){
                x+=2;
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawRect(x,y,50,50);

    }

}
