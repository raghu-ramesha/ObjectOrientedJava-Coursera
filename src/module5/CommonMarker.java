package module5;

import java.util.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a common marker for cities and earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class CommonMarker extends SimplePointMarker {

	// Records whether this marker has been clicked (most recently)
	protected boolean clicked = false;
	
	public CommonMarker(Location location) {
		super(location);
	}
	
	public CommonMarker(Location location, java.util.HashMap<java.lang.String,java.lang.Object> properties) {
		super(location, properties);
	}
	
	// Adding an additional constructor for getting the map too
//	public CommonMarker(Location location, UnfoldingMap map){
//		super(location, map);
//	}
	
	// Getter method for clicked field
	public boolean getClicked() {
		return clicked;
	}
	
	// Setter method for clicked field
	public void setClicked(boolean state) {
		clicked = state;
	}
	
	// Common piece of drawing method for markers; 
	// Note that you should implement this by making calls 
	// drawMarker and showTitle, which are abstract methods 
	// implemented in subclasses
	public void draw(PGraphics pg, float x, float y) {
		// For starter code just drawMaker(...)
		if (!hidden) {
			drawMarker(pg, x, y);
			if (selected) {
				showTitle(pg, x, y);  // You will implement this in the subclasses
			}
			
			// Important note
			// showThreat() will not be implemented here
			// REASON 1: You don't have to draw it
			// REASON 2: You have to setHidden one time to true and false
			// REASON 3: You can't get the List<Marker> necessary for calculating the threat
			//			 for each marker
		}
	}
	public abstract void drawMarker(PGraphics pg, float x, float y);
	public abstract void showTitle(PGraphics pg, float x, float y);
	public abstract void showThreat(List<Marker> quakeMarkers, List<Marker> cityMarkers);
}