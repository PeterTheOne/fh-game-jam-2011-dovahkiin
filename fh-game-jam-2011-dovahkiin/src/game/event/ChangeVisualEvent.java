package game.event;

import game.entity.PlayerEntity.Side;
import game.entity.PlayerEntity.VisualState;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class ChangeVisualEvent extends Event{

	public static final EventType TYPE = new EventType("ChangeVisual");
	
	private VisualState visualstate;
	private Side side;
	private String entityName;
	private String entityType;
	
	public ChangeVisualEvent(VisualState visualstate, Side side, 
			String entityName, String entityType) {
		this.visualstate = visualstate;
		this.side = side;
		this.entityName = entityName;
		this.entityType = entityType;
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
	
	public String getEntitiyType(){
		return this.entityType;
	}
	
	public EventType getType(){
		return TYPE;
	}
	
}
