package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class: LevelIO
 * @author Lucas, Neha, Swade
 * Purpose: Create levels and load levels
 *
 */
public class LevelIo {

	public String currentFile;

	/**
	 * Main creates new instance of LevelIo
	 * @param args
	 */
	public static void main(String[] args) {
		new LevelIo();
	}

	/**
	 * Constructor
	 */
	public LevelIo() {
	}

	/**
	 * Takes in the filename and reads it
	 */
	public ArrayList<String> readFile(String fileName) {
		Scanner scanner = null;
		this.currentFile = fileName;
		ArrayList<String> levelData = new ArrayList<String>();

		try {
			scanner = new Scanner (new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			levelData.add(line);
		}
		scanner.close();
		return levelData;
	}

	/**
	 * Gets current file
	 */
	public String getCurrentFile() {
		return this.currentFile;
	}

	/**
	 * Makes the different levels. Give it a filename and it will make that level depending on the number
	 */
	public void writeFile(String fileName) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(fileName.equals("level1")) {
			pw.println(fileName);
			pw.println("/........../");
			pw.println("/....._____/");
			pw.println("/......BBBB/");
			pw.println("/........../");
			pw.println("/BBBBBBBBBB/");
			pw.println("/..1...1.../");
			pw.println("/________../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/BBB......1/");
			pw.println("/___..H..__/");
			pw.close();
		}

		if(fileName.equals("level2")) {
			pw.println(fileName);
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/.1......../");
			pw.println("/____...___/");
			pw.println("/BBBB...BBB/");
			pw.println("/.......1../");
			pw.println("/__....__../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/BBB......H/");
			pw.println("/___.2...__/");
			pw.close();
		}
		if(fileName.equals("level3")) {
			pw.println(fileName);
			pw.println("/........../");
			pw.println("/..___..BB./");
			pw.println("/..BBB....1/");
			pw.println("/.......___/");
			pw.println("/.H.....BBB/");
			pw.println("/.......1../");
			pw.println("/__....__../");
			pw.println("/........../");
			pw.println("/.BBBBBBB../");
			pw.println("/..___....1/");
			pw.println("/..2.....__/");
			pw.close();
		}
		if(fileName.equals("level4")) {
			pw.println(fileName);
			pw.println("/..B.2...B./");
			pw.println("/...___..../");
			pw.println("/........B./");
			pw.println("/.......___/");
			pw.println("/...H...BBB/");
			pw.println("/...B...1../");
			pw.println("/..._____../");
			pw.println("/.1..B..B../");
			pw.println("/__......../");
			pw.println("/B..2....1./");
			pw.println("/B__B....__/");
			pw.close();
		}
		if(fileName.equals("level5")) {
			pw.println(fileName);
			pw.println("/........../");
			pw.println("/........H./");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/..P......./");
			pw.println("/........../");
			pw.close();
		}
		if(fileName.equals("level6")) {
			pw.println(fileName);
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.println("/........../");
			pw.close();
		}

	}


}
