class Knight extends Unit {
  
  Knight(StatSet s, int owner) {
    super(s.startX, s.knight, owner);
    
    still = loadImage(sketchPath()+"\\assets\\knights\\still"+armourlvl+".png");
    moving = loadImage(sketchPath()+"\\assets\\knights\\move"+armourlvl+".png");
    attacking = loadImage(sketchPath()+"\\assets\\knights\\attack"+armourlvl+".png");
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