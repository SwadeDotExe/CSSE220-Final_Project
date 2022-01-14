package mainApp;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Lucas, Neha, Swade
 * Purpose: It creates the level in the frame with the floors and bombs
 *
 */
public class levelChanger {

	LevelIo lvls;
	String currentLvl;
	public ArrayList<Rectangle2D> floors;
	public ArrayList<Bomb> bombs;
	public int curLevel = 0;
	int previous_key = 0;
	Integer maxLevels = 6;

	private GameComponent gameComponent;

	/**
	 * Constructor for the level changer
	 * @param gameComponent
	 */
	public levelChanger(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	/**
	 * It resets the level to the original state
	 */
	public void reloadLevel() {

		this.setLevel();
		char lvlChar = this.currentLvl.charAt(this.currentLvl.length() - 1);
		int lvl = lvlChar - '0';
		loadLevel(lvl);
	}

	/**
	 * It increases the level to the one above it
	 */
	public void increaseLevel() {
		if (curLevel + 2 <= maxLevels) {
			this.setLevel();
			char lvlChar = this.currentLvl.charAt(this.currentLvl.length() - 1);
			int lvl = lvlChar - '0';
			loadLevel(lvl += 1);
			this.curLevel = this.curLevel + 1;
		}
	}

	/**
	 * It decreases the level to the one below it
	 */
	public void decreaseLevel() {

		if(curLevel - 1 >= 0) {
		this.setLevel();
		char lvlChar = this.currentLvl.charAt(this.currentLvl.length() - 1);
		int lvl = lvlChar - '0';
		loadLevel(lvl -= 1);
		this.curLevel = this.curLevel - 1;
		}
	}

	/**
	 * Sets the levels
	 */
	public void setLevel() {
		this.gameComponent.aliens.clear();
		this.gameComponent.bullets.clear();
		this.currentLvl = this.lvls.getCurrentFile();
	}

	/**
	 * Sets level to change to
	 */
	public void setChanger(LevelIo lvls) {
		this.lvls = lvls;
	}

	/**
	 * It constructs the level parts: bombs, floors, aliens, and hero
	 */
	public void loadLevel(Integer levelNumber) {

		Boolean startFlag = false;
		ArrayList<String> levelData = lvls.readFile("level" + Integer.toString(levelNumber));
		Integer rowHeight = (int) ((gameComponent.getHeight() - (GameObject.BOARDER_WIDTH * 2)
				- GameObject.SCORE_HEIGHT) / (levelData.size() - 1));
		Integer rowWidth = (gameComponent.getWidth() - (GameObject.BOARDER_WIDTH * 2))
				/ (levelData.get(1).length() - 2);
		floors = new ArrayList<>();
		bombs = new ArrayList<>();

		for (Integer a = 1; a < levelData.size(); a++) {
			for (Integer b = 1; b < levelData.get(1).toString().length(); b++) {
				Character currChar = levelData.get(a).charAt(b);
				if (currChar == '/' && startFlag == false) {
					startFlag = true;
				}

				if (startFlag == true && currChar == '_') {
					Rectangle2D floorRect = new Rectangle2D.Double(rowWidth * (b - 1) + GameObject.BOARDER_WIDTH,
							rowHeight * (a - 1) + GameObject.BOARDER_WIDTH, rowWidth, rowHeight);

					floors.add(floorRect);
				}

				if (startFlag == true && currChar == 'B') {
					Bomb bomb = new Bomb(rowWidth * (b - 1) + GameObject.BOARDER_WIDTH,
							rowHeight * (a - 1) + GameObject.BOARDER_WIDTH, rowWidth, rowHeight, gameComponent);
					bombs.add(bomb);
				}

				if (startFlag == true && currChar == 'H') {
					this.gameComponent.setStart(rowWidth * (b - 1), rowHeight * (a - 1));
				}

				if (startFlag == true && currChar == '1') {
					this.gameComponent.createAlien(rowWidth * (b - 1) + GameObject.BOARDER_WIDTH,
												   rowHeight * (a - 1) + GameObject.BOARDER_WIDTH, 1);
				}

				if (startFlag == true && currChar == '2') {
					this.gameComponent.createAlien(rowWidth * (b - 1) + GameObject.BOARDER_WIDTH,
							   					   rowHeight * (a - 1) + GameObject.BOARDER_WIDTH, 2);
				}

				if (startFlag == true && currChar == 'P') {
					this.gameComponent.createAlien(rowWidth * (b - 1) + GameObject.BOARDER_WIDTH,
							   					   rowHeight * (a - 1) + GameObject.BOARDER_WIDTH, 3);
				}
			}
		}
		this.gameComponent.setFloors(floors);
		this.gameComponent.setBombs(bombs);

		// Actually 6
		if(this.curLevel == 4) {
			this.gameComponent.hero.isVisible = false;
		}
		else {
			this.gameComponent.hero.isVisible = true;
		}


	}

}
