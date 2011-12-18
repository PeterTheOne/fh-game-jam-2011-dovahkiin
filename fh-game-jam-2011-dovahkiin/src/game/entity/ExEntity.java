package game.entity;

import game.motion.Body;
import game.motion.MotionManager;
import game.motion.Rectangle;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.name.NameService;

public class ExEntity extends Entity {
	
	public static final String TYPE = "Ex";
	
	private Body body;
	
	public ExEntity(Core core, double x, double y, double velocityX) {
		this(core, NameService.getInstance(core).generateName(), 
				x, y, velocityX);
	}

	public ExEntity(Core core, String name, 
			double x, double y, double velocityX) {
		super(core, name);
		this.body = new Rectangle(name, 10, 10);
		this.body.setPosition(x, y);
		this.body.setCollisionFlag(0x0001);
		this.body.setVelocity(velocityX, 0);
		
		//TODO: self destroy task
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(this.body);
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
