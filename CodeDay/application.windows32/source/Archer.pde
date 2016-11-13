class Archer extends Unit {
  
  Archer(StatSet s, int owner) {
    super(s.startX, s.archer, owner);
    
    still = loadImage(sketchPath()+"\\assets\\archers\\still"+armourlvl+".png");
    moving = loadImage(sketchPath()+"\\assets\\archers\\move"+armourlvl+".png");
    attacking = loadImage(sketchPath()+"\\assets\\archers\\attack"+armourlvl+".png");
  }

  void attack(Unit other) {
    int dmgout = 0;
    if (other.armour < damage) {
      dmgout = damage - other.armour;
    }
    other.armour -= 1;
    if (other.armour <= 0)
      other.armour = 0;
      
    
    other.health -= dmgout;
  }
}