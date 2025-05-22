import javax.swing.*;

public class TitleScreen extends JFrame {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int SCALE = 2;
	public TitleScreen() {
		super("Archangel");
		//setSize(WIDTH/SCALE,HEIGHT/SCALE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
	}
    
}
