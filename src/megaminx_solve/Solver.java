package megaminx_solve;
import megaminx_move.Megaminx;

public class Solver {
	int N; //number of chromosome
	int L; //length of one chromosome
	int [][] cube;
	int[][] genes;
	int[][] next_genes;
	int[] fitness;
	Megaminx mover;
	
	public Solver(int N,int L, int[][] cube) {
		this.N = N;
		this.L = L;
		this.cube = cube;
		mover = new Megaminx();
		fitness = new int[N];
		next_genes = new int [N][L];
		genes = new int [N][L];
		//TODO randomize genes
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
			this.fitness[i] = mover.fitness_cal(cube,genes[i]);
		}
	}

}
