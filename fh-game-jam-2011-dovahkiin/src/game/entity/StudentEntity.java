package game.entity;

import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;

public abstract class StudentEntity extends Entity{
	
	public enum StudentState {
		LEFT, MIDDLE, RIGHT
	}
	
	private Body body;
	private StudentState studentState;
	
	public StudentEntity(Core core, String name, StudentState studentState, double x, double y) {
		super(core, name);
		this.studentState = studentState;
		this.body = new Rectangle(name, 90, 400);
		this.body.setPosition(x, y);
	}
	

	protected abstract void setUp();

	protected abstract void tearDown();

	public abstract void update();

	public Body getBody(){
		return this.body;
	}
	
	public StudentState getStudentState(){
		return this.studentState;
	}
}
