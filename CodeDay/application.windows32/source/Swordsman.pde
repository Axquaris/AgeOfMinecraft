class Swordsman extends Unit {
  
  Swordsman(StatSet s, int owner) {
    super(s.startX, s.swordsman, owner);
    
    still = loadImage(sketchPath()+"\\assets\\swordsmen\\still"+armourlvl+".png");
    moving = loadImage(sketchPath()+"\\assets\\swordsmen\\move"+armourlvl+".png");
    attacking = loadImage(sketchPath()+"\\assets\\swordsmen\\attack"+armourlvl+".png");
  }
  
  public void attack(Unit other) {
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