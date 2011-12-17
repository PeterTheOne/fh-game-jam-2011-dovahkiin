package game.entity;

import game.motion.Body;
import game.motion.MotionManager;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;

public class EnemyEntity extends Entity{

	public static final String TYPE = "Enemy";
	private Body body;
	
	public EnemyEntity(Core core, String name, Body body) {
		super(core, name);
		this.body = body;
	}
	
	public String getType() {
		return TYPE;
	}

	protected void setUp() {
		MotionManager.getInstance(getCore()).addBody(body);
	}

	protected void tearDown() {
		MotionManager.getInstance(getCore()).removeBody(body);
	}

	public void update() {}

}
