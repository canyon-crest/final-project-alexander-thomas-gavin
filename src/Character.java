
public class Character extends Entity {
  private int maxHealth;
  private int health;
  public Character(double xLocation, double yLocation, double friction, int width, int height, int health){
    super(xLocation, yLocation, friction, width, height, 0);
    this.health = health;
    this.maxHealth = health;
  }
  //return current health
  public int getHealth(){
    return health;
  }
  //return max health
  public int getMaxHealth(){
    return maxHealth;
  }
  //reduces the players health by an amount (bounded by 0 and max health)
  public void takeDamage(int amount){
    health -= amount;
    if(health < 0){
      health = 0;
    }
    if(health > maxHealth){
      health = maxHealth;
    }
  }
  //runs every tick
  public boolean tick(){
    super.tick();
    //checks if the character is out of bounds and puts them back in bounds
    if(getX() < 42d/SCALE){
      move(42d/SCALE,getY());
    }
    if(getY() < 120d/SCALE){
      move(getX(),120d/SCALE);
    }
    if(getX() > (double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE){
      move((double)TitleScreen.WIDTH/SCALE-getWidth()-42d/SCALE,getY());
    }
    if(getY() > (double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE){
      move(getX(),(double)TitleScreen.HEIGHT/SCALE-getHeight()-110d/SCALE);
    }
    return false;
  }
}
