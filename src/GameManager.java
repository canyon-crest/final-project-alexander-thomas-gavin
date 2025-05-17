import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameManager implements ActionListener, KeyListener, MouseListener {
	TitlePanel title;
	Timer timer;
	ArrayList<Integer> keysPressed;
	public void startGame() {

		JFrame frame = new TitleScreen();
		title = new TitlePanel(this, frame);
		frame.add(title);
		frame.setVisible(true);
		timer = new Timer(17,this);
		timer.start();
		keysPressed = new ArrayList<Integer>();


	}


	@Override
	public void actionPerformed(ActionEvent e) {
		title.tick(keysPressed);


	}

	@Override
	public void keyTyped(KeyEvent e) {


	}

	@Override
	public void keyPressed(KeyEvent e) {
		for(int i = 0; i < keysPressed.size(); i++){
			if(keysPressed.get(i).intValue() == e.getKeyCode()){
				keysPressed.remove(i--);
				break;
			}
		}
		keysPressed.add(e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i < keysPressed.size(); i++){
			if(keysPressed.get(i).intValue() == e.getKeyCode()){
				keysPressed.remove(i--);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		title.click(e.getX(),e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
