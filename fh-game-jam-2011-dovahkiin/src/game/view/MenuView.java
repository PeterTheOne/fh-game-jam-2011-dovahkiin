package game.view;

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
import org.cogaen.java2d.SceneManager;
import org.cogaen.resource.ResourceManager;
import org.cogaen.view.AbstractView;

public class MenuView extends AbstractView implements EventListener{

	private int selectedItem = 0;
	
	public MenuView(Core core) {
		super(core);
	}
	
	public void registerResources(String group) {
		ResourceManager resMngr = ResourceManager.getInstance(this.getCore());
		
	}

	public void engage() {
		EventManager.getInstance(this.getCore()).addListener(this, KeyPressedEvent.TYPE);
		EventManager.getInstance(this.getCore()).addListener(this, MenuStateEvent.TYPE);
		
		SceneManager scnMngr = SceneManager.getInstance(this.getCore());
		scnMngr.setClearBackground(true);
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
			//TODO change arrow position
		}
	}
	
	private void handleKeyPressed(KeyPressedEvent event){
		switch(event.getKeyCode()){
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ENTER:
			EventManager.getInstance(this.getCore()).enqueueEvent(new SelectItemEvent(selectedItem));
			break;
		case KeyEvent.VK_UP:
			EventManager.getInstance(this.getCore()).enqueueEvent(new ChangeItemEvent(ChangeDirection.UP));
			break;
		case KeyEvent.VK_DOWN:
			EventManager.getInstance(this.getCore()).enqueueEvent(new ChangeItemEvent(ChangeDirection.DOWN));
			break;
		}
	}

}
