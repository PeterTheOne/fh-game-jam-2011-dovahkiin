package game.view;

import game.entity.HochiEntity;
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
import org.cogaen.java2d.CircleVisual;
import org.cogaen.java2d.ImageHandle;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;
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
		this.resMngr.addResource(new ImageHandle( "hochi-walk_img_right", "hochi-walk_right.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-walk_spr_right", "hochi-walk_img_right", 8, 250, 548) );
		this.resMngr.addResource(new ImageHandle( "hochi-walk_img_left", "hochi-walk_left.png") );
		this.resMngr.addResource(new AnimatedSpriteHandle( "hochi-walk_spr_left", "hochi-walk_img_left", 8, 250, 548) );
	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(EntityCreatedEvent.TYPE)) {
			handleEntityCreated((EntityCreatedEvent) event);
		} else if (event.isOfType(EntityDestroyedEvent.TYPE)) {
			handleEntityDestroyedEvent((EntityDestroyedEvent) event);
		} else if (event.isOfType(EntityMovedEvent.TYPE)) {
			handleEntityMovedEvent((EntityMovedEvent) event);
		}else if(event.isOfType(LoadLevelEvent.TYPE)){
			
		}
	}

	private void handleEntityCreated(EntityCreatedEvent event) {
		if (event.getEntityType().equals(HochiEntity.TYPE)) {
			createHochi(event.getEntityName());
		}
	}

	private void createHochi(String entityName) {
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_spr_right");
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
		if(event.getVelocityX() > 0){
			node.removeVisual(playerVisual);
			this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_spr_right");
			this.playerVisual.play();
			node.addVisual(playerVisual);
		}else if(event.getVelocityX() < 0){
			node.removeVisual(playerVisual);
			this.playerVisual = (AnimatedSpriteVisual) resMngr.getResource("hochi-walk_spr_left");
			node.addVisual(playerVisual);
			this.playerVisual.play();
		}
	}

}
