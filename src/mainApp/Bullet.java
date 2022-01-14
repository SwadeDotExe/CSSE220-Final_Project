package mainApp;

import java.awt.Graphics2D;

/**
 *
 * @author Swade
 * Purpose: Makes bullets for hero to shoot
 *
 */
public class Bullet extends GameObject {

	/**
	 * Constructor for bullets
	 * @param x x - coordinate of bomb location
	 * @param y y - coordinate of bomb location
	 * @param component
	 * @param direction direction of bullet image
	 */
	public Bullet(int x, int y, GameComponent component, String direction) {
		super("images/bullet.png", component);
		if(direction.equals("left")) {
			this.centerX = x;
			this.centerY = y;
			this.velX = 5;
		}
		if(direction.equals("right")) {
			this.centerX = x + this.component.hero.DEFAULT_SIZE;
			this.centerY = y;
			this.velX = -5;
			this.setImage("images/bullet2.png");
		}
	}

	/**
	 *  Draws the bomb onto the screen
	 */
	public void drawBullet(Graphics2D g2d) {
		if (isVisible) {
			g2d.drawImage(objectImage, centerX, centerY, 30, 30, null);
			this.update();
		}
	}

	/**
	 * Updates location of bullet
	 */
	@Override
	public void update() {

		if (centerX <= 0 || centerX >= component.getWidth()) {
			this.isVisible = false;
		} else {

			centerX -= velX;
		}
	}
}
