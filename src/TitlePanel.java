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
    final String STARS1_IMAGE_PATH = "images/stars1.png";
    final String STARS2_IMAGE_PATH = "images/stars2.png";
    final String STARS3_IMAGE_PATH = "images/stars3.png";
    final String NAME_IMAGE_PATH = "images/titleName.png";
    final String START_IMAGE_PATH = "images/start.png";
    final String SELECTED_START_IMAGE_PATH = "images/selectedStart.png";
    private BufferedImage background;
    //private BufferedImage stars;
    private BufferedImage stars1;
    private BufferedImage stars2;
    private BufferedImage stars3;
    private BufferedImage title;
    private Button start;
    private int starX;
    private int titleX;
    private int starY;
    private int titleY;
    private double oscilation;

    public TitlePanel(GameManager manager, JFrame frame){
        super(manager, frame);
        background = null;
        //stars = null;
        stars1 = null;
        stars2 = null;
        stars3 = null;
        title = null;
        loadImages();
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
//        try{
//            stars = ImageIO.read(TitlePanel.class.getResource(STARS_IMAGE_PATH));
//        }
//        catch(IOException e) {
//            System.out.println("Loading stars failed");
//        }
        try{
            stars1 = ImageIO.read(TitlePanel.class.getResource(STARS1_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.println("Loading stars1 failed");
        }
        try{
            stars2 = ImageIO.read(TitlePanel.class.getResource(STARS2_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.println("Loading stars2 failed");
        }
        try{
            stars3 = ImageIO.read(TitlePanel.class.getResource(STARS3_IMAGE_PATH));
        }
        catch(IOException e) {
            System.out.println("Loading stars3 failed");
        }
        try{
           title = ImageIO.read(TitlePanel.class.getResource(NAME_IMAGE_PATH));
        }
        catch(IOException e) {

            System.out.println("Loading title failed");
        }
        try{
            start = new Button(571,555,820,126,ImageIO.read(TitlePanel.class.getResource(START_IMAGE_PATH))
                    ,ImageIO.read(TitlePanel.class.getResource(SELECTED_START_IMAGE_PATH)), getJFrame());
        }
        catch(IOException e) {
            start = new Button(571,555,820,126,null,null, getJFrame());
            System.out.println("Loading start button failed");
        }
    }
    public void tick(ArrayList<Integer> keys){
        super.tick(keys);

    }
    public void click(int x, int y){
        if(start.click()){
            getGameManager().startArena();
        }
    }
    public void update(ArrayList<Integer> keys){

        Point location = getJFrame().getMousePosition();
        if(location == null){
            location = new Point(0,0);
        }

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

        oscilation = oscilation + 0.04;
        if(oscilation > 2*Math.PI){
            oscilation -= 2*Math.PI;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(stars1, (int)(starX*1.2), (int)(starY*1.2), null);
        g.drawImage(stars2, (int)(starX*0.6), (int)(starY*0.6), null);
        g.drawImage(stars3, (int)(starX*2.2), (int)(starY*2.2), null);
        g.drawImage(title,titleX,titleY,null);
        g.drawImage(start.getImage(),0,0,null);

    }

}
