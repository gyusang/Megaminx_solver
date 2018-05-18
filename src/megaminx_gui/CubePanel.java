package megaminx_gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import megaminx_move.Megaminx;

public class CubePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private int[][] cube = {
			{0,1,2,3,4,5,6,7,8,9,10},
			{1,1,1,1,1,1,1,1,1,1,1},
			{2,2,2,2,2,2,2,2,2,2,2},
			{3,3,3,3,3,3,3,3,3,3,3},
			{4,4,4,4,4,4,4,4,4,4,4},
			{5,5,5,5,5,5,5,5,5,5,5},
			{6,6,6,6,6,6,6,6,6,6,6},
			{7,7,7,7,7,7,7,7,7,7,7},
			{8,8,8,8,8,8,8,8,8,8,8},
			{9,9,9,9,9,9,9,9,9,9,9},
			{10,10,10,10,10,10,10,10,10,10,10},
			{11,11,11,11,11,11,11,11,11,11,11},
			{12,12,12,12,12,12,12,12,12,12,12}
	};
	
	protected Color[] colors = new Color[13];

	private Point centerPoints[] = new Point[13];
	private Polygon frames[] = new Polygon[13];
	private Polygon center[] = new Polygon[13];
	private Polygon blocks[][] = new Polygon[13][10];
	
	private BufferedImage frame_image;
	
	private int frame_img_x, frame_img_y;
	
	public CubePanel(int radius) {
		setLayout(new BorderLayout());
		init_Polygons(radius,0.4);
		setPreferredSize(new Dimension(frame_img_x,frame_img_y));
		setOpaque(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int btn = e.getButton();
				int direction;
				switch(btn) {
				case MouseEvent.BUTTON1:
					direction = 1;
					break;
				case MouseEvent.BUTTON3:
					direction = -1;
					break;
				default : 
					direction = 0;
					return;
				}
				Point clickPoint = e.getPoint();
//				System.out.println(clickPoint);
				for(int i=1;i<=12;i++) {
					if(center[i].contains(clickPoint)) {
						rotateCube(direction*i);
						System.out.print(direction*i+" ");
						break;
					}
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawCube(g);
	}
	
	public void setCube(int [][] cube) {
		this.cube = cube;
	}
	
	public void rotateCube(int []faces) {
		cube = Megaminx.rotate(cube, faces);
		repaint();
	}
	
	public void rotateCube(int face) {
		int[] faces = {face};
		rotateCube(faces);
	}
	
	public void drawCube(Graphics g) {
//		if(background_image!=null)
//			g.drawImage(background_image, 0, 0, null);
		for (int i = 1; i <= 12; i++) {
			g.setColor(colors[cube[i][10]]);
			g.fillPolygon(center[i]);
			g.setColor(colors[0]);
			//TODO write text
			for (int j = 0; j < 10; j++) {
				g.setColor(colors[(cube[i][j])]);
				g.fillPolygon(blocks[i][j]);
			}
		}
		
		if(frame_image==null) {
			frame_image = new BufferedImage(frame_img_x,frame_img_y,BufferedImage.TYPE_INT_ARGB);
			Graphics frame_graphic =  frame_image.getGraphics();
			drawFrame(frame_graphic);
		}
		g.drawImage(frame_image, 0, 0, null);
	}
	
	public void drawFrame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(colors[0]);
		for (int i = 1; i <= 12; i++) {
			g2.setStroke(new BasicStroke(3));
			for (int j = 0; j < 10; j++)
				g2.drawPolygon(blocks[i][j]);
			g2.setStroke(new BasicStroke(5));
			g2.drawPolygon(frames[i]);
		}
	}
	
	private void init_Polygons(int radius, double ratio) {
		int[][][] in_coord = new int[13][2][5]; // (face no. 1 to 12) (x or y => x:0, y:1) (five coords)
		int[][][] out_coord = new int[13][2][15];
		int diff[] = { 0, 4, 3, 4, 0, 1, 2, 4, 0, 1, 2, 3, 2 };
		
		double p = Math.tan(Math.PI*3/10)/Math.tan(Math.PI*2/5);
		if (ratio > 1)
			ratio = 1;
		else if (ratio < p/(1+p))
			ratio = (int)Math.round(p/(1+p));
		
		// Black White Purple DarkYellow DarkBlue Red DarkGreen LightGreen Orange LightBlue LightYellow Pink Gray
		colors[0] = new Color(0, 0, 0);
		colors[1] = new Color(255, 255, 255);
		colors[2] = new Color(210, 0, 255);
		colors[3] = new Color(246, 255, 0);
		colors[4] = new Color(6, 0, 255);
		colors[5] = new Color(255, 0, 0);
		colors[6] = new Color(0, 255, 6);
		colors[7] = new Color(107, 255, 110);
		colors[8] = new Color(255, 127, 0);
		colors[9] = new Color(0, 246, 255);
		colors[10] = new Color(249, 228, 170);
		colors[11] = new Color(246, 21, 138);
		colors[12] = new Color(132, 132, 132);

		int R = (int) (2 * radius * Math.cos(Math.PI / 5));
		int r = (int) (radius * ratio);
		
		frame_img_x = (int)((5*R+2*radius)*Math.sin(3 * Math.PI / 5)+20);
		frame_img_y = (int)((R+radius)*Math.cos(Math.PI/5)+R+radius+20);
		
		ratio = ((1-p)+ratio*(1+p))/2;
		int centerX = (int) ((R +radius)*Math.sin(3 * Math.PI / 5)+10);
		int centerY = (int) (R+radius+10);
		
		
		int i, j;

		for (j = 0; j < 5; j++) {
			out_coord[1][0][3 * (j + diff[1]) % 15] = (int) (centerX - radius * Math.sin(2 * j * Math.PI / 5));
			out_coord[1][1][3 * (j + diff[1]) % 15] = (int) (centerY + radius * Math.cos(2 * j * Math.PI / 5));
			in_coord[1][0][(j + diff[1]) % 5] = (int) (centerX - r * Math.sin(2 * j * Math.PI / 5));
			in_coord[1][1][(j + diff[1]) % 5] = (int) (centerY + r * Math.cos(2 * j * Math.PI / 5));
		}
		
		centerPoints[1] = new Point(centerX,centerY);

		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++) {
				out_coord[6 - i][0][3 * (5 - j + diff[6 - i]) % 15] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
						- radius * Math.sin(2 * (i + j) * Math.PI / 5));
				out_coord[6 - i][1][3 * (5 - j + diff[6 - i]) % 15] = (int) (centerY - R * Math.cos(2 * i * Math.PI / 5)
						- radius * Math.cos(2 * (i + j) * Math.PI / 5));
				in_coord[6 - i][0][(5 - j + diff[6 - i]) % 5] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
						- r * Math.sin(2 * (i + j) * Math.PI / 5));
				in_coord[6 - i][1][(5 - j + diff[6 - i]) % 5] = (int) (centerY - R * Math.cos(2 * i * Math.PI / 5)
						- r * Math.cos(2 * (i + j) * Math.PI / 5));
			}
			centerPoints[6-i] = new Point((int)(centerX - R * Math.sin(2 * i * Math.PI / 5)),(int)(centerY - R * Math.cos(2 * i * Math.PI / 5)));
		}

		centerX += (int) (3 * R * Math.sin(3 * Math.PI / 5));
		centerY += (int) (-R * Math.cos(2 * Math.PI / 5));
		for (j = 0; j < 5; j++) {
			out_coord[12][0][3 * (5 - j + diff[12]) % 15] = (int) (centerX - radius * Math.sin(2 * j * Math.PI / 5));
			out_coord[12][1][3 * (5 - j + diff[12]) % 15] = (int) (centerY - radius * Math.cos(2 * j * Math.PI / 5));
			in_coord[12][0][(5 - j + diff[12]) % 5] = (int) (centerX - r * Math.sin(2 * j * Math.PI / 5));
			in_coord[12][1][(5 - j + diff[12]) % 5] = (int) (centerY - r * Math.cos(2 * j * Math.PI / 5));
		}
		centerPoints[12] = new Point(centerX,centerY);
		
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++) {
				out_coord[7 + i][0][3 * (j + diff[7 + i]) % 15] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
						- radius * Math.sin(2 * (i + j) * Math.PI / 5));
				out_coord[7 + i][1][3 * (j + diff[7 + i]) % 15] = (int) (centerY + R * Math.cos(2 * i * Math.PI / 5)
						+ radius * Math.cos(2 * (i + j) * Math.PI / 5));
				in_coord[7 + i][0][(j + diff[7 + i]) % 5] = (int) (centerX - R * Math.sin(2 * i * Math.PI / 5)
						- r * Math.sin(2 * (i + j) * Math.PI / 5));
				in_coord[7 + i][1][(j + diff[7 + i]) % 5] = (int) (centerY + R * Math.cos(2 * i * Math.PI / 5)
						+ r * Math.cos(2 * (i + j) * Math.PI / 5));
			}
			centerPoints[7+i] = new Point((int)(centerX - R * Math.sin(2 * i * Math.PI / 5)),(int)(centerY + R * Math.cos(2 * i * Math.PI / 5)));
		}

		for (i = 1; i <= 12; i++) {
			for (j = 0; j < 5; j++) {
				for (int k = 0; k < 2; k++) {
					out_coord[i][k][3 * j + 1] = (int) (ratio * out_coord[i][k][3 * j]
							+ (1 - ratio) * out_coord[i][k][3 * (j + 1) % 15]);
					out_coord[i][k][3 * j + 2] = (int) ((1 - ratio) * out_coord[i][k][3 * j]
							+ ratio * out_coord[i][k][3 * (j + 1) % 15]);
				}
			}
		}
		for (i = 1; i <= 12; i++) {
			frames[i] = new Polygon();
			center[i] = new Polygon(in_coord[i][0], in_coord[i][1], 5);

			for (j = 0; j < 5; j++) {
				frames[i].addPoint(out_coord[i][0][3 * j], out_coord[i][1][3 * j]);
				blocks[i][2 * j] = new Polygon(); // Edge pieces
				for (int k = 13; k <= 14; k++)
					blocks[i][2 * j].addPoint(out_coord[i][0][(3 * j + k) % 15], out_coord[i][1][(3 * j + k) % 15]);

				for (int k = 5; k >= 4; k--)
					blocks[i][2 * j].addPoint(in_coord[i][0][(j + k) % 5], in_coord[i][1][(j + k) % 5]);

				blocks[i][2 * j + 1] = new Polygon(); // Corner pieces
				for (int k = 14; k <= 16; k++)
					blocks[i][2 * j + 1].addPoint(out_coord[i][0][(3 * j + k) % 15], out_coord[i][1][(3 * j + k) % 15]);
				blocks[i][2 * j + 1].addPoint(in_coord[i][0][j], in_coord[i][1][j]);
			}
		}
	}
	
}

