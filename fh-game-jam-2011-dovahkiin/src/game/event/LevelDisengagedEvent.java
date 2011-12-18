package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class LevelDisengagedEvent extends Event {
	
	public static final EventType TYPE = new EventType("LevelDisengaged");

	private String levelName;
	
	public LevelDisengagedEvent(String levelName) {
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
