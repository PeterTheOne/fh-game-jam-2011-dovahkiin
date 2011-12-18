package game.view;

import game.event.ChangeItemEvent;
import game.event.CharacterStateEvent;
import game.event.LoadMenueEvent;
import game.event.MenuStateEvent;
import game.event.SelectItemEvent;
import game.event.ChangeItemEvent.ChangeDirection;
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
import org.cogaen.view.AbstractView;

public class IntroView extends AbstractView implements EventListener{
	
	private int selectedItem;
	private String []characters = new String[IntroState.getCharacters()];
	private Overlay character;
	public enum MainEntity{
		HOCHI, SHAUFI, RUDI
	}
	private MainEntity entities[] = new MainEntity[IntroState.getCharacters()];

	public IntroView(Core core) {
		super(core);
	}
	
	@Override
	public void disengage() {
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.destroyAll();
		EventManager.getInstance(this.getCore()).removeListener(this);
	}

	@Override
	public void engage() {
		EventManager.getInstance(this.getCore()).addListener(this, KeyPressedEvent.TYPE);
		EventManager.getInstance(getCore()).addListener(this, CharacterStateEvent.TYPE);
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.setClearBackground(true);

		Overlay bg = scnMngr.createOverlay("background");
		SpriteVisual bgSpr = scnMngr.createSpriteVisual("characters_background_spr");
		bg.addVisual(bgSpr);
		bg.setPosition(512, 384);
		
		characters[0] = "c_hochi_spr";
		characters[1] = "c_schaufi_spr";
		characters[2] = "c_rudi_spr";
		entities[0] = MainEntity.HOCHI;
		entities[1] = MainEntity.SHAUFI;
		entities[2] = MainEntity.RUDI;
		
		character = scnMngr.createOverlay("character");
		SpriteVisual v = scnMngr.createSpriteVisual(characters[0]);
		character.addVisual(v);
		character.setPosition(512, 384);
	}

	@Override
	public void registerResources(String group) {
		ResourceManager resMngr = ResourceManager.getInstance(this.getCore());
		
		resMngr.addResource(new ImageHandle( "characters_background_img", "characters_background.png") );
		resMngr.addResource(new SpriteHandle( "characters_background_spr", "characters_background_img", 1024, 768) );
		
		//hochi
		resMngr.addResource(new ImageHandle( "c_hochi_img", "c_hochi.png") );
		resMngr.addResource(new SpriteHandle( "c_hochi_spr", "c_hochi_img", 1024, 768) );
		
		//schaufi
		resMngr.addResource(new ImageHandle( "c_schaufi_img", "c_schaufi.png") );
		resMngr.addResource(new SpriteHandle( "c_schaufi_spr", "c_schaufi_img", 1024, 768) );
		
		//rudi
		resMngr.addResource(new ImageHandle( "c_rudi_img", "c_rudi.png") );
		resMngr.addResource(new SpriteHandle( "c_rudi_spr", "c_rudi_img", 1024, 768) );
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(KeyPressedEvent.TYPE)){
			handleKeyPressed((KeyPressedEvent)event);
		}else if(event.isOfType(CharacterStateEvent.TYPE)){
			this.selectedItem = ((CharacterStateEvent)event).getSelectedItem();
			handleCharacterState((CharacterStateEvent)event);
		}
		
		
	}

	private void handleCharacterState(CharacterStateEvent event) {
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		
		character.clearVisuals();
		SpriteVisual v = scnMngr.createSpriteVisual(characters[selectedItem]);
		character.addVisual(v);
		character.setPosition(512, 384);
		
	}

	private void handleKeyPressed(KeyPressedEvent event) {
		switch(event.getKeyCode()){
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ENTER:
			EventManager.getInstance(this.getCore()).enqueueEvent(new SelectItemEvent(selectedItem));
			EventManager.getInstance(getCore()).enqueueEvent(new LoadMenueEvent(entities[selectedItem]));
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_UP:
			EventManager.getInstance(this.getCore()).enqueueEvent(new ChangeItemEvent(ChangeDirection.UP));
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_DOWN:
			EventManager.getInstance(this.getCore()).enqueueEvent(new ChangeItemEvent(ChangeDirection.DOWN));
			break;
		}
	}

}
