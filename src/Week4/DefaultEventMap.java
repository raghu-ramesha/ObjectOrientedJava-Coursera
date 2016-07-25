package Week4;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class DefaultEventMap extends PApplet {
	
	private UnfoldingMap map;
	public void setup(){
		size(1024, 768, OPENGL);
		map = new UnfoldingMap(this, new Google.GoogleTerrainProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	
	public void draw(){
		map.draw();
	}
	
	public void keyPressed(){	
		if (key == 'w'){
			background(color(255, 255, 255));
		}
		else if (key == 'b'){
			background(color(0, 0, 0));
		}
		else if(key == 'r'){
			background(randomColor());
		}
	}
	
	

}
