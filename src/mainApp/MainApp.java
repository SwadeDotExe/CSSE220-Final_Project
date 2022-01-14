package mainApp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author Neha, Swade, Lucas
 * <br>Purpose: Top level class for CSSE220 Project containing main method
 */
public class MainApp {

	public static final int DELAY = 50;
	static JFrame frame = new JFrame("Here is the game");

	/**
	 * Sets up frame, game component, level changer, key listener, clock listener, and timer
	 */
	private void runApp() {

		// Frame
		frame.setSize(500,500);

		// Component
		GameComponent component = new GameComponent();
		frame.add(component, BorderLayout.CENTER);

		// Level Changer
		levelChanger lvlChanger = new levelChanger(component);
		LevelIo lvls = new LevelIo();
		lvlChanger.setChanger(lvls);
		lvls.writeFile("level1");
		lvls.writeFile("level2");
		lvls.writeFile("level3");
		lvls.writeFile("level4");
		lvls.writeFile("level5");
		lvls.writeFile("level6");
		lvls.readFile("Level1");
		component.setLevelChanger(lvlChanger);
		lvlChanger.decreaseLevel();

		// Key Listener
		InputListener inputL = new InputListener(lvlChanger, component);
		frame.addKeyListener(inputL);

		// Clock Listener
		AdvanceListener listener = new AdvanceListener(component, inputL);

		// Timer
		Timer time = new Timer(DELAY, listener);
		time.start();

		// Produces Frame
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.toFront();
		frame.requestFocus();

		lvlChanger.decreaseLevel();
		lvlChanger.decreaseLevel();
		lvlChanger.reloadLevel();

	} // runApp

	/**
	 * The main creates new instance of MainApp
	 * @param args
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

	public void movement() {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

}
