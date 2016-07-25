package module5;

import java.util.*;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class EarthquakeMarker extends CommonMarker
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	protected float radius;
	
	
	// constants for distance
	protected static final float kmPerMile = 1.6f;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// threatenedCityMarkers
	List<Marker> threatenedCityMarkers;
	
	// ADD constants for colors if you want

	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
		
	
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}
	

	// calls abstract method drawEarthquake and then checks age and draws X if needed
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		// IMPLEMENT: add X over marker if within past day		
		String age = getStringProperty("age");
		if ("Past Hour".equals(age) || "Past Day".equals(age)) {
			
			pg.strokeWeight(2);
			int buffer = 2;
			pg.line(x-(radius+buffer), 
					y-(radius+buffer), 
					x+radius+buffer, 
					y+radius+buffer);
			pg.line(x-(radius+buffer), 
					y+(radius+buffer), 
					x+radius+buffer, 
					y-(radius+buffer));
			
		}
		
		// reset to previous styling
		pg.popStyle();
		
	}

	/** Show the title of the earthquake if this marker is selected */
	@Override
	public void showTitle(PGraphics pg, float x, float y)
	{
		
		
		// Displaying the title of the Earthquake
		// When it's hovered over (or in other words selected)
		
		// color of the popup
		int popupColor = pg.color(253, 237, 44);
		int black = pg.color(0, 0, 0);
		
		String title = getTitle();
		int fontSize = 12;
		
		// popup box
		pg.fill(popupColor);
		pg.rect(x, (y + getRadius() * 2) - (fontSize), pg.textWidth(title), fontSize + 2);
		
		// drawing the title of the earthquake
		pg.fill(black);
		pg.textSize(fontSize);
		pg.text(title, x, y + getRadius() * 2);
	}

	/*
	 * (non-Javadoc)
	 * @see module5.CommonMarker#showThreat(java.util.List)
	 * Hides all other earthquakeMarkers
	 * Measures the distance between the Earthquake and each cityMarker
	 * Checks all the city for threat from the earthquake
	 * Hides if it's threatened
	 * Unhides if it's not threatened
	 */
	@Override
	public void showThreat(List<Marker> quakeMarkers, List<Marker> cityMarkers){
		hideOtherQuakes(quakeMarkers);
		showAndAddThreatenedCities(cityMarkers);
	}
	
	// hides all other quakeMarkers
	private void hideOtherQuakes(List<Marker> quakeMarkers){
		for (Marker marker: quakeMarkers){
			if (marker != this){
				marker.setHidden(true);
			}
		}
	}
	
	
	/*
	 * hiding the cities which are not threatened
	 * if the city is threatened
	 * adds the threatened city
	 */
	private void showAndAddThreatenedCities(List<Marker> cityMarkers){
		if (threatenedCityMarkers == null){
			threatenedCityMarkers = new ArrayList<Marker>();	
		}
		
		// threat circle in km
		double threat = threatCircle();
		
		// Looping over all the cityMarker
		// Adding the cities which are threatened by the earthquake
		for (Marker marker: cityMarkers){
			if ((marker).getDistanceTo(this.location) > threat){
				marker.setHidden(true);
			}
			else {
				// Not hiding marker means threatenedCities are already displayed
				
				addThreatenedCity(marker);
			}
		}
	}
	
	// Adds the the threatened City if not in the list already
	private void addThreatenedCity(Marker cityMarker){
		System.out.println(cityMarker.getProperties().toString());
		if (threatenedCityMarkers.indexOf(cityMarker) == -1) {
			threatenedCityMarkers.add(cityMarker);	
		}
	}
	
	/**
	 * Return the "threat circle" radius, or distance up to 
	 * which this earthquake can affect things, for this earthquake.   
	 * DISCLAIMER: this formula is for illustration purposes
	 *  only and is not intended to be used for safety-critical 
	 *  or predictive applications.
	 */
	public double threatCircle() {	
		double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
		double km = (miles * kmPerMile);
		return km;
	}
	
	// determine color of marker from depth
	// We use: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
		
		if (depth < THRESHOLD_INTERMEDIATE) {
			pg.fill(255, 255, 0);
		}
		else if (depth < THRESHOLD_DEEP) {
			pg.fill(0, 0, 255);
		}
		else {
			pg.fill(255, 0, 0);
		}
	}
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	

	
	
}
