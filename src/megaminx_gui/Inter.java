package megaminx_gui;

import javax.swing.SwingUtilities;

public class Inter {
	public static final int RADIUS = 120;
	
	public static void main(String[] args) {
		
		
		
		System.out.println();
		
		SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
            	new SolverFrame(RADIUS);
            }
        });
		

		
	}

}

