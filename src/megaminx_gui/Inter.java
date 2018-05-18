package megaminx_gui;

import java.util.Random;

public class Inter {
	
//	public static final int SCREEN_WIDTH = 1280;
//	public static final int SCREEN_HEIGHT = 720;
	public static final int RADIUS = 100;
	
	public static void main(String[] args) {
		Random generator = new Random();
		
		int[] moves = new int[20];
		for(int i=0;i<20;) {
			moves[i] = generator.nextInt(25)-12;
			if(moves[i]==0) continue;
			else if(i>0)
				if(moves[i]==-moves[i-1]) continue;
			System.out.print(moves[i]+", ");
			i++;
		}
		System.out.println();
		Solver2 slv = new Solver2(RADIUS);
		slv.rotateCube(moves);
	}

}
