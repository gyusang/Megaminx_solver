package megaminx_gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import megaminx_move.Megaminx;

public class Solver2 extends JFrame {
	
	private static final long serialVersionUID = 8308296838677592403L;
	int radius, centerX, centerY;
	double ratio, R, r;
	private Image ScreenImage;
	private Graphics ScreenGraphic;
	private Image Background;	
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private Point lastPoint;
	
	Color[] colors = new Color[13];
	JButton[] button = new JButton[13];
	int[][][] in_coord = new int[13][2][5]; // (face no. 1 to 12) (x or y => x:0, y:1) (five coords)
	int[][][] out_coord = new int[13][2][15];
	int[][] cube = new int[13][10];
	Polygon frames[] = new Polygon[13];
	Polygon center[] = new Polygon[13];
	Polygon blocks[][] = new Polygon[13][10];
	
	PolygonButton[] center_btn = new PolygonButton[13];
	
	public Solver2(final int SCREEN_WIDTH, final int SCREEN_HEIGHT, final int RADIUS, int[][] cube) {
		super("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
		System.arraycopy(cube, 0, this.cube, 0, cube.length);
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Background = new ImageIcon(Inter.class.getResource("../images/Background.png")).getImage();

		JPanel p = new JPanel() {
			private static final long serialVersionUID = -2714800288365993880L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
			}
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				ScreenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
				ScreenGraphic = ScreenImage.getGraphics();
				screenDraw(ScreenGraphic);
				g.drawImage(ScreenImage, 0, 0, null);
			}
		};

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				super.mouseClicked(me);
				int direction, btn = me.getButton();
				if(btn==MouseEvent.BUTTON1)
					direction = 1;
				else if(btn==MouseEvent.BUTTON3)
					direction = -1;
				else {
					direction = 0;
					return;
				}
				lastPoint = me.getPoint();
//				System.out.println(lastPoint);
				for(int i=1;i<=12;i++) {
					if(center[i].contains(lastPoint)) {
						rotateCube(direction*i);
						System.out.print(direction*i+" ");
						break;
					}
				}
				repaint();
			}
		};
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout());
		p.setBackground(Color.BLACK);
		p.setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
		p.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		p.addMouseListener(ma);
		add(p,BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	public Solver2() {
		this(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT, Inter.RADIUS, Inter.cube);
	}

	public void screenDraw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(Background, 0, 0, null);
		for (int i = 1; i <= 12; i++) {
			g.setColor(colors[i]);
			g.fillPolygon(center[i]);
			for (int j = 0; j < 10; j++) {
				g.setColor(colors[(cube[i][j])]);
				g.fillPolygon(blocks[i][j]);
			}
		}

		g2.setColor(colors[0]);
		for (int i = 1; i <= 12; i++) {
			g2.setStroke(new BasicStroke(3));
			for (int j = 0; j < 10; j++)
				g2.drawPolygon(blocks[i][j]);

			g2.setStroke(new BasicStroke(5));
			g2.drawPolygon(frames[i]);
		}
		if(lastPoint!=null)
			g.drawOval(lastPoint.x, lastPoint.y, 10, 10);
		
	}

	public void rotateCube(int face) {
		int[] faces = {face};
		cube = Megaminx.rotate(cube, faces);
		this.repaint();
	}
	
	public void updateCube(int [][] cube) {
		System.arraycopy(cube, 0, this.cube, 0, cube.length);
		this.repaint();
	}

}