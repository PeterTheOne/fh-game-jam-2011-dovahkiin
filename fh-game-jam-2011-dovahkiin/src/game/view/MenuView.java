package game.view;

import java.awt.Image;
import java.awt.event.KeyEvent;

import game.event.ChangeItemEvent;
import game.event.ChangeItemEvent.ChangeDirection;
import game.event.MenuStateEvent;
import game.event.SelectItemEvent;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.input.KeyPressedEvent;
import game.java2d.AnimatedSpriteHandle;
import game.java2d.AnimatedSpriteVisual;
import org.cogaen.java2d.ImageHandle;
import org.cogaen.java2d.Overlay;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SpriteHandle;
import org.cogaen.java2d.SpriteVisual;
import org.cogaen.resource.ResourceManager;
import org.cogaen.view.AbstractView;

public class MenuView extends AbstractView implements EventListener{

	private int selectedItem = 0;
	private Overlay door;
	private AnimatedSpriteVisual hochiSpr;
	
	public MenuView(Core core) {
		super(core);
	}
	
	public void registerResources(String group) {
		ResourceManager resMngr = ResourceManager.getInstance(this.getCore());
		
		resMngr.addResource(new ImageHandle( "menu_background_img", "menu-background.png") );
		resMngr.addResource(new SpriteHandle( "menu_background_spr", "menu_background_img", 1024, 768) );
		
		resMngr.addResource(new ImageHandle( "menu_door_img", "menu-open_door.png") );
		resMngr.addResource(new SpriteHandle( "menu_door_spr", "menu_door_img", 173, 313) );
		
		resMngr.addResource(new ImageHandle( "hochi-walk_img", "hochi-walk.png") );
		resMngr.addResource(new AnimatedSpriteHandle( "hochi-walk_spr", "hochi-walk_img", 8, 250, 538) );
		
	}

	public void engage() {
		EventManager.getInstance(this.getCore()).addListener(this, KeyPressedEvent.TYPE);
		EventManager.getInstance(this.getCore()).addListener(this, MenuStateEvent.TYPE);
		
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.setClearBackground(true);
		
		Overlay bg = scnMngr.createOverlay("background");
		SpriteVisual bgSpr = scnMngr.createSpriteVisual("menu_background_spr");
		bg.addVisual(bgSpr);
		bg.setPosition(512, 384);
		
		door = scnMngr.createOverlay("door");
		SpriteVisual doorSpr = scnMngr.createSpriteVisual("menu_door_spr");
		door.addVisual(doorSpr);
		door.setPosition(207 + 300 * this.selectedItem, 477);
		
		Overlay hochi = scnMngr.createOverlay("hochi");
		hochiSpr = (AnimatedSpriteVisual) ResourceManager.getInstance(this.getCore()).getResource("hochi-walk_spr");
		hochi.addVisual(hochiSpr);
		hochi.setPosition(512, 384);
		hochiSpr.play();
	}

	public void disengage() {
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.destroyAll();
		EventManager.getInstance(this.getCore()).removeListener(this);
	}

	public void handleEvent(Event event) {
		if (event.isOfType(KeyPressedEvent.TYPE)){
			handleKeyPressed((KeyPressedEvent)event);
		} else if(event.isOfType(MenuStateEvent.TYPE)){
			this.selectedItem = ((MenuStateEvent)event).getSelectedItem();
			door.setPosition(207 + 300 * this.selectedItem, 477);
		}
	}
	
	private void handleKeyPressed(KeyPressedEvent event){
		switch(event.getKeyCode()){
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ENTER:
			EventManager.getInstance(this.getCore()).enqueueEvent(new SelectItemEvent(selectedItem));
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
