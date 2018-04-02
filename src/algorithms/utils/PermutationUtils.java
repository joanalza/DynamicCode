package algorithms.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvaluatedCandidate;
import org.uncommons.watchmaker.framework.factories.ListPermutationFactory;

import algorithms.utils.Population;
import algorithms.utils.Solution;

public class PermutationUtils {


	/*
	 * Generates a random permutation of size 'n' in the given array.
	 */
	public static ArrayList<Integer> GenerateRandomPermutation(int n) 
	{
		//Candidate Factory
		List allItems = new ArrayList<Integer>();
		for(int i=0; i<=n-1; i++){
			allItems.add(i);
		}
		CandidateFactory<ArrayList> factory = new ListPermutationFactory(allItems);

		ArrayList<Integer> sol = factory.generateRandomCandidate(new Random());

		return sol;
	}


	public static void RandomKeysDescending( int[] a, float[] criteriaValues, int ProblemSize)
	{
		boolean[] fixedValues = new boolean [ProblemSize];
		double criteria, max;
		int i, j;
		for (i=0;i<ProblemSize;i++){
			fixedValues[i]=false;
			a[i]=0;
		}
		int maxPos=0;
		for (i=0;i<ProblemSize;i++)
		{
			max=Integer.MIN_VALUE;
			for (j=0;j<ProblemSize;j++)
			{
				criteria=criteriaValues[j];
				//cout<<"j: "<<j<<" min: "<<min<<" criteriaValue: "<<criteria<<" fixedValue: "<<fixedValues[j]<<endl;
				if (!fixedValues[j] && max<criteria )
				{
					max=criteria;
					maxPos=j;
					//	cout<<"********* new min: "<<min<<" position: "<<j<<endl;
				}
			}

			fixedValues[maxPos]=true;
			a[maxPos]=i;
			//cout<<"++++++++ fixed pos: "<<minPos<< " job assigned: "<<i<<endl;
			//cout<<"------"<<endl;
		}
	}


	public static double IndividualNormalizedKendallTau(int[] ordering1, int[] ordering2){

		int count = 0;
		int nOfTests =0;
		for (int i=0; i<ordering1.length; i++){
			for (int j=i+1; j<ordering1.length; j++){
				nOfTests ++;
				if ((ordering1[i]<ordering1[j] && ordering2[i]>ordering2[j]) || (ordering1[i]>ordering1[j] && ordering2[i]<ordering2[j])){
					count ++;
				}
			}
		}
		double kt = count / (ordering1.length * (ordering1.length -1) * 0.5);
//		double kt = count;
		return kt;
	}

	public static double IndividualNormalizedKendallTau(ArrayList<Integer> ordering1, ArrayList<Integer> ordering2){

		int count = 0;
		int nOfTests =0;
		for (int i=0; i<ordering1.size(); i++){
			for (int j=i+1; j<ordering1.size(); j++){
				nOfTests ++;
				if ((ordering1.get(i)<ordering1.get(j) && ordering2.get(i)>ordering2.get(j)) || (ordering1.get(i)>ordering1.get(j) && ordering2.get(i)<ordering2.get(j))){
					count ++;
				}
			}
		}
		double kt = count / (ordering1.size() * (ordering1.size() -1) * 0.5);
		return kt;
	}

	public static boolean isSolutionInPopulation(Solution sol, Population pop){
		boolean is = false;

		for(int i=0; i<pop.getSolutions().size(); i++){
			//		if(PermutationUtils.IndividualNormalizedKendallTau(sol.getGenes(), pop.getSolutions().get(i).getCandidate()) == 0.0){
			if(PermutationUtils.compareOrderings(sol.getGenes(), pop.getSolutions().get(i).getCandidate())){
				is = true;
				break;
			}
		}

		return is;
	}


	public static boolean isSolutionInPopulation(ArrayList<Integer> sol, List<EvaluatedCandidate<ArrayList>> pop){
		boolean is = false;

		for(int i=0; i<pop.size(); i++){
			//		if(PermutationUtils.IndividualNormalizedKendallTau(sol.getGenes(), pop.getSolutions().get(i).getCandidate()) == 0.0){
			if(PermutationUtils.compareOrderings(sol, pop.get(i).getCandidate())){
				is = true;
				break;
			}
		}

		return is;
	}


	/**
	 * Return true if the specified orderings are the same
	 * */
	public static boolean compareOrderings(ArrayList<Integer> ordering1, ArrayList<Integer> ordering2){

		boolean is = true;
		for (int i=0; i<ordering1.size(); i++){
			if(ordering1.get(i) != ordering2.get(i)){
				is = false;
				break;
			}
		}
		return is;
	}


	/*
	 * Inverts a permutation.
	 */
	public static void Invert(int[]permu, int n, int[] inverted)
	{
		for(int i=0; i<n; i++) inverted[permu[i]]=i;
	}

	/*
	 * Implements the compose of 2 permutations of size n.
	 */
	public static void Compose(int[]s1, int[]s2, int[]res, int n)
	{
		for(int i=0;i<n;i++)
			res[i]=s1[s2[i]];
	}

	/*
	 *	Calculates V_j-s vector.
	 */
	public static void vVector(int[] v, int[] permutation, int n)
	{
		for(int i=0;i<n-1;i++)
			v[i]=0;
		
		for (int i = n-2; i >= 0; i--)
			for (int j = i+1; j < n; j++)
				if(permutation[i] > permutation[j])
					v[i]++;
	}
	

	/*public static int size(Object object) {
	    if (!object.getClass().isArray()) {
	        return 1;
	    }

	    int size = 0;
	    for (int i = 0; i < Array.getLength(object); i++) {
	        size += size(Array.get(object, i));
	    }
	    return size;
	}
	
	public static boolean linearSearch(int[][] matrix, int target) {
		if (matrix != null){
			for (int i = 0; i < matrix.length; i++) {
				for (int j=0; j< matrix[i].length;j++){
					if (matrix[i][j] == target) {
						return true;
					}
				}
			}	
		}
		return false;
	}*/

}
