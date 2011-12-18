package game.java2d;
import game.event.EndFightEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.cogaen.core.Core;
import org.cogaen.event.EventManager;
import org.cogaen.java2d.SceneManager;
import org.cogaen.java2d.Visual;
import org.cogaen.sound.SoundEffect;
import org.cogaen.sound.SoundHandle;
import org.cogaen.sound.SoundService;


public class AnimatedSpriteVisual extends Visual implements Cloneable {

	protected List<Image> compImages = new ArrayList<Image>();
	protected int currentFrame = 0;
	protected AffineTransform transform;
	protected Image image;
	protected double imgWidth;
	protected double imgHeight;
	protected boolean isPlaying = false;
	protected Core core;
	protected double elapsedTime = 0.0;
	protected double updateRate = 0.1;
	protected boolean stopAfterPlay = false;
	
	public AnimatedSpriteVisual(Core core, Image image, int numFrames, double width, double height) {
		this.core = core;
		this.image = image;
		int imgWidth = image.getWidth(null)/numFrames;
		int imgHeight = image.getHeight(null);
		this.transform = AffineTransform.getScaleInstance(width / imgWidth, height / imgHeight);
		this.transform.translate(-imgWidth / 2.0, -imgHeight / 2.0);
			
		this.imgWidth = width;
		this.imgHeight = height;
		
		// create compatible image for higher rendering performance
		GraphicsConfiguration gc = SceneManager.getInstance(core).getScreen().getGraphicsConfiguration();
		for(int i=0; i<numFrames; i++) {
			this.compImages.add(gc.createCompatibleImage(imgWidth, imgHeight, getTransparence(image)));
			Graphics g = this.compImages.get(i).getGraphics();
			g.drawImage(image, -i*imgWidth, 0, null);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(this.compImages.get(currentFrame), this.transform, null);
		
		if(!isPlaying) {
			return;
		}
		
		double dt = core.getDeltaTime();
		elapsedTime += dt;
		
		if(elapsedTime >= updateRate) {
			elapsedTime -= updateRate;
			if(currentFrame == 0 && !stopAfterPlay){
				SoundHandle soundHandle = new SoundHandle("walk_handle", "walk.wav");
				soundHandle.load(this.core);
				SoundService.getInstance(this.core).play((SoundEffect)soundHandle.getResource());
			}
			currentFrame++;
			if(currentFrame == compImages.size()) {
				currentFrame = 0;
				if(stopAfterPlay){
					stop();
					EventManager.getInstance(this.core).enqueueEvent(new EndFightEvent());
				}
			}
		}
	}
	
	private int getTransparence(Image image) {
		if (image instanceof BufferedImage) {
			return ((BufferedImage) image).getTransparency();
		}
		
		return Transparency.TRANSLUCENT;
	}
	
	@Override
	public boolean contains(double x, double y) {
		Rectangle2D.Double checkRect = new Rectangle2D.Double(transform.getTranslateX(), transform.getTranslateY(), imgWidth, imgHeight);
		return checkRect.contains(x, y);
	}
	
	public void setFrame(int frame) {
		if(frame < compImages.size()) {
			this.currentFrame = frame;
		}
	}
	
	public int getFrame() {
		return currentFrame;
	}
	
	public void play() {
		isPlaying = true;
		stopAfterPlay = false;
	}
	
	public void playNStop() {
		isPlaying = true;
		stopAfterPlay = true;
	}
	
	public void stop() {
		isPlaying = false;
		currentFrame = 0;
	}
	
	public Object clone() {
		AnimatedSpriteVisual clone = new AnimatedSpriteVisual(this.core, this.image, this.compImages.size(), this.imgWidth, this.imgHeight);
		return clone;
	}
}
