package mainApp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Neha, Swade
 * Purpose: Makes a generic game object
 *
 */
public class GameObject {

	// Constants
	public int DEFAULT_SIZE = 50;
	public static final int DEFAULT_DISTANCE_X = 10;
	public static final int DEFAULT_DISTANCE_Y = 10;
	public static int DEFAULT_GRAVITY_DISTANCE = 5;
	public static int BOARDER_WIDTH = 20;
	public static int SCORE_HEIGHT = 50;

	// Variables
	public int centerX;
	public int centerY;
	public int velX;
	public Image objectImage;
	public boolean collision;
	public GameComponent component;
	public boolean isVisible = true;

	/**
	 * Constructor for GameObject
	 * @param path
	 * @param component
	 */
	public GameObject(String path, GameComponent component) {

		// Loads image for Hero
		try {
			this.objectImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.component = component;

	}

	/**
	 * Gravity called every time the game updates && an arrow key is NOT pressed.
	 */
	public void gravity() {
		if (!isOffScreen()) {
			this.centerY += DEFAULT_GRAVITY_DISTANCE;
		}
	}

	/**
	 *  Draws the Object
	 */
	public void drawObject(Graphics2D g2d) {
		g2d = (Graphics2D) g2d.create();
		g2d.translate(this.centerX, this.centerY);
		g2d.drawImage(this.objectImage, -(DEFAULT_SIZE / 2), -(DEFAULT_SIZE / 2), DEFAULT_SIZE, DEFAULT_SIZE, null);
	}

	/**
	 * Returns coordinates of Object
	 */
	public Integer[] getCoords() {
		Integer[] coords = new Integer[2];
		coords[0] = this.centerX;
		coords[1] = this.centerY + (DEFAULT_SIZE / 2);
		return coords;
	}

	/**
	 * Sets object to given X and Y
	 */
	public void startPosition(int startX, int startY) {
		// Places Hero at the starting position
		this.centerX = startX + (this.objectImage.getWidth(null) / 10);
		this.centerY = startY + (this.objectImage.getHeight(null) / 10);
	}

	/**
	 * Sets that the object got hit
	 */
	public void setCollision(boolean b) {
		this.collision = b;
	}

	/**
	 * Updates position of Object
	 */
	public void update() {

		if (!isOffScreen()) {
			centerX += velX;
			if (centerX > 180 || centerX < 100) {
				centerX = (int) Math.min(Math.max(centerX, 100), 180);
				velX = -velX;
			}
		}
	}

	/**
	 * Checks if object is collided with the floor
	 */
	public Boolean checkFloorCollision() {
		if (this.component.floors != null) {
			for (Rectangle2D floor : this.component.floors) {

				// Check X Coordinate
				if (this.getCoords()[0] > floor.getX() && this.getCoords()[0] < (floor.getX() + floor.getWidth())) {

					// Check Y Coordinate (With range for error)
					if (this.getCoords()[1] < (floor.getY()) && this.getCoords()[1] > (floor.getY())) {
						System.out.println("Hitting floor");
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if object is outside of the border
	 */
	public boolean isOffScreen() {
		boolean xLow = centerX + (DEFAULT_SIZE / 2) < BOARDER_WIDTH;
		boolean xHigh = centerX + (DEFAULT_SIZE / 2) > component.getWidth() - BOARDER_WIDTH;
		boolean yLow = centerY + (DEFAULT_SIZE / 2) < BOARDER_WIDTH;
		boolean yHigh = centerY + (DEFAULT_SIZE / 2) > component.getHeight() - (SCORE_HEIGHT + BOARDER_WIDTH);
		return xLow || xHigh || yLow || yHigh;
	}

	/**
	 * Sets the image path
	 */
	public void setImage(String path) {

		try {
			this.objectImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
