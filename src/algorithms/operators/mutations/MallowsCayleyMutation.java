package algorithms.operators.mutations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Random;

import utils.ArrayUtils;

public class MallowsCayleyMutation extends PermutationMutation {

	BigInteger[][] matrix; // Stirling matrix
	int n;
	double[] probabilityList; 
	double[] acumulProb;

	
	int centralPermutationOffset;
	
	public MallowsCayleyMutation(Random rand, int n, int offset) {
		this.rand = rand;
		this.n = n;
		this.buildStirlingMatrixOfFirstKind(n);
		probabilityList = new double[n];
		this.centralPermutationOffset = offset;
		
		countSamplesAtKCycles = new  HashMap<Double,HashMap<Integer, Integer>>();
//		for(int i=0; i<this.n; i++){
//			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//			countSamplesAtKCycles.put(i, map);
//		}
	}

	public void buildStirlingMatrixOfFirstKind(int n)
	{	
		int k = n;
		this.matrix = new BigInteger[n+1][k+1];
		matrix[0][0] = BigInteger.valueOf(1);
		for(int i=1; i<n+1; i++){
			matrix[i][0] = BigInteger.valueOf(0);
		}
		for(int i=1; i<k+1; i++){
			matrix[0][i] = BigInteger.valueOf(0);
		}

		for(int i=1; i<n+1; i++){
			for (int j=1; j<k+1; j++){
					matrix[i][j] = BigInteger.valueOf((i-1)).multiply(matrix[i-1][j]).add(matrix[i-1][j-1]);
			}
		}		
	}
	
	public void setProbabilityList(double theta){

		BigDecimal[] acumul = new BigDecimal[n];
//		acumul[0] = BigDecimal.ZERO;
		acumul[0] = BigDecimal.ONE;

//		System.out.println("--PROV2--");
		for(int dista=1; dista<n; dista++){
			BigDecimal prov2 = new BigDecimal(Math.exp(-theta * dista));
//			BigDecimal prov2 = new BigDecimal(1+(n-dista)*Math.exp(-theta));

//			System.out.println(dista+"; "+prov2);

			BigDecimal prov = new BigDecimal(matrix[n][n-dista]);

			prov = prov.multiply(prov2);
//			System.out.println("prov: "+dista+" : "+prov);
			
			acumul[dista] = acumul[dista-1].add(prov);
//			System.out.println(dista+"; "+acumul[dista]);

		}
		MathContext mc = new MathContext(2, RoundingMode.HALF_UP);

//		System.out.println("\n-- acumul -- theta: "+theta);
//		for(int i=0; i<n; i++){
//			System.out.println(i+";"+acumul[i]+";"+matrix[n][n-i]);
//		}
		
		acumulProb = new double[n];
//		System.out.println("-- acumulProb --");
		for(int i=0; i<n; i++){
			acumul[i] = acumul[i].divide(acumul[n-1],mc);
			acumulProb[i] = acumul[i].doubleValue();
//			System.out.println(i+"; "+acumulProb[i]);
		}
		
//		double[] temp = new double[n];
//		for(int i=0; i<n; i++){
//			temp[n-1-i] =acumulProb[i];
//		}
		
		
		
//		System.out.println("\n-- acumulProb -- theta: "+theta);
//		for(int i=0; i<n; i++){
//			System.out.println(i+";"+acumulProb[i]+";"+matrix[n][n-i]);
//		}
		
		probabilityList[n-1] = (acumulProb[0]); 
		for(int i=n-1; i>0; i--){
			probabilityList[n-1-i] = (acumulProb[i] - acumulProb[i-1]); 
			

		}
		
//		System.out.println("\n-- probabilityList -- theta: "+theta);
//		for(int i=0; i<n; i++){
//			System.out.println(i+";"+probabilityList[i]+";"+matrix[n][i]);
//		}
		//System.out.println(n+";"+theta+";"+probabilityList[n-1]);

	}
		
	
	public void generatePermutationWithKCycles(int n, int k, int[] sigma, BigInteger[][] matrix){

		int problemSize = n;

		int[] sigmaInv = new int[n];

		Random rand = new Random();
		double ran1;
		BigInteger aux, aux2;
		BigDecimal aux3;
		double prob;
		boolean[] long_cycle = new boolean[n];

		while( k >1){
			ran1 = rand.nextDouble();
			aux = matrix[n-1][k-1];
			aux2 = matrix[n][k];
			aux3 = new BigDecimal(aux).divide(new BigDecimal(aux2), 2, RoundingMode.HALF_UP);

			prob = aux3.doubleValue();
			if(ran1 < prob){
				long_cycle[n-1] = false;
				k --;
			}
			else{
				long_cycle[n-1] = true;
			}
			n --;
		}

		generateRandomPermutation(n, sigmaInv);

		for(int i = 0; i<n-1; i++){
			sigma[sigmaInv[i]] = sigmaInv[i+1];
		}
		sigma[sigmaInv[n-1]] = sigmaInv[0];

		for (int i = n ; i < problemSize ; i++)
		{
			if (long_cycle[i])
			{
				int ran2 = rand.nextInt(i);
				sigma[i] = sigma[ran2];
				sigma[ran2] = i;
			}
			else
			{
				sigma[i] = i;
			}
		}
	}
	
