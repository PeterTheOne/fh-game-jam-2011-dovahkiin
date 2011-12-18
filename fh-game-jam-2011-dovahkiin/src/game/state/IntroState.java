package game.state;

import game.event.ChangeItemEvent;
import game.event.CharacterStateEvent;
import game.event.MenuStateEvent;
import game.event.SelectItemEvent;
import game.view.IntroView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.event.EventType;
import org.cogaen.event.SimpleEvent;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.task.FireEventTask;
import org.cogaen.task.TaskManager;
import org.cogaen.view.View;

public class IntroState implements GameState, EventListener{

	public static final String NAME = "Intro";
	
	public static final EventType INTRO_TO_INTRO2 = new EventType("IntroToIntro2");
	private static final int CHARACTERS = 3;
	private int selectedItem;
	private Core core;
	private View view;

	@Override
	public String getName() {
		return NAME;
	}
	
	public IntroState(Core core) {
		this.core = core;
		this.view = new IntroView(core);
		this.view.registerResources(NAME);
	}

	@Override
	public void onEnter() {
		ResourceManager.getInstance(this.core).loadGroup(NAME);
		this.view.engage();
		//TODO: do stuff here
		//TaskManager.getInstance(core).attachTask(new FireEventTask(core, new SimpleEvent(INTRO_TO_PLAY), 5.0));
		EventManager.getInstance(this.core).addListener(this, ChangeItemEvent.TYPE);
		EventManager.getInstance(this.core).addListener(this, SelectItemEvent.TYPE);
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

	@Override
	public void handleEvent(Event event) {
		if ( event.isOfType(ChangeItemEvent.TYPE) ) {
			switch(((ChangeItemEvent)event).getDirection()){
			case UP:
				selectedItem = (selectedItem - 1 + CHARACTERS)%CHARACTERS;
				EventManager.getInstance(this.core).enqueueEvent(new CharacterStateEvent(selectedItem));
				break;
			case DOWN:
				selectedItem = (selectedItem + 1)%CHARACTERS;
				EventManager.getInstance(this.core).enqueueEvent(new CharacterStateEvent(selectedItem));
				break;
			}
		}
		
	}
	
	public static int getCharacters(){
		return CHARACTERS;
	}

}
