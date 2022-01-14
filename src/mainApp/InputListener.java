package mainApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Lucas, Neha, Swade
 * Purpose: Takes in keyboard inputs and either
 * increases,decreases,reloads level as well as movement for hero
 *
 */
public class InputListener implements KeyListener {

	private levelChanger levelC;
	private Hero hero;
	private GameComponent component;

	private boolean keyLock = false;
	private Integer key = 00;
	private Integer secondKey = 00;

	/**
	 * Constructor for input listener
	 * @param levelC level changer
	 * @param component
	 */
	public InputListener(levelChanger levelC, GameComponent component) {
		this.levelC = levelC;
		this.hero = component.hero;
		this.component = component;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 *  Sets the keys pressed
	 *  @param e keyevent
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == 87) {
			this.component.shootBullet("left");
		}

		if(e.getKeyCode() == 69) {
			this.component.shootBullet("right");
		}


		// Records new key if the last key has been released
		if (!keyLock) {
			key = e.getKeyCode();
			keyLock = true;
		}

		// Sets secondary key (for diagonals) while key is locked
		else {
			if (e.getKeyCode() != key && secondKey == 00) {
				secondKey = e.getKeyCode();
			}
		}

//		System.out.println(key);

	}

	/**
	 * When key is released changes the level
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		keyLock = false;
		secondKey = 00;

		// Spacebar - Reload Current Level
		if (key == 32) {
			this.levelC.reloadLevel();
		}
		// U Key - Increase Current Level
		if (key == 85) {
			this.levelC.increaseLevel();
		}
		// D Key - Decrease Current Level
		if (key == 68) {
			this.levelC.decreaseLevel();
		}

	}

	/**
	 * All the movements for Hero
	 */
	public void updateKeyState() {
		if (keyLock) {

			// Left Arrow - Move Hero Left
			if (key == 37 && secondKey == 0) {
				hero.move(180);
			}
			// Right Arrow - Move Hero Right
			if (key == 39 && secondKey == 0) {
				hero.move(0);
			}
			// Up Arrow - Move Hero Up
			if (key == 38 && secondKey == 0) {
				hero.move(90);
			}
			// Up-Right
			if (key == 38 && secondKey == 39 || key == 39 && secondKey == 38) {
				hero.move(45);
			}
			// Up-Left
			if (key == 38 && secondKey == 37 || key == 37 && secondKey == 38) {
				hero.move(135);
			}
		}

		// Calls gravity if no keys are being pressed.
		if (!keyLock && !this.component.isCollided()) {
			hero.gravity();
		}

		if (this.levelC.curLevel >= 1) {
			this.component.drawBombs();
		}

		if (this.component.bombs != null) {
			if (this.component.allBombsGone() && this.component.lvlChanger.curLevel != 4) {
				this.component.lvlChanger.increaseLevel();
			}
		}

	}

}
