package game.state;

import game.entity.HochiEntity;
import game.levelmanager.Level;
import game.levelmanager.LevelManager;
import game.motion.Rectangle;
import game.view.PlayView;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.EventManager;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class PlayState implements GameState {

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
		
		EntityManager entMngr = EntityManager.getInstance(this.core);
		entMngr.addEntity(new HochiEntity(core, "Hochi"));
		
		LevelManager lvlMngr = new LevelManager(core);
		Level startLevel = new Level(core, "startLevel");
		startLevel.addStructure(new Rectangle("plaform", 1024, 50, 0, -400));
		lvlMngr.addLevel(startLevel);
		lvlMngr.setCurrentLevel(startLevel.getName());
	}

	@Override
	public void onExit() {
		
		
		EntityManager.getInstance(this.core).removeAllEntities();
		
		this.view.disengage();
		ResourceManager.getInstance(this.core).unloadGroup(NAME);
	}

}
