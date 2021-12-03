package plantNet;
//Since my program is focus on flowers, display,add or remove, only one class Flower is enough,

//I use enum to put season and location instead of String, cause the variables in them are limited, 
//and I have 4 different data types, String int boolean and enum.

public class Flowers {
	private String name;
	private String color;
	private Season season;
	private int floNum;
	private boolean isPoison;
	private LocationName location;

	public Flowers() {

	}

	public Flowers(String name, String color, Season season, int floNum, boolean isPoison, LocationName location) {
		super();
		this.name = name;
		this.color = color;
		this.season = season;
		this.floNum = floNum;
		this.isPoison = isPoison;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public int getFloNum() {
		return floNum;
	}

	public void setFloNum(int floNum) {
		this.floNum = floNum;
	}

	public boolean isPoison() {
		return isPoison;
	}

	public void setPoison(boolean isPoison) {
		this.isPoison = isPoison;
	}

	public LocationName getLocation() {
		return location;
	}

	public void setLocation(LocationName location) {
		this.location = location;
	}

	@Override
	public String toString() {
		if (this.isPoison) {
			return "Name: " + name + "\nColor: " + color + "\nBlooming Season: " + season + "\nAbout " + floNum
					+ " Flowers" + "\nPoisonous\n" + "Location: " + location;
		} else {
			return "Name: " + name + "\nColor: " + color + "\nBlooming Season: " + season + "\nAbout " + floNum
					+ " Flowers" + "\nNot poisonous\n" + "Location: " + location;
		}
	}

	public String toWriteFile() {
		return name + "," + color + "," + season + "," + floNum + "," + isPoison + "," + location;
	}

}
