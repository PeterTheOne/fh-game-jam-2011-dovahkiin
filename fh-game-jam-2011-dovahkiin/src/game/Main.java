package game;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.cogaen.java2d.Screen;
import org.cogaen.java2d.WindowedScreen;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Screen screen = new WindowedScreen(800, 600);
		JFrame frame = new JFrame("FH Game Jam 2011 Dovahkiin");
		frame.add(screen.getComponent());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		screen.waitUntilReady();
		
		GameApp app = new GameApp(screen);
		app.runGameLoop();
	}
}
