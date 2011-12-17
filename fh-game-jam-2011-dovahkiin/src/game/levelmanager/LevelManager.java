package game.levelmanager;

import java.util.Vector;

public class LevelManager {

	private Vector<Level> levels = new Vector<Level>();
	private int currentLevel;
	
	public LevelManager(){}
	
	public void addLevel(Level lvl){
		levels.add(lvl);
		if(levels.size() == 1){
			currentLevel = levels.indexOf(lvl);
		}
	}
	
	public boolean hasLevels(){
		return !levels.isEmpty();
	}
	
	public void setCurrentLevel(Level lvl){
		if(!levels.contains(lvl)){
			levels.add(lvl);
		}
		currentLevel = levels.indexOf(lvl);
	}
	
	public Level getCurrentLevel(){
		if(this.hasLevels()){
			return levels.get(currentLevel);
		} else {
			return null;
		}
	}
	
}
