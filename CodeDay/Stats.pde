class Stats {
  int health;
  int armour;
  int damage;
  int attackspeed;
  int speed;
  int range;
  int cost;
  
  int healthUps = 0;
  int armourUps = 0;
  int damageUps = 0;
  int healthCost = 0;
  int armourCost = 0;
  int damageCost = 0;
  
  PVector size;
  PImage still;
  PImage moving;
  PImage attack;
  
  Stats(int health, int armour, int damage, int attackspeed, int speed, int range, PVector size) {
    this.health = health;
    this.armour = armour;
    this.damage = damage;
    this.attackspeed = attackspeed;
    this.speed = speed;
    this.range = range;
    this.size = size;
    
    updateCosts();
  }
  
  void updateCosts() {
    cost = 5*(int)(Math.pow(1.5, health) + Math.pow(2, armour) + Math.pow(1.7, damage));
    healthCost = (int)(cost*(1+Math.pow(1.4, healthUps)));
    armourCost = (int)(cost*(1+Math.pow(1.5, armourUps)));
    damageCost = (int)(cost*(1+Math.pow(1.4, damageUps)));
  }
}