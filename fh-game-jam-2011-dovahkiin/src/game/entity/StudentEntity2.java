package game.entity;

import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;

public class StudentEntity2 extends StudentEntity{
	
	public static final String TYPE = "Student2";
	
	public StudentEntity2(Core core, String name, StudentState state, double x, double y) {
		super(core, name, state, x, y);
	}
	
	public String getType() {
		return TYPE;
	}

	public void update() {
		//emtpy
	}
	
	@Override
	protected void setUp() {
		// TODO Auto-generated method stub
		MotionManager.getInstance(getCore()).addBody(this.getBody());
	}

	@Override
	protected void tearDown() {
		// TODO Auto-generated method stub
		MotionManager.getInstance(getCore()).removeBody(this.getBody());
	}

}
