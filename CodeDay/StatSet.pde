class StatSet {
  int baseHealth = baseStartingHealth;
  
  float income = .5;
  float incomeCost;
  float gold = startingGold;
  
  int startX;
  
  // HP, A, DMG, ATTACKSPEED(frames btwn shot), SPEED, RANGE, SIZE
  Stats swordsman = new Stats(5, 4, 6, 60, 1, 50, new PVector(60, 100));
  Stats pikeman = new Stats(5, 3, 5, 90, 2, 120, new PVector(60, 100));
  Stats archer = new Stats(5, 2, 4, 75, 3, 200, new PVector(60, 100));
  Stats knight = new Stats(10, 7, 7, 75, 3, 90, new PVector(100, 165));
  
  StatSet (int startX) {
    this.startX = startX;
    updateCost();
  }
  
  void updateCost() {
    incomeCost = income*FPS*7;
  }
}