package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class MenuStateEvent extends Event{

	public static final EventType TYPE = new EventType("Menu State");
	
	private int selectedItem;
	
	public MenuStateEvent(int selectedItem){
		this.selectedItem = selectedItem;
	}
	
	public int getSelectedItem(){
		return this.selectedItem;
	}
	
	public EventType getType() {
		return TYPE;
	}

}
