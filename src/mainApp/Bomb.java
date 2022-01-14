package mainApp;

import java.awt.Graphics2D;

/**
 *
 * @author Lucas, Neha, Swade
 * Purpose: To make cherry bombs that the hero can pick up
 *
 */
public class Bomb extends GameObject {

	Integer width;
	Integer height;

	/** Constructor for bombs
	 * @param x x - coordinate of bomb location
	 * @param y y - coordinate of bomb location
	 * @param imagePath
	 * @param component
	 */
	public Bomb(int x, int y, int width, int height, GameComponent component) {
		super("images/bomb.png", component);
		this.centerX = x;
		this.centerY = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Changes visibility if hero touches the bomb
	 */
	public void touchBomb() {
		this.isVisible = false;
	}

	/**
	 * Draws the bomb onto the screen
	 */
	public void drawBomb(Graphics2D g2d) {
		if (g2d != null) {
			g2d.drawImage(objectImage, centerX, centerY, width, height, null);
		}
	}

	/**
	 * Gets x coordinate of bomb
	 */
	public Integer getX() {
		return this.centerX;
	}

	/**
	 * Gets y coordinate of bomb
	 */
	public Integer getY() {
		return this.centerY;
	}

}
