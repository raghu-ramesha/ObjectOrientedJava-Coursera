package demos;

import processing.core.*;

public class MyPApplet1 extends PApplet{
	private int red;
	private int green;
	private int blue;
	
	private String URL = "palmTrees.jpg";
	private PImage backgroundImg;
	
	
	
	public void setup(){
		size(200, 200);
		backgroundImg = loadImage(URL, "jpg");
		
	}
	
	public void draw(){
		image(backgroundImg, 0, 0);
		backgroundImg.resize(0, height);
		secSunColor(second());
		fill(red, green, blue);
		ellipse(width / 4, height / 5, width / 5, height / 5);
	}
	
	// Sets the color of the sun according to second of the minute
	// Sun is brightest at 30 sec
	private void secSunColor(int seconds){
		double diff30 = Math.abs(30 - seconds);
		double ratio = diff30 / 30;
		
		red = (int) (255 * ratio);
		green = (int) (255 * ratio);
		blue = 0;
	}
	

}
