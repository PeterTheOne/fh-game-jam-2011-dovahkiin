package game.motion;

public class Circle extends Body {

	private double radius;
	
	public Circle(String name, double radius) {
		super(name);
		this.radius = radius;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public boolean isColliding(Body body) {
		return body.isColliding(this);
	}

	@Override
	public boolean isColliding(Circle circle) {
		return CollisionTester.isColliding(this, circle);
	}

	@Override
	public boolean isColliding(Rectangle rectangle) {
		return CollisionTester.isColliding(this, rectangle);
	}

}
