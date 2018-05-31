package megaminx_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import megaminx_move.Megaminx;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 20L;

	private static final int[][] solved_cube = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
			{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 }, { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
			{ 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 }, { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 },
			{ 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11 }, { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 } };

	public CubePanel cubeGraphic;
	public InfoPanel info;

	private Random generator;

	private boolean nowSolving = false;

	private JButton reset, mix, solve;

	private SASolver solver;
	
	private JTextField field_k,field_T,field_N,field_a;

	public ControlPanel(CubePanel cubeGraphic, InfoPanel info) {
		generator = new Random();
		this.cubeGraphic = cubeGraphic;
		this.info = info;
		// setPreferredSize(new Dimension(305,500));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		reset = new JButton("RESET");
		mix = new JButton("MIX");
		solve = new JButton("SOLVE");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cubeGraphic.setCube(ControlPanel.solved_cube);
				info.setSeq("NONE");
			}
		});
		mix.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cubeGraphic.setCube(ControlPanel.solved_cube);
				int[] moves = new int[30];
				String seq = "";
				for (int i = 0; i < moves.length;) {
					moves[i] = generator.nextInt(25) - 12;
					if (moves[i] == 0)
						continue;
					else if (i > 0) {
						if (moves[i] == -moves[i - 1])
							continue;
						seq = seq + ", " + moves[i];
					}
					if (i == 0)
						seq = seq + moves[i];
					i++;
				}
				cubeGraphic.rotateCube(moves);
				info.setSeq(seq);
			}
		});
		solve.addActionListener(new SolverListener());
		reset.setMnemonic('R');
		mix.setMnemonic('M');
		solve.setMnemonic('S');
		add(reset);
		add(mix);
		add(solve);
		add(Box.createRigidArea(new Dimension(50, 50)));
		add(new JLabel("k,T,N,a"));
		field_k = new JTextField(20);
		field_k.setText("1.0");
		field_T = new JTextField(20);
		field_T.setText("5000");
		field_N = new JTextField(20);
		field_N.setText("100");
		field_a = new JTextField(20);
		field_a.setText("0.05");
		add(field_k);
		add(field_T);
		add(field_N);
		add(field_a);
	}

	private class SolverListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int[][] cube = new int[13][11];
			cube = cubeGraphic.getCube();
			if (Megaminx.fitness_cal(cube) == 0) {
				info.setSolving("0");
				return;
			}
			if (!nowSolving) {
				int T,N;
				double a,k;
				try {
					T = Integer.parseInt(field_T.getText());
					N = Integer.parseInt(field_N.getText());
					a = Double.parseDouble(field_a.getText());
					k = Double.parseDouble(field_k.getText());
				} catch (java.lang.NumberFormatException nfe) {
					field_k.setText("1.0k");
					field_T.setText("5000T");
					field_N.setText("100N");
					field_a.setText("0.05a");
					return;
				}
				cubeGraphic.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				nowSolving = true;
				cubeGraphic.cube_Available = false;
				mix.setEnabled(false);
				reset.setEnabled(false);
				solve.setText("CANCEL");
				info.setSolving("Solving...");
				solver = new SASolver(T,N,a,k);
				solver.execute();
			} else {
				try {
					solver.cancel(false);
				} catch (CancellationException ex) {
				} finally {
					info.setSolving("CANCELED");
					solve.setText("SOLVE");
					cubeGraphic.setCursor(null);
					nowSolving = false;
					mix.setEnabled(true);
					reset.setEnabled(true);
					cubeGraphic.cube_Available = true;
				}
			}

		}
	}
	
