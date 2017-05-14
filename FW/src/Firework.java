import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Firework {
	
	private long launchDelay, burstDelay;
	private ArrayList<Burst> bursts;
	private boolean launched, dead;
	private double angle, velocity, fuel;
	private Point2D position;
	
	public Firework(int launchDelay, int burstDelay, Burst... bursts) {
		this.bursts = new ArrayList<>();
		for(Burst burst : bursts) {
			this.bursts.add(burst);
		}
		this.position = (Point2D) Main.firingPoint.clone();
		this.launchDelay = launchDelay;
		this.burstDelay = burstDelay;
	}
	
	public boolean isLaunched() {
		return launched;
	}
	public boolean isDead() {
		return dead;
	}
	
	public boolean readyToFire() {
		if(Main.programStart + launchDelay < System.currentTimeMillis()) return true;
		return false;
	}
	
	public void launch() {
		//System.out.println("Launching!");
		this.velocity = 0;
		this.angle = Math.toRadians(90 + (Math.random() - 0.5) * 20);
		double vx = 0;
		double vy = -5;
		this.velocity = Math.sqrt(vx * vx + vy * vy);
		fuel = (Math.random() * 10) + 120;
		this.launched=true;
	}
	
	public void move() {
		if(this.fuel > 1) {
			double fuelConsumed = this.fuel / 100 + 1;
			this.velocity += fuelConsumed*0.2*Main.meter_pixels;
			fuel-=fuelConsumed;
			if(Math.random() > .6) this.createTrail();
		}
		double vx = this.velocity * Math.cos(this.angle);
		double vy = this.velocity * Math.sin(this.angle);
		this.position.setLocation(this.position.getX() + vx*Main.meter_pixels, this.position.getY() - vy*Main.meter_pixels);
		vy = this.addGravity(vy);
		this.angle = Math.atan2(vy, vx);
		this.velocity = Math.sqrt(vx * vx + vy * vy);
		if(Math.random() > .9) this.createTrail();
		if(System.currentTimeMillis() > Main.programStart + this.launchDelay + this.burstDelay) {
			for(Burst b : this.bursts) {
				b.activateBurst(this.position);
			}
			this.dead = true;
		}
	}
	
	public double addGravity(double vy) {
		return vy - (9.6 * Main.meter_pixels / 60);
	}
	
	
	public void createTrail() {
		Main.particles.add(new Particle(new Color(150, 150, 150, 255), 20, 4000, this.position));
	
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(new Ellipse2D.Double(this.position.getX() - 10, this.position.getY() - 10, 20, 20));
	}
}
