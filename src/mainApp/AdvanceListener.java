package mainApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author  Neha, Swade
 * Purpose: Makes a timer for the game and updates the game
 *
 */
public class AdvanceListener implements ActionListener {

	private static Integer PRINT_DELAY = 200;

	private GameComponent gameComponent;
	private InputListener keyL;
	private long startTick = -1;
	private long endTick = -1;
	private long prevTick = System.currentTimeMillis()+1000;

	/**
	 * Constructor
	 * @param gameComponent
	 * @param keyListener
	 */
	public AdvanceListener(GameComponent gameComponent, InputListener keyListener) {
		this.gameComponent = gameComponent;
		this.keyL = keyListener;
	}


	/**
	 * Updates the clock, checks for collisions, regenerates hero health, updates keylistener, and prints diagnostics.
	 * @param e the action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Updates clock
		advanceOneTick();

		// Checks for collisions
		checkCollisions();

		// Regenerates the hero's health if he was hit
		regenHero();

		// Updates Key Listener
		keyL.updateKeyState();

		// Print Diagnostics
		printStats();
	}

	/**
	 * Adds a tick to the timer
	 */
	public void advanceOneTick() {
		this.gameComponent.updateState();
		this.gameComponent.drawScreen();
	}

	/**
	 *  Checks if collisions happened
	 */
	public void checkCollisions() {

		//Hero with Alien
		if(this.gameComponent.isHit() && !gameComponent.hero.collision) {
			gameComponent.hero.setCollision(true);
			gameComponent.lostLife();
		}
	}


	/**
	 * If hero is hit then it gives it invincibility
	 */
	public void regenHero() {
		if(this.gameComponent.hero.regen && endTick == -1) {
			startTick = System.currentTimeMillis();
			endTick = System.currentTimeMillis() + 1000;
		}
		if(System.currentTimeMillis() > endTick && this.gameComponent.hero.regen) {
			this.gameComponent.hero.regen = false;
			this.gameComponent.hero.collision = false;
			this.startTick = -1;
			this.endTick = -1;
		}
	}

	/**
	 * Prints stats to Console which makes it easier to debug the code
	 */
	public void printStats() {
		if(System.currentTimeMillis() >= prevTick + PRINT_DELAY) {
			prevTick = System.currentTimeMillis();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(" -------------------------------------------------------------------");
			System.out.println("| Hero Lives: " + this.gameComponent.livesLeft + " 	    |  Game Score: " + String.format("%03d", this.gameComponent.score) + "       | Aliens: " + this.gameComponent.aliens.size() + "    |");
			System.out.println("| Hero X Position: " + this.gameComponent.hero.centerX + "      |  Bombs Picked: " + String.format("%02d", this.gameComponent.bombsNotVis) + "      | Bullets: " + this.gameComponent.bullets.size() + "   |");
			System.out.println("| Hero Y Position: " + this.gameComponent.hero.centerY + "	    |  Total Bombs: " + String.format("%02d", this.gameComponent.bombs.size()) + "                      |");
			System.out.println("| Hero Invincible?: " + this.gameComponent.hero.regen + "   |  Current Level: " + (this.gameComponent.lvlChanger.curLevel + 1) + "                     |");
			System.out.println(" -------------------------------------------------------------------");
		}
	}
}