	public void generateRandomPermutation (int len, int[] sigma) {
		Random gen = new Random();
		int pos;
		int aux;
		int i;
		int len_1 = len - 1;
		for (i = 0;i < len;i++)
		{
			sigma[i] = i;
		}

		for (i = 0;i < len_1;i++)
		{
			pos = gen.nextInt((len_1-i)+1) + i ;
			aux = sigma[i];
			sigma[i] = sigma[pos];
			sigma[pos] = aux;
		}
	}
	
	public int sampleKFromDistribution(){
		double temp = rand.nextDouble();
		for(int i=0; i<this.acumulProb.length; i++){
			if(temp <=this.acumulProb[i]){
//				return i;
				return n-i;	
			}
		}
		return -1;
	}
	
	public int[] sample(int k, int[] sigma){
		int[] newsol = new int[sigma.length];
		System.arraycopy(sigma, 0, newsol, 0, sigma.length);
		generatePermutationWithKCycles(n, k, newsol, this.matrix);
		return newsol;
	}

	
	@Override
	public int[] mutate(int[] solution, double distance, boolean fixeddistance) {

		double thedistance = distance;
		if(!fixeddistance){
			thedistance = distance * rand.nextDouble();
		}
		
		setProbabilityList(thedistance); // 1. set new probablity distribution based on theta
		
		int k = this.sampleKFromDistribution();// 2. Sample this new distribution to get the number of cycles between original solution and new solution 

		int[] newcentralpermutation = new int[solution.length];
		System.arraycopy(solution, 0, newcentralpermutation, 0, solution.length);
		if(this.centralPermutationOffset > 0){
			System.arraycopy(this.mutateAtK(newcentralpermutation, this.n - this.centralPermutationOffset), 0, newcentralpermutation, 0, newcentralpermutation.length);
		}
				
		if(!(this.centralPermutationOffset == 0 && k == n)){
		
		if(!this.countSamplesAtKCycles.containsKey(distance)){
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
			map.put(k, 1);
			countSamplesAtKCycles.put(distance, map);
		}
		else{
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
			for (int i : this.countSamplesAtKCycles.get(distance).keySet()) {
				map.put(i, this.countSamplesAtKCycles.get(distance).get(i));
			}
			if(!this.countSamplesAtKCycles.get(distance).containsKey(k)){
				map.put(k, 1);
				countSamplesAtKCycles.put(distance, map);
			}
			else{
//				countSamplesAtKCycles.get(distance).put(k, countSamplesAtKCycles.get(distance).get(k)+1);
				
				map.put(k, countSamplesAtKCycles.get(distance).get(k) + 1);
			}
			countSamplesAtKCycles.put(distance, map);
		}
		
		
		return this.mutateAtK(newcentralpermutation, k);
		}
		else{ 
			return solution;
		}
				
	}

	public int[] mutateAtK(int[] solution, int k){
		int[] mask = new int[solution.length];
		System.arraycopy(solution, 0, mask, 0, solution.length);
		mask = this.sample(k, mask);

		
		int[] newsol = new int[solution.length];
		for(int i=0; i<mask.length; i++){
			newsol[i] = solution[mask[i]];
		}

		return newsol;
	}
	
	
	@Override
	public boolean isIntDistance() {
		return false;
	}

	@Override
	public boolean isNaturalOrder() {
		return false;
	}

	public double getThetaForProbOfCentralPermutation(double p, int d){
		
//		BigDecimal nd = new BigDecimal(matrix[n][n-d]);
		BigDecimal nd = new BigDecimal(matrix[n][n-d]);

		System.out.println("nd: "+nd);
		
		BigDecimal temp0 = new BigDecimal(p*this.n);

		BigDecimal temp1 = temp0.divide(nd);
		
		double theta = - Math.log(temp1.doubleValue()) / d;
		
		return theta;
		
	}
	
	public double getProbabilityOfCentralPermutation(double theta){
		BigDecimal sum = BigDecimal.ONE;
		for(int i=1; i<n; i++){
			BigDecimal nd = new BigDecimal(matrix[n][n-i]);
			BigDecimal temp0 = new BigDecimal(Math.exp(-theta*i));
			BigDecimal temp1 = temp0.multiply(nd);
			sum = sum.add(temp1);
		}
//		System.out.println("Sum: "+sum);
		double prob = 1.0/sum.doubleValue();
		return prob;
	}
	

	
}
