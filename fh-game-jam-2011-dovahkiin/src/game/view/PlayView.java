package game.view;

import game.entity.HochiEntity;
import game.entity.SchaufiEntity;
import game.event.LevelDisengagedEvent;
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
		evtMngr.addListener(this, LevelDisengagedEvent.TYPE);
		evtMngr.addListener(this, LoadLevelEvent.TYPE);
		
		this.keyboardSrc.engage();
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
		this.resMngr.addResource(new ImageHandle( "hochi-walk_right_img", "hochi-walk_spr_right.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-walk_right_spr", "hochi-walk_right_img", 8, 186, 400) );
		this.resMngr.addResource(new ImageHandle( "hochi-walk_left_img", "hochi-walk_spr_left.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-walk_left_spr", "hochi-walk_left_img", 8, 186, 400) );
		this.resMngr.addResource(new ImageHandle( "hochi-fight_left_img", "hochi-fight_left.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-fight_left_spr", "hochi-fight_left_img", 8, 186, 400) );
		this.resMngr.addResource(new ImageHandle( "hochi-fight_right_img", "hochi-fight_right.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-fight_right_spr", "hochi-fight_right_img", 8, 186, 400) );
		
		// schaufi
		this.resMngr.addResource(new ImageHandle( "schaufi-walk_right_img", "schaufi-walk_spr_right.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "schaufi-walk_right_spr", "schaufi-walk_right_img", 8, 186, 400));
		this.resMngr.addResource(new ImageHandle( "schaufi-walk_left_img", "schaufi-walk_spr_left.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "schaufi-walk_left_spr", "schaufi-walk_left_img", 8, 186, 400));
		
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
		} else if (event.isOfType(LevelDisengagedEvent.TYPE)) {
			handleLevelDisengagedEvent((LevelDisengagedEvent) event);
		} else if(event.isOfType(LoadLevelEvent.TYPE)){
			//empty
		}
	}

	private void handleEntityCreated(EntityCreatedEvent event) {
		if (event.getEntityType().equals(HochiEntity.TYPE)) {
			createHochi(event.getEntityName());
		} else if (event.getEntityType().equals(SchaufiEntity.TYPE)) {
			createSchaufi(event.getEntityName());
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

	private void handleEntityDestroyedEvent(EntityDestroyedEvent event) {
		this.scnMngr.destroySceneNode(event.getEntityName());
	}

	private void handleEntityMovedEvent(EntityMovedEvent event) {
		SceneNode node = this.scnMngr.getSceneNode(event.getEntityName());
		if (node != null) {
			node.setPose(event.getX(), event.getY(), event.getAngle());
		}
		//TODO: change this to HochiEntity state changes
		if (event.getEntityName().equals("Hochi")) {
			if(event.getVelocityX() > 0){
				node.removeVisual(playerVisual);
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_right_spr");
				this.playerVisual.play();
				node.addVisual(playerVisual);
			} else if(event.getVelocityX() < 0) {
				node.removeVisual(playerVisual);
				this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_left_spr");
				node.addVisual(playerVisual);
			}
			if (Math.abs(event.getVelocityY()) < 60 && event.getVelocityX() != 0) {
				this.playerVisual.play();
			} else {
				this.playerVisual.stop();
			}
		}
	}

	private void handleLevelEngagedEvent(LevelEngagedEvent event) {
		SceneNode scnNode = this.scnMngr.createSceneNode(event.getLevelName());
		SpriteVisual bgVisual = this.scnMngr.createSpriteVisual("level1_1_spr");
		scnNode.addVisual(bgVisual);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void handleLevelDisengagedEvent(LevelDisengagedEvent event) {
		this.scnMngr.destroySceneNode(event.getLevelName());
	}

}
