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
	/**
	 *  constructor for game manager
	 */
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

	/**
	 *  gets when an action is performed
	 * @param e action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		current.tick(keysPressed);


	}
	/**
	 *  method that gets when a key is typed
	 * @param e action event
	 */
	@Override
	public void keyTyped(KeyEvent e) {


	}
	/**
	 *  method that gets when a key is pressed
	 * @param e action event
	 */
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
	/**
	 *  method that gets when a key is released
	 * @param e action event
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i < keysPressed.size(); i++){
			if(keysPressed.get(i).intValue() == e.getKeyCode()){
				keysPressed.remove(i--);
			}
		}

	}
	/**
	 *  method that gets when the mouse is clicked
	 * @param e mouse event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	/**
	 *  method that gets when the mouse is pressed
	 * @param e mouse event
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		current.click(e.getX(),e.getY());
	}
	/**
	 *  method that gets when the mouse is released
	 * @param e mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	/**
	 *  method that gets when the mouse enters
	 * @param e mouse event
	 */

	@Override
	public void mouseEntered(MouseEvent e) {

	}
	/**
	 *  method that gets when the mouse exits
	 * @param e mouse event
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}
	/**
	 *  method that runs when the game returns to menu
	 */
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
	/**
	 *  method that runs when the intro starts
	 */
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
	/**
	 *  method that runs after a win
	 */
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
	/**
	 *  method that runs to return from the win screen to the start screen
	 */
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
	/**
	 *  method that takes you to the loss screen from the arena screen
	 */
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
	/**
	 *  method that returns from the loss screen to the title screen
	 */
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
	/**
	 *  method that loads the arena
	 */
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
