import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class IntroCutscenePanel extends GamePanel{
    private int transitionTimer;
    final String CUTSCENE_IMAGE_PATH = "images/cutscenes/introCutscene.png";
    private Image cutsceneImage;
    private boolean fadeOut = false;
    /**
     *  constructor method for the intro cutscene
     * @param gameManager the current gameManager
     * @param jFrame the current jFrame
     */
    public IntroCutscenePanel(GameManager gameManager, JFrame jFrame) {
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
     *  method that runs on mouse click
     * @param x x location of mouse
     * @param y y location of mouse
     */

    //ends the cutscene when the player clicks
    @Override
    public void click(int x, int y) {
        fadeOut = true;
    }
    /**
     *  method that runs every frame
     * @param keys all keys that are pressed
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
                    getGameManager().startArena();
                }
            }

        }
    }
    /**
     *  method that runs every frame to draw graphics
     */
    //draws the graphics
    public void paintComponent(Graphics g){
        g.drawImage(cutsceneImage, 0, 0, null);
        g.setColor(new Color(0,0,0,(transitionTimer*255)/20));
        g.fillRect(0,0,TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE);

    }
}
