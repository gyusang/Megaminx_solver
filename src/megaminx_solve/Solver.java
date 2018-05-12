package megaminx_solve;
import megaminx_move.Megaminx;
import java.util.Random;

public class Solver {
	int N; //number of chromosome
	int L; //length of one chromosome
	int [][] cube;
	int[][] genes;
	int[][] next_genes;
	int[] fitness;
	Random generator;
	
	public Solver(int N,int L, int[][] cube) {
		this.N = N;
		this.L = L;
		this.cube = cube;
		fitness = new int[N];
		next_genes = new int [N][L];
		genes = new int [N][L];
		generator = new Random();
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
			this.fitness[i] = Megaminx.fitness_cal(cube,genes[i]);
		}
	}

}
