package megaminx_gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Solver2 extends JFrame {
	private static final long serialVersionUID = -3119930909029998588L;
	
	int radius;
	private Image ScreenImage;
	private Graphics ScreenGraphic;
	private Image Background;
	
	Color[] colors= new Color[13];
	
	@Override
	public void paint(Graphics g) {
		ScreenImage = createImage(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
		ScreenGraphic = ScreenImage.getGraphics();
		screenDraw(ScreenGraphic);
		g.drawImage(ScreenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(Background, 0, 0, null);	
		
		double R = 2 * radius * Math.cos(Math.PI / 5);
		double r = radius*3/7;
		int centerX = (int) Math.round(2 * R);
		int centerY = (int) Math.round(2 * R);
		Polygon frames[] = new Polygon[13];
		Polygon in_frames[] = new Polygon[13];
		for (int i = 1; i <= 12; i++) {
			frames[i] = new Polygon();
			in_frames[i] = new Polygon();
		}

		for (int j = 0; j < 5; j++) {
			frames[1].addPoint((int) (centerX - radius * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY + radius * Math.cos(2 * j * Math.PI / 5)));
			in_frames[1].addPoint((int) (centerX - r * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY + r * Math.cos(2 * j * Math.PI / 5)));
		}
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				frames[6-i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- radius * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY - R * Math.cos(2 * i * Math.PI / 5)
								- radius * Math.cos(2 * (i + j) * Math.PI / 5)));
				in_frames[6-i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- r * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY - R * Math.cos(2 * i * Math.PI / 5)
								- r * Math.cos(2 * (i + j) * Math.PI / 5)));
			}
		centerX += 3 * R * Math.sin(3 * Math.PI / 5);
		centerY += -R * Math.cos(2 * Math.PI / 5);
		for (int j = 0; j < 5; j++) {
			frames[12].addPoint((int) (centerX - radius * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY - radius * Math.cos(2 * j * Math.PI / 5)));
			in_frames[12].addPoint((int) (centerX - r * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY - r * Math.cos(2 * j * Math.PI / 5)));
		}

		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				frames[7 + i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- radius * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY + R * Math.cos(2 * i * Math.PI / 5)
								+ radius * Math.cos(2 * (i + j) * Math.PI / 5)));
				in_frames[7 + i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- r * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY + R * Math.cos(2 * i * Math.PI / 5)
								+ r * Math.cos(2 * (i + j) * Math.PI / 5)));
			}

		for (int i = 1; i <= 12; i++) {
			g.setColor(colors[1]);
			g.fillPolygon(frames[i]);
			g.setColor(colors[i]);
			g.fillPolygon(in_frames[i]);
		}
		for (int i = 1; i <= 12; i++) {
			g.setColor(colors[0]);
			g.drawPolygon(frames[i]);
		}
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
		radius = Inter.RADIUS;
		Background = new ImageIcon(Inter.class.getResource("../images/Background.png")).getImage();
		
		// ImageIcon(Inter.class.getResource("../images/megaminxframe.png")).getImage();
		colors[0] = new Color(0,0,0);
		//White Purple DarkYellow DarkBlue Red DarkGreen LightGreen Orange LightBlue LightYellow Pink Gray
		colors[1] = new Color(255, 255, 255);
		colors[2] = new Color(210,0,255); //TODO purple
		colors[3] = new Color(246,255,0);
		colors[4] = new Color(6,0,255);;
		colors[5] = new Color(255,0,0);
		colors[6] = new Color(0,255,6);//TODO darkgreen
		colors[7] = new Color(107,255,110); 
		colors[8] = new Color(255,127,0);
		colors[9] = new Color(0,246,255);
		colors[10] = new Color(249,228,170); //TODO lightyellow
		colors[11] = new Color(246,21,138);
		colors[12] = new Color(132,132,132);
	}

}

/**
 * 
 */
