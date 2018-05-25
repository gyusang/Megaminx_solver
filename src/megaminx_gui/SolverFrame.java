package megaminx_gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.net.URL;

public class SolverFrame extends JFrame {
	
	private static final long serialVersionUID = 2L;
	
	public boolean ready = false;
	
	private CubePanel cubeGraphic;
	
	public SolverFrame(final int RADIUS) {
		super("Megaminx Solver v1 by MolotovCocktail and Sanggyu Lee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		
		setLayout(new BorderLayout());
		cubeGraphic = new CubePanel(RADIUS);
		getContentPane().add(cubeGraphic,BorderLayout.CENTER);
		InfoPanel info = new InfoPanel();
		getContentPane().add(info, BorderLayout.PAGE_START);
		ControlPanel control = new ControlPanel(cubeGraphic,info);
		getContentPane().add(control, BorderLayout.LINE_END);
		
		pack();
		BufferedImage img=null;
		URL url = SolverFrame.class.getResource("../images/Background.png");
		if(url!=null) {
			try{
				img = ImageIO.read(url);
			} catch(IOException e) {
				e.printStackTrace();
			}
			setContentPane(new JLabel(new ImageIcon(img.getScaledInstance(getSize().width,getSize().height, Image.SCALE_SMOOTH))));
			getContentPane().add(cubeGraphic,BorderLayout.CENTER);
			getContentPane().add(info, BorderLayout.PAGE_END);
			getContentPane().add(control, BorderLayout.LINE_END);
		} else {
			getContentPane().setBackground(Color.WHITE);
		}
		setPreferredSize(getSize());
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		setResizable(false);
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