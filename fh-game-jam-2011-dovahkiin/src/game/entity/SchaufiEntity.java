package game.entity;

import game.entity.PlayerEntity.Side;
import game.event.ChangeVisualEvent;
import game.event.EndFightEvent;
import game.event.LeaveScreenEvent;
import game.event.LeaveScreenEvent.LeaveScreen;
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
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.Screen;
import org.cogaen.logging.LoggingService;

public class SchaufiEntity extends PlayerEntity implements EventListener{
	
	public static final String TYPE = "Schaufi";

	private static final double JUMP_POWER = 800;
	private static final int JUMP_HOLD = 20;
	//TODO: jump hold power and jump init power
	private static final int GRAVITY_STRENGTH = 1800;
	private static final double WALK_SPEED = 400;
	
	private EventManager evtMngr;
	private EntityManager entMngr;
	private Body body;
	private TwoAxisController ctrl;
	private boolean isFighting = false;
	
	private VisualState visualstate;
	private Side side;

	public SchaufiEntity(Core core, String name) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
		this.entMngr = EntityManager.getInstance(getCore());
		this.ctrl = new TwoAxisController(core, "PlayerOne", 2);
		this.body = new Rectangle(name, 90, 400);
		this.body.setCollisionFlag(0x0001);
		this.body.setCollisionMask(0x000C);
		this.body.setAcceleration(0, - GRAVITY_STRENGTH);	//gravity
		this.visualstate = VisualState.STAND;
		this.side = Side.RIGHT;
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
		this.evtMngr.addListener(this, LeaveScreenEvent.TYPE);
		evtMngr.addListener(this, EndFightEvent.TYPE);
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
			if (this.body.getVelocityY() == 0) {
				this.body.setVelocity(this.body.getVelocityX(), JUMP_POWER);
				this.body.setAcceleration(0, - GRAVITY_STRENGTH);	//gravity
			}
			this.body.setVelocity(this.body.getVelocityX(), this.body.getVelocityY() + JUMP_HOLD);
		}
		
		//leaveScreen
		Screen screen = SceneManager.getInstance(getCore()).getScreen();
		if(this.body.getPositionX() > screen.getWidth()/2){
			EventManager.getInstance(getCore()).enqueueEvent(new LeaveScreenEvent(LeaveScreen.RIGHT));
		}else if(this.body.getPositionX() < -screen.getWidth()/2){
			EventManager.getInstance(getCore()).enqueueEvent(new LeaveScreenEvent(LeaveScreen.LEFT));
		}
		
		
		this.body.setVelocity(WALK_SPEED * this.ctrl.getHorizontalPosition(), 
				this.body.getVelocityY());
		//set horizontal Position of Player
		if(this.ctrl.getHorizontalPosition() < 0){
			this.side = Side.LEFT;
		}else if(this.ctrl.getHorizontalPosition() > 0){
			this.side = Side.RIGHT;
		}
		
		if(this.ctrl.isAction(1) && !isFighting){
			evtMngr.enqueueEvent(new ChangeVisualEvent(VisualState.FIGHT, this.side, getName(), getType()));
			this.isFighting = true;
			if (this.side == Side.RIGHT) {
				entMngr.addEntity(new SchallEntity(getCore(), 
						this.body.getPositionX() + 150, 
						this.body.getPositionY(), 
						400));
			}
		}else if(!this.ctrl.isAction(0) && !isFighting && this.body.getVelocityY() != 0){
			evtMngr.enqueueEvent(new ChangeVisualEvent(VisualState.JUMP, this.side, getName(), getType()));
		}else if(!isFighting && this.body.getVelocityX() == 0){
			evtMngr.enqueueEvent(new ChangeVisualEvent(VisualState.STAND, this.side, getName(), getType()));
		}else if(!isFighting){
			evtMngr.enqueueEvent(new ChangeVisualEvent(VisualState.WALK, this.side, getName(), getType()));
		}
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(CollisionEvent.TYPE)) {
			handleCollision((CollisionEvent) event);
		}else if(event.isOfType(LeaveScreenEvent.TYPE)){
			handleLeaveScreenEvent((LeaveScreenEvent)(event));
		}else if(event.isOfType(EndFightEvent.TYPE)){
			this.isFighting = false;
		}
	}

	private void handleLeaveScreenEvent(LeaveScreenEvent leaveScreenEvent) {
		Screen screen = SceneManager.getInstance(getCore()).getScreen();
		if(leaveScreenEvent.getSide().equals(LeaveScreen.LEFT)){
			this.body.setPositionX(screen.getWidth()/2);
		}else if(leaveScreenEvent.getSide().equals(LeaveScreen.RIGHT)){
			this.body.setPositionX(-screen.getWidth()/2);
		}
		
	}

	private void handleCollision(CollisionEvent event) {
		MotionManager moMngr = MotionManager.getInstance(getCore());
		Rectangle opponent = (Rectangle) moMngr.getBody(event.getOpponent(getName()));
		if (opponent == null || !(opponent instanceof Rectangle)) {
			return;
		}
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
