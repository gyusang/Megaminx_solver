package megaminx_gui;

import javax.swing.JFrame;


public class Solver extends JFrame{

		public Solver() {
			setTitle("Megaminx Solver v1");
			setSize(Inter.SCREEN_WIDTH, Inter.SCREEN_HEIGHT);
			setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
		
}
