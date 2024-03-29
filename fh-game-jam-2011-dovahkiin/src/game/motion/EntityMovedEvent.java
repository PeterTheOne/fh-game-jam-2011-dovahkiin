package game.motion;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class EntityMovedEvent extends Event {

	public static final EventType TYPE = new EventType("EntityMoved");
	private double x;
	private double y;
	private double angle;
	private String entityName;
	private double velocityX;
	private double velocityY;
		
	public EntityMovedEvent(String entityName, double x, 
			double y, double angle, double velocityX, double velocityY) {
		this.entityName = entityName;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}
	
	public double getVelocityX(){
		return this.velocityX;
	}
	
	public double getVelocityY(){
		return this.velocityY;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}
	
}
