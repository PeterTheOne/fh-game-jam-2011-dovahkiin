package game.entity;

import game.motion.Body;
import game.motion.CollisionEvent;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;

public class StudentEntity3 extends StudentEntity implements EventListener{
	
	public static final String TYPE = "Student3";
	private EventManager evtMngr;
	
	public StudentEntity3(Core core, String name, StudentState state, double x, double y) {
		super(core, name, state, x, y);
		this.evtMngr = EventManager.getInstance(core);
		super.body.setCollisionFlag(0x0004);
		super.body.setCollisionMask(0x0041);
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
		this.evtMngr.addListener(this, CollisionEvent.TYPE);
	}

	@Override
	protected void tearDown() {
		this.evtMngr.removeListener(this);
		MotionManager.getInstance(getCore()).removeBody(this.getBody());
	}

}
