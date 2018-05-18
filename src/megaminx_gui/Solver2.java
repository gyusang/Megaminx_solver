package megaminx_gui;

import java.awt.*;

import javax.swing.*;

public class Solver2 extends JFrame {
	
	private static final long serialVersionUID = 2L;
	
	public boolean ready = false;
	
	private CubePanel cubeGraphic;
	
	public Solver2(final int RADIUS) {
		super("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
//		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//		setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//		setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.GREEN);
		cubeGraphic = new CubePanel(RADIUS);
		add(BorderLayout.CENTER,cubeGraphic);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setCube(int[][] cube) {
		cubeGraphic.setCube(cube);
	}
	
	public void rotateCube(int face) {
		cubeGraphic.rotateCube(face);
	}
	
	public void rotateCube(int[] faces) {
		cubeGraphic.rotateCube(faces);
	}

}