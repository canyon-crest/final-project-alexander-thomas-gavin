import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class TitleCanvas extends Canvas{
	final String TITLE_IMAGE_PATH = "/download.jpg";
	BufferedImage background;
	public TitleCanvas() {
		setBackground(Color.CYAN);
		background = null;
		try{
			background = ImageIO.read(new File(TITLE_IMAGE_PATH));
		}
		catch(IOException e) {
			System.out.print("AAAAAA");
		}
		
		
	}
	public void paint(Graphics g) {
		g.drawRect(50, 50, 50, 50);
		g.drawImage(background, 0, 0, null);
	
	}
}
