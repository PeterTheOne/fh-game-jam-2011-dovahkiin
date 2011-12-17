package game.java2d;

import java.awt.Image;

import org.cogaen.core.Core;
import org.cogaen.java2d.AnimatedSpriteVisual;

public class ContinuousAnimatedSpriteVisual extends AnimatedSpriteVisual{

	public ContinuousAnimatedSpriteVisual(Core core, Image image, int numFrames, double width, double height){
		super(core, image, numFrames, width, height);
	}
	
	public void stop(){
		currentFrame = 0;
	}
	
	public void stop(boolean end) {
		isPlaying = !end;
		currentFrame = 0;
	}
}
