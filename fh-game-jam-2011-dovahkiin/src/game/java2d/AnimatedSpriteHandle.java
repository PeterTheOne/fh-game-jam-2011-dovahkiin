package game.java2d;

import java.awt.Image;

import org.cogaen.core.Core;
import org.cogaen.resource.ResourceHandle;
import org.cogaen.resource.ResourceManager;

public class AnimatedSpriteHandle extends ResourceHandle {

	private double width;
	private double height;
	private String imageName;
	private AnimatedSpriteVisual visual;
	private int numFrames;
	
	public AnimatedSpriteHandle(String name, String imageName, int numFrames, double width, double height) {
		super(name);
		this.imageName = imageName;
		this.width = width;
		this.height = height;
		this.numFrames = numFrames;
	}
	
	public AnimatedSpriteHandle(String name, String imageName, int numFrames) {
		this(name, imageName, numFrames, 0, 0);
	}

	@Override
	public Object getResource() {
		if (!isLoaded()) {
			throw new RuntimeException("resource not loaded");
		}
		
		return this.visual;
	}

	@Override
	public boolean isLoaded() {
		return this.visual != null;
	}

	@Override
	public void load(Core core) {
		Image image = (Image) ResourceManager.getInstance(core).getResource(this.imageName);
		double width = this.width;
		double height = this.height;
		
		if (width <= 0) {
			width = image.getWidth(null);
		}
		if (height <= 0) {
			height = image.getHeight(null);
		}
		this.visual = new AnimatedSpriteVisual(core, image, numFrames, width, height);
	}

	@Override
	public void unload() {
		this.visual = null;
	}

}
