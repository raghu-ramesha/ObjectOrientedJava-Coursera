package guimodule;

import processing.core.*;
public class smileyFace extends PApplet{
	
	public void setup(){
		size(400, 400);
	}
	
	public void draw(){
		background(111, 255, 223);
		
		stroke(0, 0, 0);

		
		// face
		fill(255, 255, 0);
		ellipse(200, 200, 390, 390);
		
		// two white eyes
		fill(255, 255, 255);
		ellipse(100, 100, 70, 50);
		ellipse(300, 100, 70, 50);
		
		// eyes pupil
		noStroke();
		fill(111, 188, 255);
		ellipse(100, 100, 15, 21);
		ellipse(300, 100, 15, 21);

		// red nose
		
		fill(255, 0, 0);
		ellipse(190, 190, 10, 10);
		ellipse(200, 190, 10, 10);
		ellipse(210, 190, 10, 10);
		
		// o.O lips
//		stroke(color(0, 0, 0));
//		fill(0, 0, 0);
//		ellipse(200, 300, 200, 100);
//		
	
		// bezier curve for smiley curve
		noFill();
		stroke(color(0, 0, 0));
		bezier(50, 250, 180, 300, 220, 300, 350, 250);
	}
	
	
}
