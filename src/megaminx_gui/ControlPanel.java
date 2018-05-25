package megaminx_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

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
	
	CubePanel cubeGraphic;
	InfoPanel info;
	
	private Random generator;
	
	private final int N = 30;
	
	public ControlPanel(CubePanel cubeGraphic, InfoPanel info) {
		generator = new Random();
		this.cubeGraphic = cubeGraphic;
		this.info = info;
//		setPreferredSize(new Dimension(305,500));
		setOpaque(false);
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		JButton reset = new JButton("RESET");
		JButton mix = new JButton("MIX");
		JButton solve = new JButton("SOLVE");
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
				int[] moves = new int[N];
				String seq = "";
				for(int i=0;i<N;) {
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
		solve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				info.setSolving("Solver starting...");
//				new Megaminx()
				setVisible(true);
				info.setSolving("Solver finished.");
			}
		});
		reset.setMnemonic('r');
		mix.setMnemonic('m');
		solve.setMnemonic('s');
		add(reset);
		add(mix);
		add(solve);
		add(Box.createRigidArea(new Dimension(50,50)));
	}
	
	
}
