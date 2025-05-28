import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class WinPanel extends GamePanel{
    private int transitionTimer;
    final String CUTSCENE_IMAGE_PATH = "images/cutscenes/victoryCutscene.png";
    private Image cutsceneImage;
    private boolean fadeOut = false;
    public WinPanel(GameManager gameManager, JFrame jFrame) {
        super(gameManager, jFrame);
        loadImages();
        transitionTimer = 20;

    }
    //loads all the images
    private void loadImages(){
        try{
            cutsceneImage = ImageIO.read(ArenaPanel.class.getResource(CUTSCENE_IMAGE_PATH)).getScaledInstance(TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading cutscene failed");
        }
    }
    //ends the cutscene when the player clicks
    @Override
    public void click(int x, int y) {
        fadeOut = true;
    }
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
    //draws the graphics
    public void paintComponent(Graphics g){
        g.drawImage(cutsceneImage, 0, 0, null);
        g.setColor(new Color(0,0,0,(transitionTimer*255)/20));
        g.fillRect(0,0,TitleScreen.WIDTH/TitleScreen.SCALE,TitleScreen.HEIGHT/TitleScreen.SCALE);

    }
}
