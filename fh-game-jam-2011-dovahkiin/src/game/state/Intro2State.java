package game.state;

import game.event.LoadMenueEvent;
import game.view.Intro2View;
import game.view.IntroView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.EventManager;
import org.cogaen.event.EventType;
import org.cogaen.event.SimpleEvent;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.task.FireEventTask;
import org.cogaen.task.TaskManager;
import org.cogaen.view.View;

public class Intro2State implements GameState {

	public static final String NAME = "IntroTwo";
	
	public static final EventType INTRO2_TO_PLAY = new EventType("Intro2ToPlay");
	
	private Core core;
	private View view;
	
	public Intro2State(Core core) {
		this.core = core;
		this.view = new Intro2View(core);
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
		TaskManager.getInstance(core).attachTask(new FireEventTask(core, new SimpleEvent(INTRO2_TO_PLAY), 5.0));

	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

}
