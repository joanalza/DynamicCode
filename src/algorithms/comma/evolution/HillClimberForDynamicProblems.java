package algorithms.comma.evolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import algorithms.comma.Solution;
import algorithms.comma.distances.DistanceVectorInitialiser;
import algorithms.operators.mutations.PermutationMutation;
import problems.DynamicPermutationProblem;
import utils.ArrayUtils;

public class HillClimberForDynamicProblems extends EvolutionForDynamicProblems {

	public HillClimberForDynamicProblems(DistanceVectorInitialiser distanceInitialiser, ArrayList<Solution> population, PermutationMutation mutation, boolean fixeddistance, DynamicPermutationProblem problem, Random rand, Comparator<Solution> comparator) {
		//this.distanceVector = distanceVector;
		this.population = population;
		this.mutation = mutation;
		this.fixeddistance = fixeddistance;
		this.problem = problem;
		this.rand = rand;
		this.comparator = comparator;
		this.distanceInitialiser = distanceInitialiser;
	}

	@Override
	public void evolveOnce(double[] distanceVector) {

		boolean isNatural = this.problem.isNatural();
		
		for(int i=0; i<population.size(); i++){
			double dist = distanceVector[i];
			
			int[] tempSol = new int[problem.problemSize()];
			tempSol = mutation.mutate(population.get(i).individual, dist, this.fixeddistance);
			
//			System.out.println("rank: "+i+" - fitness: "+population.get(i).fitness+" - dist: "+dist);
//			System.out.println("1: "+ArrayUtils.tableToString(population.get(i).individual));
//			System.out.println("2: "+ArrayUtils.tableToString(tempSol));

			/**Evaluate solution*/
			double tempFit = population.get(i).fitness;
			if(!tempSol.equals(population.get(i).individual)){
				tempFit = problem.evaluate(tempSol);
				fes ++;
			}

			if(isNatural){
				if(population.get(i).fitness < tempFit){
					population.get(i).individual = tempSol;
					population.get(i).fitness = tempFit;
				}
			}
			else{
				if(population.get(i).fitness > tempFit){
					population.get(i).individual = tempSol;
					population.get(i).fitness = tempFit;
				}
			}
		}
		
	}

}
