package game.entity;

import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;

public class StudentEntity extends Entity{
	
	public enum StudentState {
		STAND, DEFEATED
	}

	public static final String TYPE = "Student";
	
	private Body body;
	private StudentState studentState;
	
	public StudentEntity(Core core, String name, StudentState studentState, double x, double y) {
		super(core, name);
		this.studentState = studentState;
		this.body = new Rectangle(name, 90, 400);
		this.body.setPosition(x, y);
	}
	
	public String getType() {
		return TYPE;
	}

	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(body);
	}

	protected void tearDown() {
		MotionManager.getInstance(getCore()).removeBody(body);
	}

	public void update() {
		//emtpy
	}

}
