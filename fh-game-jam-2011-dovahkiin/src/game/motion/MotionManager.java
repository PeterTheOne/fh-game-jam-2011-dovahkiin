package game.motion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import org.cogaen.core.Core;
import org.cogaen.core.UpdateableService;
import org.cogaen.event.EventManager;
import org.cogaen.logging.LoggingService;
import org.cogaen.time.TimeService;
import org.cogaen.time.Timer;

public class MotionManager implements UpdateableService {

	public static final String NAME = "cogaen.motionmanager";
	
	private List<Body> bodies = new ArrayList<Body>();
	private Core core;
	private EventManager evtMngr;
	private String timerName;
	private Timer timer;

	public MotionManager() {
		this(TimeService.DEFAULT_TIMER);
	}
	
	public MotionManager(String timerName) {
		this.timerName = timerName;
	}
	
	public static MotionManager getInstance(Core core) {
		return (MotionManager) core.getService(NAME);
	}
			
	public void addBody(Body body) {
		this.bodies.add(body);
	}
	
	public void removeBody(Body body) {
		this.bodies.remove(body);
	}
	
	public Body getBody(String name) {
		for (Body body : this.bodies) {
			if (body.getName().equals(name)) {
				return body;
			}
		}
		return null;
	}
		
	@Override
	public void update() {
		double dt = this.timer.getDeltaTime();
		for (Body body : this.bodies) {
			body.update(dt);
			this.evtMngr.enqueueEvent(new EntityMovedEvent(body.getName(), body.getPositionX(), body.getPositionY(), body.getAngularPosition(), body.getVelocityX()));
		}
		doCollisionTest();
	}
	
	private void doCollisionTest() {
		for (int i = 0; i < this.bodies.size(); ++i) {
			Body b1 = this.bodies.get(i);
			for (int j = i + 1; j < this.bodies.size(); ++j) {
				Body b2 = this.bodies.get(j);
				
				if (b1.isCollisionAllowed(b2) && b1.isColliding(b2)) {
					CollisionEvent cEvent = new CollisionEvent(
							(String) b1.getName(), 
							(String) b2.getName(), 
							CollisionTester.collisionDirection(b1, b2));
					this.evtMngr.enqueueEvent(cEvent);					
				}
				
			}
		}	
	}
		
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void initialize(Core core) {
		this.core = core;
		this.evtMngr = EventManager.getInstance(this.core);
		this.timer = TimeService.getInstance(this.core).getTimer(this.timerName);
	}

}
