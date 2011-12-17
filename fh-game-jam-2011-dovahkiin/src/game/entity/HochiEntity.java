package game.entity;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.input.TwoAxisController;
import org.cogaen.motion.Body;
import org.cogaen.motion.MotionManager;
import org.cogaen.motion.Rectangle;

public class HochiEntity extends PlayerEntity implements EventListener {
	
	public static final String TYPE = "Hochi";
	
	private EventManager evtMngr;
	private EntityManager entMngr;
	private Body body;
	private TwoAxisController ctrl;

	public HochiEntity(Core core, String name) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
		this.entMngr = EntityManager.getInstance(getCore());
		this.ctrl = new TwoAxisController(core, name, 2);
		this.body = new Rectangle(name, 1, 1);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
