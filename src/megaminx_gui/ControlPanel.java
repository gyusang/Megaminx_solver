package megaminx_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

import megaminx_solve.Solver;

public class ControlPanel extends JPanel{
	private static final long serialVersionUID = 20L;
	
	private static final int[][] cube = {
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
	
	private final int N = 30;
	private final int L = 100;
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
				cubeGraphic.setCube(ControlPanel.cube);
				info.setSeq("NONE");
			}
		});
		mix.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cubeGraphic.setCube(ControlPanel.cube);
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
			if(!nowSolving) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			cubeGraphic.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			nowSolving=true;
			cubeGraphic.cube_Available=false;
			mix.setEnabled(false);
			reset.setEnabled(false);
			solve.setText("CANCEL");
			info.setSolving("Solving...");
			solver = new SolverTask();
			solver.execute();
			} else {
				solver.cancel(true);//TODO add management
				info.setSolving("CANCELED");
			}
			
		}
	}
	
	private class SolverTask extends SwingWorker<Void,Void> {
		@Override
		public Void doInBackground() {
			new Solver(N,L,ControlPanel.this);
			return null;
		}
		
		@Override
		public void done() {
			setCursor(null);
			cubeGraphic.setCursor(null);
			nowSolving=false;
			mix.setEnabled(true);
			reset.setEnabled(true);
			solve.setText("SOLVE");
			cubeGraphic.cube_Available=true;
		}
	}
	
}
