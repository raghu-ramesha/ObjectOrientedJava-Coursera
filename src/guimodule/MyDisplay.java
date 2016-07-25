package guimodule;

import java.awt.Color;

import processing.core.*;

public class MyDisplay extends PApplet{
	PFont verdana;
	PFont couriernew;
	PFont verdanaitalic;
	PFont comicsansms;
	
	int red = 49;
	int blue = 236;
	int green = 157;
	
	// setup method for PApplet
	public void setup(){
		size(1024, 600);
		verdana = createFont("Verdana Bold", 32);
		verdanaitalic = createFont("Verdana Bold Italic", 32);
		couriernew = createFont("Courier New Bold", 32);
		comicsansms = createFont("Comic Sans MS", 72);
		printArray(PFont.list());
	}
	
	// draw method for PApplet
	public void draw(){
		background(red, green, blue);
		fill(255, 0, 0);
		quad(45, 20, 354, 20, 350, 70, 55, 70);
		noFill();
		fill(0, 0, 0);	
		textFont(verdana);
		text("Switch", 150, 55);
		textFont(couriernew);
		text("Chip Heath And Dan Heath", 50, 400);
		textFont(comicsansms);
		text("YO!", 600, 200);
		
		
	}
	
}
