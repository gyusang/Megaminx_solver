package megaminx_gui;

import java.awt.*;
import javax.swing.*;

public class InfoPanel extends JPanel{
	private static final long serialVersionUID = 10L;

	private JLabel cube_seq;
	private JLabel solving;
	
	public InfoPanel() {
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
//		setPreferredSize(new Dimension(1000,198));
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(20,10)));
		cube_seq= new JLabel("WELCOME",JLabel.LEFT);
		add(cube_seq);
		add(Box.createRigidArea(new Dimension(1,5)));
		solving = new JLabel("This is Solver",JLabel.LEFT);
		add(solving);
		
	}
	
	public void setSeq(String text) {
		cube_seq.setText(text);
		System.out.println(text);
	}
	
	public void setSolving(String text) {
		solving.setText(text);
		System.out.println(text);
	}
}
