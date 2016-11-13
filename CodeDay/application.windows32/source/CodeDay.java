import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class CodeDay extends PApplet {

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

public void setup() {
  
  p1Stats = new StatSet(200);
  p2Stats = new StatSet(width-300);
  frameRate(FPS);
  f = createFont("Helvetica", 30, true);
  //noCursor();

  background = loadImage(sketchPath()+"\\assets\\background.png");
  p1Win = loadImage(sketchPath()+"\\assets\\p1Win.png");
  p2Win = loadImage(sketchPath()+"\\assets\\p2Win.png");
}

public void draw() {
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
  fill(color(0xffB5160E));
  stroke(color(0xff7B0600));
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

class Archer extends Unit {
  
  Archer(StatSet s, int owner) {
    super(s.startX, s.archer, owner);
    
    still = loadImage(sketchPath()+"\\assets\\archers\\still"+armourlvl+".png");
    moving = loadImage(sketchPath()+"\\assets\\archers\\move"+armourlvl+".png");
    attacking = loadImage(sketchPath()+"\\assets\\archers\\attack"+armourlvl+".png");
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
class DmgIndicator {
  final int RED = color(0xfffc5353);
  final int GREY = color(0xff3d3939);
  
  String txt;
  float xpos;
  float ypos;
  int col;
  
  float drift;
  float up;
  
  DmgIndicator (String txt, float x, float y, int c) {
    this.txt = txt;
    xpos = x;
    ypos = y;
    col = c;
    
    drift = random(-1, 1);
    up = 1;
  }
  
  public void draw() {
    pushMatrix();
    textFont(f, 12);
    fill(col);
    text(txt, xpos, ypos);
    popMatrix();
    
    xpos += drift;
    ypos += up;
    up *= 1.5f;
  }
}
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
      dmgout *= 1.5f;
    
    other.health -= dmgout;
  }
}
class StatSet {
  int baseHealth = baseStartingHealth;
  
  float income = .5f;
  float incomeCost;
  float gold = startingGold;
  
  int startX;
  
  // HP, A, DMG, ATTACKSPEED(frames btwn shot), SPEED, RANGE, SIZE
  Stats swordsman = new Stats(5, 4, 6, 60, 1, 50, new PVector(60, 100));
  Stats pikeman = new Stats(5, 3, 5, 90, 2, 120, new PVector(60, 100));
  Stats archer = new Stats(5, 2, 4, 45, 3, 300, new PVector(60, 100));
  Stats knight = new Stats(10, 5, 7, 75, 3, 90, new PVector(100, 165));
  
  StatSet (int startX) {
    this.startX = startX;
    updateCost();
  }
  
  public void updateCost() {
    incomeCost = income*FPS*10;
  }
}
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
  
  public void updateCosts() {
    cost = (int)(Math.pow(1.4f, health) + Math.pow(1.4f, armour) + Math.pow(1.4f, damage));
    healthCost = (int)(cost*(1+Math.pow(1.5f, healthUps)));
    armourCost = (int)(cost*(1+Math.pow(1.5f, armourUps)));
    damageCost = (int)(cost*(1+Math.pow(1.5f, damageUps)));
  }
}
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
  
  public abstract void attack(Unit other);
  
  public void draw() {
    
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




public void drawUI() {
  pushMatrix();
  strokeWeight(3);
  
  fill(color(0xff4C5555));
  stroke(color(0xff151717));
  rect(0, height-150, 360, 150);
  rect(width-365, height-150, 365, 150);
  
  fill(230);
  
  //makes top rectangles for both sides
  rect(10, height-110, 80, 40);//a
  rect(95, height-110, 80, 40);//s
  rect(180, height-110, 80, 40);//d
  rect(265, height-110, 80, 40);//f


  rect(1050, height-110, 80, 40);//j
  rect(1135, height-110, 80, 40);//k
  rect(1220, height-110, 80, 40);//l
  rect(1305, height-110, 80, 40);//;
  
  //resets fill color
  fill(0);
  textFont(f);//change font


  //gold
  pushMatrix();
  fill(color(0xffD69C00));
  text(("Gold:"+(int)(p1Stats.gold)), 10, 485);
  text(("Gold:"+(int)(p2Stats.gold)), 1050, 485);
  popMatrix();
  
  fill(0);
  //creates the text
  text("A", 20, 520);//type fucking A!
  text("S", 105, 520);//type fucking S!
  text("D", 190, 520);//type fucking D!
  text("F", 275, 520);//type fucking F!
  text("V", 275, 570);


  text("J", 1060, 520);//type fucking j!
  text("K", 1145, 520);//type fucking k!
  text("L", 1230, 520);//type fucking l!
  text(";", 1315, 520);//type fucking ;!
  text("/", 1315, 570);///
  //class names 
  textFont(f, 12);
  text("Pikeman", 43, 520);
  text("Archer", 125, 520);
  text("Swords", 213, 512);
  text("man", 220, 525);
  text("Knight", 295, 520);


  text("Pikeman", 1080, 520);
  text("Archer", 1169, 520);
  text("Swords", 1247, 512);
  text("man", 1256, 525);
  text("Knight", 1330, 520);
  textFont(f, 30);
  //if no key pressed show far rght keys
  if (!keys['a']||!keys['s']||!keys['d']||!keys['f'])
  {
    fill(230);
    rect(10, height-60, 80, 40);//z
    rect(95, height-60, 80, 40);//x
    rect(180, height-60, 80, 40);//c
  
    if (keys['v']) {
      fill(200);
    }
    rect(265, height-60, 80, 40);//v
    fill(0);
    textFont(f, 12);
    text("Income+", 295, 562);
    text((int)(p1Stats.incomeCost), 305, 575);
    textFont(f, 30);
    text("V", 275, 570);
  }


  if (!keys['j']||!keys['k']||!keys['l']||!keys[';'])
  {
    fill(230);
    rect(1050, height-60, 80, 40);//m
    rect(1135, height-60, 80, 40);//,
    rect(1220, height-60, 80, 40);//.
    
    if (keys['/']) {
      fill(200);
    }
    rect(1305, height-60, 80, 40);///

    fill(0);
    textFont(f, 12);
    text("Income+", 1325, 562);
    text((int)(p2Stats.incomeCost), 1335, 575);
    textFont(f, 30);
    text("/", 1315, 570);
    //selects the top row
  }
  if (keys['a'])
  {


    fill(200);
    rect(10, height-110, 80, 40);
    fill(0);
    text("A", 20, 520);
    textFont(f, 12);
    text("Pikeman", 43, 520);
    textFont(f, 30);
  }
  if (keys['s'])
  {


    fill(200);
    rect(95, height-110, 80, 40);
    fill(0);
    text("S", 105, 520);
    textFont(f, 12);
    text("Archer", 125, 520);
    textFont(f, 30);
  }
  if (keys['d'])
  {
    fill(200);
    rect(180, height-110, 80, 40);
    fill(0);
    text("D", 190, 520);
    textFont(f, 12);
    text("Swords", 213, 512);
    text("man", 220, 525);
    textFont(f, 30);
  }
  if (keys['f'])
  {
    fill(200);
    rect(265, height-110, 80, 40);
    fill(0);
    text("F", 275, 520);
    textFont(f, 12);
    text("Knight", 295, 520);
    textFont(f, 30);
  }





  if (keys['j'])
  {


    fill(200);
    rect(1050, height-110, 80, 40);//j
    fill(0);
    text("J", 1060, 520);//type fucking j!
    textFont(f, 12);
    text("Pikeman", 1080, 520);
    textFont(f, 30);
  }
  if (keys['k'])
  {


    fill(200);
    rect(1135, height-110, 80, 40);//k
    fill(0);
    text("K", 1145, 520);//type fucking k!
    textFont(f, 12);
    text("Archer", 1169, 520);
    textFont(f, 30);
  }
  if (keys['l'])
  {
    fill(200);
    rect(1220, height-110, 80, 40);//l
    fill(0);
    text("L", 1230, 520);//type fucking l!
    textFont(f, 12);
    text("Swords", 1247, 512);
    text("man", 1256, 525);
    textFont(f, 30);
  }
  if (keys[';'])
  {
    fill(200);
    rect(1305, height-110, 80, 40);//;
    fill(0);
    text(";", 1315, 520);//type fucking ;!
    textFont(f, 12);
    text("Knight", 1330, 520);
    textFont(f, 30);
  }


  if (keys['a']||keys['s']||keys['d']||keys['f'])
  {
    fill(230);
    rect(10, height-60, 80, 40);//z
    rect(95, height-60, 80, 40);//x
    rect(180, height-60, 80, 40);//c
    rect(265, height-60, 80, 40);//v
    fill(0);
    text("Z", 20, 570);
    text("X", 105, 570);
    text("C", 190, 570);
    text("V", 275, 570);
    textFont(f, 12);


    if (keys['a'])
    {
      price1(p1Stats.pikeman, 'a');
    }
    if (keys['s'])
    {
      price1(p1Stats.archer, 's');
    }
    if (keys['d'])
    {
      price1(p1Stats.swordsman, 'd');
    }


    if (keys['f'])
    {
      price1(p1Stats.knight, 'f');
    }
    fill(0);
    textFont(f, 30);
    text("Z", 20, 570);
    text("X", 105, 570);
    text("C", 190, 570);
    text("V", 275, 570);
    textFont(f, 12);




    text("Dmg+", 40, 555);


    text("Hp+", 125, 555);


    text("Armour+", 210, 555);


    text("Build", 295, 555);


    textFont(f, 30);
  }


  if (keys['j']||keys['k']||keys['l']||keys[';'])
  {
    fill(230);
    rect(1050, height-60, 80, 40);//m
    rect(1135, height-60, 80, 40);//,
    rect(1220, height-60, 80, 40);//.
    rect(1305, height-60, 80, 40);///
    fill(0);
    text("M", 1060, 570);//type fucking m!
    text(",", 1145, 570);//type fucking ,!
    text(".", 1230, 570);//type fucking .!
    text("/", 1315, 570);//type fucking /!
    textFont(f, 12);


    if (keys['j'])
    {
      price2(p2Stats.pikeman, 'j');
    }
    if (keys['k'])
    {
      price2(p2Stats.archer, 'k');
    }
    if (keys['l'])
    {
      price2(p2Stats.swordsman, 'l');
    }
    if (keys[';'])
    {
      price2(p2Stats.knight, ';');
    }
    fill(0);
    textFont(f, 30);
    text("M", 1060, 570);//type fucking m!
    text(",", 1145, 570);//type fucking ,!
    text(".", 1230, 570);//type fucking .!
    text("/", 1315, 570);//type fucking /!
    textFont(f, 12);




    text("Dmg+", 1085, 555);


    text("Hp+", 1155, 555);


    text("Armour+", 1240, 555);


    text("Build", 1335, 555);


    textFont(f, 30);
  }
  popMatrix();
}


public void price1(Stats s, char thechar) {
  int c = color(255, 68, 68);
  textFont(f, 12);
  if (thechar=='a'||thechar=='s'||thechar=='d'||thechar=='f')
  {
    if (keys['z']) {
      fill(200);
      rect(10, height-60, 80, 40);
    }
    if (keys['x'])
    {
      fill(200);
      rect(95, height-60, 80, 40);
    }
    if (keys['c'])
    {
      fill(200);
      rect(180, height-60, 80, 40);
    }
    if (keys['v'])
    {
      fill(200);
      rect(265, height-60, 80, 40);
    }
  }
  fill(0);
  text(s.damageCost+"G", 40, 568);
  text(s.healthCost+"G", 125, 568);
  text(s.armourCost+"G", 210, 568);
  text(s.cost+"G", 295, 568);
}












public void price2(Stats s, char thechar) {
  int c = color(255, 68, 68);
  textFont(f, 12);
  if (thechar=='j'||thechar=='k'||thechar=='l'||thechar==';')
  {
    if (keys['m']) {
      fill(200);
      rect(1050, height-60, 80, 40);
    }
    if (keys[','])
    {
      fill(200);
      rect(1135, height-60, 80, 40);
    }
    if (keys['.'])
    {
      fill(200);
      rect(1220, height-60, 80, 40);
    }
    if (keys['/'])
    {
      fill(200);
      rect(1305, height-60, 80, 40);
    }
  }
  fill(0);
  text(s.damageCost+"G", 1085, 568);
  text(s.healthCost+"G", 1155, 568);
  text(s.armourCost+"G", 1240, 568);
  text(s.cost+"G", 1335, 568);
  textFont(f, 30);
}


public void healthbar(int x, int y, int hp, int a, int powerlevel)
{
  int mainh = color(0xffB5160E);
  int accenth = color(0xff7B0600);
  int maind = color(0xff3BF8FF);
  int accentd = color(0xff007B81);
  int maing = color(0xffFFEC17);
  int accentg = color(0xff595200);
  int maini = color(0xffC4C0C0);
  int accenti = color(0xff827878);
  int mainl = color(0xffB55C0E);
  int accentl = color(0xff7B3A00);
  int they=y;
  strokeWeight(2);
  for (int k=0; k<hp; k++)
  {
    fill(mainh);
    stroke(accenth);
    rect(x, they, 10, 10);
    they-=15;
  }
  x+=15;
  they=y;
  for (int k=0; k<a; k++)
  {
    if (powerlevel==0) {
      stroke(accentl);
      fill(mainl);
    } else if (powerlevel==1) {
      stroke(accenti);
      fill(maini);
    } else if (powerlevel==2) {
      stroke(accentg);
      fill(maing);
    } else if (powerlevel==3) {
      stroke(accentd);
      fill(maind);
    }
    rect(x, they, 10, 10);
    they-=15;
  }
  fill(230);
  strokeWeight(1);
  stroke(0);
}




public void keyPressed() {
  if (key == 'a' || key == 'A') {
    keys['a'] = !keys['a'];
    keys['s'] = false;
    keys['d'] = false;
    keys['f'] = false;
  }
  if (key == 's' || key == 'S') {
    keys['a'] = false;
    keys['s'] = !keys['s'];
    keys['d'] = false;
    keys['f'] = false;
  }
  if (key == 'd' || key == 'D') {
    keys['a'] = false;
    keys['s'] = false;
    keys['d'] = !keys['d'];
    keys['f'] = false;
  }
  if (key == 'f' || key == 'F') {
    keys['a'] = false;
    keys['s'] = false;
    keys['d'] = false;
    keys['f'] = !keys['f'];
  }
  if (key == 'z' || key == 'Z') { //Damage+
    keys['z'] = true; 
    if (keys['a']) { //Pikeman
      if (p1Stats.gold >= p1Stats.pikeman.damageCost) {
        p1Stats.gold -= p1Stats.pikeman.damageCost;
        p1Stats.pikeman.damage += 2;
        p1Stats.pikeman.damageUps++;
        p1Stats.pikeman.updateCosts();
      }
    }
    if (keys['s']) { //Archer
      if (p1Stats.gold >= p1Stats.archer.damageCost) {
        p1Stats.gold -= p1Stats.archer.damageCost;
        p1Stats.archer.damage += 2;
        p1Stats.archer.damageUps++;
        p1Stats.archer.updateCosts();
      }
    }
    if (keys['d']) { //Swordsman
      if (p1Stats.gold >= p1Stats.swordsman.damageCost) {
        p1Stats.gold -= p1Stats.swordsman.damageCost;
        p1Stats.swordsman.damage += 1;
        p1Stats.swordsman.damageUps++;
        p1Stats.swordsman.updateCosts();
      }
    }
    if (keys['f']) { //Knight
      if (p1Stats.gold >= p1Stats.knight.damageCost) {
        p1Stats.gold -= p1Stats.knight.damageCost;
        p1Stats.knight.damage += 1;
        p1Stats.knight.damageUps++;
        p1Stats.knight.updateCosts();
      }
    }
  }
  if (key == 'x' || key == 'X') { //Health+
    keys['x'] = true; //X
    if (keys['a']) { //Pikeman
      if (p1Stats.gold >= p1Stats.pikeman.healthCost) {
        p1Stats.gold -= p1Stats.pikeman.healthCost;
        p1Stats.pikeman.health += 1;
        p1Stats.pikeman.healthUps++;
        p1Stats.pikeman.updateCosts();
      }
    }
    if (keys['s']) { //Archer
      if (p1Stats.gold >= p1Stats.archer.healthCost) {
        p1Stats.gold -= p1Stats.archer.healthCost;
        p1Stats.archer.health += 1;
        p1Stats.archer.healthUps++;
        p1Stats.archer.updateCosts();
      }
    }
    if (keys['d']) { //Swordsman
      if (p1Stats.gold >= p1Stats.swordsman.healthCost) {
        p1Stats.gold -= p1Stats.swordsman.healthCost;
        p1Stats.swordsman.health += 2;
        p1Stats.swordsman.healthUps++;
        p1Stats.swordsman.updateCosts();
      }
    }
    if (keys['f']) { //Knight
      if (p1Stats.gold >= p1Stats.knight.healthCost) {
        p1Stats.gold -= p1Stats.knight.healthCost;
        p1Stats.knight.health += 2;
        p1Stats.knight.healthUps++;
        p1Stats.knight.updateCosts();
      }
    }
  }
  if (key == 'c' || key == 'C') { //Armour+
    keys['c'] = true; //C
    if (keys['a']) { //Pikeman
      if (p1Stats.gold >= p1Stats.pikeman.armourCost && p1Stats.pikeman.armourUps < 6) {
        p1Stats.gold -= p1Stats.pikeman.armourCost;
        p1Stats.pikeman.armour += 1;
        p1Stats.pikeman.armourUps++;
        p1Stats.pikeman.updateCosts();
      }
    }
    if (keys['s']) { //Archer
      if (p1Stats.gold >= p1Stats.archer.armourCost && p1Stats.archer.armourUps < 6) {
        p1Stats.gold -= p1Stats.archer.armourCost;
        p1Stats.archer.armour += 1;
        p1Stats.archer.armourUps++;
        p1Stats.archer.updateCosts();
      }
    }
    if (keys['d']) { //Swordsman
      if (p1Stats.gold >= p1Stats.swordsman.armourCost && p1Stats.swordsman.armourUps < 6) {
        p1Stats.gold -= p1Stats.swordsman.armourCost;
        p1Stats.swordsman.armour += 2;
        p1Stats.swordsman.armourUps++;
        p1Stats.swordsman.updateCosts();
      }
    }
    if (keys['f']) { //Knight
      if (p1Stats.gold >= p1Stats.knight.armourCost && p1Stats.knight.armourUps <  6) {
        p1Stats.gold -= p1Stats.knight.armourCost;
        p1Stats.knight.armour += 2;
        p1Stats.knight.armourUps++;
        p1Stats.knight.updateCosts();
      }
    }
  }
  if (key == 'v' || key == 'V') { //Build
    keys['v'] = true; //V
    if (keys['a']) { //Pikeman
      if (p1Stats.gold >= p1Stats.pikeman.cost) {
        p1Stats.gold -= p1Stats.pikeman.cost;
        p1Units.add(new Pikeman(p1Stats, 1));
      }
    }
    if (keys['s']) { //Archer
      if (p1Stats.gold >= p1Stats.archer.cost) {
        p1Stats.gold -= p1Stats.archer.cost;
        p1Units.add(new Archer(p1Stats, 1));
      }
    }
    if (keys['d']) { //Swordsman
      if (p1Stats.gold >= p1Stats.swordsman.cost) {
        p1Stats.gold -= p1Stats.swordsman.cost;
        p1Units.add(new Swordsman(p1Stats, 1));
      }
    }
    if (keys['f']) { //Knight
      if (p1Stats.gold >= p1Stats.knight.cost) {
        p1Stats.gold -= p1Stats.knight.cost;
        p1Units.add(new Knight(p1Stats, 1));
      }
    }
    else {
      if (p1Stats.gold >= p1Stats.incomeCost) {
        p1Stats.gold -= p1Stats.incomeCost;
        p1Stats.income *= 1.2f;
        p1Stats.updateCost();
      }
    }
  }
  
  
  
  //SWITCHING FROM PLAYER 1 BINDINGS TO PLAYER 2 BINDINGS
  
  
  
  if (key == 'j' || key == 'J') {
    keys['j'] = !keys['j'];
    keys['k'] = false;
    keys['l'] = false;
    keys[';'] = false;
  }
  if (key == 'k' || key == 'K') {
    keys['j'] = false;
    keys['k'] = !keys['k'];
    keys['l'] = false;
    keys[';'] = false;
  }
  if (key == 'l' || key == 'L') {
    keys['j'] = false;
    keys['k'] = false;
    keys['l'] = !keys['l'];
    keys[';'] = false;
  }
  if (key == ';' || key == ':') {
    keys['j'] = false;
    keys['k'] = false;
    keys['l'] = false;
    keys[';'] = !keys[';'];
  }
  if (key == 'm' || key == 'M') { //Damage+
    keys['m'] = true; //M
    if (keys['j']) { //Pikeman
      if (p2Stats.gold >= p2Stats.pikeman.damageCost) {
        p2Stats.gold -= p2Stats.pikeman.damageCost;
        p2Stats.pikeman.damage += 2;
        p2Stats.pikeman.damageUps++;
        p2Stats.pikeman.updateCosts();
      }
    }
    if (keys['k']) { //Archer
      if (p2Stats.gold >= p2Stats.archer.damageCost) {
        p2Stats.gold -= p2Stats.archer.damageCost;
        p2Stats.archer.damage += 2;
        p2Stats.archer.damageUps++;
        p2Stats.archer.updateCosts();
      }
    }
    if (keys['l']) { //Swordsman
      if (p2Stats.gold >= p2Stats.swordsman.damageCost) {
        p2Stats.gold -= p2Stats.swordsman.damageCost;
        p2Stats.swordsman.damage += 1;
        p2Stats.swordsman.damageUps++;
        p2Stats.swordsman.updateCosts();
      }
    }
    if (keys[';']) { //Knight
      if (p2Stats.gold >= p2Stats.knight.damageCost) {
        p2Stats.gold -= p2Stats.knight.damageCost;
        p2Stats.knight.damage += 1;
        p2Stats.knight.damageUps++;
        p2Stats.knight.updateCosts();
      }
    }
  }
  if (key == ',' || key == '<') { //Health+
    keys[','] = true; //,
    if (keys['j']) { //Pikeman
      if (p2Stats.gold >= p2Stats.pikeman.healthCost) {
        p2Stats.gold -= p2Stats.pikeman.healthCost;
        p2Stats.pikeman.health += 1;
        p2Stats.pikeman.healthUps++;
        p2Stats.pikeman.updateCosts();
      }
    }
    if (keys['k']) { //Archer
      if (p2Stats.gold >= p2Stats.archer.healthCost) {
        p2Stats.gold -= p2Stats.archer.healthCost;
        p2Stats.archer.health += 1;
        p2Stats.archer.healthUps++;
        p2Stats.archer.updateCosts();
      }
    }
    if (keys['l']) { //Swordsman
      if (p2Stats.gold >= p2Stats.swordsman.healthCost) {
        p2Stats.gold -= p2Stats.swordsman.healthCost;
        p2Stats.swordsman.health += 2;
        p2Stats.swordsman.healthUps++;
        p2Stats.swordsman.updateCosts();
      }
    }
    if (keys[';']) { //Knight
      if (p2Stats.gold >= p2Stats.knight.healthCost) {
        p2Stats.gold -= p2Stats.knight.healthCost;
        p2Stats.knight.health += 2;
        p2Stats.knight.healthUps++;
        p2Stats.knight.updateCosts();
      }
    }
  }
  if (key == '.' || key == '>') { //Armour+
    keys['.'] = true; //.
    if (keys['j']) { //Pikeman
      if (p2Stats.gold >= p2Stats.pikeman.armourCost && p2Stats.pikeman.armourUps < 6) {
        p2Stats.gold -= p2Stats.pikeman.armourCost;
        p2Stats.pikeman.armour += 1;
        p2Stats.pikeman.armourUps++;
        p2Stats.pikeman.updateCosts();
      }
    }
    if (keys['k']) { //Archer
      if (p2Stats.gold >= p2Stats.archer.armourCost && p2Stats.archer.armourUps < 6) {
        p2Stats.gold -= p2Stats.archer.armourCost;
        p2Stats.archer.armour += 1;
        p2Stats.archer.armourUps++;
        p2Stats.archer.updateCosts();
      }
    }
    if (keys['l']) { //Swordsman
      if (p2Stats.gold >= p2Stats.swordsman.armourCost && p2Stats.swordsman.armourUps < 6) {
        p2Stats.gold -= p2Stats.swordsman.armourCost;
        p2Stats.swordsman.armour += 2;
        p2Stats.swordsman.armourUps++;
        p2Stats.swordsman.updateCosts();
      }
    }
    if (keys[';']) { //Knight
      if (p2Stats.gold >= p2Stats.knight.armourCost && p2Stats.knight.armourUps < 6) {
        p2Stats.gold -= p2Stats.knight.armourCost;
        p2Stats.knight.armour += 2;
        p2Stats.knight.armourUps++;
        p2Stats.knight.updateCosts();
      }
    }
  }
  if (key == '/' || key == '?') { //Build
    keys['/'] = true; // /
    if (keys['j']) { //Pikeman
      if (p2Stats.gold >= p2Stats.pikeman.cost) {
        p2Stats.gold -= p2Stats.pikeman.cost;
        p2Units.add(new Pikeman(p2Stats, 2));
      }
    }
    if (keys['k']) { //Archer
      if (p2Stats.gold >= p2Stats.archer.cost) {
        p2Stats.gold -= p2Stats.archer.cost;
        p2Units.add(new Archer(p2Stats, 2));
      }
    }
    if (keys['l']) { //Swordsman
      if (p2Stats.gold >= p2Stats.swordsman.cost) {
        p2Stats.gold -= p2Stats.swordsman.cost;
        p2Units.add(new Swordsman(p2Stats, 2));
      }
    }
    if (keys[';']) { //Knight
      if (p2Stats.gold >= p2Stats.knight.cost) {
        p2Stats.gold -= p2Stats.knight.cost;
        p2Units.add(new Knight(p2Stats, 2));
      }
    }
    else {
      if (p2Stats.gold >= p2Stats.incomeCost) {
        p2Stats.gold -= p2Stats.incomeCost;
        p2Stats.income *= 1.2f;
        p2Stats.updateCost();
      }
    }
  }
}

public void keyReleased() {
  if (key == 'z' || key == 'Z') keys['z'] = false; // Z
  if (key == 'x' || key == 'X') keys['x'] = false; // X
  if (key == 'c' || key == 'C') keys['c'] = false; // C
  if (key == 'v' || key == 'V') keys['v'] = false; // V
  if (key == 'm' || key == 'M') keys['m'] = false; // M
  if (key == ',' || key == '<') keys[','] = false; // ,
  if (key == '.' || key == '>') keys['.'] = false; // .
  if (key == '/' || key == '?') keys['/'] = false; // /
}
  public void settings() {  size(1400, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "CodeDay" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
