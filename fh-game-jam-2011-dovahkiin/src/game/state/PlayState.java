package game.state;

import game.entity.HochiEntity;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.entity.StudentEntity.StudentState;
import game.event.DestroyEntityEvent;
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
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void onEnter() {
		ResourceManager.getInstance(this.core).loadGroup(NAME);
		this.view.engage();
		
		LevelManager lvlMngr = new LevelManager(core);
		
		Level startLevel = new Level(this.core, "level1_1");
		startLevel.addFloor();
		lvlMngr.addLevel(startLevel);

		Level secondLevel = new Level(this.core, "level1_2");
		secondLevel.addFloor();
		secondLevel.addEnemy(new StudentEntity(this.core, "Student01", StudentState.STAND, 300, -50));
		lvlMngr.addLevel(secondLevel);
		
		Level thirdLevel = new Level(this.core, "level1_3");
		thirdLevel.addFloor();
		lvlMngr.addLevel(thirdLevel);
		
		startLevel.setNextLevel(secondLevel.getName());
		secondLevel.setNextLevel(thirdLevel.getName());
		
		//thirdLevel.setPrevLevel(secondLevel.getName());
		//secondLevel.setPrevLevel(startLevel.getName());
		
		lvlMngr.setCurrentLevel(startLevel.getName());

		EventManager.getInstance(core).addListener(this, LoadMenueEvent.TYPE);
		EventManager.getInstance(core).addListener(this, DestroyEntityEvent.TYPE);
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
		} else if (event.isOfType(DestroyEntityEvent.TYPE)) {
			handleDestroyEntityEvent((DestroyEntityEvent) event);
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
	
	private void handleDestroyEntityEvent(DestroyEntityEvent event) {
		EntityManager entMngr = EntityManager.getInstance(this.core);
		if (entMngr.hasEntity(event.getEntityName())) {
			entMngr.removeEntity(event.getEntityName());
		}
	}

}
