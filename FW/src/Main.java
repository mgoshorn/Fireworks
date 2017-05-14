import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main extends JPanel{
	public static final int ALLOWED_PARTICLES = 2500;
	public static final int FRAMES = 120;
	public static final long serialVersionUID = 1L;
	public static Dimension size;
	public static final int DELAY = 1000 / FRAMES;
	public static double meter_pixels;
	public static JFrame application;
	static ArrayList<Firework> fireworks = new ArrayList<>();
	static ArrayList<Particle> particles = new ArrayList<>();
	public static long programStart = System.currentTimeMillis();
	public static Point2D firingPoint;

	public void setupGraphics() {
		application = new JFrame();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.add(this);
		//application.setPreferredSize(new Dimension(1000, 1400));
		//application.setSize(2000, 1400);
		application.setExtendedState(JFrame.MAXIMIZED_BOTH);
		application.setUndecorated(true);
		application.setVisible(true);
		application.setFocusable(true);
		application.requestFocus();
		application.requestFocusInWindow();
		size = application.getSize();
		firingPoint = new Point2D.Double(size.getWidth()/2, size.getHeight());
		meter_pixels = size.getHeight() / 2160;
		System.out.println(meter_pixels);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(Particle parti : particles) {
			parti.draw(g2);
		}
	}

	public Main() {
		super();
		setBackground(Color.BLACK);
	}
	
	public static void recalculate() {
		ArrayList<Firework> deadFireworks = new ArrayList<>();
		for(Firework f : fireworks) {
			if(!f.isLaunched() && f.readyToFire() && particles.size() < ALLOWED_PARTICLES) {
				f.launch();
			}
			if(f.isLaunched()) {
				f.move();
			}
			if(f.isDead()) deadFireworks.add(f);
		}
		

		ArrayList<Particle> deadParticles = new ArrayList<>();
		for(Particle p : particles) {
			if(Math.random() > 0.5) p.updateFade();
			p.Move();
			if(p.isDead()) {
				deadParticles.add(p);
			}
		}
		for(Particle p : deadParticles) {
			particles.remove(p);
		}
		for(Firework f : deadFireworks) {
			fireworks.remove(f);
		}
	}
	
	public static void main(String[] args) {
		Main program = new Main();
		program.setupGraphics();
		fireworks.add(new Firework(0, 4000,
				new Burst(Color.RED, 25, 2),
				new Burst(Color.GREEN, 40, 4),
				new Burst(Color.BLUE, 37, 6),
				new Burst(Color.PINK, 50, 8)));

		fireworks.add(new Firework(1000, 4000, 
				new Burst(Color.BLUE, 10, 1),
				new Burst(Color.LIGHT_GRAY, 15, 2)));
		
		fireworks.add(new Firework(5000, 3000,
				new Burst(Color.YELLOW, 10, 1),
				new Burst(Color.YELLOW, 25, 2),
				new Burst(Color.YELLOW, 30, 3)));
		fireworks.add(new Firework(5050, 3100,
				new Burst(Color.RED, 10, 1),
				new Burst(Color.RED, 25, 2),
				new Burst(Color.RED, 30, 3)));
		fireworks.add(new Firework(5100, 3150,
				new Burst(Color.CYAN, 10, 1),
				new Burst(Color.CYAN, 25, 2),
				new Burst(Color.CYAN, 30, 3)));
		fireworks.add(new Firework(5150, 3250,
				new Burst(Color.PINK, 10, 1),
				new Burst(Color.PINK, 25, 2),
				new Burst(Color.PINK, 30, 3)));
		
		fireworks.add(new Firework(8000, 4100,
				new Burst(Color.RED, 25, 2),
				new Burst(Color.BLUE, 40, 4),
				new Burst(Color.RED, 37, 6),
				new Burst(Color.BLUE, 50, 8)));
		fireworks.add(new Firework(8100, 4000,
				new Burst(Color.GREEN, 25, 2),
				new Burst(Color.RED, 40, 4),
				new Burst(Color.GREEN, 37, 6),
				new Burst(Color.RED, 50, 8)));
		fireworks.add(new Firework(8300, 4300,
				new Burst(Color.PINK, 25, 2),
				new Burst(Color.MAGENTA, 40, 4),
				new Burst(Color.PINK, 37, 6),
				new Burst(Color.MAGENTA, 50, 8)));
		
		
		fireworks.add(new Firework(11000, 4000,
				new Burst(Color.BLUE, 50, 1),
				new Burst(Color.BLUE, 55, 3),
				new Burst(Color.GREEN, 60, 5),
				new Burst(Color.green, 65, 7)));
		fireworks.add(new Firework(11000, 4150,
				new Burst(Color.YELLOW, 50, 1),
				new Burst(Color.YELLOW, 55, 3),
				new Burst(Color.WHITE, 60, 5),
				new Burst(Color.WHITE, 65, 7)));
		
		for(int i = 11000; i < 100000; i += Math.random() * 2000) {
			int bursts = (int) (Math.random() * 7 + 1);
			Burst[] burstArray = new Burst[bursts];
			for(int x = 0; x < bursts; x++) {
				burstArray[x] = new Burst(new Color((int)(Math.random() * 255),(int)(Math.random() * 255),
						(int)(Math.random() * 255),(int)(Math.random() * 255)),
						(int)(Math.random() * 30 + 30), (int)(Math.random() * 3 + x));
			}
			fireworks.add(new Firework(i, (int)(Math.random() * 4000 + 3000), burstArray));
		}
		
		
		ActionListener repainter = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				recalculate();
				program.repaint();
			}
		};
		Timer paintingTimer = new Timer(DELAY, repainter);
		paintingTimer.start();
	}

}