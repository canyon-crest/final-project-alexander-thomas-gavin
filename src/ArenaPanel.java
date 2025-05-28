import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class ArenaPanel extends GamePanel {
	public static final int SCALE = TitleScreen.SCALE;
	public static final int W = TitleScreen.WIDTH;
	public static final int H = TitleScreen.HEIGHT;

    private Player player;
    private Enemy enemy;

    private ArrayList<Integer> prevKeys;
    private double prevDirection;
    final String BACKGROUND_IMAGE_PATH = "images/arena/arenaBackground.png";
    final String TOPWALL_IMAGE_PATH = "images/arena/arenaTopwall.png";
    final String MAINWALL_IMAGE_PATH = "images/arena/arenaMainwall.png";
    
    private Image background;
    private Image topwall;
    private Image mainwall;
    private int transitionTimer;
    private boolean ending;
    private int endingType;
    public ArenaPanel(GameManager manager, JFrame frame){
        super(manager, frame);
        setBackground(Color.cyan);
        background = null;
        topwall = null;
        mainwall = null;
        loadImages();
        prevKeys = new ArrayList<Integer>();
        prevDirection = 0;
        player = new Player((W-60)/(2.0*SCALE),800.0/SCALE,1.2/SCALE,60/SCALE,60/SCALE,100);
        enemy = new Enemy((W-90)/(2.0*SCALE), H/(3.0*SCALE),1.6/SCALE,90/SCALE,90/SCALE,1000,player);
        transitionTimer = 30;
        ending = false;
        endingType = 0;



    }
    public void restart() {
        //called when the player leaves the arena panel
    	ending = true;
    	transitionTimer = 30;

    }
    @Override
    public void click(int x, int y) {
        //called when a player clicks
    	if(transitionTimer <= 0) {
    	
	    	if(prevDirection % (Math.PI/2d) < 0.1) {
	    		
	    		player.swordAttack(prevDirection,(int)(player.getWidth()+Math.abs(player.getWidth()*Math.cos(prevDirection))/3+Math.abs(player.getWidth()*Math.sin(prevDirection))),(int)(player.getHeight()+Math.abs(player.getWidth()*Math.sin(prevDirection))/3+Math.abs(player.getWidth()*Math.cos(prevDirection))));
	    	}
	    	else {
	    		player.swordAttack(prevDirection,(int)(player.getWidth()+Math.abs(player.getWidth()*Math.sin(prevDirection))),(int)(player.getHeight()+Math.abs(player.getWidth()*Math.cos(prevDirection))));
	    	}
    	}
    }
    
    private void loadImages(){
        //load images for ArenaPanel
    	try{
            background = ImageIO.read(ArenaPanel.class.getResource(BACKGROUND_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading background failed");
        }
    	try{
            topwall = ImageIO.read(ArenaPanel.class.getResource(TOPWALL_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading topwall failed");
        }
    	try{
            mainwall = ImageIO.read(ArenaPanel.class.getResource(MAINWALL_IMAGE_PATH)).getScaledInstance(W/SCALE,H/SCALE,Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            System.out.println("Loading mainwall failed");
        }
    }

    //called every frame
    @Override
    public void update(ArrayList<Integer> keys) {

    	if(transitionTimer <= 0) {
            if(enemy.getHealth() <= 0){
                endingType = 1;
                restart();
            }
            else if(player.getHealth() <= 0) {
                endingType = 2;
                restart();
            }

	        for(int i = 0; i < Entity.getAllEntities().size(); i++) {
	            if(Entity.getAllEntities().get(i).tick()){
	                i--;
	            }
	        }
	        //directions
	        boolean up = false;
	        boolean down = false;
	        boolean left = false;
	        boolean right = false;
	        //dash
	        boolean space = false;
	        boolean prevSpace = false;
	        double direction = prevDirection;
	        for(int i: keys){
	        	if(i == KeyEvent.VK_ESCAPE) {
	        		restart();
	        	}
	            //directions
	            if(i == KeyEvent.VK_UP || i == KeyEvent.VK_W){
	                up = true;
	            }
	            if(i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S){
	                down = true;
	            }
	            if(i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A){
	                left = true;
	            }
	            if(i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D){
	                right = true;
	            }
	            //dash
	            if(i == KeyEvent.VK_SPACE){
	                space = true;
	
	
	            }
	        }

	        for(int i: prevKeys){
	            if(i == KeyEvent.VK_SPACE){
	                prevSpace = true;
	
	            }
	        }
	        //moving directions
	        if(up){
	            direction = 3*Math.PI/2;
	        }
	        if(down){
	            direction = Math.PI/2;
	        }
	        if(left){
	            direction = Math.PI;
	        }
	        if(right){
	            direction = 0;
	        }
	
	
	        if(up && right){
	            direction = 7*Math.PI/4;
	        }
	        if(down && right){
	            direction = Math.PI/4;
	        }
	        if(up && left){
	            direction = 5*Math.PI/4;
	        }
	        if(down && left){
	            direction = 3*Math.PI/4;
	        }
	
	
	        if(up||down||left||right) {
	            player.changeVelocity(direction, 10/SCALE);
	        }
	        //dashing
	        if(space && !prevSpace){
	            player.dash(direction,26/SCALE);
	        }
	        player.setCurrentDirection(direction);
	        prevDirection = direction;
	
	
	        prevKeys = new ArrayList<Integer>(keys);
    	}
    	else {
    		transitionTimer--;
    		if(ending && transitionTimer == 0) {
                if(endingType==0) {
                    getGameManager().returnToMain();
                }
                else if(endingType == 1){
                    getGameManager().arenaToWin(0);
                }
    		}
    	}

    }
    //draws graphics
    public void paintComponent(Graphics g){
        //draws background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,TitleScreen.WIDTH/SCALE,TitleScreen.HEIGHT/SCALE);
        g.drawImage(background, 0, 0, null);
        g.drawImage(topwall, 0, 0, null);
        g.drawImage(player.getImage(), (int) player.getImageX(), (int) player.getImageY(), null);
        //draws hitboxes
        for(Entity e: Entity.getAllEntities()) {
            g.setColor(e.getColor());
            if(e instanceof EntropyBeam){
                EntropyBeam eb = (EntropyBeam) e;
                if(eb.isActive()) {
                    double newAngle = eb.getAngle()-Math.PI/2;
                    double newX = Math.cos(newAngle);
                    double newY = Math.sin(newAngle);
                    g.drawLine((int) (eb.getX()+eb.getWidth()*newX), (int)(eb.getY()+eb.getWidth()*newY), (int)(eb.getEndX()+eb.getWidth()*newX), (int)(eb.getEndY()+eb.getWidth()*newY));
                    g.drawLine((int) (eb.getX()-eb.getWidth()*newX), (int)(eb.getY()-eb.getWidth()*newY), (int)(eb.getEndX()-eb.getWidth()*newX), (int)(eb.getEndY()-eb.getWidth()*newY));
                }
                else{
                    g.drawLine((int) eb.getX(), (int) eb.getY(), eb.getEndX(), eb.getEndY());
                }
            }
            else if(e instanceof Slam) {
            	g.drawOval((int)e.getX()-e.getWidth()/2, (int)e.getY()-e.getWidth()/2, e.getWidth(), e.getWidth());
            }
           
            else {
                g.drawRect((int) e.getX(), (int) e.getY(), e.getWidth(), e.getHeight());
            }
        }

        g.drawImage(mainwall, 0, 0, null);
        //draws health bars
        int xLocHealth = 100/SCALE;
        g.setColor(Color.RED);
        for(int i = 10; i <= player.getHealth(); i+=10){
            g.fillRect(xLocHealth,100/SCALE,30/SCALE,50/SCALE);
            xLocHealth += 40/SCALE;
        }
        if(player.getHealth()%10 > 0) {
            g.fillRect(xLocHealth, 100 / SCALE, (3*(player.getHealth() % 10)) / SCALE, 50 / SCALE);
        }
        xLocHealth = 100/SCALE;
        for(int i = 10; i <= player.getMaxHealth(); i+=10){
            g.setColor(Color.WHITE);
            g.drawRect(xLocHealth-1,100/SCALE-1,30/SCALE+1,50/SCALE+1);
            xLocHealth += 40/SCALE;
        }
        xLocHealth = 100/SCALE;
        g.setColor(new Color(53,24,87));
        for(int i = 100; i <= enemy.getHealth(); i+=100){
            g.fillRect(xLocHealth,1000/SCALE,100/SCALE,50/SCALE);
            xLocHealth += 110/SCALE;
        }
        int enemyShield = enemy.getShield();
        if(enemy.getHealth()%100 > 0) {
            g.fillRect(xLocHealth, 1000 / SCALE, ((enemy.getHealth() % 100)) / SCALE, 50 / SCALE);
            g.setColor(Color.GRAY);
            if(enemyShield >= 100-(enemy.getHealth() % 100)) {
                g.fillRect(xLocHealth + (enemy.getHealth() % 100)/SCALE, 1000 / SCALE, 100/SCALE - ((enemy.getHealth() % 100)) / SCALE, 50 / SCALE);
                enemyShield -= 100-(enemy.getHealth() % 100);
            }
            else if(enemyShield > 0){
                g.fillRect(xLocHealth + (enemy.getHealth() % 100)/SCALE, 1000 / SCALE, ((enemyShield % 100)) / SCALE, 50 / SCALE);
                enemyShield = 0;
            }
            xLocHealth += 110/SCALE;
        }
        g.setColor(Color.GRAY);
        for(int i = 100; i <= enemyShield; i+=100){
            g.fillRect(xLocHealth,1000/SCALE,100/SCALE,50/SCALE);
            xLocHealth += 110/SCALE;
        }
        if(enemyShield%100 > 0) {
            g.fillRect(xLocHealth, 1000 / SCALE, ((enemyShield % 100)) / SCALE, 50 / SCALE);
        }

        xLocHealth = 100/SCALE;
        for(int i = 100; i <= enemy.getMaxHealth(); i+=100){
            g.setColor(Color.WHITE);
            g.drawRect(xLocHealth-1,1000/SCALE-1,100/SCALE+1,50/SCALE+1);
            xLocHealth += 110/SCALE;
        }
        g.setColor(Color.CYAN);
        //handles transitions
        if(transitionTimer > 0) {
        	if(ending) {
        		g.setColor(new Color(0,0,0,(30-transitionTimer)*255/30));
        		g.fillRect(0, 0, TitleScreen.WIDTH/SCALE, TitleScreen.HEIGHT/SCALE);
        	}
        	else {
        		g.setColor(new Color(0,0,0,(transitionTimer)*255/30));
        		g.fillRect(0, 0, TitleScreen.WIDTH/SCALE, TitleScreen.HEIGHT/SCALE);
        	}
        }


    }
}
