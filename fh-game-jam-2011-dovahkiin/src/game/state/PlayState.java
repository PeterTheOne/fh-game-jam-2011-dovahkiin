package game.state;

import game.entity.HochiEntity;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.entity.StudentEntity.StudentState;
import game.entity.StudentEntity1;
import game.entity.StudentEntity2;
import game.entity.StudentEntity3;
import game.event.ChangeCharacterEvent;
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
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;
import org.cogaen.java2d.SpriteVisual;
import org.cogaen.event.EventType;
import org.cogaen.logging.LoggingService;
import org.cogaen.name.NameService;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class PlayState implements GameState, EventListener{

	public static final String NAME = "Play";

	public static final EventType END_OF_PLAY = new EventType("EndOfPlay");
	
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
		
		Level level1_1 = new Level(this.core, "level1_1");
		level1_1.addFloor();
		lvlMngr.addLevel(level1_1);

		Level level1_2 = new Level(this.core, "level1_2");
		level1_2.addFloor();
		level1_2.addEnemy(createStudent(StudentState.MIDDLE, 300, -150));
		level1_2.addEnemy(createStudent(StudentState.RIGHT, 340, -200));
		level1_2.addEnemy(createStudent(StudentState.LEFT, 270, -150));
		level1_2.addEnemy(createStudent(StudentState.RIGHT, 230, -250));
		lvlMngr.addLevel(level1_2);
		
		Level level1_3 = new Level(this.core, "level1_3");
		level1_3.addFloor();
		lvlMngr.addLevel(level1_3);
		
		Level level2_1 = new Level(this.core, "level2_1");
		level2_1.addFloor();
		lvlMngr.addLevel(level2_1);
		
		Level level2_2 = new Level(this.core, "level2_2");
		level2_2.addFloor();
		lvlMngr.addLevel(level2_2);
		
		Level level2_3 = new Level(this.core, "level2_3");
		level2_3.addFloor();
		lvlMngr.addLevel(level2_3);
		
		Level level3_1 = new Level(this.core, "level3_1");
		level3_1.addFloor();
		lvlMngr.addLevel(level3_1);
		
		Level level3_2 = new Level(this.core, "level3_2");
		level3_2.addFloor();
		lvlMngr.addLevel(level3_2);
		
		Level level3_3 = new Level(this.core, "level3_3");
		level3_3.addFloor();
		lvlMngr.addLevel(level3_3);
		
		level1_1.setNextLevel(level1_2.getName());
		level1_2.setNextLevel(level1_3.getName());
		level1_3.setNextLevel(level2_1.getName());
		level2_1.setNextLevel(level2_2.getName());
		level2_2.setNextLevel(level2_3.getName());
		level2_3.setNextLevel(level3_1.getName());
		level3_1.setNextLevel(level3_2.getName());
		level3_2.setNextLevel(level3_3.getName());
		
		lvlMngr.setCurrentLevel(level1_1.getName());
		
		level3_3.setSwitchToEnd(true);

		EventManager.getInstance(core).addListener(this, DestroyEntityEvent.TYPE);
		EventManager.getInstance(core).addListener(this, ChangeCharacterEvent.TYPE);
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
		}else if(event.isOfType(ChangeCharacterEvent.TYPE)){
			handleChangeCharacter((ChangeCharacterEvent)event);
		}
	}

	private void handleChangeCharacter(ChangeCharacterEvent event) {
		LoggingService.getInstance(core).logDebug("ALERT", "test");
		Entity currentChar = entMngr.getInstance(core).getEntity(event.getName());
		
		SceneNode s = SceneManager.getInstance(core).getSceneNode(event.getName());
		s.clearVisuals();
		//SpriteVisual vis = SceneManager.getInstance(core).createSpriteVisual(event.getNextEntitiy());
		
		EntityManager e = EntityManager.getInstance(this.core);
		e.removeEntity(currentChar);
		if(event.getNextEntitiy().equals("Hochi")){
			HochiEntity h = new HochiEntity(this.core, event.getNextEntitiy());
			h.setPositionX(event.getPositionX());
			h.setPositionY(event.getPositionY());
			h.setSide(event.getSide());
			e.addEntity(h);
		}else if(event.getNextEntitiy().equals("Schaufi")){
			SchaufiEntity sc = new SchaufiEntity(this.core, event.getNextEntitiy());
			sc.setPositionX(event.getPositionX());
			sc.setPositionY(event.getPositionY());
			sc.setSide(event.getSide());
			e.addEntity(sc);
		}else if(event.getNextEntitiy().equals("Rudi")){
			RudiEntity r = new RudiEntity(this.core, event.getNextEntitiy());
			r.setPositionX(event.getPositionX());
			r.setPositionY(event.getPositionY());
			r.setSide(event.getSide());
			e.addEntity(r);
		}
		//s.setPose(event.getPositionX(), event.getPositionY(), 0);
		//s.addVisual(vis);
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
