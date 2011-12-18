package game.entity;

import game.motion.Body;
import game.motion.CollisionEvent;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.logging.LoggingService;

public class StudentEntity extends Entity implements EventListener {
	
	public enum StudentState {
		STAND, DEFEATED
	}

	public static final String TYPE = "Student";

	private EventManager evtMngr;
	private Body body;
	private StudentState studentState;
	
	public StudentEntity(Core core, String name, StudentState studentState, double x, double y) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
		this.studentState = studentState;
		this.body = new Rectangle(name, 90, 400);
		this.body.setPosition(x, y);
		this.body.setCollisionFlag(0x0004);
	}
	
	public String getType() {
		return TYPE;
	}

	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(body);
		this.evtMngr.addListener(this, CollisionEvent.TYPE);
	}

	protected void tearDown() {
		this.evtMngr.removeListener(this);
		MotionManager.getInstance(getCore()).removeBody(body);
	}

	public void update() {
		//emtpy
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(CollisionEvent.TYPE)) {
			handleCollision((CollisionEvent) event);
		}
	}

	private void handleCollision(CollisionEvent event) {
		String oppName = event.getOpponent(getName());
		if (oppName == null) {
			return;
		}
		EntityManager entMngr = EntityManager.getInstance(getCore());
		Entity oppEntity = entMngr.getEntity(oppName);
		if (oppEntity == null) {
			return;
		}
		LoggingService.getInstance(getCore()).logAlert("Student", "collision");
		if (oppEntity.getType().equals(ExEntity.TYPE)) {
			entMngr.removeEntity(this);
		}
	}

}
