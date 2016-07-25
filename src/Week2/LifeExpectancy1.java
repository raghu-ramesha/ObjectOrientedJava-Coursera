package Week2;

import java.util.*;
import java.util.Map.Entry;

import processing.core.PApplet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.core.Coordinate;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.utils.MapUtils;


public class LifeExpectancy1  extends PApplet{
	UnfoldingMap map;
	
	// Map for storing the life expectancy rate for each country
	Map<String, Float> lifeExpByCountry;
	
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 600, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		lifeExpByCountry = loadLifeExpectencyFromCSV("../data/LifeExpectancyWorldBankModule3.csv");
		
		// Loads countries features
		countries = GeoJSONReader.loadData(this, "../data/countries.geo.json");
		
		// List of markers for countries
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		// shade all the countries markers
		shadeCountries();

		map.addMarkers(countryMarkers);	
	}
	
	public void draw() {
		map.draw();
	}
	
	// Returns the Mapped collection of Life Expectancy data 
	// after loading it from the file
	private Map loadLifeExpectencyFromCSV(String fileName) {
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		
		
		// Processing built in method for loading the strings from the 
		// array
		String[] rows = loadStrings(fileName);
		
		for (String row: rows) {
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				lifeExpMap.put(columns[4], Float.parseFloat(columns[5]));
			}
		}
		
		return lifeExpMap;
		
	}
	
	/*
	 * Shade the countries according to the life expectancy rate there
	 */
	private void shadeCountries() {
		for (Marker marker: countryMarkers) {
			String countryId = marker.getId();
			
			if (lifeExpByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255 - colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(155, 155, 155));
			}
		}
	}
}
