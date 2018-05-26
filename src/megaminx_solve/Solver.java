package megaminx_solve;
import megaminx_move.Megaminx;
import java.util.Random;
import megaminx_gui.*;
import java.util.concurrent.TimeUnit;

public class Solver {
	int N; //number of chromosome
	int L; //length of one chromosome
	int [][] cube;
	int[][] genes;
	int[][] next_genes;
	int[] fitness;
	Random generator;
	ControlPanel control;
	
	private static final int[][] solved_cube = {
			{0,1,2,3,4,5,6,7,8,9,10},
			{1,1,1,1,1,1,1,1,1,1,1},
			{2,2,2,2,2,2,2,2,2,2,2},
			{3,3,3,3,3,3,3,3,3,3,3},
			{4,4,4,4,4,4,4,4,4,4,4},
			{5,5,5,5,5,5,5,5,5,5,5},
			{6,6,6,6,6,6,6,6,6,6,6},
			{7,7,7,7,7,7,7,7,7,7,7},
			{8,8,8,8,8,8,8,8,8,8,8},
			{9,9,9,9,9,9,9,9,9,9,9},
			{10,10,10,10,10,10,10,10,10,10,10},
			{11,11,11,11,11,11,11,11,11,11,11},
			{12,12,12,12,12,12,12,12,12,12,12}
	};
	
	public Solver(int N,int L, ControlPanel control) {
		this.N = N;
		this.L = L;
		this.control = control;
		this.cube = control.cubeGraphic.getCube();
		fitness = new int[N];
		next_genes = new int [N][L];
		genes = new int [N][L];
		generator = new Random();
		//TODO randomize genes
		solve();
//		cubeGraphics.setCube(solved_cube);
	}
	
	public Solver(int N,int L, int[][] cube) {
		//TODO Temporary
	}
	
	public void sort() {
		//TODO sort genes 
	}
	
	public void selection(int cross, int mutate, int elite) {
		//TODO natural selection
	}
	
	public void make_fitness(){
		int i;
		for(i=0;i<N;i++) {
			this.fitness[i] = Megaminx.fitness_cal(cube,genes[i]);
		}
	}
	
	public void solve() {
		try {
			for(int i=0;i<=12*10;i++) {
				control.cubeGraphic.rotateCube(i%12+1);
				Thread.sleep(100);
			}
			control.cubeGraphic.setCube(solved_cube);
			control.info.setSolving("DONE"
					);
		} catch (InterruptedException e) { }
	}

}
