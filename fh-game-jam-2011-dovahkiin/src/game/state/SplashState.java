package game.state;

import game.view.SplashView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class SplashState implements GameState {

	public static final String NAME = "Splash";
	
	private Core core;
	private View view;
	
	public SplashState(Core core) {
		this.core = core;
		this.view = new SplashView();
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
