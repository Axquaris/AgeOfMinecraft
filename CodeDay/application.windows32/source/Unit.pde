abstract class Unit {
  int owner;
  
  int position;
  int health;
  int armour;
  int armourlvl;
  
  int speed;
  int range;
  int damage;
  int attackspeed;
  int cooldown = 0;
  
  PVector size;
  PImage still;
  PImage moving;
  PImage attacking;
  float state = 0;
  final static float MOVING = -30, STILL = 0, ATTACKING = 15;

  public Unit(int position, Stats stats, int owner) {
    this.position=position;
    health=stats.health;
    armour=stats.armour;
    speed=stats.speed;
    range=stats.range;
    damage=stats.damage;
    size = stats.size;
    still=stats.still;
    moving=stats.moving;
    attacking=stats.attack;
    attackspeed=stats.attackspeed;
    this.owner = owner;
    armourlvl = stats.armourUps/2;
  }
  
  abstract void attack(Unit other);
  
  void draw() {
    
    pushMatrix();
    if (owner == 1) {
      translate(position, height-150-size.y);
    }
    else {
      translate(position+size.x, height-150-size.y);
      scale(-1, 1);
    }
    
    
    if (state < Unit.MOVING/2) { //MOVING
      image(moving, 0, 0);
      state += 1;
    }
    else if (state <= 0) {
      image(still, 0, 0);
      if (state < 0) state += 1;
    }
    else if (state > 0) { //ATTACKING
      image(attacking, 0, 0);
      state -= 1;
    }
    
    healthbar(0, 0, health, armour, armourlvl);
    popMatrix();
  }
}