/*
	private class GASolver extends SwingWorker<int[], int[]> {
		private int[][] cube;
		private final int N, L;// N 염색체수, 염색체 길이
		private int[][] genes, next_genes;
		private int[] fitness;

		public GASolver(int N, int L) {
			this.N = N;
			this.L = L;
			cube = new int[13][11];
			genes = new int[N][L];
			next_genes = new int[N][L];
			fitness = new int[N];
			fitness[0] = -1;
		}

		private void make_fitness() {
			for (int i = 0; i < N; i++) {
				fitness[i] = Megaminx.fitness_cal(cube, genes[i]);
			}
		}

		private void sort() {
			// TODO sort
		}

		private void generation() {
			// TODO one generation.
			make_fitness();
		}

		private void copyCube(int[][] src, int[][] dest) {
			for (int i = 1; i <= 12; i++) {
				dest[i] = Arrays.copyOf(src[i], 11);
			}
		}
		
		@Override
		protected int[] doInBackground() {
			System.out.println("Starting to work");
			cube = cubeGraphic.getCube();
			// Initialization.
			while (fitness[0] != 0 && !isCancelled()) {
				generation();
				publish(genes[0]);
			}
			return genes[0];
		}

		@Override
		protected void process(List<int[]> move) {
			cubeGraphic.setCube(Megaminx.rotate(cube, move.get(move.size() - 1)));
		}

		@Override
		public void done() {
			int[] solve_moves;
			try {
				solve_moves = get();
			} catch (InterruptedException | ExecutionException e) {
				return;
			}
			String seq = Integer.toString(solve_moves[0]);
			for (int i = 1; i < solve_moves.length; i++) {
				seq = seq + ", " + solve_moves[i];
			}
			info.setSolving(seq);
			solve.setText("SOLVE");
			cubeGraphic.rotateCube(solve_moves);
			cubeGraphic.setCursor(null);
			nowSolving = false;
			mix.setEnabled(true);
			reset.setEnabled(true);
			cubeGraphic.cube_Available = true;
		}
	}
*/

	private class SASolver extends SwingWorker<Integer, int[][]> {
		private final int T, N;// N 염색체수, 염색체 길이
		private final double a;
		private final double boltzmann;

		public SASolver(int temp, int num, double ratio, double boltzmann) {
			this.N = num;
			this.T = temp;
			this.a = ratio;
			this.boltzmann = boltzmann;
		}
		
		private void copyCube(int[][] src, int[][] dest) {
			for (int i = 1; i <= 12; i++) {
				dest[i] = Arrays.copyOf(src[i], 11);
			}
		}

		@Override
		protected Integer doInBackground() {
			int fit_x;
			ArrayList<Integer> all_moves = new ArrayList<Integer>();
			int[][] x,y=new int[13][11];
			int fit_y, delta, cnt = 0;
			double temp = T;
			int move,before = 0;
			x = cubeGraphic.getCube();
			fit_x = Megaminx.fitness_cal(x);
			info.setSolving("Initialized, fitness = "+ fit_x);

			while (fit_x > 0 && temp > 0.1 && !isCancelled()) {
				
				for (int j = 0; j < N; j++) {
					copyCube(x,y);
					move = newFace(before);
					Megaminx.rotate_save(y, move);
					fit_y = Megaminx.fitness_cal(y);
					delta = fit_y-fit_x;
					if (delta <= 0) {
						Megaminx.rotate_save(x, move);
						fit_x = fit_y;
						all_moves.add(move);
						before = move;
					} else if (Math.pow(Math.E, -delta/(temp*boltzmann))>generator.nextDouble()) {
						Megaminx.rotate_save(x, move);
						fit_x = fit_y;
						all_moves.add(move);
						before = move;
					}
					if(delta>0) {
					}
				}
				info.setSolving(++cnt + "th run, temperature = " + temp+", fitness = "+fit_x);
				publish(x);
				temp *= 1-a;
			}
			return all_moves.size();
		}

		private int newFace(int before) {
			int m;
			for (;;) {
				m = generator.nextInt(25) - 12;
				if (m == 0)
					continue;
				else if (m == -before)
					continue;
				else
					break;
			}
			return m;
		}

		@Override
		protected void process(List<int[][]> cubes) {
			cubeGraphic.setCube(cubes.get(cubes.size() - 1));
		}

		@Override
		public void done() {
			int length;
			try {
				length = get();
			} catch (InterruptedException | ExecutionException e) {
				return;
			}
			// String seq = Integer.toString(moves[0]);
			// for(int i=1;i<moves.length;i++) {
			// seq = seq +", " + moves[i];
			// }
			info.setSolving(length + " moves calculated.");
			solve.setText("SOLVE");
			cubeGraphic.setCursor(null);
			nowSolving = false;
			mix.setEnabled(true);
			reset.setEnabled(true);
			cubeGraphic.cube_Available = true;
		}
	}

}
