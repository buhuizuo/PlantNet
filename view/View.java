package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import listService.Service;
import plantNet.Flowers;
import plantNet.LocationName;

//the original App I found on Goodgle play is an Android App called PictureThis - Plant Identifier, 
//so I designed the page like 
//an App more than a console. so the logic will be choose a function, than go back to home page for another one.

//at Homepage the App shows other users upload pics and can access camera which I 
//can't do, but what I can do is like the App, to show all different flowers, sort by name,color or location.
//and details but not pics, allow user to add flowers to MyCollection and display.

//when run this program, user need to enter location, depends on location, randomly picks 5 flowers of that location
//to display and inspired user.

//I choose to use ArrayList instead of array, it saves me lot's of work like, add, remove
//alternatively I can use Set because flowers don't repeat each other, and  I do want to practice ArrayList in a real program.

public class View {
	Service service = new Service();
	Scanner scan = new Scanner(System.in);

	public void enterMainMenu() {
		System.out.println("-----------------Welcom to PlantNet---------------------\n");
		LocationName[] location = LocationName.values();
		for (int i = 0; i < location.length; i++) {
			System.out.println(location[i] + "\n");
		}
		System.out.println("\n---------------------------------------------------------\n");
		System.out.println("enter your location: ");
		String loca = scan.next().toUpperCase();
		boolean isRight = false;
		while (!isRight) {
			if (loca.equals("NSW") || loca.equals("QLD") || loca.equals("SA") || loca.equals("TAS")
					|| loca.equals("VIC") || loca.equals("WA")) {
				isRight = true;
			} else {
				System.out.println("put in name of states(example: NSW)");
				loca = scan.next().toUpperCase();
			}
		}
		LocationName myLocation = LocationName.valueOf(loca);
		service.getLocalFlo(myLocation);
		homePage();
	}

