package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.MapQuestProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.providers.OpenWeatherProvider;
import de.fhpotsdam.unfolding.providers.StamenMapProvider;
import de.fhpotsdam.unfolding.providers.Yahoo;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Hannan Ali
 * Date: October 1, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers;

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    	map.addMarker(createMarker(f));
	    }
	    
	    markers = createMarkers(earthquakes);	
	    
	    map.addMarkers(markers);
	    
	    //TODO: Add code here as appropriate
	}
	
	// returns a List<Marker> for the given data by calling createMarker()
	// for each element in the list
	private List<Marker> createMarkers(List<PointFeature> earthquakes){
		List<Marker> earthquakeMarkers = new ArrayList<Marker>();
		
		
		for (PointFeature e: earthquakes){
			// Marker for the given earthquake
			Marker marker = createMarker(e);
			earthquakeMarkers.add(marker);
		}
		
		return earthquakeMarkers;
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// Defining some colors
		int red = color(255, 0, 0);
		int yellow = color(255, 255, 0);
		int blue = color(0, 0, 255);
		
		
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		// Getting different properties of the feature
		float magnitude = Float.parseFloat(feature.getProperty("magnitude").toString());
		
		/// Styling the marker according to the feature properties
		
		
		// Higher magnitude earthquakes will have larger SimplePointMarker
		// Setting the radiusIncrement according to the magnitudes 
		// default 5
		float radiusIncrement = 5;
		
		// Changing the color according to the magnitude intensity
		// high if >= 5.0
		// color = red
		if (magnitude >= 5.0){
			marker.setColor(red);
			radiusIncrement = 10;
		}
		// moderate if >= 4.0 and < 5.0
		// color = yellow
		else if (magnitude >= 4.0 && magnitude < 5.0){
			marker.setColor(yellow);
			// defualt radius increment for moderate
		}
		// minor if < 4.0
		// color = blue
		else if (magnitude < 4.0){
			marker.setColor(blue);
			radiusIncrement = 2;
		}
		
		marker.setRadius(radiusIncrement + magnitude);
		
		return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		stroke(color(255, 255, 255));
		fill(color(255, 255, 255));
		rect(25, 50, 150, 250);
		
		// black stroke and fill
		stroke(color(0, 0, 0));
		fill(color(0, 0, 0));
		
		// Text for the key
		textSize(12);
		text("5.0 + Magnitude", 50, 90);
		text("4.0 + Magnitude", 50, 130);
		text("Below 4.0", 50, 170);
		
		
		// Simple Markers for different magnitudes
		stroke(color(0, 0, 0));
		
		// Red Marker for 5.0 + magnitude
		fill(color(255, 0, 0));
		ellipse(35, 84, 10, 10);
		
		// Yellow Marker for 4.0 + magnitude
		fill(color(255, 255, 0));
		ellipse(35, 124, 5, 5);
		
		// Blue for below 4.0
		fill(color(0, 0, 255));
		ellipse(35, 164, 2, 2);
		
	}
}
