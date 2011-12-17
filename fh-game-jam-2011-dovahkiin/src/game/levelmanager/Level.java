package game.levelmanager;

import game.entity.EnemyEntity;
import game.motion.MotionManager;
import game.motion.Rectangle;

import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.entity.Entity;
import org.cogaen.event.EventManager;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;

public abstract class Level {

	private Vector<Rectangle> structures = new Vector<Rectangle>();
	private Vector<EnemyEntity> enemies = new Vector<EnemyEntity>();
	private Core core;
	private String levelName;
	private String prevLevel;
	private String nextLevel;
	
	public Level(Core core, String levelName){
		this.core = core;
		this.levelName = levelName;
	}
	
	public void drawLevel(){
		SceneManager scnMngr = SceneManager.getInstance(core);
		SceneNode node = scnMngr.createSceneNode(levelName);
		EventManager evtMngrEventManager = EventManager.getInstance(core);
		for(Rectangle struct : structures){
			MotionManager.getInstance(core).addBody(struct);
		}
		for(EnemyEntity enemy : enemies){
			//TODO: Gegner in node einfügen (neue child-Node pro Gegner)
		}
	}
	
	public void destroyLevel(){
		SceneManager scnMngr = SceneManager.getInstance(core);
		scnMngr.destroySceneNode(levelName);
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
	
	
}
