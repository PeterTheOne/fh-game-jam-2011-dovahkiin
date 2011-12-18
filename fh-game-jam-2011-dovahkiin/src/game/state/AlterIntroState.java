package game.state;

import game.view.IntroView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.EventType;
import org.cogaen.event.SimpleEvent;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.task.FireEventTask;
import org.cogaen.task.TaskManager;
import org.cogaen.view.View;

public class AlterIntroState implements GameState {

	public static final String NAME = "Intro";
	
	public static final EventType INTRO_TO_PLAY = new EventType("IntroToPlay");
	
	private Core core;
	private View view;
	
	public AlterIntroState(Core core) {
		this.core = core;
		this.view = new IntroView(core);
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
		//TODO: do stuff here
		TaskManager.getInstance(core).attachTask(new FireEventTask(core, new SimpleEvent(INTRO_TO_PLAY), 5.0));
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

}
