package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class DestroyEntityEvent extends Event {

	public static final EventType TYPE = new EventType("DestroyEntity");
	
	private String entityName;

	public DestroyEntityEvent(String entityName) {
		this.entityName = entityName;
	}
	
	public String getEntityName() {
		return this.entityName;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}

}
