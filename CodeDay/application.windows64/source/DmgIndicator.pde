class DmgIndicator {
  final color RED = color(#fc5353);
  final color GREY = color(#3d3939);
  
  String txt;
  float xpos;
  float ypos;
  color col;
  
  float drift;
  float up;
  
  DmgIndicator (String txt, float x, float y, color c) {
    this.txt = txt;
    xpos = x;
    ypos = y;
    col = c;
    
    drift = random(-1, 1);
    up = 1;
  }
  
  void draw() {
    pushMatrix();
    textFont(f, 12);
    fill(col);
    text(txt, xpos, ypos);
    popMatrix();
    
    xpos += drift;
    ypos += up;
    up *= 1.5;
  }
}