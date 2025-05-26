import java.awt.*;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> images;
    private ArrayList<Integer> timing;
    private int currentImage;
    private boolean started;
    private int timer;
    private boolean repeating;
    public Animation(){
        images = new ArrayList<Image>();
        timing = new ArrayList<Integer>();
        repeating = false;
        currentImage = 0;
        started = false;
        timer = 0;
    }
    public Animation(ArrayList<Image> images,ArrayList<Integer> timing,boolean repeating){
        this.images = images;
        this.timing = timing;
        this.repeating = repeating;
        currentImage = 0;
        started = false;
        timer = 0;
    }
    public Animation(ArrayList<Image> images,int times, boolean repeating){
        this.images = images;
        this.repeating = repeating;
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
    public boolean animationStarted(){
        return started;
    }
    public void addImage(Image image, int nextTiming){
        images.add(image);
        timing.add(nextTiming);
    }
    public void stopAnimation(){
        started = false;
    }
    public void tick(){
        if(started){
            if(timer == 0){
                currentImage +=1;
                if(currentImage >= images.size()){
                    if(repeating){
                        startAnimation();
                    }
                    else {
                        stopAnimation();
                    }
                }
                else {
                    timer = timing.get(currentImage);
                }

            }
            else {
                timer--;
            }
        }
    }

}
