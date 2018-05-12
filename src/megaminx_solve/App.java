package megaminx_solve;

public class App {

	public static void main(String[] args) {
		int N=100; //number of chromosome
		int L=200; //length of one chromosome
		int i;
		int[][] initial = {
				{1},
				{2},
				{3},
				{4},
				{5},
				{6},
				{7},
				{8},
				{9},
				{10},
				{11},
				{12}
		};
		Solver solver = new Solver(N,L,initial);
		solver.make_fitness();
		
		System.out.println("Solver Started");
		for(i=0;i<N;i++) {
			System.out.println(i+" "+solver.fitness[i]+" ");
		}
		
	}

}
