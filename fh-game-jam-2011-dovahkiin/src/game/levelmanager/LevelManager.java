package game.levelmanager;

import game.event.LoadLevelEvent;

import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;

public class LevelManager implements EventListener{

	private Vector<Level> levels = new Vector<Level>();
	private Level currentLevel;
	private Core core;
	
	public LevelManager(Core core){
		this.core = core;
		EventManager.getInstance(this.core).addListener(this, LoadLevelEvent.TYPE);
		Level lvl = new Level(core, "startLevel");
		EventManager.getInstance(this.core).enqueueEvent(new LoadLevelEvent(lvl.getName()));
	}
	
	public void addLevel(Level lvl){
		this.levels.add(lvl);
	}
	
	public boolean hasLevels(){
		return !levels.isEmpty();
	}
	
	public void setCurrentLevel(String lvl){
		/*if(!levels.contains(lvl)){
			levels.add(lvl);
		}
		currentLevel = levels.indexOf(lvl);*/
		for (Level level : levels) {
			if (level.getName().equals(lvl)) {
				if (this.currentLevel != null) {
					this.currentLevel.disenage();
				}
				this.currentLevel = level;
				this.currentLevel.engage();
			}
		}
	}
	
	public Level getCurrentLevel(){
		if(this.hasLevels()){
			return this.currentLevel;
		} else {
			return null;
		}
	}

	public void handleEvent(Event event) {
		/*if(event.isOfType(LoadLevelEvent.TYPE)){
			LoadLevelEvent e = (LoadLevelEvent)event;
			String levelName = e.getLevel();
			for(Level l: levels){
				if(levelName.equals(l.getLevelName())){
					setCurrentLevel(l);
				}
			}
		}*/
		
	}
	
}
