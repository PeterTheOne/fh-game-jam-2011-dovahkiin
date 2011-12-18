package game.event;

import game.event.ChangeItemEvent.ChangeDirection;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class EndFightEvent extends Event {

public static final EventType TYPE = new EventType("EndFight");
	
	public EndFightEvent() {
	}
	
	public EventType getType(){
		return TYPE;
	}

}
