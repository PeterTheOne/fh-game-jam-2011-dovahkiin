package game.event;

import game.entity.HochiEntity.Side;
import game.entity.HochiEntity.VisualState;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class ChangeVisualEvent extends Event{

	public static final EventType TYPE = new EventType("ChangeVisual");
	
	private VisualState visualstate;
	private Side side;
	private String entityName;
	
	public ChangeVisualEvent(VisualState visualstate, Side side, String entityName) {
		this.visualstate = visualstate;
		this.side = side;
		this.entityName = entityName;
	}
	
	public VisualState getVisualState(){
		return this.visualstate;
	}
	
	public Side getSide(){
		return this.side;
	}
	
	public String getEntitiyName(){
		return this.entityName;
	}
	
	public EventType getType(){
		return TYPE;
	}
	
}
