import java.awt.*;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> images;
    private ArrayList<Integer> timing;
    private int currentImage;
    private boolean started;
    private int timer;
    public Animation(){
        images = new ArrayList<Image>();
        timing = new ArrayList<Integer>();
        currentImage = 0;
        started = false;
        timer = 0;
    }
    public Animation(ArrayList<Image> images,ArrayList<Integer> timing){
        this.images = images;
        this.timing = timing;
        currentImage = 0;
        started = false;
        timer = 0;
    }
    public Animation(ArrayList<Image> images,int times){
        this.images = images;
        this.timing = new ArrayList<Integer>();
        for(int i = 0; i < images.size(); i++){
            this.timing.add(times);
        }
        currentImage = 0;
        started = false;
        timer = 0;
    }
    public Image getCurrentImage(){
        return images.get(currentImage);
    }
    public void startAnimation(){
        started = true;
        timer = timing.get(0);
        currentImage = 0;
    }
    public void stopAnimation(){
        started = false;
    }
    public void tick(){
        if(started){
            if(timer == 0){
                currentImage +=1;
                timer = timing.get(currentImage);
            }
            else {
                timer--;
            }
        }
    }

}
