package megaminx_gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class Solver2 extends JFrame{
	
	private Image ScreenImage;
	private Graphics ScreenGraphic;
	
	private Image Background;
	private Image Minxframe;
	
	public void paint (Graphics g) {
		ScreenImage = createImage(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
		ScreenGraphic = ScreenImage.getGraphics();
		screenDraw(ScreenGraphic);
		g.drawImage(ScreenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(Background, 0, 0, null);
		g.drawImage(Minxframe, 0, 0, null);
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
		Minxframe = new ImageIcon(Inter.class.getResource("../images/megaminxframe.png")).getImage();	
	}
	
}
