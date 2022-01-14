package mainApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Lucas, Neha, Swade 
 * Purpose: Ties the game together before putting
 * it onto the frame. Renders: floors, aliens, hero, bombs, and background
 */
public class GameComponent extends JComponent {

	// Constants
	private static final long serialVersionUID = 1L;
	private static Integer ALIEN_WIDTH = 50;
	private static Integer ALIEN_HEIGHT = 25;
	private static Integer BULLET_SIZE = 20;

	// Variables
	private int numTicks;
	public Hero hero;
	public Integer livesLeft = 4;
	private HeroLives[] lives = new HeroLives[livesLeft];
	private int distanceX;
	private int distanceY;
	ArrayList<Rectangle2D> floors;
	public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	public ArrayList<Alien> aliens = new ArrayList<Alien>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Image bgImage;
	public Image floorTexture;
	public Image bombTexture;
	public Image gameOver;
	public levelChanger lvlChanger;
	private Graphics2D g2d;
	public int score = 0;
	public int bombsNotVis = 0;
	public boolean lost = false;
	int lost1 = 0;

	private int startX = 150;
	private int startY = 100;

	private int startX2 = 100;
	private int startY2 = 200;

	private int floorCount;

	/**
	 * Constructor creates new hero instance
	 */
	public GameComponent() {
		this.hero = new Hero(this);

	}

	/**
	 * Sets the level to change to
	 */
	public void setLevelChanger(levelChanger lvlChanger) {
		this.lvlChanger = lvlChanger;
	}

	/**
	 * Updates timer, collision, gravity, and hero tracking
	 */
	public void updateState() {
		// Add update functions here

		if (floorCount > 0) {// gives the alien gravity
			if (!this.setAlienCollided()) {
				for (Alien a : aliens) {
					a.gravity();
				}
			}
		}
		this.numTicks++;
		for (Alien a : aliens) {
			a.setHeroPosition(hero.getCoords()[0], hero.getCoords()[1]);
		}
	}

	/**
	 * Sets a vector to the unit
	 */
	public void setDistance(int distanceX, int distanceY) {
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}

	/**
	 * Sets the position of initial start to the hero
	 */
	public void setStart(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
		this.hero.startPosition(startX, startY);
	}

	/**
	 * Paints the hero, floors, bombs, and aliens
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// This is the order the objects are drawn in!
		this.drawLevel(g2d);

		for (Bullet b : bullets) {
			b.drawBullet(g2d);
		}
		this.drawBombs();
		for (Alien a : aliens) {
			a.drawAlien(g2d);
		}
		this.drawScoreboard(g2d);
		this.hero.drawHero(g2d);
	}

	/**
	 * Updates the screen
	 */
	public void drawScreen() {
		this.repaint();
		for (Alien a : aliens) {
			a.update();
		}
	}

	/**
	 * Sets the floors
	 */
	public void setFloors(ArrayList<Rectangle2D> floors) {
		this.floors = floors;
	}

	/**
	 * Sets the bombs
	 */
	public void setBombs(ArrayList<Bomb> bombs) {
		this.bombs = bombs;
	}

