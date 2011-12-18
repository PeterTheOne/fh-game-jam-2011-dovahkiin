package game.view;

import game.state.Intro2State;
import game.state.IntroState;

import java.awt.event.KeyEvent;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.event.SimpleEvent;
import org.cogaen.input.KeyPressedEvent;
import org.cogaen.java2d.ImageHandle;
import org.cogaen.java2d.Overlay;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SpriteHandle;
import org.cogaen.java2d.SpriteVisual;
import org.cogaen.resource.ResourceManager;
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;
import org.cogaen.view.AbstractView;

public class Intro2View extends AbstractView implements EventListener{

	public Intro2View(Core core) {
		super(core);
	}
	
	@Override
	public void disengage() {
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.destroyAll();
		EventManager.getInstance(this.getCore()).removeListener(this);

		SoundHandle soundHandle = new SoundHandle("eoIntro_handle", "eoIntro.wav");
		soundHandle.load(this.getCore());
		SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle.getResource());
	}

	@Override
	public void engage() {
		EventManager.getInstance(this.getCore()).addListener(this, KeyPressedEvent.TYPE);
		
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.setClearBackground(true);

		Overlay bg = scnMngr.createOverlay("background");
		SpriteVisual bgSpr = scnMngr.createSpriteVisual("intro_background_spr");
		bg.addVisual(bgSpr);
		bg.setPosition(512, 384);
		
	}

	@Override
	public void registerResources(String group) {
		ResourceManager resMngr = ResourceManager.getInstance(this.getCore());
		
		resMngr.addResource(new ImageHandle( "intro_background_img", "intro-background.png") );
		resMngr.addResource(new SpriteHandle( "intro_background_spr", "intro_background_img", 1024, 768) );
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(KeyPressedEvent.TYPE)){
			handleKeyPressed((KeyPressedEvent)event);
		}
		
		
	}

	private void handleKeyPressed(KeyPressedEvent event) {
		switch(event.getKeyCode()){
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ESCAPE:
			EventManager.getInstance(this.getCore()).enqueueEvent(new SimpleEvent(Intro2State.INTRO2_TO_PLAY));
			break;
		}
	}

}
