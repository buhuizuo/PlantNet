package listService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import plantNet.Flowers;
import plantNet.LocationName;
import plantNet.Season;

//this service class is the Controller part of MVC, it did all that's not interacts with user.
//in the construction, reads data from file and add to ArrayList, alternatively can call readFromFile
//in View part,while the program runs, but that's against MVC.
public class Service {
	ArrayList<Flowers> allFlower = new ArrayList<Flowers>();
	ArrayList<Flowers> localFlower = new ArrayList<Flowers>();
	ArrayList<Flowers> myCollection = new ArrayList<Flowers>();

	public Service() {
		readFromFile("flowerList.csv", allFlower);
		readFromFile("myCollection.csv", myCollection);
	}

	public void readFromFile(String fileName, ArrayList<Flowers> list) {
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader(fileName));
			String currLine = inFile.readLine();
			while (currLine != null) {
				String[] detail = currLine.split(",");
				String name = detail[0];
				String color = detail[1];
				Season season = Season.valueOf(detail[2]);
				int floNum = Integer.parseInt(detail[3]);
				boolean isPoison = Boolean.parseBoolean(detail[4]);
				LocationName location = LocationName.valueOf(detail[5]);
				list.add(new Flowers(name, color, season, floNum, isPoison, location));
				currLine = inFile.readLine();
			}
			inFile.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public ArrayList<Flowers> searchList(String keyword) {
		ArrayList<Flowers> found = new ArrayList<Flowers>();
		Iterator<Flowers> iterator = allFlower.iterator();
		while (iterator.hasNext()) {
			Flowers f = iterator.next();
			if (f.getName().toUpperCase().contains(keyword.toUpperCase())) {
				found.add(f);
			}
			if (f.getColor().contains(keyword)) {
				found.add(f);
			}
		}
		return found;
	}

	public ArrayList<Flowers> getAllFlo() {
		return allFlower;
	}

	public ArrayList<Flowers> getMyCollection() {
		return myCollection;
	}

	public ArrayList<Flowers> getLocalFlo() {
		return localFlower;
	}

	public void writeFile() {
		BufferedWriter outFile = null;
		try {
			outFile = new BufferedWriter(new FileWriter("myCollection.csv"));
			for (Flowers o : myCollection) {
				Flowers flowers = o;
				String toWrite = flowers.toWriteFile();
				outFile.write(toWrite + "\n");
			}
			outFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void getLocalFlo(LocationName myLocation) {
		ArrayList<Flowers> allFlo = getAllFlo();
		Iterator<Flowers> iterator = allFlo.iterator();
		while (iterator.hasNext()) {
			Flowers f = iterator.next();
			if (f.getLocation() == myLocation) {
				getLocalFlo().add(f);
			}
		}
	}

	public ArrayList<Flowers> sortByFloNumber() {
		allFlower.sort(new Comparator<Flowers>() {

			@Override
			public int compare(Flowers o1, Flowers o2) {
				return Integer.compare(o1.getFloNum(), o2.getFloNum());
			}
		});
		return allFlower;
	}

	public ArrayList<Flowers> sortBySeason() {
		allFlower.sort(new Comparator<Flowers>() {

			@Override
			public int compare(Flowers o1, Flowers o2) {
				return o1.getSeason().compareTo(o2.getSeason());
			}

		});
		return allFlower;
	}

	public ArrayList<Flowers> sortByColor() {
		allFlower.sort(new Comparator<Flowers>() {

			@Override
			public int compare(Flowers o1, Flowers o2) {
				return o1.getColor().compareTo(o2.getColor());
			}
		});
		return allFlower;
	}

	public ArrayList<Flowers> sortByName() {
		allFlower.sort(new Comparator<Flowers>() {

			@Override
			public int compare(Flowers o1, Flowers o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return allFlower;
	}
}
