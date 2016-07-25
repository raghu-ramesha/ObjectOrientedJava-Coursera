package Week4;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MapWithButton extends PApplet {
	
	private UnfoldingMap map;
	
	public void setup(){
		size(1024, 768, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 1000, 700, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	
	public void draw(){
		map.draw();
		drawButtons();
	}
	
	// handle the mouseReleased() event
	public void mouseReleased(){
		// white button was clicked
		if (mouseX > 100 && mouseX < 125){
			if (mouseY > 100 && mouseY < 125){
				background(color(255, 255, 255));
			}
		}
		
		// randomColor button was clicked
		if (mouseX > 100 && mouseX < 125 ){
			if (mouseY > 150 && mouseY < 175){
				background(randomColor());
			}
		}
	}
	
	public void drawButtons () {
		int white = color(255, 255, 255);
		// white button 
		fill(white);
		rect(100, 100, 25, 25);
		// random magic button
		fill(randomColor());
		rect(100, 150, 25, 25);
		
		
	}
	
	private int randomColor(){
		long r = Math.round(Math.random() * 255);
		long g = Math.round(Math.random() * 255);
		long b = Math.round(Math.random() * 255);
		
		return color(r, g, b);
	}

}
