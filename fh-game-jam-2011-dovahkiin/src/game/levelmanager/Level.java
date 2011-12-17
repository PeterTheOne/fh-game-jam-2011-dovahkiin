package game.levelmanager;

import game.entity.EnemyEntity;
import game.event.LeaveScreenEvent;
import game.event.LevelEngagedEvent;
import game.event.LoadLevelEvent;
import game.event.LeaveScreenEvent.LeaveScreen;
import game.motion.MotionManager;
import game.motion.Rectangle;

import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;


public class Level {

	private Vector<Rectangle> structures = new Vector<Rectangle>();
	private Vector<EnemyEntity> enemies = new Vector<EnemyEntity>();
	private Core core;
	private String name;
	private String prevLevel;
	private String nextLevel;
	
	public Level(Core core, String name){
		this.core = core;
		this.name = name;
		//EventManager.getInstance(this.core).addListener(this, LoadLevelEvent.TYPE);
	}
	
	/*public void drawLevel(){
		SceneManager scnMngr = SceneManager.getInstance(core);
		SceneNode node = scnMngr.createSceneNode(levelName);
		EventManager.getInstance(this.core).addListener(this, LeaveScreenEvent.TYPE);
		for(Rectangle struct : structures){
			MotionManager.getInstance(core).addBody(struct);
		}
		for(EnemyEntity enemy : enemies){
			//TODO: Gegner in node einfügen (neue child-Node pro Gegner)
		}
	}*/
	
	public void engage() {
		EventManager.getInstance(this.core).enqueueEvent(new LevelEngagedEvent(this.name));
	}
	
	public void disenage(){
		/*SceneManager scnMngr = SceneManager.getInstance(core);
		scnMngr.destroySceneNode(levelName);*/
		for(Rectangle struct : structures){
			MotionManager.getInstance(core).removeBody(struct);
		}
	}
	
	public void addStructure(Rectangle structure){
		structures.add(structure);
	}
	
	public void addEnemy(EnemyEntity enemy){
		enemies.add(enemy);
	}
	
	public void setPrevLevel(String prevLevel){
		this.prevLevel = prevLevel;
	}
	
	public void setNextLevel(String nextLevel){
		this.nextLevel = nextLevel;
	}
	
	public String getName(){
		return this.name;
	}

	/*public void handleEvent(Event event) {
		if(event.isOfType(LeaveScreenEvent.TYPE)){
			LeaveScreenEvent leave = (LeaveScreenEvent)(event);
			LeaveScreen side = leave.getSide();
			switch(side){
			case LEFT:
				this.destroyLevel();
				EventManager.getInstance(this.core).enqueueEvent(new LoadLevelEvent(this.prevLevel));
				break;
			case RIGHT:
				this.destroyLevel();
				EventManager.getInstance(this.core).enqueueEvent(new LoadLevelEvent(this.nextLevel));
				break;
				
			}
		}else if(event.isOfType(LoadLevelEvent.TYPE)){
			LoadLevelEvent e = (LoadLevelEvent)(event);
			if(this.levelName.equals(e.getLevel())){
				drawLevel();
			}
		}
	}*/
	
	
}
