package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class LeaveScreenEvent extends Event{

public static final EventType TYPE = new EventType("LeaveScreenEvent");
	
	public enum LeaveScreen{
		UP, DOWN
	}
	
	private LeaveScreen side;
	
	public LeaveScreenEvent(LeaveScreen side) {
		this.side = side;
	}
	
	public LeaveScreen getSide(){
		return side;
	}
	
	public EventType getType(){
		return TYPE;
	}

}
