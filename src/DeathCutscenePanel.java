import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class DeathCutscenePanel extends GamePanel{
    private int transitionTimer;
    final String BACKGROUND_IMAGE_PATH = "images/cutscenes/deathBackground.png";
    final String TEXT_IMAGE_PATH = "images/cutscenes/deathText.png";
    final String SELECTED_IMAGE_PATH = "images/cutscenes/deathSelected.png";
    final String UNSELECTED_IMAGE_PATH = "images/cutscenes/deathUnselected.png";
    private Image cutsceneImage;
    private Image textImage;
    private Image selectedImage;
    private Image unselectedImage;
    private Button retryButton;
    private boolean fadeOut = false;
    /**
     *  constructor for the DeathCutscenePanel
     * @param gameManager the current gameMangager
     * @param jFrame the current jFrame
     */
    public DeathCutscenePanel(GameManager gameManager, JFrame jFrame) {
        super(gameManager, jFrame);
        loadImages();
        retryButton= new Button(712/TitleScreen.SCALE,799/TitleScreen.SCALE,496/TitleScreen.SCALE,209/TitleScreen.SCALE,unselectedImage,selectedImage,this);
        transitionTimer = 20;

    }
    /**
     *  method that loads all images
     */
    //loads all the images
    private void loadImages(){
        try{
            cutsceneImage = ImageIO.read(ArenaPanel.class.getResource(BACKGROUND_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
        try{
            textImage = ImageIO.read(ArenaPanel.class.getResource(TEXT_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
        try{
            selectedImage = ImageIO.read(ArenaPanel.class.getResource(SELECTED_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
        try{
            unselectedImage = ImageIO.read(ArenaPanel.class.getResource(UNSELECTED_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
    }

    //ends the cutscene when the player clicks
    //tests if button is pressed
    /**
     *  method that runs when a click happens to fade out the screen
     * @param x the mouse x location
     * @param y the mouse y location
     */
    @Override
    public void click(int x, int y) {
        if(retryButton.click()){
            fadeOut = true;
        }
    }
    //runs every game tick
    /**
     *  method that runs every tick
     * @param keys an arraylist of all keys that are pressed
     */
    @Override
    public void update(ArrayList<Integer> keys) {
        //gets mouse X and mouse Y and passes it to the button
        Point location = this.getMousePosition();
        if(location == null) {
            location = new Point(0,0);
        }
        retryButton.tick(location.getX(),location.getY());
        //handles fading in/out
        if(!fadeOut && transitionTimer > 0){
            transitionTimer--;
        }
        else{
            if(fadeOut) {
                transitionTimer++;
                if(transitionTimer == 20){
                    getGameManager().loseToTitle();
                }
            }

        }
    }
    /**
     *  method that runs every tick and draws graphics
     */
    //draws the graphics
    public void paintComponent(Graphics g){
        g.drawImage(cutsceneImage, 0, 0, null);
        g.drawImage(textImage, 0, 0, null);
        g.drawImage(retryButton.getImage(), 0, 0, null);
        g.setColor(new Color(0,0,0,(transitionTimer*255)/20));
        g.fillRect(0,0,TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE);
        Point location = this.getMousePosition();

    }
}
