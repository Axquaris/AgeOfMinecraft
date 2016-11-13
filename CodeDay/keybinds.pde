void keyPressed() {
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
        p1Stats.income *= 1.4;
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
        p2Stats.income *= 1.4;
        p2Stats.updateCost();
      }
    }
  }
}

void keyReleased() {
  if (key == 'z' || key == 'Z') keys['z'] = false; // Z
  if (key == 'x' || key == 'X') keys['x'] = false; // X
  if (key == 'c' || key == 'C') keys['c'] = false; // C
  if (key == 'v' || key == 'V') keys['v'] = false; // V
  if (key == 'm' || key == 'M') keys['m'] = false; // M
  if (key == ',' || key == '<') keys[','] = false; // ,
  if (key == '.' || key == '>') keys['.'] = false; // .
  if (key == '/' || key == '?') keys['/'] = false; // /
}