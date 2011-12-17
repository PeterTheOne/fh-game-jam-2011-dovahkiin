package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class ChangeItemEvent extends Event{

	public static final EventType TYPE = new EventType("ChangeItem");
	
	public enum ChangeDirection{
		UP, DOWN
	}
	
	private ChangeDirection direction;
	
	public ChangeItemEvent(ChangeDirection direction) {
		this.direction = direction;
	}
	
	public ChangeDirection getDirection(){
		return direction;
	}
	
	public EventType getType(){
		return TYPE;
	}
	
}
