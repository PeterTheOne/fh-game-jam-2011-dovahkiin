package game.motion;

import org.cogaen.logging.LoggingService;

public class CollisionTester {

	public static boolean isColliding(Circle c1, Circle c2) {
		double dx = c1.getPositionX() - c2.getPositionX();
		double dy = c1.getPositionY() - c2.getPositionY();
		double radii = c1.getRadius() + c2.getRadius();
		
		return dx * dx + dy * dy < radii * radii;
	}
	
	public static boolean isColliding(Rectangle r1, Rectangle r2) {
		if (r1.getRight() > r2.getLeft()
			&& r1.getTop() > r2.getBottom()
			&& r2.getRight() > r1.getLeft()
			&& r2.getTop() > r1.getBottom()) {
			return true;
		}
		return false;
	}
	
	public static boolean isColliding(Circle c, Rectangle r) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	public static boolean collisionDirection(Body b1, Body b2) {
		Rectangle rectA = (Rectangle)b1;
		Rectangle rectB = (Rectangle)b2;
		
		// Calculate half sizes.
        double halfWidthA = rectA.getWidth() / 2.0;
        double halfHeightA = rectA.getHeight() / 2.0;
        double halfWidthB = rectB.getWidth() / 2.0;
        double halfHeightB = rectB.getHeight() / 2.0;

        double centerAX = rectA.getPositionX();
        double centerAY = rectA.getPositionY();
        double centerBX = rectB.getPositionX();
        double centerBY = rectB.getPositionY();
        
        // Calculate current and minimum-non-intersecting distances between centers.
        double distanceX = centerAX - centerBX;
        double distanceY = centerAY - centerBY;
        double minDistanceX = halfWidthA + halfWidthB;
        double minDistanceY = halfHeightA + halfHeightB;
		
        double depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
        double depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;
        
		return Math.abs(depthY) < Math.abs(depthX);
	}
}
