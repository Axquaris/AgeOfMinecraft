boolean keys[] = new boolean[Character.MAX_VALUE];

ArrayList<Unit> p1Units = new ArrayList<Unit>();
ArrayList<Unit> p2Units = new ArrayList<Unit>();
StatSet p1Stats;
StatSet p2Stats;

int FPS = 30;
PFont f;
int baseStartingHealth = 100;
int startingGold = 100;

PImage background;
PImage p1Win;
PImage p2Win;

void setup() {
  size(1400, 600);
  p1Stats = new StatSet(200);
  p2Stats = new StatSet(width-300);
  frameRate(FPS);
  f = createFont("Helvetica", 30, true);
  //noCursor();

  background = loadImage(sketchPath()+"\\assets\\background.png");
  p1Win = loadImage(sketchPath()+"\\assets\\p1Win.png");
  p2Win = loadImage(sketchPath()+"\\assets\\p2Win.png");
}

void draw() {
  background(0);
  image(background, 0, 0);

  p1Stats.gold += p1Stats.income;
  p2Stats.gold += p2Stats.income;

  Unit u;
  int buffer = 20;
  //Move P1s
  if (p1Units.size() > 0) {
    //Move First P1
    u = p1Units.get(0);
    if (p2Units.size() == 0) {
      if (u.position + u.size.x + u.speed + buffer < width - 300)
        u.position += u.speed;
      if (u.state == 0)
        u.state = Unit.MOVING;
    } else {
      if (u.position + u.size.x + u.speed + buffer < p2Units.get(0).position) {
        u.position += u.speed;
        if (u.state == 0)
          u.state = Unit.MOVING;
      }
    }
  }
  if (p1Units.size() > 1) {
    //Move other P1s
    for (int i = 1; i < p1Units.size(); i++) { //p1 First
      u = p1Units.get(i);
      if (u.position + u.size.x + u.speed +buffer < p1Units.get(i-1).position) {
        u.position += u.speed;
        if (u.state == 0)
          u.state = Unit.MOVING;
      }
    }
  }

  //Move P2s
  if (p2Units.size() > 0) {
    //Move First P2
    u = p2Units.get(0);
    if (p1Units.size() == 0) {
      if (u.position - u.speed - buffer > 300)
        u.position -= u.speed;
      if (u.state == 0)
        u.state = Unit.MOVING;
    } else {
      if (u.position - u.speed - buffer > p1Units.get(0).position + p1Units.get(0).size.x) {
        u.position -= u.speed;
        if (u.state == 0)
          u.state = Unit.MOVING;
      }
    }
  }
  if (p2Units.size() > 1) {
    //Move other P2s
    for (int i = 1; i < p2Units.size(); i++) {
      u = p2Units.get(i);
      if (u.position - u.speed - buffer > p2Units.get(i-1).position + p2Units.get(i-1).size.x) {
        u.position -= u.speed;
        if (u.state == 0)
          u.state = Unit.MOVING;
      }
    }
  }

  //Attack
  if (p2Units.size() > 0) {
    for (Unit i : p2Units) { //p2 First
      i.cooldown--;
      if (p1Units.size() > 0) {
        if (p1Units.get(0).position + p1Units.get(0).size.x >= i.position - i.range && i.cooldown <= 0) {
          i.attack(p1Units.get(0));

          if (p1Units.get(0).health <= 0) {
            p1Units.remove(0);
          }

          i.cooldown = i.attackspeed;
          i.state = Unit.ATTACKING;
        }
      } else if (300 >= i.position - i.range && i.cooldown <= 0) {
        p1Stats.baseHealth -= i.damage;
        i.cooldown = i.attackspeed;
        i.state = Unit.ATTACKING;
      }
    }
  }
  if (p1Units.size() > 0) {
    for (Unit i : p1Units) {
      i.cooldown--;
      if (p2Units.size() > 0) {
        if (i.position + i.size.x + i.range >= p2Units.get(0).position && i.cooldown <= 0) {
          i.attack(p2Units.get(0));

          if (p2Units.get(0).health <= 0) {
            p2Units.remove(0);
          }

          i.cooldown = i.attackspeed;
          i.state = Unit.ATTACKING;
        }
      } else if (i.position + i.size.x + i.range >= width-300 && i.cooldown <= 0) {
        p2Stats.baseHealth -= i.damage;
        i.cooldown = i.attackspeed;
        i.state = Unit.ATTACKING;
      }
    }
  }

  //Draw
  for (Unit i : p1Units) {
    i.draw();
  }
  for (Unit i : p2Units) {
    i.draw();
  }

  //Forts
  fill(color(#B5160E));
  stroke(color(#7B0600));
  rect(0, 0, 200*p1Stats.baseHealth/baseStartingHealth, 30);
  rect(width-200*p2Stats.baseHealth/baseStartingHealth, 0, 200*p2Stats.baseHealth/baseStartingHealth, 30);
  
  drawUI();

  if (p1Stats.baseHealth <= 0) {
    image(p2Win, width/2-p2Win.width/2, height/2-p2Win.height/2);
    noLoop();
  } else if (p2Stats.baseHealth <= 0) {
    image(p1Win, width/2-p1Win.width/2, height/2-p1Win.height/2);
    noLoop();
  }
}