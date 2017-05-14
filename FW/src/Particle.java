import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;

public class Particle {
	private Color color;
	private Double diameter;
	private double fallOffTime;
	private Ellipse2D ellipse;
	private double dx, dy;
	long birth;
	boolean dead;

	public Particle(Color c, double diam, double falloff, Point2D point) {
		this.dx = (Math.random() - 0.5) * Main.meter_pixels;
		this.color = c;
		this.diameter = diam;
		this.fallOffTime = falloff;
		this.ellipse = new Ellipse2D.Double(point.getX() - diam / 2, point.getY() - diam/2, diam, diam);
		this.birth = System.currentTimeMillis();
	}
	
	public void setDx(double dx) {
		this.dx = dx * Main.meter_pixels;
	}
	
	public void setDy(double dy) {
		this.dy = dy * Main.meter_pixels;
	}
	
	public void updateFade() {
		int alpha = (int)((double)((1 - (System.currentTimeMillis() - this.birth) / this.fallOffTime)) * 255);
		if(alpha < 0) {
			this.dead = true;
			alpha = 0;
		}
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha);
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void addGravity() {
		this.dy += (1.96 * Main.meter_pixels / 60) * (Math.random() * 0.5 + 0.5);
	}
	
	public void addAirResistance() {
		this.dy *= 0.98;
		this.dx *= 0.98;
	}
	
	public void Move() {
		Ellipse2D e = this.ellipse;
		this.ellipse = new Ellipse2D.Double(e.getMinX() + dx, e.getMinY() + dy, e.getWidth(), e.getHeight());
		this.addGravity();
		this.addAirResistance();
	}
	
	
	public void draw(Graphics2D g) {
		Point2D center = new Point2D.Double(this.ellipse.getCenterX(), this.ellipse.getCenterY());
		Color[] colors = {this.color, new Color(0, 0, 0, 0)};
		float[] dist = {0.0f, 1.0f};
		float radius = (float)(this.ellipse.getWidth() / 2);
		RadialGradientPaint rg = new RadialGradientPaint(center, radius, dist, colors, CycleMethod.NO_CYCLE);
		//g.setColor(this.color);
		g.setPaint(rg);
		g.fill(this.ellipse);
	}
	
}
