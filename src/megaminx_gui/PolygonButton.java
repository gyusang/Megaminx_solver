package megaminx_gui;

import javax.swing.JButton;
import java.awt.*;

public class PolygonButton extends JButton{
	private static final long serialVersionUID = 7778319362503859987L;
	Polygon p;
	public PolygonButton(String name, Polygon p) {
		super(name);
		this.p = p;
	}
	@Override
	public void paintBorder(Graphics g) {
		g.drawPolygon(p);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.fillPolygon(p);
	}
	
	@Override
	public boolean contains(int x, int y) {
		return p.contains(x,y);
	}
}
