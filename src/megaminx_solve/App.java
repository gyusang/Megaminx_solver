package megaminx_solve;
import megaminx_move.Megaminx;
import megaminx_gui.SolverFrame;
import java.util.Random;


public class App {

	public static void main(String[] args) {
		final int RADIUS = 100;
		
		int N=100; //number of chromosome
		int L=200; //length of one chromosome

		int i,j;
		int[] moves = new int[20];
		int[][] cube = new int [13][11];
		Random generator = new Random();

		for(j=0;j<=10;j++)
			cube[0][j] = j;
		for(i=1;i<=12;i++)
			for(j=0;j<=10;j++)
				cube[i][j] = i;
		
		System.out.print("rotations : ");
		for(i=0;i<20;i++) {
			moves[i] = generator.nextInt(25)-12;
			System.out.print(moves[i]+", ");
		}
		System.out.println();
		cube = Megaminx.rotate(cube, moves);
		Megaminx.printCube(cube);
		Solver solver = new Solver(N,L,cube);
		solver.make_fitness();
		System.out.print("fitness : ");
		//for(i=0;i<N;i++) 
			System.out.print(+solver.fitness[i]+" ");
		
		System.out.println();
		SolverFrame slv = new SolverFrame(RADIUS);
		slv.setCube(cube);
/*
  		int fit,cnt=0;
  		int[] faces = {0};
  		for(j=0;j<100;j++) { //permorders for 100 random L length moves
			cnt=0;
		System.out.print("rotations : ");
		for(i=0;i<L;i++) {
			moves[i] = generator.nextInt(25)-12;
			System.out.print(moves[i]+", ");
		}
		System.out.println();
		
		do{
		cnt++;
		cube = Megaminx.rotate(cube,moves);
		fit =  Megaminx.fitness_cal(cube,faces);
		}while(fit>0);
		System.out.println(cnt + " iterations");
		}
*/
	}

}
