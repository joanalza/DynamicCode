package tests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import algorithms.operators.mutations.DirectCayleyMutation;
import algorithms.operators.mutations.PermutationMutation;
import utils.ArrayUtils;

public class strirlingVsJosus {

	public static void main(String[] args)  throws IOException{
		// TODO Auto-generated method stub
		
		long startTime = System.currentTimeMillis();
		
		int times = 1000;
		int[] sizes = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,20};
		Random rand = new Random();
		int[] cayleyDistances = {1,2,3,4,5};
		
		for(int k =0;k<cayleyDistances.length;k++){
			int cayleyDistance = cayleyDistances[k];
			for (int j=0; j<sizes.length ;j++){
				int size = sizes[j];
				int cycles = size - cayleyDistance;
				int[] newsol = new int[size];
				String output = "Size;Cayley;Method;Permutation\n";
				BufferedWriter br1 = new BufferedWriter(new FileWriter("./results/stirlingVSJosus/cayley-"+ cayleyDistance +"_size-"+size+".csv"));
				
				DirectCayleyMutation mutation = new DirectCayleyMutation(rand, size);
				mutation.buildStirlingMatrixOfFirstKind(size);
				BigInteger[][] matrix = mutation.getMatrix();
				
				for(int i=0; i<times;i++){
					// Stirlings method
					algorithms.operators.mutations.DirectCayleyMutation.generatePermutationWithKCycles(size, cycles, newsol, matrix);
					output += size + ";" + cayleyDistance + ";Stirling;" + ArrayUtils.tableToString(newsol)+"\n";
					
					//Josu's method
					int[] X = DirectCayleyMutation.generateRandomBinaryArray(size,cycles);
					DirectCayleyMutation.generatePermutationFromX(X,newsol);
					output += size  + ";" + cayleyDistance + ";Josus;" + ArrayUtils.tableToString(newsol)+"\n";
					System.out.println(i);
				}
		        br1.write(output);
		        br1.close();
		
				long endTime = System.currentTimeMillis();
				System.out.println("That took " + (endTime - startTime) + " milliseconds");
			}
		}
	}

}
