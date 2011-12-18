package game.view;

import game.entity.HochiEntity;
import game.entity.HochiEntity.Side;
import game.entity.HochiEntity.VisualState;
import game.event.ChangeVisualEvent;
import game.event.EndFightEvent;
import game.entity.RudiEntity;
import game.entity.SchaufiEntity;
import game.entity.StudentEntity;
import game.event.LevelEngagedEvent;
import game.event.LoadLevelEvent;
import game.java2d.AnimatedSpriteHandle;
import game.java2d.AnimatedSpriteVisual;
import game.motion.EntityMovedEvent;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.cogaen.core.Core;
import org.cogaen.entity.EntityCreatedEvent;
import org.cogaen.entity.EntityDestroyedEvent;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.input.KeyboardControllerSource;
import org.cogaen.java2d.Camera;
import org.cogaen.java2d.ImageHandle;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;
import org.cogaen.java2d.SpriteHandle;
import org.cogaen.java2d.SpriteVisual;
import org.cogaen.java2d.Visual;
import org.cogaen.logging.LoggingService;
import org.cogaen.resource.ResourceManager;
import org.cogaen.view.AbstractView;

public class PlayView extends AbstractView implements EventListener {

	private SceneManager scnMngr;
	private KeyboardControllerSource keyboardSrc;
	private ResourceManager resMngr;
	AnimatedSpriteVisual playerVisual;
	
	public PlayView(Core core) {
		super(core);
		this.scnMngr = SceneManager.getInstance(core);
		this.keyboardSrc = new KeyboardControllerSource(core, "PlayerOne");
		this.keyboardSrc.setAxisKeys(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, -1);
		this.keyboardSrc.addAction(KeyEvent.VK_SPACE);
		this.keyboardSrc.addAction(KeyEvent.VK_S);
		this.resMngr = ResourceManager.getInstance(this.getCore());
	}

	@Override
	public void engage() {
		this.scnMngr.setClearBackground(true);
		this.scnMngr.setBackgroundColor(Color.black);

		Camera cam = this.scnMngr.createCamera();
		//cam.setZoom(this.scnMngr.getScreen().getWidth() / 500d);
		
		EventManager evtMngr = EventManager.getInstance(getCore());
		evtMngr.addListener(this, EntityCreatedEvent.TYPE);
		evtMngr.addListener(this, EntityDestroyedEvent.TYPE);
		evtMngr.addListener(this, EntityMovedEvent.TYPE);
		evtMngr.addListener(this, LevelEngagedEvent.TYPE);
		evtMngr.addListener(this, LoadLevelEvent.TYPE);
		evtMngr.addListener(this, ChangeVisualEvent.TYPE);
		
		this.keyboardSrc.engage();
		
		SceneNode scnNode = this.scnMngr.createSceneNode("level_bg");
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}
	
	@Override
	public void disengage() {

		EventManager.getInstance(getCore()).removeListener(this);
		this.scnMngr.destroyAll();
		
		this.keyboardSrc.disengage();
	}

