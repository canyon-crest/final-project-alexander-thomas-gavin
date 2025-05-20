
public class Character extends Entity {
  private int maxHealth;
  private int health;
  public Character(double xLocation, double yLocation, double friction, int width, int height, int health){
    super(xLocation, yLocation, friction, width, height, 0);
    this.health = health;
    this.maxHealth = health;
  }
  public int getHealth(){
    return health;
  }
  public int getMaxHealth(){
    return maxHealth;
  }
  public void takeDamage(int amount){
    health -= amount;
    if(health < 0){
      health = 0;
    }
    if(health > maxHealth){
      health = maxHealth;
    }
  }
  public boolean tick(){
    super.tick();
    if(getX() < 0){
      move(0,getY());
    }
    if(getY() < 0){
      move(getX(),0);
    }
    if(getX() > (double)TitleScreen.WIDTH/SCALE-getWidth()){
      move((double)TitleScreen.WIDTH/SCALE-getWidth(),getY());
    }
    if(getY() > (double)TitleScreen.HEIGHT/SCALE-getHeight()){
      move(getX(),(double)TitleScreen.HEIGHT/SCALE-getHeight());
    }
    return false;
  }
}
