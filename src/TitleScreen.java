import javax.swing.*;

public class TitleScreen extends JFrame {
	public final int WIDTH = 1920;
	public final int HEIGHT = 1080;
	public TitleScreen() {
		super("Arhangel");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
    
}
