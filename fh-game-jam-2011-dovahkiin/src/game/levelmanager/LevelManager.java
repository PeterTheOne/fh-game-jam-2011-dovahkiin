package game.levelmanager;

import game.event.LeaveScreenEvent;
import game.event.LoadLevelEvent;
import game.event.LeaveScreenEvent.LeaveScreen;

import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.logging.LoggingService;
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;

public class LevelManager implements EventListener{

	private Vector<Level> levels = new Vector<Level>();
	private Level currentLevel;
	private Core core;
	
	public LevelManager(Core core){
		this.core = core;
		EventManager evntMngr = EventManager.getInstance(this.core);
		evntMngr.addListener(this, LoadLevelEvent.TYPE);
		evntMngr.addListener(this, LeaveScreenEvent.TYPE);
		Level lvl = new Level(core, "startLevel");
		EventManager.getInstance(this.core).enqueueEvent(new LoadLevelEvent(lvl.getName()));
	}
	
	public void addLevel(Level lvl){
		this.levels.add(lvl);
	}
	
	public Level getLevel(String name) {
		for (Level level : levels) {
			if (level.getName().equals(name)) {
				return level;
			}
		}
		return null;
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
		
		if (event.isOfType(LeaveScreenEvent.TYPE)) {
			handleLeaveScreenEvent((LeaveScreenEvent) event);

			SoundHandle soundHandle = new SoundHandle("nextLevel_handle", "nextLevel.wav");
			soundHandle.load(this.core);
			SoundService.getInstance(this.core).play((SoundEffect)soundHandle.getResource());
		}
		
	}

	private void handleLeaveScreenEvent(LeaveScreenEvent event) {
		String newLevelName;
		if (event.getSide().equals(LeaveScreen.RIGHT)) {
			newLevelName = this.currentLevel.getNextLevel();
		} else {
			return;
			//newLevelName = this.currentLevel.getPrevLevel();
		}
		if (getLevel(newLevelName) != null) {
			setCurrentLevel(newLevelName);
		} else {
			LoggingService.getInstance(this.core).logError("LevelManager", "newLevel not found");
		}
	}
	
}
