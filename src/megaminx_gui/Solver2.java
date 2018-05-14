package megaminx_gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Solver2 extends JFrame {
	private int radius = 85;

	private static final long serialVersionUID = -3119930909029998588L;
	private Image ScreenImage;
	private Graphics ScreenGraphic;

	private Image Background;
	// private Image Minxframe;
	private Image[][] Minxpiece_up = new Image[13][10];
	private Image[][] Minxpiece_down = new Image[13][10];
	// private int xpoints[] = {5,100,100,5,5};
	// private int ypoints[] = {5,5,100,100,5};
	// private int npoints = 5;

	public void paint(Graphics g) {
		ScreenImage = createImage(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
		ScreenGraphic = ScreenImage.getGraphics();
		screenDraw(ScreenGraphic);
		g.drawImage(ScreenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(Background, 0, 0, null);
		// g.drawImage(Minxframe, 0, 0, null);
		// if(int [][] cube = 1 1 cube) {
		// g.drawImage(Minxpiece_up[1][], 0, 0, null);
		// };

		int radius = 85;
		int centerX = 500;
		int centerY = 300;
		double R = radius * Math.cos(Math.PI / 5);
		Polygon frames[] = new Polygon[6];
		for(int i=0;i<6;i++)
			frames[i] = new Polygon();
		for (int j = 0; j < 5; j++)
			frames[5].addPoint((int) (centerX - radius * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY - radius * Math.cos(2 * j * Math.PI / 5)));
		for (int i = 0; i < 5; i++)
			for(int j = 0;j < 5; j++)
			frames[i].addPoint((int) (centerX - R * Math.sin(2 * i * Math.PI / 5)-radius*Math.sin(2*(i+j)*Math.PI/5)),
					(int) (centerY + R * Math.cos(2 * i * Math.PI / 5)+radius*Math.cos(2*(i+j)*Math.PI/5)));
		
		for(int i=0;i<6;i++)
			g.fillPolygon(frames[i]);
		// Polygon p = new Polygon();
		// for (int i = 0; i < 5; i++)
		// p.addPoint((int) (680 + 85 * Math.sin(i * 2 * Math.PI / 5)),
		// (int) (244 - 85 * Math.cos(i * 2 * Math.PI / 5)));

		// g.drawPolygon(p);

		// Polygon q = new Polygon();
		// for (int i = 0; i < 5; i++)
		// q.addPoint((int) (762 + 85 * Math.sin(i * 2 * Math.PI / 5)),
		// (int) (130 + 85 * Math.cos(i * 2 * Math.PI / 5)));

		// g.drawPolygon(q);

		// Polygon r = new Polygon();
		// for (int i = 0; i < 5; i++)
		// r.addPoint((int) (597 + 85 * Math.sin(i * 2 * Math.PI / 5)),
		// (int) (130 + 85 * Math.cos(i * 2 * Math.PI / 5)));

		// g.drawPolygon(r);

		// int[] xp1 = {245, 273, 300, 273};
		// int[] yp1 = {85, 65, 85, 105};

		// g.fillPolygon(xp1, yp1, xp1.length);

		// int[] xp2 = {150, 168, 140, 168};
		// int[] yp2 = {165, 145, 165, 185};

		// g.fillPolygon(xp2, yp2, xp2.length);

		// int[] xp3 = {130, 170, 100};
		// int[] yp3 = {50, 90, 120};

		// g.fillPolygon(xp3, yp3, xp3.length);

		this.repaint();
	}

	public Solver2() {
		setTitle("Megaminx Solver v1 by MolotovCocktail");
		setSize(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Background = new ImageIcon(Inter.class.getResource("../images/Background.png")).getImage();
		// Minxframe = new
		// ImageIcon(Inter.class.getResource("../images/megaminxframe.png")).getImage();

	}

}

/**
 * 
 */
