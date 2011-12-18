package game.event;

import org.cogaen.event.Event;
import org.cogaen.event.EventType;

public class StudentShotEvent extends Event {
	
	public static final EventType TYPE = new EventType("StudentShot");
	
	private String studentName;
	private String studentType;

	public StudentShotEvent(String studentName, String studentType) {
		this.studentName = studentName;
		this.studentType = studentType;
	}
	
	public String getStudentName() {
		return this.studentName;
	}
	
	public String getStudentType() {
		return this.studentType;
	}

	@Override
	public EventType getType() {
		return TYPE;
	}

}
