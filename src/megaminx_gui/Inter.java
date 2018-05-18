package megaminx_gui;

import java.awt.*;
import javax.swing.*;

public class Inter {
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int RADIUS = 100;
	public static int [][] cube = new int[13][11];
	
	public static void main(String[] args) {
		for(int j=0;j<=10;j++)
			cube[0][j] = j;
		for(int i=1;i<=12;i++)
			for(int j=0;j<=10;j++)
				cube[i][j] = i;
//		Solver2 slv = new Solver2(SCREEN_WIDTH,SCREEN_HEIGHT,RADIUS,cube);
//		slv.repaint();
		JFrame frame = new JFrame("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
		frame.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		CubePanel cubeGraphic = new CubePanel(RADIUS);
		cubeGraphic.setCube(cube);
		cubeGraphic.setBounds(100,100,700,600);
		frame.add(BorderLayout.CENTER,cubeGraphic);
		frame.setVisible(true);
		for(int i=0;i<=13*60*5000;i++)
		cubeGraphic.rotateCube(i%13);
	}

}
