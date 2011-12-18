package game.event;

import game.entity.PlayerEntity.Side;

import org.cogaen.entity.Entity;
import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class ChangeCharacterEvent extends Event{

public static final EventType TYPE = new EventType("ChangeCharacter");
	
	private String name;
	private double positionX;
	private double positionY;
	private String nextEntity;
	private Side side;
	
	public ChangeCharacterEvent(String name, double positionX, double positionY, String nextEntity, Side side) {
		this.name = name;
		this.positionX = positionX;
		this.positionY = positionY;
		this.nextEntity = nextEntity;
		this.side = side;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}
	
	public Side getSide(){
		return this.side;
	}

	public EventType getType(){
		return TYPE;
	}
	
	public String getNextEntitiy(){
		return this.nextEntity;
	}

}
