package mainApp;

import java.awt.Graphics2D;

/**
 *
 * @author Swade, Neha
 * Purpose: Makes a hero that can move, collect bombs,
 *
 */
public class Hero extends GameObject {

	Boolean regen = false;

	/**
	 * Constructor for Hero
	 * @param component
	 */
	public Hero(GameComponent component) {

		// Sets image
		super("images/hero_jack.png", component);
	}

	/**
	 * Takes an angle and moves the hero
	 */
	public void move(Integer angle) {

		// Detect Collision
		if (!collision) {
		}

		// Move up
		if (angle == 90 && (this.centerY - DEFAULT_DISTANCE_Y) > GameObject.BOARDER_WIDTH * 2) {
			this.centerY -= DEFAULT_DISTANCE_Y;
		}
		// Move left
		if (angle == 180 && (this.centerX - DEFAULT_DISTANCE_X) > GameObject.BOARDER_WIDTH * 2) {
			this.centerX -= DEFAULT_DISTANCE_X;
//			this.gravity();
		}
		// Move right
		if (angle == 0 && (this.centerX + DEFAULT_DISTANCE_X) < component.getWidth() - GameObject.BOARDER_WIDTH * 2) {
			this.centerX += DEFAULT_DISTANCE_X;
//			this.gravity();
		}
		// Move up-right
		if (angle == 45 && (this.centerY - DEFAULT_DISTANCE_Y) > GameObject.BOARDER_WIDTH * 2
				&& (this.centerX + DEFAULT_DISTANCE_X) < component.getWidth() - GameObject.BOARDER_WIDTH * 2) {
			this.centerY -= DEFAULT_DISTANCE_Y;
			this.centerX += DEFAULT_DISTANCE_X;
		}
		// Move up-left
		if (angle == 135 && (this.centerY - DEFAULT_DISTANCE_Y) > GameObject.BOARDER_WIDTH * 2
				&& (this.centerX - DEFAULT_DISTANCE_X) > GameObject.BOARDER_WIDTH * 2) {
			this.centerY -= DEFAULT_DISTANCE_Y;
			this.centerX -= DEFAULT_DISTANCE_X;
		}
	}

	/**
	 *  Draws the Hero
	 */
	public void drawHero(Graphics2D g2d) {
		if (this.isVisible) {
			drawObject(g2d);
		}
	}

}
