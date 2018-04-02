package algorithms.comma.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import algorithms.comma.Solution;
import algorithms.comma.distances.DistanceVectorInitialiser;
import algorithms.operators.mutations.PermutationMutation;
import problems.DynamicPermutationProblem;

public abstract class EvolutionForDynamicProblems {

	//double[] distanceVector;
	int originalPopulationSize;
	DistanceVectorInitialiser distanceInitialiser;
	ArrayList<Solution> population;
	PermutationMutation mutation;
	boolean fixeddistance;
	DynamicPermutationProblem problem;
	Random rand;
	Comparator<Solution> comparator;
	int fes;
	
	
	public EvolutionForDynamicProblems() {
	}

	public abstract void evolveOnce(double[] distanceVector);
	
	public void evolve(int maxfes, boolean fixedPopSize){
		
		this.originalPopulationSize = population.size();
		this.fes = population.size(); // because population has already been initialised and evaluated
		this.rankPopulation();
		System.out.println("fes,bestFit,worstFit");
		System.out.println(this.fes+","+this.population.get(0).fitness+","+this.population.get(this.population.size()-1).fitness);

		while(this.fes <= maxfes){
			boolean hasChangedOccured = this.problem.changeIdentityPermutation(this.fes, maxfes);
			if(hasChangedOccured){
				// Re-evaluate population fitness
				for(int i=0; i<this.population.size(); i++){
					this.population.get(i).fitness = problem.evaluate(this.population.get(i).individual);
				}
			}
			System.out.println(this.fes+","+this.population.get(0).fitness+","+this.population.get(this.population.size()-1).fitness);

			if(!fixedPopSize){
				int solToRemove = this.population.size() - this.getnextPopulationSize(maxfes);
//				if(solToRemove > 0){
//					System.out.println(fes+";"+(this.population.size()-solToRemove)+";"+this.population.get(this.population.size()-1).fitness);
//				}
				for(int i=0; i<solToRemove; i++){
					this.population.remove(this.population.size()-1);
				}
			}
			
			this.evolveOnce(this.distanceInitialiser.getNextDistanceVector(this.fes, maxfes));
			this.rankPopulation();
		}
	}
	
	public void rankPopulation(){
		Collections.sort(population, comparator);
	}
	
	public int getnextPopulationSize(int maxfes){
		int val = (int)Math.ceil(this.originalPopulationSize - (double)((this.fes + 1)*(this.originalPopulationSize - 1)) / (double)maxfes);
		return val;
	}
}
