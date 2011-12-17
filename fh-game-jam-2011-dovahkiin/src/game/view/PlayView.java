package game.view;

import game.entity.HochiEntity;

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
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.SceneNode;
import org.cogaen.logging.LoggingService;
import org.cogaen.view.AbstractView;

public class PlayView extends AbstractView implements EventListener {

	private SceneManager scnMngr;
	private KeyboardControllerSource keyboardSrc;
	
	public PlayView(Core core) {
		super(core);
		this.scnMngr = SceneManager.getInstance(core);
		this.keyboardSrc = new KeyboardControllerSource(core, "PlayerOne");
		this.keyboardSrc.setAxisKeys(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, -1);
		this.keyboardSrc.addAction(KeyEvent.VK_SPACE);
	}

	@Override
	public void engage() {
		this.scnMngr.setClearBackground(true);
		this.scnMngr.setBackgroundColor(Color.black);

		Camera cam = this.scnMngr.createCamera();
		cam.setZoom(this.scnMngr.getScreen().getWidth() / 27.5);
		
		EventManager evtMngr = EventManager.getInstance(getCore());
		evtMngr.addListener(this, EntityCreatedEvent.TYPE);
		evtMngr.addListener(this, EntityDestroyedEvent.TYPE);
		
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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(Event event) {
		if (event.isOfType(EntityCreatedEvent.TYPE)) {
			handleEntityCreated((EntityCreatedEvent) event);
		} else if (event.isOfType(EntityDestroyedEvent.TYPE)) {
			handleEntityDestroyedEvent((EntityDestroyedEvent) event);
		}
	}

	private void handleEntityCreated(EntityCreatedEvent event) {
		if (event.getEntityType().equals(HochiEntity.TYPE)) {
			createHochi(event.getEntityName());
		}
	}

	private void createHochi(String entityName) {
		SceneNode scnNode = this.scnMngr.createSceneNode(entityName);
		CircleVisual circle = this.scnMngr.createCircleVisual(2);
		circle.setColor(Color.red);
		scnNode.addVisual(circle);
		this.scnMngr.getRootSceneNode().addChild(scnNode);
	}

	private void handleEntityDestroyedEvent(EntityDestroyedEvent event) {
		this.scnMngr.destroySceneNode(event.getEntityName());
	}

}
