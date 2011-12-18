package game;

import game.event.LoadMenueEvent;
import game.motion.MotionManager;
import game.state.Intro2State;
import game.state.IntroState;
import game.state.MenuState;
import game.state.OutroState;
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
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
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
		stateManager.addState(new Intro2State(this.core));
		stateManager.addState(new PlayState(this.core));
		stateManager.addState(new OutroState(this.core));
		stateManager.setCurrentState(SplashState.NAME);
		
		stateManager.addTransition(SplashState.NAME, MenuState.NAME, SplashState.END_OF_SPLASH);
		stateManager.addTransition(MenuState.NAME, SplashState.NAME, MenuState.MENU_TO_CREDITS);
		stateManager.addTransition(MenuState.NAME, IntroState.NAME, MenuState.MENU_TO_INTRO);
		stateManager.addTransition(IntroState.NAME, Intro2State.NAME, IntroState.INTRO_TO_INTRO2);
		stateManager.addTransition(Intro2State.NAME, PlayState.NAME, Intro2State.INTRO2_TO_PLAY);
		stateManager.addTransition(PlayState.NAME, OutroState.NAME, PlayState.END_OF_PLAY);
		stateManager.addTransition(OutroState.NAME, MenuState.NAME, OutroState.END_OF_OUTRO);
	}

	public void runGameLoop() throws InterruptedException {
		Clock clock = new Clock();
		SceneManager scnMngr = SceneManager.getInstance(this.core);
		
		SoundHandle musicHandle = new SoundHandle("music_handle", "music.wav");
		musicHandle.load(this.core);
		//SoundService.getInstance(this.core).playBackgroundMusic((SoundEffect)musicHandle.getResource());
		
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
