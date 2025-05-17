import java.awt.image.BufferedImage;

public class Button{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean selected;
    private BufferedImage imageUnselected;
    private BufferedImage imageSelected;

    public Button(int x, int y, int width, int height,BufferedImage imageUnselected, BufferedImage imageSelected){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageUnselected = imageUnselected;
        this.imageSelected = imageSelected;

    }

    public void tick(double mouseX, double mouseY){
        selected = mouseX <= x + width && mouseX >= x && mouseY <= y + height && mouseY >= y;

    }
    public BufferedImage getImage(){
        if(selected){
            return imageSelected;
        }
        return imageUnselected;
    }



}
