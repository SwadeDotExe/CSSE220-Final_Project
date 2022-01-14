package mainApp;

import java.awt.Graphics2D;

/**
 *
 * @author Neha, Swade 
 * Purpose: Sets up aliens of different type. Includes: Generic Aliens that move back and forth and hero tracking aliens.
 *
 */
public class Alien extends GameObject {

	// Variables
	public int moveSpeed = 5;
	public int velY;
	public boolean isVisible = true;
	public int startX;
	private Boolean isBoss = false;
	public Integer bossCount = 0;
	private int heroX;
	private int heroY;
	private int alienType;

	/** Constructor for aliens
	 * @param x x - coordinate of alien location
	 * @param y y - coordinate of alien location
	 * @param imagePath
	 * @param component
	 * @param alienType tracking or back and forth moving
	 */
	public Alien(int x, int y, String imagePath, GameComponent component, int alienType) {

		// Sets image
		super(imagePath, component);

		this.centerX = x;
		this.centerY = y;
		this.startX = x;
		this.alienType = alienType;
		this.velX = (int) (moveSpeed);

		if(alienType != 1) {
			this.velX = 2;
			this.velY = 2;
		}
	}

	/**
	 *  Draws the Alien on the screen
	 *  @param g2d
	 */
	public void drawAlien(Graphics2D g2d) {
		if (isVisible) {
			drawObject(g2d);
		}

	}

	/**
	 * Makes Alien disappear if hit with bullet and gives point for killing alien
	 */
	public void killAlien() {

		if(isBoss) {
			bossCount++;
		}

		if (isBoss && bossCount >= 10) {
			this.isVisible = false;
			this.component.score += 6969;
			this.component.lvlChanger.increaseLevel();
		}

		else if(!isBoss) {
			this.isVisible = false;
			this.component.score += 50;
		}


	}

	/**
	 * Makes Boss Alien
	 */
	public void setBoss() {
		this.DEFAULT_SIZE = 150;
		this.velX = 2;
		this.isBoss = true;
	}

	/**
	 * Gives the alien movement and gravity based on type
	 */
	@Override
	public void update() {

		if(this.alienType == 1) {
			centerX += velX;
			centerY += velY;

			if (centerX > this.startX + 20 || centerX < this.startX - 20) {
				centerX = (int) Math.min(Math.max(centerX,  this.startX - 20), this.startX + 20);
				velX = -velX;
			}

			if (centerY > (component.getHeight() - BOARDER_WIDTH - SCORE_HEIGHT) || centerY < BOARDER_WIDTH) {
				centerY = (int) Math.min(Math.max(centerY, 0), 450);
				velY = -velY;
				centerY += velY;
			}
		} else {
			//hero is to the left of the alien
			if(this.centerX > this.heroX && centerX < (component.getWidth() - BOARDER_WIDTH) && centerX > BOARDER_WIDTH) {
				this.centerX -= velX;
			}

			//hero is to the right of the alien
			if(this.centerX < this.heroX && centerX < (component.getWidth() - BOARDER_WIDTH) && centerX > BOARDER_WIDTH) {
				this.centerX += velX;
			}

			//hero is below alien
			if(this.centerY > this.heroY - 20 && centerY < (component.getHeight() - BOARDER_WIDTH - SCORE_HEIGHT) && centerY > BOARDER_WIDTH) {
				this.centerY -= velY;
			}

			//hero is above alien
			if(this.centerY < this.heroY - 20 && centerY < (component.getHeight() - BOARDER_WIDTH - SCORE_HEIGHT) && centerY > BOARDER_WIDTH) {
				this.centerY += velY;
			}
		}
	}

	/**
	 * Allows tracking alien to get the hero's position
	 */
	public void setHeroPosition(int x, int y){
		this.heroX = x;
		this.heroY = y;
	}

}
