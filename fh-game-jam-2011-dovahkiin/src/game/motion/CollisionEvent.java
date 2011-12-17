package game.motion;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class CollisionEvent extends Event {

	public static final EventType TYPE = new EventType("CollisionEvent");

	private String firstEntity;
	private String secondEntity;
	
	public CollisionEvent(String first, String second) {
		this.firstEntity = first;
		this.secondEntity = second;
	}
	
	public String getFirstEntity() {
		return this.firstEntity;
	}
	
	public String getSecondEntity() {
		return this.secondEntity;
	}

	public boolean isInvolve(String entityName) {
		return this.firstEntity.equals(entityName) || this.secondEntity.equalsIgnoreCase(entityName);
	}
	
	public String getOpponent(String entityName) {
		if (this.firstEntity.equals(entityName)) {
			return this.secondEntity;
		} else if (this.secondEntity.equals(entityName)) {
			return this.firstEntity;
		}
		
		return null;
	}
	
	
	@Override
	public EventType getType() {
		return TYPE;
	}

}
