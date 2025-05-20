public class Slash extends Projectile{
    private Entity entity;
    private int offsetX;
    private int offsetY;
    public Slash(double xLocation, double yLocation, int width, int height, Entity entity,int offsetX,int offsetY){
        super(xLocation,yLocation,0,width,height,1,10,20);
        this.entity = entity;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    public boolean tick(){

        move(entity.getX()+ entity.getWidth()/2-getWidth()/2+offsetX,entity.getY()+ entity.getHeight()/2-getHeight()/2+offsetY);
        return super.tick();

    }
}
