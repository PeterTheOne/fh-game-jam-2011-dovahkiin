package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class FightEvent extends Event {
	
	public static final EventType TYPE = new EventType("Fight");

	private boolean fighting;
	
	public FightEvent(boolean fighting) {
		this.fighting = fighting;
	}
	
	public boolean isFighting() {
		return fighting;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}

}
