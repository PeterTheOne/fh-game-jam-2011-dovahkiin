package game.entity;

import game.event.StudentShotEvent;
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
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;

public abstract class StudentEntity extends Entity implements EventListener{
	
	public enum StudentState {
		LEFT, MIDDLE, RIGHT
	}

	public static final String TYPE = "Student";

	private EventManager evtMngr;
	protected Body body;
	private StudentState studentState;
	
	public StudentEntity(Core core, String name, StudentState studentState, double x, double y) {
		super(core, name);
		this.evtMngr = EventManager.getInstance(getCore());
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
		if (oppEntity.getType().equals(RudiEntity.TYPE) && 
				this instanceof StudentEntity3) {
			EventManager.getInstance(getCore()).enqueueEvent(new StudentShotEvent(getName(), getType()));
			this.body.setCollisionFlag(0x0010);
			SoundHandle soundHandle = new SoundHandle("koC_handle", "koC.wav");
			soundHandle.load(this.getCore());
			SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle.getResource());
		} else if (oppEntity.getType().equals(ExEntity.TYPE)) {
			//entMngr.removeEntity(this);
			entMngr.removeEntity(oppEntity);
			EventManager.getInstance(getCore()).enqueueEvent(new StudentShotEvent(getName(), getType()));
			this.body.setCollisionFlag(0x0010);
			SoundHandle soundHandle = new SoundHandle("hitA_handle", "hitA.wav");
			soundHandle.load(this.getCore());
			SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle.getResource());
			SoundHandle soundHandle2 = new SoundHandle("koA_handle", "koA.wav");
			soundHandle2.load(this.getCore());
			SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle2.getResource());
		} else if (oppEntity.getType().equals(SchallEntity.TYPE)) {
			//entMngr.removeEntity(this);
			entMngr.removeEntity(oppEntity);
			EventManager.getInstance(getCore()).enqueueEvent(new StudentShotEvent(getName(), getType()));
			this.body.setCollisionFlag(0x0010);
			SoundHandle soundHandle = new SoundHandle("hitB_handle", "hitB.wav");
			soundHandle.load(this.getCore());
			SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle.getResource());
			SoundHandle soundHandle2 = new SoundHandle("koB_handle", "koB.wav");
			soundHandle2.load(this.getCore());
			SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle2.getResource());
		}
		
		
	}
}
