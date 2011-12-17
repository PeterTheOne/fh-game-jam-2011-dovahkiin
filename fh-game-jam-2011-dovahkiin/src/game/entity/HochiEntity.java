package game.entity;

import game.motion.Body;
import game.motion.CollisionEvent;
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

	private static final double JUMP_POWER = 250;
	//TODO: jump hold power and jump init power
	private static final int GRAVITY_STRENGTH = 200;
	private static final double WALK_SPEED = 200;
	
	private EventManager evtMngr;
	private EntityManager entMngr;
	private Body body;
	private TwoAxisController ctrl;

	public HochiEntity(Core core, String name) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
		this.entMngr = EntityManager.getInstance(getCore());
		this.ctrl = new TwoAxisController(core, "PlayerOne", 2);
		this.body = new Rectangle(name, 250, 548);
		this.body.setCollisionFlag(0x0001);
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
		this.evtMngr.addListener(this, CollisionEvent.TYPE);
	}

	@Override
	protected void tearDown() {
		this.evtMngr.removeListener(this);
		this.ctrl.disengage();
		MotionManager.getInstance(getCore()).removeBody(this.body);
	}

	@Override
	public void update() {
		// TODO: can only jump when on object
		if (this.ctrl.isAction(0)) {
			this.body.setVelocity(0, JUMP_POWER);
			this.body.setAcceleration(0, - GRAVITY_STRENGTH);	//gravity
		}
		
		this.body.setVelocity(WALK_SPEED * this.ctrl.getHorizontalPosition(), 
				this.body.getVelocityY());
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(CollisionEvent.TYPE)) {
			handleCollision((CollisionEvent) event);
		}
	}

	private void handleCollision(CollisionEvent event) {
		if (event.getOpponent(getName()) == null) {
			return;
		}
		Rectangle opponent = (Rectangle) MotionManager.getInstance(getCore()).getBody(event.getOpponent(getName()));
		double halfBodyWidth = ((Rectangle) this.body).getWidth() / 2d;
		double halfBodyHeight = ((Rectangle) this.body).getHeight() / 2d;
		if (event.getYDepthSmallerXDepth()) { // is on top or bottom
			double newY;
			if (this.body.getVelocityY() < 0) { //top
				newY = opponent.getTop() + halfBodyHeight;
			} else { //bottom
				newY = opponent.getBottom() - halfBodyHeight;
			}
			this.body.setVelocity(this.body.getVelocityX(), 0);
			if (Math.abs(this.body.getPositionY() - newY) < halfBodyHeight) { // anti crazy positions
				this.body.setPositionY(newY);
			}
		} else { // is on side
			double newX;
			if (this.body.getVelocityX() < 0) {
				newX = opponent.getRight() + halfBodyWidth;
			} else {
				newX = opponent.getLeft() - halfBodyWidth;
			}
			if (Math.abs(this.body.getPositionX() - newX) < halfBodyHeight) { // anti crazy positions
				this.body.setPositionX(newX);
			}
		}
	}

}
