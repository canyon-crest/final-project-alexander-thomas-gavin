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

    public Button(int x, int y, int width, int height,Image imageUnselected, Image imageSelected, JPanel jPanel){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageUnselected = imageUnselected;
        this.imageSelected = imageSelected;
        this.jPanel = jPanel;

    }
    //runs every tick
    public void tick(double mouseX, double mouseY){
        //checks to see if the mouse is hovering over the button
        selected = mouseX <= x + width && mouseX >= x && mouseY <= y + height && mouseY >= y;

    }
    //returns one of two images depending on whether a player hovers over a button
    public Image getImage(){
        if(selected){
            return imageSelected;
        }
        return imageUnselected;
    }
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
