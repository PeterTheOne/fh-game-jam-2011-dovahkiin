package game.levelmanager;

import game.event.LoadLevelEvent;

import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;

public class LevelManager implements EventListener{

	private Vector<Level> levels = new Vector<Level>();
	private int currentLevel;
	private Core core;
	
	public LevelManager(Core core){
		this.core = core;
		EventManager.getInstance(this.core).addListener(this, LoadLevelEvent.TYPE);
		Level lvl = new Level(core, "startLevel");
		EventManager.getInstance(this.core).enqueueEvent(new LoadLevelEvent(lvl.getLevelName()));
	}
	
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

	public void handleEvent(Event event) {
		if(event.isOfType(LoadLevelEvent.TYPE)){
			LoadLevelEvent e = (LoadLevelEvent)event;
			String levelName = e.getLevel();
			for(Level l: levels){
				if(levelName.equals(l.getLevelName())){
					setCurrentLevel(l);
				}
			}
		}
		
	}
	
}
