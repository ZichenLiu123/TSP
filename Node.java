
public class Node {
	private static final double R = 6371;
	private String name ; // node name
	private double lat ; // latitude coordinate
	private double lon ; // longitude coordinate
	
	// constructors
	public Node () {
	}
	
	public Node ( String name , double lat , double lon ) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	// setters
	public void setName ( String name ) {
		this.name = name;
	}
	public void setLat ( double lat ) {
		this.lat = lat;
	}
	public void setLon ( double lon ) {
		this.lon = lon;
	}
	
	// getters
	public String getName () {
		return this.name;
	}
	public double getLat () {
		return this.lat;
	}
	public double getLon () {
		return this.lon;
	} 
	
	//get input from user
	public void userEdit () {
		this.name = Pro5_liuzic17.getString1("   Name: ");
		this.lat = Pro5_liuzic17.getDouble("   latitude: ", -90.00, 90.00);
		this.lon = Pro5_liuzic17.getDouble("   longitude: ", -180.00, 180.00);
		}

	//not needed, already did in Graph Class
	public void print () {
	} 
	
	//// calculate distance between two nodes
	public static double distance ( Node i , Node j ) {
		double radX = Math.toRadians(i.getLat());
		double radY = Math.toRadians(j.getLat());
		double deltaX = i.getLat() - j.getLat();
		double deltaY = i.getLon() - j.getLon();
		
		double radDeltaX = Math.toRadians(deltaX);
		double radDeltaY = Math.toRadians(deltaY);
		double a = Math.pow((Math.sin(radDeltaX / 2)), 2.0) + Math.cos(radX) * Math.cos(radY) * Math.pow((Math.sin(radDeltaY / 2)), 2.0);
		double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * b;
		
		return distance;
	}
}
