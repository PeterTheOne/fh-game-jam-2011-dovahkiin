package game.entity;

import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.input.TwoAxisController;
import org.cogaen.logging.LoggingService;

public class HochiEntity extends PlayerEntity implements EventListener {
	
	public static final String TYPE = "Hochi";

	private static final double JUMP_POWER = 20;
	//TODO: jump hold power and jump init power
	private static final int GRAVITY_STRENGTH = 30;
	
	private EventManager evtMngr;
	private EntityManager entMngr;
	private Body body;
	private TwoAxisController ctrl;

	public HochiEntity(Core core, String name) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
		this.entMngr = EntityManager.getInstance(getCore());
		this.ctrl = new TwoAxisController(core, "PlayerOne", 2);
		this.body = new Rectangle(name, 1, 1);
		this.body.setAcceleration(0, - GRAVITY_STRENGTH);	//gravity
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(this.body);
		this.ctrl.engage();
	}

	@Override
	protected void tearDown() {
		this.evtMngr.removeListener(this);
		this.ctrl.disengage();
		MotionManager.getInstance(getCore()).removeBody(this.body);
	}

	@Override
	public void update() {
		if (this.ctrl.isAction(0)) {
			this.body.setVelocity(0, JUMP_POWER);
		}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
