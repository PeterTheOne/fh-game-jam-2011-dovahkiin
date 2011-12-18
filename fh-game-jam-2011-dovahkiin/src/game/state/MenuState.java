package game.state;

import javax.sound.sampled.AudioInputStream;

import game.GameApp;
import game.event.ChangeItemEvent;
import game.event.LoadMenueEvent;
import game.event.MenuStateEvent;
import game.event.SelectItemEvent;
import game.view.IntroView.MainEntity;
import game.view.MenuView;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.event.EventType;
import org.cogaen.event.SimpleEvent;
import org.cogaen.sound.LoopingByteInputStream;
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;
import org.cogaen.state.GameState;
import org.cogaen.view.View;

public class MenuState implements GameState, EventListener {

	public static final String NAME = "Menu";
	public static final EventType MENU_TO_INTRO = new EventType("MenuToIntro");
	public static final EventType MENU_TO_CREDITS = new EventType("MenuToCredits");
	private static final int MENU_ENTRIES = 3;
	private int selectedItem;
	private Core core;
	private View view;
	private MainEntity entity;
	
	public MenuState(Core core) {
		this.core = core;
		this.view = new MenuView(core);
		this.view.registerResources(NAME);
	}
	
	public String getName() {
		return NAME;
	}

	public void onEnter() {
		this.view.engage();
		EventManager.getInstance(this.core).addListener(this, ChangeItemEvent.TYPE);
		EventManager.getInstance(this.core).addListener(this, SelectItemEvent.TYPE);
		SoundHandle musicHandle = new SoundHandle("music_handle", "sound.wav");
		musicHandle.load(this.core);
		SoundHandle soundHandle = new SoundHandle("sound_handle", "sound2.wav");
		soundHandle.load(this.core);
		SoundService.getInstance(this.core).playBackgroundMusic((SoundEffect)musicHandle.getResource());
		SoundService.getInstance(this.core).play((SoundEffect)soundHandle.getResource());
	}

	public void onExit() {
		this.view.disengage();
		EventManager.getInstance(this.core).removeListener(this);
	}

	public void handleEvent(Event event) {
		if ( event.isOfType(ChangeItemEvent.TYPE) ) {
			switch(((ChangeItemEvent)event).getDirection()){
			case UP:
				selectedItem = (selectedItem - 1 + MENU_ENTRIES)%MENU_ENTRIES;
				EventManager.getInstance(this.core).enqueueEvent(new MenuStateEvent(selectedItem));
				break;
			case DOWN:
				selectedItem = (selectedItem + 1)%MENU_ENTRIES;
				EventManager.getInstance(this.core).enqueueEvent(new MenuStateEvent(selectedItem));
				break;
			}
		} else if(event.isOfType(SelectItemEvent.TYPE)) {
			switch(((SelectItemEvent)event).getSelectedItem()){
			case 0:
				EventManager.getInstance(this.core).enqueueEvent(new SimpleEvent(MENU_TO_INTRO));
				break;
			case 1:
				EventManager.getInstance(this.core).enqueueEvent(new SimpleEvent(MENU_TO_CREDITS));
				break;
			case 2:
				EventManager.getInstance(this.core).enqueueEvent(new SimpleEvent(GameApp.QUIT_GAME));
				break;
			}
		}
	}
}
