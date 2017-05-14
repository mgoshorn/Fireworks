import java.awt.Color;
import java.awt.geom.Point2D;

public class Burst {
	private Color color;
	private int number;
	private int velocity;

	public Burst(Color c, int n, int velocity) {
		this.color = c;
		this.number = n;
		this.velocity = velocity;
	} 
	
	public void activateBurst(Point2D point) {
		double frequency = 360 / number;
		for(int i = 0; i <= this.number; i++) {
			double angle = i * frequency;
			Particle p = new Particle(this.color, Main.size.getHeight()/70 * 0.5 + Main.size.getHeight()/150 * 0.5 * Math.random(), 5000, point);
			p.setDx((Math.random() * 0.35 + 0.65) * this.velocity * Math.cos(Math.toRadians(angle)));
			p.setDy((Math.random() * 0.35 + 0.65) * this.velocity * Math.sin(Math.toRadians(angle)));
			Main.particles.add(p);
		}
	}
}
