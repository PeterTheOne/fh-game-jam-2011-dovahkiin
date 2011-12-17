package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class SelectItemEvent extends Event{

	public static final EventType TYPE = new EventType("Selected Item");
	
	private int selectedItem;
	
	public SelectItemEvent(int selectedItem){
		this.selectedItem = selectedItem;
	}
	
	public int getSelectedItem(){
		return this.selectedItem;
	}
	
	public EventType getType() {
		return TYPE;
	}

}
