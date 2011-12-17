package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class LoadLevelEvent extends Event {

public static final EventType TYPE = new EventType("LoadLevel");
	
	private String level;
	
	public LoadLevelEvent(String level) {
		this.level = level;
	}
	
	public String getLevel(){
		return level;
	}
	
	public EventType getType(){
		return TYPE;
	}
}
