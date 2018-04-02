package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utils.ArrayUtils;

public class DynamicQAP extends DynamicPermutationProblem {

	public int[][] matrixA;
	public int[][] matrixB;
	
	public DynamicQAP(String path, String dynProfilePath) {
		this.dynProfilePath = dynProfilePath;
		try {
			BufferedReader reader = new BufferedReader (new FileReader (new File (path)));

			String templine = " ";
			String separator = ",";
			int line=0;

			this.optimum = Integer.parseInt(reader.readLine().trim());
			
			this.theSize = Integer.parseInt(reader.readLine().trim());
			matrixA = new int[this.theSize][this.theSize];
			matrixB = new int[this.theSize][this.theSize];

			templine = reader.readLine().trim(); // blank line

			line =0;
			for(int i = 0; i< this.theSize; i++){
				templine = reader.readLine().trim();
				String[] lineArray = templine.split(separator);
				for(int j=0; j<lineArray.length; j++){
					int val = Integer.parseInt(lineArray[j]);
					matrixA[line][j] = val;
				}
				line++;
			}

			templine = reader.readLine().trim(); // blank line

			line =0;
			for(int i = 0; i< this.theSize; i++){
				templine = reader.readLine().trim();
				String[] lineArray = templine.split(separator);
				for(int j=0; j<lineArray.length; j++){
					int val = Integer.parseInt(lineArray[j]);
					matrixB[line][j] = val;
				}
				line++;
			}
		}   
		catch (IOException e) {System.out.println("Couldn't find file: " + path);}   
		
		// Identity permutation initialised to 01234....
    	this.identityPerm = new int[this.theSize];
    	for(int i=0; i<this.theSize; i++){
    		this.identityPerm[i] = i;
    	}
    	// Set change of identity schedule
    	this.setIdentityPermutationChanges();
	}

	@Override
	public boolean isNatural() {
		return false;
	}

	@Override
	public double evaluate(int[] solution) {
//		System.out.println("this.identityPerm: "+ArrayUtils.tableToString(this.identityPerm));

		int total = 0;
		for (int i=0; i<this.theSize; i++){
			for (int j=0; j<this.theSize; j++){
				total += this.matrixA[i][j] * this.matrixB[this.identityPerm[solution[i]]][this.identityPerm[solution[j]]];
			}
		}
		
		double fitness = (double)total;
		return fitness;
	}

}
