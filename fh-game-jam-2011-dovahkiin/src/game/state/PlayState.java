package game.state;

import game.entity.HochiEntity;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.entity.StudentEntity.StudentState;
import game.event.LoadMenueEvent;
import game.levelmanager.Level;
import game.levelmanager.LevelManager;
import game.motion.Rectangle;
import game.view.PlayView;
import game.view.IntroView.MainEntity;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class PlayState implements GameState, EventListener{

	public static final String NAME = "Play";
	
	private Core core;
	private View view;
	
	public PlayState(Core core) {
		this.core = core;
		this.view = new PlayView(core);
		this.view.registerResources(NAME);
		EventManager.getInstance(core).addListener(this, LoadMenueEvent.TYPE);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void onEnter() {
		ResourceManager.getInstance(this.core).loadGroup(NAME);
		this.view.engage();
		EntityManager entMngr = EntityManager.getInstance(this.core);
		//entMngr.addEntity(new SchaufiEntity(core, "Schaufi"));
		//entMngr.addEntity(new RudiEntity(this.core, "Rudi"));
		entMngr.addEntity(new StudentEntity(this.core, "Student01", StudentState.STAND, 500, -100));
		
		LevelManager lvlMngr = new LevelManager(core);
		
		Level startLevel = new Level(this.core, "startLevel");
		startLevel.addFloor();
		lvlMngr.addLevel(startLevel);
		
		Level secondLevel = new Level(this.core, "secondLevel");
		secondLevel.addFloor();
		lvlMngr.addLevel(secondLevel);
		
		secondLevel.setPrevLevel(startLevel.getName());
		startLevel.setNextLevel(secondLevel.getName());
		
		lvlMngr.setCurrentLevel(startLevel.getName());
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event.isOfType(LoadMenueEvent.TYPE)){
			handleLoadEvent((LoadMenueEvent) event);
		}
	}
	
	private void handleLoadEvent(LoadMenueEvent event) {
		EntityManager entMngr = EntityManager.getInstance(this.core);
		if(event.getSelected().equals(MainEntity.HOCHI)){
			entMngr.addEntity(new HochiEntity(core, "Hochi"));
		}else if(event.getSelected().equals(MainEntity.SHAUFI)){
			entMngr.addEntity(new SchaufiEntity(core, "Schaufi"));
		}else if(event.getSelected().equals(MainEntity.RUDI)){
			entMngr.addEntity(new RudiEntity(core, "Rudi"));
		}
	}

}
