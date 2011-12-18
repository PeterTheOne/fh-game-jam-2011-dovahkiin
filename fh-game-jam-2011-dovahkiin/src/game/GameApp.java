package game;

import game.event.LoadMenueEvent;
import game.motion.MotionManager;
import game.state.IntroState;
import game.state.MenuState;
import game.state.PlayState;
import game.state.SplashState;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.event.EventType;
import org.cogaen.input.InputManager;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.Screen;
import org.cogaen.logging.LoggingService;
import org.cogaen.resource.ResourceManager;
import org.cogaen.sound.SoundService;
import org.cogaen.state.GameStateManager;
import org.cogaen.time.Clock;

public class GameApp implements EventListener{		
	
	private Core core;
	private boolean running = true;
	public static final EventType QUIT_GAME = new EventType("QuitGame");
	
	public GameApp(Screen screen) {
		this.core = Core.createCoreWithStandardServices(LoggingService.DEBUG);
		this.core.installService(new SceneManager(screen));
		this.core.installService(new InputManager(screen.getComponent()));
		this.core.installService(new ResourceManager());
		this.core.installService(new MotionManager());
		this.core.installService(new SoundService());
		
		EventManager.getInstance(this.core).addListener(this, QUIT_GAME);
		
		initializeGameStates();
	}
	
	private void initializeGameStates() {
		GameStateManager stateManager = GameStateManager.getInstance(this.core);
		
		stateManager.addState(new SplashState(this.core));
		stateManager.addState(new MenuState(this.core));
		stateManager.addState(new IntroState(this.core));
		stateManager.addState(new PlayState(this.core));
		stateManager.setCurrentState(SplashState.NAME);
		
		stateManager.addTransition(SplashState.NAME, MenuState.NAME, SplashState.END_OF_SPLASH);
		stateManager.addTransition(MenuState.NAME, SplashState.NAME, MenuState.MENU_TO_CREDITS);
		stateManager.addTransition(MenuState.NAME, IntroState.NAME, MenuState.MENU_TO_INTRO);
		stateManager.addTransition(IntroState.NAME, PlayState.NAME, LoadMenueEvent.TYPE);
	}

	public void runGameLoop() throws InterruptedException {
		Clock clock = new Clock();
		SceneManager scnMngr = SceneManager.getInstance(this.core);
		
		while (running) {
			clock.tick();
			this.core.update(clock.getDelta());
			scnMngr.renderScene();
			Thread.sleep(1);
		}
		
		System.exit(0);
	}

	public void handleEvent(Event event) {
		if (event.isOfType(QUIT_GAME)){
			if (event.isOfType(QUIT_GAME)){
				running = false;
			}
		}
	}

}
