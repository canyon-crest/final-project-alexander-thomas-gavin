import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameManager implements ActionListener, KeyListener, MouseListener {
	private JFrame frame;
	private GamePanel current;
	private TitlePanel title;
	private ArenaPanel arena;
	private WinPanel win;
	private IntroCutscenePanel intro;
	private DeathCutscenePanel death;
	private int highestScore;
	private Timer timer;
	private ArrayList<Integer> keysPressed;
	public void startGame() {

		frame = new TitleScreen();
		title = new TitlePanel(this, frame);
		arena = new ArenaPanel(this,frame);
		intro = new IntroCutscenePanel(this,frame);
		win = new WinPanel(this,frame);
		death = new DeathCutscenePanel(this,frame);
		current = title;
		frame.add(current);
		frame.pack();
		frame.setVisible(true);
		timer = new Timer(17,this);
		timer.start();
		keysPressed = new ArrayList<Integer>();
		highestScore = 0;


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
	//runs when arena panel goes back to main panel
	public void returnToMain() {
		frame.remove(current);
		current = title;
		title.setScore(highestScore);
		Entity.clearEntities();
		arena = new ArenaPanel(this,frame);
		frame.add(current);
		frame.pack();
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
		title.fadeIn();
	}
	//runs when the main screen transitions to the cutscene
	public void startIntro(){
		frame.remove(current);
		title = new TitlePanel(this,frame);
		title.setScore(highestScore);
		current = intro;
		frame.add(current);
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
	}
	//runs after a win
	public void arenaToWin(int score){
		highestScore = Math.max(score,highestScore);
		title.setScore(highestScore);
		frame.remove(current);
		Entity.clearEntities();
		arena = new ArenaPanel(this, frame);
		win.setScore(score);
		current = win;
		frame.add(current);
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
	}
	//runs after a win cutscene
	public void winToStart(){
		frame.remove(current);
		title.setScore(highestScore);
		win = new WinPanel(this, frame);
		current = title;
		frame.add(current);
		title.fadeIn();
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
	}
	//runs after the player dies
	public void arenaToLose(){
		frame.remove(current);
		title.setScore(highestScore);
		Entity.clearEntities();
		arena = new ArenaPanel(this, frame);
		current = death;
		frame.add(current);
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
	}
	//runs when the player presses retry
	public void loseToTitle(){
		frame.remove(current);
		title.setScore(highestScore);
		death = new DeathCutscenePanel(this, frame);
		current = title;
		frame.add(current);
		title.fadeIn();
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
	}
	//runs after the first cutscene
	public void startArena(){
		frame.remove(current);
		title.setScore(highestScore);
		intro = new IntroCutscenePanel(this,frame);
		current = arena;
		frame.add(current);
		frame.pack();
		frame.repaint();
		frame.revalidate();
		current.requestFocusInWindow();
		
	}
}
