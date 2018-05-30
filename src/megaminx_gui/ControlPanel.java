package megaminx_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.List;

import javax.swing.*;

import megaminx_move.Megaminx;

public class ControlPanel extends JPanel{
	private static final long serialVersionUID = 20L;
	
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
	
	public CubePanel cubeGraphic;
	public InfoPanel info;
	
	private Random generator;
	
	private boolean nowSolving = false;
	
	private JButton reset,mix,solve;
	
	private SolverTask solver;
	
	public ControlPanel(CubePanel cubeGraphic, InfoPanel info) {
		generator = new Random();
		this.cubeGraphic = cubeGraphic;
		this.info = info;
//		setPreferredSize(new Dimension(305,500));
		setOpaque(false);
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
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
				for(int i=0;i<moves.length;) {
					moves[i] = generator.nextInt(25)-12;
					if(moves[i]==0) continue;
					else if(i>0) {
						if(moves[i]==-moves[i-1]) continue;
						seq = seq +", " +  moves[i];
					}
					if(i==0) seq = seq + moves[i];
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
		add(Box.createRigidArea(new Dimension(50,50)));
	}
	
	private class SolverListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int [][] cube = new int [13][11];
			cubeGraphic.getCube(cube);
			if(Megaminx.fitness_cal(cube)==0) {
				info.setSolving("0");
				return;
			}
			if(!nowSolving) {
			cubeGraphic.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			nowSolving=true;
			cubeGraphic.cube_Available=false;
			mix.setEnabled(false);
			reset.setEnabled(false);
			solve.setText("CANCEL");
			info.setSolving("Solving...");
			solver = new SolverTask(50,100);
			solver.execute();
			} else {
				try {
				solver.cancel(false);
				} catch(CancellationException ex) {	}
				finally {
					info.setSolving("CANCELED");
					solve.setText("SOLVE");
					cubeGraphic.setCursor(null);
					nowSolving=false;
					mix.setEnabled(true);
					reset.setEnabled(true);
					cubeGraphic.cube_Available=true;
				}
			}
			
		}
	}
	
	private class SolverTask extends SwingWorker<int[],int[]> {
		private int[][] cube;
		private final int N,L;//N 염색체수, 염색체 길이
		private int[][] genes, next_genes;
		private int[] fitness;
		
		public SolverTask(int N, int L) {
			this.N = N;
			this.L = L;
			cube = new int[13][11];
			genes = new int [N][L];
			next_genes = new int [N][L];
			fitness = new int [N];
			fitness[0]=-1;
		}
		
		private void make_fitness() {
			for(int i=0;i<N;i++) {
				fitness[i] = Megaminx.fitness_cal(cube,genes[i]);
			}
		}
		
		private void sort() {
			//TODO sort 
		}
		
		private void generation() {
			//TODO one generation.
			make_fitness();
		}
		
		@Override
		protected int[] doInBackground() {
			cubeGraphic.getCube(cube);
//			Initialization.
			while(fitness[0]!=0&&!isCancelled()) {
			generation();
			publish(genes[0]);
			}
			return genes[0];
		}
		
		@Override
		protected void process(List<int[]> move) {
			cubeGraphic.setCube(Megaminx.rotate(cube, move.get(move.size()-1)));
		}
		
		@Override
		public void done() {
			int [] solve_moves;
			try {
				solve_moves = get();
			} catch (InterruptedException | ExecutionException e) {	return; }
			String seq = Integer.toString(solve_moves[0]);
			for(int i=1;i<solve_moves.length;i++) {
				seq = seq +", " +  solve_moves[i];
			}
			info.setSolving(seq);
			solve.setText("SOLVE");
			cubeGraphic.rotateCube(solve_moves);
			cubeGraphic.setCursor(null);
			nowSolving=false;
			mix.setEnabled(true);
			reset.setEnabled(true);
			cubeGraphic.cube_Available=true;
		}
	}
	
}
