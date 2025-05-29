import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Button{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean selected;
    private Image imageUnselected;
    private Image imageSelected;
    private JPanel jPanel;
    /**
     *  constructor for Button class
     * @param x x location
     * @param y y location
     * @param width width of the button
     * @param height height of the hitbox
     * @param imageUnselected the image when the button is not selected
     * @param imageSelected the image when the button is selected
     * @param jPanel the current jPanel
     */
    public Button(int x, int y, int width, int height,Image imageUnselected, Image imageSelected, JPanel jPanel){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageUnselected = imageUnselected;
        this.imageSelected = imageSelected;
        this.jPanel = jPanel;

    }
    /**
     *  method that runs every tick
     * @param mouseX the mouse x location
     * @param mouseY the mouse y location
     */
    public void tick(double mouseX, double mouseY){
        //checks to see if the mouse is hovering over the button
        selected = mouseX <= x + width && mouseX >= x && mouseY <= y + height && mouseY >= y;

    }
    /**
     *  method that gives the current image of the button
     * @return the current image
     */
    //returns one of two images depending on whether a player hovers over a button
    public Image getImage(){
        if(selected){
            return imageSelected;
        }
        return imageUnselected;
    }
    /**
     *  method that runs on click
     * @return whether the button was clicked
     */
    //returns whether the button was clicked
    public boolean click(){
    	Point actualLoc = jPanel.getMousePosition();
    	if(actualLoc == null) {
    		return false;
    	}
        double mouseX = actualLoc.getX();
        double mouseY = actualLoc.getY();
        return mouseX <= x + width && mouseX >= x && mouseY <= y + height && mouseY >= y;
    }



}
