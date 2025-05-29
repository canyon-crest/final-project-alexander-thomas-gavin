import java.awt.*;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> images;
    private ArrayList<Integer> timing;
    private int currentImage;
    private boolean started;
    private int timer;
    private boolean repeating;
    /**
     *  constructor for an animation
    */
    public Animation(){
        images = new ArrayList<Image>();
        timing = new ArrayList<Integer>();
        repeating = false;
        currentImage = 0;
        started = false;
        timer = 0;
    }
    /**
     *  constructor for an animation
     *  @param images list of images in animation
     *  @param timing list of timings of each frame
     *  @param repeating whether the animation repeats
     */
    public Animation(ArrayList<Image> images,ArrayList<Integer> timing,boolean repeating){
        this.images = images;
        this.timing = timing;
        this.repeating = repeating;
        currentImage = 0;
        started = false;
        timer = 0;
    }
    /**
     *  constructor for an animation
     *  @param images list of images in animation
     *  @param times timing of each frame
     *  @param repeating whether the animation repeats
     */
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


    /**
     *  gets the current frame
     *  @return the image from the current frame
     */
    public Image getCurrentImage(){
        return images.get(currentImage);
    }

    /**
     *  starts the animations
     */
    public void startAnimation(){
        started = true;
        timer = timing.get(0);
        currentImage = 0;
    }

    /**
     *  whether the animation has started
     *  @return a boolean representing whether the animation started
     */
    public boolean animationStarted(){
        return started;
    }

    /**
     *  adds an image to the images
     *  @param image the image to add to the arraylist
     *  @param nextTiming how many frames the image should be
     */
    public void addImage(Image image, int nextTiming){
        images.add(image);
        timing.add(nextTiming);
    }

    /**
     *  stops the animation
     */
    public void stopAnimation(){
        started = false;
    }


    /**
     *  the method that runs every tick
     */
    public void tick(){
        //cycles the animation if the animation has been started
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
