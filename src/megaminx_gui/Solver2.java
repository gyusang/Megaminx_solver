package megaminx_gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Solver2 extends JFrame {
	
	private static final long serialVersionUID = 2L;
	
	public boolean ready = false;
	
	private CubePanel cubeGraphic;
	
	public Solver2(final int RADIUS) {
		super("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		BufferedImage img=null;
		try {
		img = ImageIO.read(Solver2.class.getResource("../images/Background.png"));
		} catch (IOException e) {
			e.printStackTrace();
			getContentPane().setBackground(Color.WHITE);
		}
		if(img!=null) {
			setContentPane(new JLabel(new ImageIcon(img)));
		}
		setLayout(new BorderLayout());
		cubeGraphic = new CubePanel(RADIUS);
		getContentPane().add(cubeGraphic,BorderLayout.CENTER);
		JPanel controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(305,500));
		controlPanel.setOpaque(false);
		getContentPane().add(controlPanel, BorderLayout.LINE_END);
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(1000,198));
		infoPanel.setOpaque(false);
		getContentPane().add(infoPanel, BorderLayout.PAGE_END);
		
		setSize(1280,742);
		setPreferredSize(getSize());
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setResizable(false);
		setLocationRelativeTo(null);
		
		setVisible(true);
		System.out.println(getSize());
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