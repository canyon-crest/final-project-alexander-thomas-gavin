import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class WinPanel extends GamePanel{
    private int transitionTimer;
    final String CUTSCENE_IMAGE_PATH = "images/cutscenes/victoryCutscene.png";
    private Image cutsceneImage;
    private int score;
    private boolean fadeOut = false;
    /**
     *  constructor method for the WinPanel
     * @param gameManager the current gameManager
     * @param jFrame the current jFrame
     */
    public WinPanel(GameManager gameManager, JFrame jFrame) {
        super(gameManager, jFrame);
        loadImages();
        transitionTimer = 20;

    }
    /**
     *  method that loads all the images
     */
    //loads all the images
    private void loadImages(){
        try{
            cutsceneImage = ImageIO.read(ArenaPanel.class.getResource(CUTSCENE_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
    }
    /**
     *  method that sets the displayed score
     * @param score the score to display
     */
    /*sets the displayed score*/
    public void setScore(int score){
        this.score = score;
    }
    /**
     *  method that runs on click
     * @param x x location of the mouse
     * @param y y location of the mouse
     */
    //ends the cutscene when the player clicks
    @Override
    public void click(int x, int y) {
        fadeOut = true;
    }
    /**
     *  method that runs every frame
     * @param keys arraylist of all keys that are pressed
     */
    //runs every game tick
    @Override
    public void update(ArrayList<Integer> keys) {
        //handles fading in/out
        if(!fadeOut && transitionTimer > 0){
            transitionTimer--;
        }
        else{
            if(fadeOut) {
                transitionTimer++;
                if(transitionTimer == 20){
                    getGameManager().winToStart();
                }
            }

        }
    }
    /**
     *  method that runs every frame and displays graphics
     * @param g graphics
     */
    //draws the graphics
    public void paintComponent(Graphics g){
        g.drawImage(cutsceneImage, 0, 0, null);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Font",Font.PLAIN,80/TitleScreen.SCALE));
        g.drawString(score+"",625/TitleScreen.SCALE,469/TitleScreen.SCALE);
        g.setColor(new Color(0,0,0,(transitionTimer*255)/20));
        g.fillRect(0,0,TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE);

    }
}
