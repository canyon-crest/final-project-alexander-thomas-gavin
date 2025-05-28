import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class GamePanel extends JPanel {
    private JFrame jFrame;
    private GameManager gameManager;
    public GamePanel(GameManager gameManager, JFrame jFrame){
        setPreferredSize(new Dimension(TitleScreen.WIDTH/TitleScreen.SCALE, TitleScreen.HEIGHT/TitleScreen.SCALE));
        this.jFrame = jFrame;
        this.gameManager = gameManager;
        addKeyListener(gameManager);
        addMouseListener(gameManager);
        setFocusable(true);
    }
    public void tick(ArrayList<Integer> keys){
        update(keys);
        repaint();
    }
    public abstract void click(int x, int y);
    public abstract void update(ArrayList<Integer> keys);

    public GameManager getGameManager(){
        return gameManager;
    }
    public JFrame getJFrame(){
        return jFrame;
    }

}
