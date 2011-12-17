package game.view;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.cogaen.core.Core;
import org.cogaen.event.Event;
import org.cogaen.event.EventListener;
import org.cogaen.event.EventManager;
import org.cogaen.input.KeyboardControllerSource;
import org.cogaen.java2d.Camera;
import org.cogaen.java2d.SceneManager;
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
		//TODO: add Events here
		
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
		// TODO Auto-generated method stub
		
	}

}
