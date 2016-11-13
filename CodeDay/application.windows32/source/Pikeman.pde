class Pikeman extends Unit {

  Pikeman(StatSet s, int owner) {
    super(s.startX, s.pikeman, owner);
    
    still = loadImage(sketchPath()+"\\assets\\pikemen\\still"+armourlvl+".png");
    moving = loadImage(sketchPath()+"\\assets\\pikemen\\move"+armourlvl+".png");
    attacking = loadImage(sketchPath()+"\\assets\\pikemen\\attack"+armourlvl+".png");
  }
  
  public void attack(Unit other) {
    int dmgout = 0;
    if (other.armour < damage) {
      dmgout = damage - other.armour;
    }
    other.armour -= 2;
    if (other.armour <= 0)
      other.armour = 0;
    
    if (other instanceof Knight)
      dmgout *= 1.5;
    
    other.health -= dmgout;
  }
}