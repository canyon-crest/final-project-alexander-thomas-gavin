import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class GamePanel extends JPanel {
    private JFrame jFrame;
    private GameManager gameManager;
    /**
     *  constructor for gamepanel class
     * @param gameManager current game manager
     * @param jFrame current jFrame
     */
    public GamePanel(GameManager gameManager, JFrame jFrame){
        setPreferredSize(new Dimension(TitleScreen.WIDTH/TitleScreen.SCALE, TitleScreen.HEIGHT/TitleScreen.SCALE));
        this.jFrame = jFrame;
        this.gameManager = gameManager;
        addKeyListener(gameManager);
        addMouseListener(gameManager);
        setFocusable(true);
    }
    /**
     *  method that runs every tick
     * @param keys arraylist of all keys pressed
     */
    public void tick(ArrayList<Integer> keys){
        update(keys);
        repaint();
    }
    /**
     *  method that runs on click
     * @param x x location
     * @param y y location
     */
    public abstract void click(int x, int y);
    /**
     *  method that runs every frame to update things
     */
    public abstract void update(ArrayList<Integer> keys);
    /**
     *  method that returns gameManager
     * @return gameManager
     */

    public GameManager getGameManager(){
        return gameManager;
    }
    /**
     *  method that returns jFrame
     * @return jFrame
     */
    public JFrame getJFrame(){
        return jFrame;
    }

}