	/**
	 * Checks if hero is collided with floor
	 */
	public Boolean isCollided() {

		if (this.floors != null) {
			for (Rectangle2D floor : this.floors) {

				// Check X Coordinate
				if (this.hero.getCoords()[0] > floor.getX()
						&& this.hero.getCoords()[0] < (floor.getX() + floor.getWidth())) {

					// Check Y Coordinate (With range for error)
					if (this.hero.getCoords()[1] < (floor.getY() + 4)
							&& this.hero.getCoords()[1] > (floor.getY() - 4)) {
						// System.out.println("Hitting floor");
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if Hero collided with Alien
	 */
	public Boolean isHit() {
		for (Alien a : aliens) {

			// Hero hit from above (Hero loses life)
			if ((hero.centerX - a.centerX <= ALIEN_WIDTH && a.centerX - hero.centerX <= ALIEN_WIDTH)
					&& (hero.centerY - a.centerY <= ALIEN_WIDTH && a.centerY - hero.centerY <= ALIEN_WIDTH)
					&& a.isVisible) {
				return true;
			}

			// Alien gets hit with bullet
			for (Bullet b : bullets) {
				if ((b.centerX - a.centerX <= BULLET_SIZE && a.centerX - b.centerX <= BULLET_SIZE)
						&& (b.centerY - (a.centerY + a.DEFAULT_SIZE / 2) <= BULLET_SIZE
								&& (a.centerY - a.DEFAULT_SIZE / 2) - b.centerY <= BULLET_SIZE)
						&& a.isVisible && b.isVisible) {
					a.killAlien();
					b.isVisible = false;
				}
			}
		}

		return false;

	}

	/**
	 * Check if Alien is collided with floor
	 */
	public Boolean setAlienCollided() {
		for (Alien a : aliens) {
			if (this.floors != null) {
				for (Rectangle2D floor : this.floors) {
					if (a.getCoords()[0] > (floor.getX() - 4)
							&& a.getCoords()[0] < (floor.getX() + (floor.getWidth() + 4))) {
						if (a.getCoords()[1] < (floor.getY() + 4) && a.getCoords()[1] > (floor.getY() - 4)) {
							// System.out.println("collision");
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 *  Draws the current level
	 */
	public void drawLevel(Graphics2D g2d) {
		this.g2d = g2d;
		g2d.create();

		// Draws image for the Background

		if (this.lvlChanger.curLevel == 0) {
			try {
				this.bgImage = ImageIO.read(new File("images/background.png"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (this.lvlChanger.curLevel == 1) {
			try {
				this.bgImage = ImageIO.read(new File("images/Background2.png"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (this.lvlChanger.curLevel == 2) {
			try {
				this.bgImage = ImageIO.read(new File("images/Background3.png"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (this.lvlChanger.curLevel == 3) {
			try {
				this.bgImage = ImageIO.read(new File("images/Background4.png"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (this.lvlChanger.curLevel == 4) {
			try {
				this.bgImage = ImageIO.read(new File("images/Background5.png"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (this.lvlChanger.curLevel == 5) {
			try {
				this.bgImage = ImageIO.read(new File("images/WinScreen.jpg"));
				g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Draws image for the boarder
		try {
			this.bgImage = ImageIO.read(new File("images/Border.png"));
			g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight() - 50, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Loads image for Floor
		try {
			this.floorTexture = ImageIO.read(new File("images/FloorBlocks.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Draws floors
		if (this.floors != null) {
			for (int i = 0; i < this.floors.size(); i++) {
				floorCount = i;
				g2d.drawImage(floorTexture, (int) this.floors.get(i).getMinX(), (int) this.floors.get(i).getMinY(),
						(int) this.floors.get(i).getWidth() + 3, (int) this.floors.get(i).getHeight(), null);
			}
		}

		// Draws Lives

		for (Integer a = 0; a < livesLeft; a++) {
			this.lives[a] = new HeroLives(HeroLives.LIVES_SIZE * (a * 2), this.getHeight(), this);
			this.lives[a].drawLives(g2d);
		}

		if (this.lost) {
			this.end();
		}
	}

	/**
	 * Draws the bombs onto the screen
	 */
	public void drawBombs() {
		// Loads image for Bombs
		try {
			this.bombTexture = ImageIO.read(new File("images/bomb.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Draws Bombs
		isPickedup();
		if (this.bombs != null) {
			for (int i = 0; i < this.bombs.size(); i++) {
				if (this.bombs.get(i).isVisible) {
					this.bombs.get(i).drawBomb(g2d);
				}
			}
		}

	}

	/**
	 *  If a life is lost it will reset the heros position and take a life away
	 */
	public void lostLife() {
		if (this.livesLeft > 1 && !this.hero.regen) {
			this.livesLeft = livesLeft - 1;
			this.setStart(startX, startY);
			this.repaint();
			System.out.println("LOST LIFE");
			this.hero.regen = true;
			this.hero.collision = true;
		} else {
			// end game
			System.out.println("--------GAME OVER--------");
			this.lost = true;
		}
	}

	/**
	 *  Picks up the bombs and adds it to the scoreboard
	 */
	public void isPickedup() {
		for (int i = 0; i < this.bombs.size(); i++) {
			if ((this.hero.centerX - this.bombs.get(i).getX() <= 40 && this.bombs.get(i).getX() - hero.centerX <= 40)
					&& (this.hero.centerY - this.bombs.get(i).getY() <= 40
							&& this.bombs.get(i).getY() - hero.centerY <= 40)
					&& this.bombs.get(i).isVisible) {
				this.bombs.get(i).isVisible = false;
				bombsNotVis++;
				this.score += 10;
			}
		}
	}

	/**
	 * Draws the scoreboard
	 */
	public void drawScoreboard(Graphics2D g2d) {
		g2d.setFont(new Font("8514oem Regular", Font.PLAIN, 50));
		g2d.drawString("Score:", this.getWidth() - 275, this.getHeight() - 5);

		g2d.drawString(Integer.toString(this.score), this.getWidth() - 120, this.getHeight() - 5);

	}

	/**
	 * Checks if the bombs are all picked up
	 */
	public Boolean allBombsGone() {
		if (lvlChanger.curLevel >= 0) {
			if (bombsNotVis >= this.bombs.size()) {
				this.bombsNotVis = 0;
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates different types of aliens
	 */
	public void createAlien(Integer startX, Integer startY, Integer alienType) {

		// Simple Alien, just moves back and forth on platform
		if (alienType == 1) {
			Alien alien = new Alien(startX, startY, "images/alien.png", this, 1);
			alien.moveSpeed = 5;
			this.aliens.add(alien);
		}

		// Complex Alien, chases hero with speed dependent on level
		if (alienType == 2) {
			Alien alien = new Alien(startX, startY, "images/alien2.png", this, 2);
			alien.moveSpeed = 4;
			alien.velY = (int) alien.moveSpeed;
			this.aliens.add(alien);
		}

		// Boss Alien, only found in final level
		if (alienType == 3) {
			Alien alien = new Alien(startX, startY, "images/alien2.png", this, 3);
			alien.moveSpeed = 4;
			alien.velY = (int) alien.moveSpeed;
			alien.setBoss();
			this.aliens.add(alien);
		}
	}

	/**
	 * What is called when the game is over
	 */
	public void end() {
		this.removeAll();

		Image objectImage = null;
		try {
			objectImage = ImageIO.read(new File("images/GameOverImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.aliens.removeAll(aliens);
		this.bombs.removeAll(bombs);
		this.lvlChanger.increaseLevel();
		g2d.drawImage(objectImage, 40, 100, null);

		this.repaint();
	}

	/**
	 * Shoots bullets given a direction
	 * @param direction
	 */
	public void shootBullet(String direction) {
		Bullet bullet = new Bullet(this.hero.centerX - 45, this.hero.centerY - 15, this, direction);
		this.bullets.add(bullet);
	}
}
