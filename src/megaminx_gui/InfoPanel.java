package megaminx_gui;

import java.awt.*;
import javax.swing.*;

public class InfoPanel extends JPanel{
	private static final long serialVersionUID = 10L;

	private JLabel cube_seq;
	
	public InfoPanel() {
		setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		setPreferredSize(new Dimension(1000,198));
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(50,10)));
		cube_seq= new JLabel("WELCOME");
		add(cube_seq);
		
	}
	
	public void setSeq(String text) {
		cube_seq.setText(text);
	}
}
