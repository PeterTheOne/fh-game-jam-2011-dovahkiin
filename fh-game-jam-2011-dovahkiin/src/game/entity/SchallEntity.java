package game.entity;

import game.event.DestroyEntityEvent;
import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.name.NameService;
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;
import org.cogaen.task.FireEventTask;
import org.cogaen.task.TaskManager;

public class SchallEntity extends Entity {
	
	public static final String TYPE = "Schall";
	
	private Body body;
	
	public SchallEntity(Core core, double x, double y, double velocityX) {
		this(core, NameService.getInstance(core).generateName(), 
				x, y, velocityX);
	}

	public SchallEntity(Core core, String name, 
			double x, double y, double velocityX) {
		super(core, name);
		this.body = new Rectangle(name, 10, 10);
		this.body.setPosition(x, y);
		this.body.setVelocity(velocityX, 0);
		this.body.setCollisionFlag(0x0020);
		this.body.setCollisionMask(0x0004);
		
		TaskManager tskMngr = TaskManager.getInstance(getCore());
		tskMngr.attachTask(new FireEventTask(getCore(), new DestroyEntityEvent(getName()), 2));
		//TODO: self destroy task
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(this.body);
		SoundHandle soundHandle = new SoundHandle("attackB_handle", "attackB.wav");
		soundHandle.load(this.getCore());
		SoundService.getInstance(this.getCore()).play((SoundEffect)soundHandle.getResource());
	}

	@Override
	protected void tearDown() {
		MotionManager.getInstance(getCore()).removeBody(this.body);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