	public void homePage() {
		boolean isFinish = false;
		while (!isFinish) {
			System.out.println("\n-----------------Welcom to HomePage---------------------\n");
			System.out.println("Flowers aroud you:\n");
			int[] num = new int[5];
			for (int i = 0; i < 5; i++) {
				num[i] = (int) (Math.random() * 8);
				for (int j = 0; j < i; j++) {
					if (num[j] == num[i]) {
						i--;
					}
				}
			}

			for (int i = 0; i < 5; i++) {
				Flowers f = (Flowers) service.getLocalFlo().get(num[i]);
				System.out.println(f.getName());
			}
			System.out.println("\n---------------------------------------------------------\n");
			System.out.println("1.My Collection  2.Search  3.All Flowers in AUS  4.Change Location  5.Quit");
			boolean isRight = false;
			String select = scan.next();
			while (!isRight) {
				char c = select.charAt(0);
				if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5') {
					isRight = true;
				} else {
					System.out.println("Choose from 1 to 5");
					select = scan.next();
				}
			}
			char c = select.charAt(0);
			if (c == '1') {
				showMyCollection();
			} else if (c == '2') {
				search();
			} else if (c == '3') {
				fullList();
			} else if (c == '4') {
				enterMainMenu();
			} else if (c == '5') {
				System.out.println("Are you sure?(Y/N)");
				String confirm = scan.next().toUpperCase();
				char c1 = confirm.charAt(0);
				while (c1 != 'Y' && c1 != 'N') {
					System.out.println("Enter Y or N");
					confirm = scan.next().toUpperCase();
					c1 = confirm.charAt(0);
				}
				if (c1 == 'Y') {
					isFinish = true;
				}
			}
		}
	}

	public void showMyCollection() {
		boolean isDone = false;
		while (!isDone) {
			System.out.println("\n-------------------------My Collection----------------------------------\n");
			if (service.getMyCollection().size() <= 0) {
				System.out.println("Nothing in your Collection yet, go to <3.All Flowers> to add some");
				homePage();
			} else {
				Iterator<Flowers> iterator = service.getMyCollection().iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next() + "\n");
				}
				System.out.println("\n----------------------------------------------------------------------\n");
				System.out.println("1.Add Flower  2.Remove Flower  3.Back to Home Page");
				boolean isRight = false;
				String select = scan.next();
				while (!isRight) {
					char c = select.charAt(0);
					if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5') {
						isRight = true;
					} else {
						System.out.println("Choose from 1 to 5");
						select = scan.next();
					}
				}
				char c = select.charAt(0);
				if (c == '1') {
					addToMyColletion();
				} else if (c == '2') {
					remove();
				} else if (c == '3') {
					isDone = true;
				}
			}
		}
	}

	public void remove() {
		ArrayList<Flowers> myCol = service.getMyCollection();
		System.out.println("Enter name of the flower to remove from My Collection, '0' to cancel:");
		String name = scan.next();
		boolean isFound = false;

		Iterator<Flowers> iterator2 = myCol.iterator();
		while (iterator2.hasNext()) {
			Flowers flo = iterator2.next();
			if (flo.getName().toUpperCase().contains(name.toUpperCase())) {
				isFound = true;
				System.out.println("Do you want to remove " + flo.getName() + " from My Collection?(Y/N)");
				String confirm = scan.next().toUpperCase();
				char c = confirm.charAt(0);
				while (c != 'Y' && c != 'N') {
					System.out.println("Enter Y or N");
					confirm = scan.next().toUpperCase();
					c = confirm.charAt(0);
				}
				if (c == 'Y') {
					iterator2.remove();
					System.out.println("Removed " + flo.getName() + " from My Collection.");
					service.writeFile();
				}
			}
		}
		if (!isFound) {
			System.out.println("Can not find any flowers by this name.or you canceled.");
		}
	}

	public void search() {
		System.out.println("\nWhat flower is in your mind?(or Color)");
		String keyWord = scan.next();
		ArrayList<Flowers> list = service.searchList(keyWord);
		if (list.size() == 0) {
			System.out.println("Can't find anything.");
		} else {
			Iterator<Flowers> iterator = list.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next() + "\n");
			}
			addToMyColletion();
			showMyCollection();
		}

	}

	public void fullList() {
		ArrayList<Flowers> allFlo = service.sortByName();
		printFull(allFlo);
		boolean isFlag = true;
		while (isFlag) {
			System.out.println("---------------------------------------------------------\n");
			System.out.println("1.Add to My Collection  2.Sort by Color  3.Sort By Season "
					+ " 4.Sort by Flowers Number  5.Back to Home");
			boolean isRight = false;
			String select = scan.next();
			while (!isRight) {
				char c = select.charAt(0);
				if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5') {
					isRight = true;
				} else {
					System.out.println("Choose from 1 to 6");
					select = scan.next();
				}
			}
			char c = select.charAt(0);
			if (c == '1') {
				addToMyColletion();
			} else if (c == '2') {
				ArrayList<Flowers> sortByColor = service.sortByColor();
				printFull(sortByColor);
			} else if (c == '3') {
				ArrayList<Flowers> sortBySeason = service.sortBySeason();
				printFull(sortBySeason);
			} else if (c == '4') {
				ArrayList<Flowers> sortByFloNumber = service.sortByFloNumber();
				printFull(sortByFloNumber);
			} else if (c == '5') {
				isFlag = false;
			}
		}
	}

	public void printFull(ArrayList<Flowers> list) {
		System.out.println("\n-----------------All Flowers in Australia---------------------\n");
		System.out.println("Name\t\t\tColor\t\t\tSeason\t\t\tFlowerAmong\t\t\tLocation");

		Iterator<Flowers> iterator = list.iterator();
		while (iterator.hasNext()) {
			Flowers flowers = iterator.next();
			System.out.println(flowers.getName() + "\t\t\t" + flowers.getColor() + "\t\t\t" + flowers.getSeason()
					+ "\t\t\t" + flowers.getFloNum() + "\t\t\t" + flowers.getLocation());
		}
	}

	public void addToMyColletion() {
		ArrayList<Flowers> allFlo = service.getAllFlo();
		System.out.println("Enter name of the flower to add to My Collection, '0' to cancel:");
		String name = scan.next();
		boolean isFound = false;

		Iterator<Flowers> iterator2 = allFlo.iterator();
		while (iterator2.hasNext()) {
			Flowers flo = iterator2.next();
			if (flo.getName().toUpperCase().contains(name.toUpperCase())) {
				isFound = true;
				System.out.println("Do you want to add " + flo.getName() + " to My Collection?(Y/N)");
				String confirm = scan.next().toUpperCase();
				char c = confirm.charAt(0);
				while (c != 'Y' && c != 'N') {
					System.out.println("Enter Y or N");
					confirm = scan.next().toUpperCase();
					c = confirm.charAt(0);
				}
				if (c == 'Y') {
					service.getMyCollection().add(flo);
					System.out.println("Added " + flo.getName() + " to My Collection.");

				}
			}
		}

		if (!isFound) {
			System.out.println("Can not find any flowers by this name. or you canceled.");
		}
		service.writeFile();
	}

	public static void main(String[] args) {
		View view = new View();
		view.enterMainMenu();
	}
}
