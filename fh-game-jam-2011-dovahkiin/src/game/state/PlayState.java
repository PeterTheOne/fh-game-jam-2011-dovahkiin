package game.state;

import game.entity.HochiEntity;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.entity.StudentEntity.StudentState;
import game.entity.StudentEntity1;
import game.entity.StudentEntity2;
import game.entity.StudentEntity3;
import game.event.DestroyEntityEvent;
import game.event.LoadMenueEvent;
import game.levelmanager.Level;
import game.levelmanager.LevelManager;
import game.motion.Rectangle;
import game.view.PlayView;
import game.view.IntroView.MainEntity;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.logging.LoggingService;
import org.cogaen.name.NameService;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class PlayState implements GameState, EventListener{

	public static final String NAME = "Play";
	
	private Core core;
	private View view;
	private EntityManager entMngr;
	private Entity character;
	
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
		
		this.entMngr = EntityManager.getInstance(this.core);
		EntityManager entMngr = EntityManager.getInstance(this.core);
		
		entMngr.addEntity(this.character);
		
		LevelManager lvlMngr = new LevelManager(core);
		
		Level startLevel = new Level(this.core, "level1_1");
		startLevel.addFloor();
		lvlMngr.addLevel(startLevel);

		Level secondLevel = new Level(this.core, "level1_2");
		secondLevel.addFloor();
		secondLevel.addEnemy(createStudent(StudentState.MIDDLE, 300, -50));
		lvlMngr.addLevel(secondLevel);
		
		Level thirdLevel = new Level(this.core, "level1_3");
		thirdLevel.addFloor();
		lvlMngr.addLevel(thirdLevel);
		
		startLevel.setNextLevel(secondLevel.getName());
		secondLevel.setNextLevel(thirdLevel.getName());
		
		//thirdLevel.setPrevLevel(secondLevel.getName());
		//secondLevel.setPrevLevel(startLevel.getName());
		
		lvlMngr.setCurrentLevel(startLevel.getName());

		EventManager.getInstance(core).addListener(this, DestroyEntityEvent.TYPE);
		EventManager.getInstance(core).addListener(this, LoadMenueEvent.TYPE);
	}

	private StudentEntity createStudent(StudentState state, int positionX, int positionY) {
		String name = NameService.getInstance(core).generateName();
		int rand = (int)(Math.random() * IntroState.getCharacters());
		switch(rand){
		case 0:
			return createStudent1(state, name, positionX, positionY);
		case 1:
			return createStudent2(state, name, positionX, positionY);
		case 2:
		default:
			return createStudent3(state, name, positionX, positionY);
		}
	}

	private StudentEntity createStudent1(StudentState state, String name, int positionX, int positionY) {
		return new StudentEntity1(core, name , state, positionX, positionY);
	}
	
	private StudentEntity createStudent2(StudentState state, String name, int positionX, int positionY) {
		return new StudentEntity2(core, name , state, positionX, positionY);
	}
	
	private StudentEntity createStudent3(StudentState state, String name, int positionX, int positionY) {
		return new StudentEntity3(core, name , state, positionX, positionY);
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
		//EntityManager entMngr = EntityManager.getInstance(this.core);
		if(event.getSelected().equals(MainEntity.HOCHI)){
			this.character = new HochiEntity(core, "Hochi");
		}else if(event.getSelected().equals(MainEntity.SHAUFI)){
			this.character = new SchaufiEntity(core, "Schaufi");
		}else if(event.getSelected().equals(MainEntity.RUDI)){
			this.character = new RudiEntity(core, "Rudi");
		}
	}
	
	private void handleDestroyEntityEvent(DestroyEntityEvent event) {
		EntityManager entMngr = EntityManager.getInstance(this.core);
		if (entMngr.hasEntity(event.getEntityName())) {
			entMngr.removeEntity(event.getEntityName());
		}
	}

}
