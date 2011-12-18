package game.state;

import game.entity.HochiEntity;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.entity.StudentEntity.StudentState;
import game.entity.StudentEntity1;
import game.entity.StudentEntity2;
import game.entity.StudentEntity3;
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

import com.sun.corba.se.impl.naming.pcosnaming.NameService;

public class PlayState implements GameState, EventListener{

	public static final String NAME = "Play";
	
	private Core core;
	private View view;
	private EntityManager entMngr;
	
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

	private void createStudent(StudentState state, int positionX, int positionY) {
		// TODO Auto-generated method stub
		String name = org.cogaen.name.NameService.getInstance(core).generateName();
		int rand = (int)(Math.random() * IntroState.getCharacters());
		switch(rand){
		case 1:
			createStudent1(state, name, positionX, positionY);
			break;
		case 2:
			createStudent2(state, name, positionX, positionY);
			break;
		case 3:
			createStudent3(state, name, positionX, positionY);
			break;
		}
	}

	private void createStudent1(StudentState state, String name, int positionX, int positionY) {
		this.entMngr.addEntity(new StudentEntity1(core, name , state, positionX, positionY));
	}
	
	private void createStudent2(StudentState state, String name, int positionX, int positionY) {
		this.entMngr.addEntity(new StudentEntity2(core, name , state, positionX, positionY));
	}
	
	private void createStudent3(StudentState state, String name, int positionX, int positionY) {
		this.entMngr.addEntity(new StudentEntity3(core, name , state, positionX, positionY));
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
