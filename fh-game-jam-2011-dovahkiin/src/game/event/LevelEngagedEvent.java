package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class LevelEngagedEvent extends Event {
	
	public static final EventType TYPE = new EventType("LevelEngaged");

	private String levelName;
	
	public LevelEngagedEvent(String levelName) {
		this.levelName = levelName;
	}
	
	public String getLevelName() {
		return this.levelName;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}

}
