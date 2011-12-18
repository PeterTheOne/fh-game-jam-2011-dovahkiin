package game.state;

import game.view.SplashView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.EventType;
import org.cogaen.event.SimpleEvent;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.task.FireEventTask;
import org.cogaen.task.TaskManager;
import org.cogaen.view.View;

public class SplashState implements GameState {

	public static final String NAME = "Splash";
	
	public static final EventType END_OF_SPLASH = new EventType("EndOfSplash");
	
	private Core core;
	private View view;
	
	public SplashState(Core core) {
		this.core = core;
		this.view = new SplashView(core);
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
		
		TaskManager.getInstance(core).attachTask(new FireEventTask(core, new SimpleEvent(END_OF_SPLASH), 15.0));
		
		//TODO: do stuff here
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

}