	@Override
	public void registerResources(String group) {
		// level backgrounds
		this.resMngr.addResource(new ImageHandle("level1_1_img", "level1_1.png"));
		this.resMngr.addResource(new SpriteHandle( "level1_1_spr", "level1_1_img", 1024, 768));

		// character
		// hochi
		this.resMngr.addResource(new ImageHandle("hochi-walk_right_img", "hochi-walk_spr_right.png"));
		this.resMngr.addResource(new ImageHandle("hochi-walk_left_img", "hochi-walk_spr_left.png"));
		this.resMngr.addResource(new ImageHandle("hochi-fight_left_img", "hochi-fight_left.png"));
		this.resMngr.addResource(new ImageHandle("hochi-fight_right_img", "hochi-fight_right.png"));
		this.resMngr.addResource(new AnimatedSpriteHandle("hochi-walk_right_spr", "hochi-walk_right_img", 8, 186, 400));
		this.resMngr.addResource(new AnimatedSpriteHandle("hochi-walk_left_spr", "hochi-walk_left_img", 8, 186, 400));
		this.resMngr.addResource(new AnimatedSpriteHandle("hochi-fight_left_spr", "hochi-fight_left_img", 5, 260, 400));
		this.resMngr.addResource(new AnimatedSpriteHandle("hochi-fight_right_spr", "hochi-fight_right_img", 5, 260, 400));

		// schaufi
		this.resMngr.addResource(new ImageHandle("schaufi-walk_right_img", "schaufi-walk_spr_right.png"));
		this.resMngr.addResource(new ImageHandle("schaufi-walk_left_img", "schaufi-walk_spr_left.png"));
		this.resMngr.addResource(new AnimatedSpriteHandle("schaufi-walk_right_spr", "schaufi-walk_right_img", 8, 186, 400));
		this.resMngr.addResource(new AnimatedSpriteHandle("schaufi-walk_left_spr", "schaufi-walk_left_img", 8, 186, 400));

		// rudi
		this.resMngr.addResource(new ImageHandle("rudi-walk_right_img", "rudi-walk_spr_right.png"));
		//this.resMngr.addResource(new ImageHandle("rudi-walk_left_img", "rudi-walk_spr_left.png"));
		this.resMngr.addResource(new AnimatedSpriteHandle("rudi-walk_right_spr", "rudi-walk_right_img", 8, 186, 400));
		//this.resMngr.addResource(new AnimatedSpriteHandle("rudi-walk_left_spr", "rudi-walk_left_img", 8, 186, 400));
		
		// students
		this.resMngr.addResource(new ImageHandle("student_img", "student.png"));
		this.resMngr.addResource(new ImageHandle("student_side_img", "student_side.png"));
		this.resMngr.addResource(new ImageHandle("student_defeated_img", "student_defeated.png"));
		this.resMngr.addResource(new ImageHandle("studentin_img", "studentin.png"));
		this.resMngr.addResource(new ImageHandle("studentin_side_img", "studentin_side.png"));
		this.resMngr.addResource(new ImageHandle("studentin_defeated_img", "studentin_defeated.png"));
		this.resMngr.addResource(new SpriteHandle("student_spr", "student_img", 182, 400));
		this.resMngr.addResource(new SpriteHandle("student_side_spr", "student_side_img", 182, 400));
		this.resMngr.addResource(new SpriteHandle("student_defeated_spr", "student_defeated_img", 182, 400));
		this.resMngr.addResource(new SpriteHandle("studentin_spr", "studentin_img", 182, 400));
		this.resMngr.addResource(new SpriteHandle("studentin_side_spr", "studentin_side_img", 182, 400));
		this.resMngr.addResource(new SpriteHandle("studentin_defeated_spr", "studentin_defeated_img", 182, 400));
		
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(EntityCreatedEvent.TYPE)) {
			handleEntityCreated((EntityCreatedEvent) event);
		} else if (event.isOfType(EntityDestroyedEvent.TYPE)) {
			handleEntityDestroyedEvent((EntityDestroyedEvent) event);
		} else if (event.isOfType(EntityMovedEvent.TYPE)) {
			handleEntityMovedEvent((EntityMovedEvent) event);
		} else if (event.isOfType(LevelEngagedEvent.TYPE)) {
			handleLevelEngagedEvent((LevelEngagedEvent) event);
		} else if(event.isOfType(LoadLevelEvent.TYPE)){
			//empty
		}else if(event.isOfType(ChangeVisualEvent.TYPE)){
			handleChangeVisualEvent((ChangeVisualEvent)event);
		}
	}


	private void handleChangeVisualEvent(ChangeVisualEvent event) {
		SceneNode s = scnMngr.getSceneNode(event.getEntitiyName());
		s.removeVisual(playerVisual);
		if(event.getSide() == Side.LEFT){
			if(event.getVisualState().equals(VisualState.FIGHT)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-fight_left_spr");
				this.playerVisual.playNStop();
			}else if(event.getVisualState().equals(VisualState.STAND)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_left_spr");
				this.playerVisual.stop();
			}else if(event.getVisualState().equals(VisualState.WALK)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_left_spr");
				this.playerVisual.play();
			}else if(event.getEntitiyName().equals(VisualState.JUMP)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_left_spr");
				this.playerVisual.stop();
			}
		}else if(event.getSide().equals(Side.RIGHT)){
			if(event.getVisualState().equals(VisualState.FIGHT)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-fight_right_spr");
				this.playerVisual.playNStop();
			}else if(event.getVisualState().equals(VisualState.STAND)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_right_spr");
				this.playerVisual.stop();
			}else if(event.getVisualState().equals(VisualState.WALK)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_right_spr");
				this.playerVisual.play();
			}else if(event.getEntitiyName().equals(VisualState.JUMP)){
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_right_spr");
				this.playerVisual.stop();
			}
		}
		if(event.getVisualState().equals(VisualState.JUMP)){
			this.playerVisual.stop();
		}
		s.addVisual(playerVisual);
	}

	private void handleEntityCreated(EntityCreatedEvent event) {
		if (event.getEntityType().equals(HochiEntity.TYPE)) {
			createHochi(event.getEntityName());
		} else if (event.getEntityType().equals(SchaufiEntity.TYPE)) {
			createSchaufi(event.getEntityName());
		} else if (event.getEntityType().equals(RudiEntity.TYPE)) {
			createRudi(event.getEntityName());
		} else if (event.getEntityType().equals(StudentEntity.TYPE)) {
			createStudent(event.getEntityName());
		}
	}

	private void createHochi(String entityName) {
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_right_spr");
		this.playerVisual.play();
		scnNode.addVisual(this.playerVisual);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void createSchaufi(String entityName) {
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("schaufi-walk_right_spr");
		this.playerVisual.play();
		scnNode.addVisual(this.playerVisual);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void createRudi(String entityName) {
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("rudi-walk_right_spr");
		this.playerVisual.play();
		scnNode.addVisual(this.playerVisual);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void createStudent(String entityName) {
		//TODO: use different sprites
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		SpriteVisual vis = this.scnMngr.createSpriteVisual("student_spr");
		scnNode.addVisual(vis);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void handleEntityDestroyedEvent(EntityDestroyedEvent event) {
		this.scnMngr.destroySceneNode(event.getEntityName());
	}

	private void handleEntityMovedEvent(EntityMovedEvent event) {
		SceneNode node = this.scnMngr.getSceneNode(event.getEntityName());
		if (node != null) {
			node.setPose(event.getX(), event.getY(), event.getAngle());
		}
	}

	private void handleLevelEngagedEvent(LevelEngagedEvent event) {
		SceneNode scnNode = this.scnMngr.getSceneNode("level_bg");
		scnNode.getVisuals().clear();
		SpriteVisual bgVisual = this.scnMngr.createSpriteVisual("level1_1_spr");
		scnNode.addVisual(bgVisual);
	}

}
