
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
    if(getX() < 42/SCALE){
      move(42/SCALE,getY());
    }
    if(getY() < 80/SCALE){
      move(getX(),80/SCALE);
    }
    if(getX() > (double)TitleScreen.WIDTH/SCALE-getWidth()-42/SCALE){
      move((double)TitleScreen.WIDTH/SCALE-getWidth()-42/SCALE,getY());
    }
    if(getY() > (double)TitleScreen.HEIGHT/SCALE-getHeight()){
      move(getX(),(double)TitleScreen.HEIGHT/SCALE-getHeight());
    }
    return false;
  }
}
