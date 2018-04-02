package algorithms.operators.mutations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//import problems.generators.dynamicprofilegenerators.IdentityPermutationGenerator;


public class DirectCayleyMutation extends PermutationMutation {

	BigInteger[][] matrix; // Stirling matrix
	static int n;

	public DirectCayleyMutation(Random rand, int n) {
		this.rand = rand;
		DirectCayleyMutation.n = n;
		this.buildStirlingMatrixOfFirstKind(n);
	}

	public void buildStirlingMatrixOfFirstKind(int n) {
		int k = n;
		this.matrix = new BigInteger[n + 1][k + 1];
		matrix[0][0] = BigInteger.valueOf(1);
		for (int i = 1; i < n + 1; i++) {
			matrix[i][0] = BigInteger.valueOf(0);
		}
		for (int i = 1; i < k + 1; i++) {
			matrix[0][i] = BigInteger.valueOf(0);
		}

		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < k + 1; j++) {
				matrix[i][j] = BigInteger.valueOf((i - 1)).multiply(matrix[i - 1][j]).add(matrix[i - 1][j - 1]);
			}
		}
	}
	
	public BigInteger[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(BigInteger[][] matrix) {
		this.matrix = matrix;
	}

	public static void generatePermutationWithKCycles(int n, int k, int[] sigma, BigInteger[][] matrix) {

		int problemSize = n;

		int[] sigmaInv = new int[n];

		Random rand = new Random();
		double ran1;
		BigInteger aux, aux2;
		BigDecimal aux3;
		double prob;
		boolean[] long_cycle = new boolean[n];

		while (k > 1) {
			ran1 = rand.nextDouble();
			aux = matrix[n - 1][k - 1];
			aux2 = matrix[n][k];
			aux3 = new BigDecimal(aux).divide(new BigDecimal(aux2), 2, RoundingMode.HALF_UP);

			prob = aux3.doubleValue();
			if (ran1 < prob) {
				long_cycle[n - 1] = false;
				k--;
			} else {
				long_cycle[n - 1] = true;
			}
			n--;
		}

		generateRandomPermutation(n, sigmaInv);

		for (int i = 0; i < n - 1; i++) {
			sigma[sigmaInv[i]] = sigmaInv[i + 1];
		}
		sigma[sigmaInv[n - 1]] = sigmaInv[0];

		for (int i = n; i < problemSize; i++) {
			if (long_cycle[i]) {
				int ran2 = rand.nextInt(i);
				sigma[i] = sigma[ran2];
				sigma[ran2] = i;
			} else {
				sigma[i] = i;
			}
		}
	}

	public static void generatePermutationFromX(int[] X, int[] sigma){
		int random, aux,i;
		for (i=0; i<n;i++)
			sigma[i]=i;
		int [] identity = sigma.clone();
		for (i=0; i<X.length;i++){
			if (X[i]==1){
				random = i + 1 +rand.nextInt(n-1-i); // random=[i+1,n-1]
				aux = sigma[random];
				sigma[random]= sigma[i];
				sigma[i] = aux;
			}
		}
		//System.out.println("Cayley distance between permutations:" + IdentityPermutationGenerator.getCayleyDistance(identity, sigma));
	}
	
	// Create a binary array "X" that contains 1 "Cayley distance" times
	public static int[] generateRandomBinaryArray(int n, int k){

		List<Integer> solution = new ArrayList<Integer>(Collections.nCopies(n-1, 1));
		for (int i = 0; i < k -1; i++) {
		    solution.set(i,0);
		}
		Collections.shuffle(solution);
		
		int[] X = new int[n-1];
		for(int i = 0; i < solution.size(); i++) 
			X[i] = solution.get(i);
		return X;
	}
	
	
	public static void generateRandomPermutation(int len, int[] sigma) {
		Random gen = new Random();
		int pos;
		int aux;
		int i;
		int len_1 = len - 1;
		for (i = 0; i < len; i++) {
			sigma[i] = i;
		}

		for (i = 0; i < len_1; i++) {
			pos = gen.nextInt((len_1 - i) + 1) + i;
			aux = sigma[i];
			sigma[i] = sigma[pos];
			sigma[pos] = aux;
		}
	}

	@Override
	public int[] mutate(int[] solution, double distance, boolean fixeddistance) {
		int[] newsol = new int[solution.length];
		int k = n - (int) distance;
		if (!fixeddistance) {
			int newdist = rand.nextInt((int) distance) + 1;
			k = n - newdist;
		}

		System.arraycopy(solution, 0, newsol, 0, solution.length);
		// generatePermutationWithKCycles(n, k, newsol, this.matrix);
		// return newsol;
		return this.mutateAtK(newsol, k);
	}

	public int[] mutateAtK(int[] solution, int k) {
		int[] mask = new int[solution.length];
		System.arraycopy(solution, 0, mask, 0, solution.length);
		mask = this.sample(k, mask);

		// COMPOSE FUNCTION
		int[] newsol = new int[solution.length];
		for (int i = 0; i < mask.length; i++) {
			newsol[i] = solution[mask[i]];
		}

		return newsol;
	}

	public int[] sample(int k, int[] sigma) {
		int[] newsol = new int[sigma.length];
		System.arraycopy(sigma, 0, newsol, 0, sigma.length);
		
		// Josu's method  --> Change constructor, Stirling matrix is not used (considerable reduction of execution time)
		int[] X = generateRandomBinaryArray(sigma.length,k);
		generatePermutationFromX(X,newsol);
		
		// Stirling matrix
//		generatePermutationWithKCycles(n, k, newsol, this.matrix);
		return newsol;
	}

	@Override
	public boolean isIntDistance() {
		return true;
	}

	@Override
	public boolean isNaturalOrder() {
		return true;
	}

}
