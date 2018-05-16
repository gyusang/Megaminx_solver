package megaminx_gui;
import megaminx_move.Megaminx;
import java.util.Random;

public class Inter {
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int RADIUS = 100;
	public static final double RATIO = 0.4;
	public static int [][] cube = new int[13][10];
	
	public static void main(String[] args) {
		Random generator = new Random();
		int rand = generator.nextInt(13);
		int faces[] = {rand};
		for(int j=0;j<10;j++)
			cube[0][j] = j;
		for(int i=1;i<=12;i++)
			for(int j=0;j<10;j++)
				cube[i][j] = i;
		cube = Megaminx.rotate(cube, faces);
		Solver2 slv = new Solver2(SCREEN_WIDTH,SCREEN_HEIGHT,RADIUS,RATIO,cube);
		slv.repaint();
	}

}
