import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TitlePanel extends GamePanel {
    final String TITLE_IMAGE_PATH = "images/background.png";
    final String STARS_IMAGE_PATH = "images/star.png";
    final String NAME_IMAGE_PATH = "images/titleName.png";
    final String START_IMAGE_PATH = "images/start.png";
    final String SELECTED_START_IMAGE_PATH = "images/selectedStart.png";
    BufferedImage background;
    BufferedImage stars;
    BufferedImage title;
    Button start;
    int x;
    int starX;
    int titleX;
    int y;
    int starY;
    int titleY;
    double oscilation;

    public TitlePanel(GameManager manager){
        super(manager);
        background = null;
        stars = null;
        title = null;
        loadImages();
        x=0;
        y=0;
        titleX = 0;
        titleY = 0;
        starX = 0;
        starY = 0;

        oscilation = 0;

        setBackground(Color.cyan);


    }
    private void loadImages(){
        try{
            background = ImageIO.read(TitlePanel.class.getResource(TITLE_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.println("Loading background failed");
        }
        try{
            stars = ImageIO.read(TitlePanel.class.getResource(STARS_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.println("Loading stars failed");
        }
        try{
           title = ImageIO.read(TitlePanel.class.getResource(NAME_IMAGE_PATH));
        }
        catch(IOException e) {

            System.out.println("Loading title failed");
        }
        try{
            start = new Button(581,556,803,119,ImageIO.read(TitlePanel.class.getResource(START_IMAGE_PATH))
                    ,ImageIO.read(TitlePanel.class.getResource(SELECTED_START_IMAGE_PATH)));
        }
        catch(IOException e) {
            start = new Button(581,556,803,119,null,null);
            System.out.println("Loading start button failed");
        }
    }
    public void tick(ArrayList<Integer> keys){
        super.tick(keys);

    }
    public void click(){

    }
    public void update(ArrayList<Integer> keys){
        Point location = MouseInfo.getPointerInfo().getLocation();
        if(isFocusOwner()){

            starX = (int)(-30*(Math.atan(((double)TitleScreen.WIDTH/2-location.getX())/((double)TitleScreen.WIDTH/4))/Math.PI));
            starY = (int)(-15*(Math.atan(((double)TitleScreen.HEIGHT/2-location.getY())/((double)TitleScreen.HEIGHT/4))/Math.PI));

        }
        else{
            starX = 0;
            starY = 0;
        }
        start.tick(location.getX(),location.getY());

        titleY = (int)(15*Math.sin(oscilation));


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

        oscilation = oscilation + 0.04;
        if(oscilation > 2*Math.PI){
            oscilation -= 2*Math.PI;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(stars, starX, starY, null);
        g.drawImage(title,titleX,titleY,null);
        g.drawImage(start.getImage(),0,0,null);
        g.drawRect(x,y,50,50);


    }

}
