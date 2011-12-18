package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class StudentShotEvent extends Event {
	
	public static final EventType TYPE = new EventType("StudentShot");
	
	private String studentName;

	public StudentShotEvent(String studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentName() {
		return this.studentName;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}

}
