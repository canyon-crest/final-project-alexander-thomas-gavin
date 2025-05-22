import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameManager implements ActionListener, KeyListener, MouseListener {
	private JFrame frame;
	private GamePanel current;
	private TitlePanel title;
	private ArenaPanel arena;
	private Timer timer;
	private ArrayList<Integer> keysPressed;
	public void startGame() {

		frame = new TitleScreen();
		title = new TitlePanel(this, frame);
		arena = new ArenaPanel(this,frame);
		current = title;
		frame.add(current);
		frame.pack();
		frame.setVisible(true);
		timer = new Timer(17,this);
		timer.start();
		keysPressed = new ArrayList<Integer>();


	}


	@Override
	public void actionPerformed(ActionEvent e) {
		current.tick(keysPressed);


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
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		current.click(e.getX(),e.getY());
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
	public void startArena(){
		frame.remove(current);
		current = arena;
		frame.add(current);
		frame.pack();
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
		
	}
}
