import javax.swing.*;

public class TitleScreen extends JFrame {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public TitleScreen() {
		super("Archangel");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
    
}
