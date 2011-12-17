package game;

import game.motion.MotionManager;
import game.state.PlayState;
import game.state.SplashState;

import org.cogaen.core.Core;
import org.cogaen.input.InputManager;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.Screen;
import org.cogaen.logging.LoggingService;
import org.cogaen.resource.ResourceManager;
import org.cogaen.state.GameStateManager;
import org.cogaen.time.Clock;

public class GameApp {		
	
	private Core core;
	
	public GameApp(Screen screen) {
		this.core = Core.createCoreWithStandardServices(LoggingService.DEBUG);
		this.core.installService(new SceneManager(screen));
		this.core.installService(new InputManager(screen.getComponent()));
		this.core.installService(new ResourceManager());
		this.core.installService(new MotionManager());
		
		initializeGameStates();
	}
	
	private void initializeGameStates() {
		GameStateManager stateManager = GameStateManager.getInstance(this.core);

		//TODO: gamestates
		
		stateManager.addState(new SplashState(this.core));
		stateManager.addState(new PlayState(this.core));
		stateManager.setCurrentState(SplashState.NAME);
		
		stateManager.addTransition(SplashState.NAME, PlayState.NAME, SplashState.END_OF_SPLASH);
	}

	public void runGameLoop() throws InterruptedException {
		Clock clock = new Clock();
		SceneManager scnMngr = SceneManager.getInstance(this.core);
		
		while (true) {
			clock.tick();
			this.core.update(clock.getDelta());
			scnMngr.renderScene();
			Thread.sleep(1);
		}
	}

}
