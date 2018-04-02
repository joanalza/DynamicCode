package problems.generators.dynamicprofilegenerators;

import java.io.IOException;
import java.util.Random;
//import java.util.Vector;

public class DynamicPermutationInstanceGeneratorMain {

	public static void main(String[] args) throws IOException {

		String resultsPath = "./data/dynamic/";
		String saveAs;
		
		int nChanges = 7;
		int size = 4;
		Random rand = new Random();
		int distance = 1;
		
//		int[] e = {0,1,2,3,4,5,6,7,8,9,10,11};
//		int[] sigma = {11,10,9,8,7,6,5,4,3,2,1,0};
//		
//		System.out.println("Number of changes between first two permutations:" + IdentityPermutationGenerator.getCayleyDistance(e, sigma));
		//System.out.println(rand.nextGaussian());
		long startTime = System.currentTimeMillis();
		
		
		
		saveAs = "dynProfile-n" + size + "-c" + nChanges + "-Cayley" + distance +".txt";
		IdentityPermutationGenerator generator = new CayleyIdentityPermutationGenerator(nChanges, size, rand, distance);

		generator.generate();
		generator.printInstance();
//		generator.createInstance(resultsPath, saveAs);
		
		long endTime = System.currentTimeMillis();

		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		
//		for (int i = 0; i<nChanges - 1;i++){
//			System.out.println("Cayley distance between permutations:" + IdentityPermutationGenerator.getCayleyDistance(generator.permutations[i], generator.permutations[i+1]));
//		}
	}

}
