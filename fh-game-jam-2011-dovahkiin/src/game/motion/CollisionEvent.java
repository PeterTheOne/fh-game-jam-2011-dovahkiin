package game.motion;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class CollisionEvent extends Event {

	public static final EventType TYPE = new EventType("CollisionEvent");

	private String firstEntity;
	private String secondEntity;
	private boolean yDepthSmallerXDepth;
	
	public CollisionEvent(String first, String second, boolean yDepthSmallerXDepth) {
		this.firstEntity = first;
		this.secondEntity = second;
		this.yDepthSmallerXDepth = yDepthSmallerXDepth;
	}
	
	public String getFirstEntity() {
		return this.firstEntity;
	}
	
	public String getSecondEntity() {
		return this.secondEntity;
	}
	
	public boolean getYDepthSmallerXDepth() {
		return this.yDepthSmallerXDepth;
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
