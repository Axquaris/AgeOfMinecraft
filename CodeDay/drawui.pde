



void drawUI() {
  pushMatrix();
  strokeWeight(3);
  
  fill(color(#4C5555));
  stroke(color(#151717));
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
  fill(color(#D69C00));
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


void price1(Stats s, char thechar) {
  color c = color(255, 68, 68);
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












void price2(Stats s, char thechar) {
  color c = color(255, 68, 68);
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


void healthbar(int x, int y, int hp, int a, int powerlevel)
{
  color mainh = color(#B5160E);
  color accenth = color(#7B0600);
  color maind = color(#3BF8FF);
  color accentd = color(#007B81);
  color maing = color(#FFEC17);
  color accentg = color(#595200);
  color maini = color(#C4C0C0);
  color accenti = color(#827878);
  color mainl = color(#B55C0E);
  color accentl = color(#7B3A00);
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