import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TitlePanel extends GamePanel {
	public static final int SCALE = TitleScreen.SCALE;
	public static final int W = TitleScreen.WIDTH;
	public static final int H = TitleScreen.HEIGHT;
    final String TITLE_IMAGE_PATH = "images/background.png";
    final String STARS1_IMAGE_PATH = "images/stars1.png";
    final String STARS2_IMAGE_PATH = "images/stars2.png";
    final String STARS3_IMAGE_PATH = "images/stars3.png";
    final String NAME_IMAGE_PATH = "images/titleName.png";
    final String START_IMAGE_PATH = "images/start.png";
    final String SELECTED_START_IMAGE_PATH = "images/selectedStart.png";
    private Image background;
    //private BufferedImage stars;
    private Image stars1;
    private Image stars2;
    private Image stars3;
    private Image title;
    private Button start;
    private int starX;
    private int titleX;
    private int starY;
    private int titleY;
    private double oscilation;
    private boolean transitioning;
    private int transitionTimer;
    private int score;
    /**
     *  constructor for TitlePanel
     * @param manager the current gameManager
     * @param frame the current jFrame
     */
    public TitlePanel(GameManager manager, JFrame frame){
        super(manager, frame);
        transitioning = false;
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
        transitionTimer = 0;
        oscilation = 0;
        score = 0;

        setBackground(Color.cyan);


    }
    /**
     *  method sets the score displayed
     * @param score the amount of score to set
     */
    public void setScore(int score){
        this.score = score;
    }
    /**
     *  method that loads all the images
     */
    private void loadImages(){
        try{
            background = ImageIO.read(TitlePanel.class.getResource(TITLE_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading background failed");
        }

        try{
            stars1 = ImageIO.read(TitlePanel.class.getResource(STARS1_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading stars1 failed");
        }
        try{
            stars2 = ImageIO.read(TitlePanel.class.getResource(STARS2_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading stars2 failed");
        }
        try{
            stars3 = ImageIO.read(TitlePanel.class.getResource(STARS3_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading stars3 failed");
        }
        try{
           title = ImageIO.read(TitlePanel.class.getResource(NAME_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {

            System.out.println("Loading title failed");
        }
        try{
            start = new Button(561/SCALE,523/SCALE,820/SCALE,126/SCALE,ImageIO.read(TitlePanel.class.getResource(START_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH)
                    ,ImageIO.read(TitlePanel.class.getResource(SELECTED_START_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH), this);
        }
        catch(IOException e) {
            start = new Button(561/SCALE,523/SCALE,820/SCALE,126/SCALE,null,null, this);
            System.out.println("Loading start button failed");
        }
    }
    /**
     *  method that runs every frame
     */
    public void tick(ArrayList<Integer> keys){
        super.tick(keys);

    }
    /**
     *  method that runs on click
     * @param x x location of the mouse
     * @param y y location of the mouse
     */
    public void click(int x, int y){
        if(start.click()){
        	transitionTimer = 30;
        	transitioning = true;
            
        }
    }
    /**
     *  method that fades the screen in
     */
    public void fadeIn() {
    	transitionTimer = 30;
    	transitioning = false;
    }
    /**
     *  method that runs every frame
     * @param keys arraylist of all keys pressed
     */
    
    public void update(ArrayList<Integer> keys){
        for(int i: keys){
            if(i == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }

        }
    	
    	Point location = this.getMousePosition();
    	if(location == null) {
    		location = new Point(0,0);
    	}
        
        //sets the locations of all moving parts such as stars
        if(isFocusOwner()){

            starX = (int)(-(30/SCALE)*(Math.atan(((double)TitleScreen.WIDTH/(2*SCALE)-location.getX())/((double)TitleScreen.WIDTH/(4*SCALE)))/Math.PI));
            starY = (int)(-(15/SCALE)*(Math.atan(((double)TitleScreen.HEIGHT/(2*SCALE)-location.getY())/((double)TitleScreen.HEIGHT/(4*SCALE)))/Math.PI));

        }
        else{
            starX = 0;
            starY = 0;
        }
        start.tick(location.getX(),location.getY());

        titleY = (int)((15/SCALE)*Math.sin(oscilation));

        oscilation = oscilation + 0.04;
        if(oscilation > 2*Math.PI){
            oscilation -= 2*Math.PI;
        }
        //handles transitions
        if(transitionTimer > 0) {
        	transitionTimer --;
        }
        if(transitioning) {
    		
    		if(transitionTimer <= 0) {
    			getGameManager().startIntro();
    		}
    	}
        
    }
    /**
     *  method that runs every frame to handle graphics
     * @param g graphics
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(stars1, (int)(starX*1.2), (int)(starY*1.2), null);
        g.drawImage(stars2, (int)(starX*0.6), (int)(starY*0.6), null);
        g.drawImage(stars3, (int)(starX*2.2), (int)(starY*2.2), null);
        g.drawImage(title,titleX,titleY,null);
        g.drawImage(start.getImage(),0,0,null);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Font",Font.PLAIN,80/SCALE));
        g.drawString(score+"",1172/SCALE,738/SCALE);
        if(transitioning) {
        	g.setColor(new Color(0,0,0,(30-transitionTimer)*255/30));
        	g.fillRect(0, 0, TitleScreen.WIDTH/SCALE, TitleScreen.HEIGHT/SCALE);
        }
        else if(transitionTimer > 0) {
        	g.setColor(new Color(0,0,0,(transitionTimer)*255/30));
        	g.fillRect(0, 0, TitleScreen.WIDTH/SCALE, TitleScreen.HEIGHT/SCALE);
        }

    }

}
