package game.state;

import game.view.IntroView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.EventType;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class IntroState implements GameState {

	public static final String NAME = "Intro";
	
	public static final EventType INTRO_TO_PLAY = new EventType("IntroToPlay");
	
	private Core core;
	private View view;
	
	public IntroState(Core core) {
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
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

}
