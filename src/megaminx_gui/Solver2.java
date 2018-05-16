package megaminx_gui;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Solver2 extends JFrame {
	private static final long serialVersionUID = -3119930909029998588L;
	
	int radius,centerX,centerY;
	double ratio,R,r;
	private Image ScreenImage;
	private Graphics ScreenGraphic;
	private Image Background;
	
	Color[] colors= new Color[13];
	int[][][] in_coord = new int[13][2][5]; //(face no. 1 to 12) (x or y => x:0, y:1) (five coords)
	int[][][] out_coord = new int[13][2][10];
	Polygon frames[] = new Polygon[13];
	Polygon center[] = new Polygon[13];
	Polygon blocks[][] = new Polygon[13][10];
	
	@Override
	public void paint(Graphics g) {
		ScreenImage = createImage(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
		ScreenGraphic = ScreenImage.getGraphics();
		screenDraw(ScreenGraphic);
		g.drawImage(ScreenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(Background, 0, 0, null);	
		
		for (int i = 1; i <= 12; i++) {
			g.setColor(colors[1]);
			g.fillPolygon(frames[i]);
			g.setColor(colors[i]);
			g.fillPolygon(center[i]);
		}
		for (int i = 1; i <= 12; i++) {
			g2.setColor(colors[0]);
			g2.setStroke(new BasicStroke(8));
			g2.drawPolygon(frames[i]);
		}

		// int[] xp3 = {130, 170, 100};
		// int[] yp3 = {50, 90, 120};

		// g.fillPolygon(xp3, yp3, xp3.length);

		this.repaint();
	}

	public Solver2(int SCREEN_WIDTH,int SCREEN_HEIGHT, int RADIUS, double RATIO) {
		radius = RADIUS;
		ratio = RATIO;
		Background = new ImageIcon(Inter.class.getResource("../images/Background.png")).getImage();
		
		colors[0] = new Color(0,0,0);
		//White Purple DarkYellow DarkBlue Red DarkGreen LightGreen Orange LightBlue LightYellow Pink Gray
		colors[1] = new Color(255, 255, 255);
		colors[2] = new Color(210,0,255);
		colors[3] = new Color(246,255,0);
		colors[4] = new Color(6,0,255);;
		colors[5] = new Color(255,0,0);
		colors[6] = new Color(0,255,6);
		colors[7] = new Color(107,255,110); 
		colors[8] = new Color(255,127,0);
		colors[9] = new Color(0,246,255);
		colors[10] = new Color(249,228,170);
		colors[11] = new Color(246,21,138);
		colors[12] = new Color(132,132,132);
		
		R = 2 * radius * Math.cos(Math.PI / 5);
		r = radius*ratio;
		centerX = (int) Math.round(2 * R);
		centerY = (int) Math.round(2 * R);
		
/** Soon to be replaced*/
		for (int i = 1; i <= 12; i++) {
			frames[i] = new Polygon();
			center[i] = new Polygon();
			for(int j=0;j<10;j++) {
				blocks[i][j] = new Polygon();
			}
		}
/** Soon to be replaced*/
		
		centerX = (int) Math.round(2 * R);
		centerY = (int) Math.round(2 * R);
		
		for (int j = 0; j < 5; j++) {
			frames[1].addPoint((int) (centerX - radius * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY + radius * Math.cos(2 * j * Math.PI / 5)));
			in_coord[1][0][j] = (int) (centerX - r * Math.sin(2 * j * Math.PI / 5));
			in_coord[1][1][j] = (int) (centerY + r * Math.cos(2 * j * Math.PI / 5));
		}
		
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				frames[6-i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- radius * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY - R * Math.cos(2 * i * Math.PI / 5)
								- radius * Math.cos(2 * (i + j) * Math.PI / 5)));
				in_coord[6-i][0][j] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5) - r * Math.sin(2 * (i + j) * Math.PI / 5));
				in_coord[6-i][1][j] = (int) (centerY - R * Math.cos(2 * i * Math.PI / 5) - r * Math.cos(2 * (i + j) * Math.PI / 5));
			}
		
		centerX += 3 * R * Math.sin(3 * Math.PI / 5);
		centerY += -R * Math.cos(2 * Math.PI / 5);
		for (int j = 0; j < 5; j++) {
			frames[12].addPoint((int) (centerX - radius * Math.sin(2 * j * Math.PI / 5)),
					(int) (centerY - radius * Math.cos(2 * j * Math.PI / 5)));
			in_coord[12][0][j] = (int) (centerX - r * Math.sin(2 * j * Math.PI / 5));
			in_coord[12][1][j] = (int) (centerY - r * Math.cos(2 * j * Math.PI / 5));
		}

		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				frames[7 + i].addPoint(
						(int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
								- radius * Math.sin(2 * (i + j) * Math.PI / 5)),
						(int) (centerY + R * Math.cos(2 * i * Math.PI / 5)
								+ radius * Math.cos(2 * (i + j) * Math.PI / 5)));
				in_coord[7+i][0][j] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5) - r * Math.sin(2 * (i + j) * Math.PI / 5));
				in_coord[7+i][1][j] = (int) (centerY + R * Math.cos(2 * i * Math.PI / 5) + r * Math.cos(2 * (i + j) * Math.PI / 5));
			}
		
		for (int i = 1; i <= 12; i++) {
			//frames[i] = new Polygon();
			center[i] = new Polygon(in_coord[i][0],in_coord[i][1],5);
			for(int j=0;j<10;j++) {
				//blocks[i][j] = new Polygon();
			}
		}
		
		setTitle("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public Solver2() {
		this(Inter.SCREEN_WIDTH,Inter.SCREEN_HEIGHT,Inter.RADIUS,Inter.RATIO);
	}
}

/**
 * 
 */
