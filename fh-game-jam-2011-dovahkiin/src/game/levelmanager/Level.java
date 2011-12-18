package game.levelmanager;

import game.entity.StudentEntity;
import game.event.LevelEngagedEvent;
import game.event.StudentShotEvent;
import game.motion.MotionManager;
import game.motion.Rectangle;

import java.util.ArrayList;
import java.util.Vector;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityManager;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;


public class Level implements EventListener {

	private Vector<Rectangle> structures = new Vector<Rectangle>();
	private Vector<StudentEntity> enemies = new Vector<StudentEntity>();
	private Core core;
	private String name;
	//private String prevLevel = null;
	private String nextLevel = null;
	private boolean switchToEnd;
	
	public Level(Core core, String name){
		this.core = core;
		this.name = name;
		this.switchToEnd = false;
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
		EventManager evtMngr = EventManager.getInstance(this.core);
		evtMngr.enqueueEvent(new LevelEngagedEvent(this.name));
		evtMngr.addListener(this, StudentShotEvent.TYPE);
		EntityManager entMngr = EntityManager.getInstance(this.core);
		for (StudentEntity student : enemies) {
			entMngr.addEntity(student);
		}
	}
	
	public void disenage(){
		for(Rectangle struct : structures){
			MotionManager.getInstance(core).removeBody(struct);
		}
		EntityManager entMngr = EntityManager.getInstance(this.core);
		for (StudentEntity student : enemies) {
			entMngr.removeEntity(student);
		}
		EventManager evtMngr = EventManager.getInstance(this.core);
		evtMngr.removeListener(this);
	}
	
	public void addStructure(Rectangle structure){
		structures.add(structure);
		MotionManager.getInstance(this.core).addBody(structure);
	}
	
	public void addEnemy(StudentEntity enemy){
		enemies.add(enemy);
	}
	
	/*public void setPrevLevel(String prevLevel){
		this.prevLevel = prevLevel;
	}*/
	
	public void setNextLevel(String nextLevel){
		this.nextLevel = nextLevel;
	}
	
	/*public String getPrevLevel() {
		return this.prevLevel;
	}*/
	
	public void setSwitchToEnd(boolean switchToEnd) {
		this.switchToEnd = switchToEnd;
	}
	
	public String getNextLevel() {
		return this.nextLevel;
	}
	
	public String getName(){
		return this.name;
	}

	public void addFloor() {
		//TODO: replace magic numbers
		Rectangle rec = new Rectangle("plaform", 4096, 50, 0, -400);
		rec.setCollisionFlag(0x0008);
		rec.setCollisionMask(0x0001);
		addStructure(rec);
		
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(StudentShotEvent.TYPE)) {
			//handleStudentShotEvent((StudentShotEvent) event);
		}
	}

	private void handleStudentShotEvent(StudentShotEvent event) {
		ArrayList<StudentEntity> toRemove = new ArrayList<StudentEntity>();
		for (StudentEntity stud : enemies) {
			if (stud.getName().equals(event.getStudentName())) {
				toRemove.add(stud);
			}
		}
		for (StudentEntity stud: toRemove) {
			enemies.remove(stud);
		}
	}

	public boolean getSwitchToEnd() {
		return this.switchToEnd;
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
