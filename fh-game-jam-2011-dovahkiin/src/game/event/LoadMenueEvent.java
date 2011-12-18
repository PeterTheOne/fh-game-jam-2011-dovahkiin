package game.event;

import game.view.IntroView.MainEntity;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class LoadMenueEvent extends Event {

public static final EventType TYPE = new EventType("LoadMenue");
	
	private MainEntity selected;
	
	
	public LoadMenueEvent(MainEntity entity) {
		// TODO Auto-generated constructor stub
		this.selected = entity;
	}

	public MainEntity getSelected(){
		return this.selected;
	}
	
	public EventType getType() {
		return TYPE;
	}

}
