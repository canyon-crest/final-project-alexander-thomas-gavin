import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class GamePanel extends JPanel {
    public GamePanel(GameManager gameManager){
        setPreferredSize(new Dimension(TitleScreen.WIDTH, TitleScreen.HEIGHT));
        addKeyListener(gameManager);
        setFocusable(true);
    }
    public void tick(ArrayList<Integer> keys){
        update(keys);
        repaint();
    }
    public abstract void click();
    public abstract void update(ArrayList<Integer> keys);

}
