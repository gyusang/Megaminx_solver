package megaminx_move;

public class Megaminx {
	static final int [][][] PERMS = { //numbered as R : permutations package, Robin K. S. Hankin
			//Face 0 Nothing
			{{}},
			//Face 1 White
			{{10,12,14,16,18},{11,13,15,17,19},{21,33,45,57,69},{22,34,46,58,60},{23,35,47,59,61}},
			//Face 2 Purple
			{{15,67,91,81,35},{16,68,92,82,36},{17,69,93,83,37},{20,22,24,26,28},{21,23,25,27,29}},
			//Face 3 DarkYellow
			{{17,29,89,79,47},{18,20,80,70,48},{19,21,81,71,49},{30,32,34,36,38},{31,33,35,37,39}},
			//Face 4 DarkBlue
			{{10,32,78,118,50},{11,33,79,119,51},{19,31,77,117,59},{40,42,44,46,48},{41,43,45,47,49}},
			//Face 5 Red
			{{11,43,115,105,61},{12,44,116,106,62},{13,45,117,107,63},{50,52,54,56,58},{51,53,55,57,59}},
			//Face 6 DarkGreen
			{{13,55,103,93,23},{14,56,104,94,24},{15,57,105,95,25},{60,62,64,66,68},{61,63,65,67,69}},
			//Face 7 LightGreen
			{{30,88,120,110,40},{31,89,121,111,41},{39,87,129,119,49},{70,72,74,76,78},{71,73,75,77,79}},
			//Face 8 Orange
			{{27,99,121,71,37},{28,90,122,72,38},{29,91,123,73,39},{80,82,84,86,88},{81,83,85,87,89}},
			//Face 9 LightBlue
			{{25,65,101,123,83},{26,66,102,124,84},{27,67,103,125,85},{90,92,94,96,98},{91,93,95,97,99}},
			//Face 10 LightYellow
			{{53,113,125,95,63},{54,114,126,96,64},{55,115,127,97,65},{100,102,104,106,108},{101,103,105,107,109}},
			//Face 11 Pink
			{{41,75,127,107,51},{42,76,128,108,52},{43,77,129,109,53},{110,112,114,116,118},{111,113,115,117,119}},
			//Face 12 Grey
			{{73,85,97,109,111},{74,86,98,100,112},{75,87,99,101,113},{120,122,124,126,128},{121,123,125,127,129}}
	};
	public static int[][] rotate(int[][] cube,int[] faces) {
		int[][]new_cube = new int[13][10];
		int direction,cur_face,before,after;
		int i,j,k;
		for(i=0;i<faces.length;i++){
			cur_face = faces[i];
			if(cur_face>0) {
				direction=1;
			} else if(cur_face<0){
				direction=-1;
				cur_face = -cur_face;
			} else {
				continue;
			}
			for(j=0;j<5;j++) {
				for(k=0;k<5;k++) {
					before = PERMS[cur_face][j][(k+5-direction)%5];
					after = PERMS[cur_face][j][k];
					new_cube[after/10][after%10]=cube[before/10][before%10];
				}
			}
			for(j=0;j<5;j++) {
				for(k=0;k<5;k++) {
					after = PERMS[cur_face][j][k];
					cube[after/10][after%10]=new_cube[after/10][after%10];
				}
			}
			//Megaminx.printCube(cube);
		}
		return cube;
	}
	
	public static int fitness_cal(int[][] cube, int[] faces) {
		int fitness = 0;
		int i,j;
		cube = Megaminx.rotate(cube, faces);
		for(i=1;i<=12;i++) {
			for(j=0;j<10;j++) {
				if(cube[i][j]!=i) fitness++;
			}
		}
		return fitness;
	}
	
	public static void printCube(int[][] cube) {
		int i,j,faces[] = {0};
		//System.out.println("Fitness : "+ Megaminx.fitness_cal(cube,faces));
		System.out.print("\\\t");
		for(j=0;j<10;j++)
			System.out.print(cube[0][j]+"\t");
		System.out.println();
		for(i=1;i<=12;i++) {
			System.out.print(i+"\t");
			for(j=0;j<10;j++) {
				System.out.print(cube[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
}
