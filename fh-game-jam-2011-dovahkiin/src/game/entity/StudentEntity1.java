package game.entity;

import game.entity.StudentEntity.StudentState;
import game.motion.Body;
import game.motion.CollisionEvent;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;

public class StudentEntity1 extends StudentEntity implements EventListener{
	
	public static final String TYPE = "Student1";
	private EventManager evtMngr;
	
	public StudentEntity1(Core core, String name, StudentState state, double x, double y) {
		super(core, name, state, x, y);
		this.evtMngr = EventManager.getInstance(core);
	}
	
	public String getType() {
		return TYPE;
	}

	public void update() {
		//emtpy
	}
	
	@Override
	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(this.getBody());
		this.evtMngr.addListener(this,
				CollisionEvent.TYPE);
	}

	@Override
	protected void tearDown() {
		this.evtMngr.removeListener(this);
		MotionManager.getInstance(getCore()).removeBody(this.getBody());
	}

}
