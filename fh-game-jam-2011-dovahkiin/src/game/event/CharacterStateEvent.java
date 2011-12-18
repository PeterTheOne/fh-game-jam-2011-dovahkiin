package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class CharacterStateEvent extends Event{

	public static final EventType TYPE = new EventType("Character State");
	
	private int selectedItem;
	
	public CharacterStateEvent(int selectedItem){
		this.selectedItem = selectedItem;
	}
	
	public int getSelectedItem(){
		return this.selectedItem;
	}
	
	public EventType getType() {
		return TYPE;
	}

}
