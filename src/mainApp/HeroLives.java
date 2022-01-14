package mainApp;

import java.awt.Graphics2D;

/**
 * @author Neha
 * Purpose: Shows the hero's lives as images of the bomb jack
 */
public class HeroLives extends GameObject{
	public static int lives = 3;
	public static int LIVES_SIZE = 25;

	/**
	 * @param x x - coordinate of hero lives location
	 * @param y y - coordinate of hero lives location
	 * @param component
	 */
	public HeroLives(int x, int y, GameComponent component) {
		super("images/Lives.png", component);
		this.centerX = x + LIVES_SIZE;
		this.centerY = y - LIVES_SIZE;
	}

	/**
	 * Draws the lives
	 */
	public void drawLives(Graphics2D g2d) {
		drawObject(g2d);
	}
}
