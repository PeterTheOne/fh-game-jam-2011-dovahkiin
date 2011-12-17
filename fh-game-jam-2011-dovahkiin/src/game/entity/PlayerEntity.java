package game.entity;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;

public abstract class PlayerEntity extends Entity {

	public PlayerEntity(Core core, String name) {
		super(core, name);
		// TODO Auto-generated constructor stub
	}
	
	public abstract String getType();
	
	protected abstract void setUp();
	
	protected abstract void tearDown();
	
	public abstract void update();

}
