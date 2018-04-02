package algorithms.comma;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import algorithms.comma.distances.DistanceVectorInitialiser;
import algorithms.comma.distances.ExponentialDistanceInitialiser;
import algorithms.comma.evolution.EvolutionForDynamicProblems;
import algorithms.comma.evolution.HillClimberForDynamicProblems;
import algorithms.operators.mutations.DirectCayleyMutation;
import algorithms.operators.mutations.MallowsCayleyMutation;
import algorithms.operators.mutations.PermutationMutation;
import problems.DynamicPFSP_Makespan;
import problems.DynamicPermutationProblem;
import problems.DynamicQAP;
import utils.ArrayUtils;
import utils.PermutationUtils;

public class CommaPermutationDynamic {

	Random rand;
	public ArrayList<Solution> population;
	public PermutationMutation mutation;
	boolean fixeddistance;
	public DynamicPermutationProblem problem;
	Comparator<Solution> comparator;
	EvolutionForDynamicProblems evolution;
	DistanceVectorInitialiser distanceinitialiser;
	int maxfes;
	
	public CommaPermutationDynamic(int seed, int popsize,
			String mutationtype, int fixeddistance,
			String problemfile, String dynamicProfilePath,
			String evolutiontype, String distanceinitialisertype,
			double dmin, double dmax, int maxfes, boolean fixedDistanceVector, boolean fixedPopSize) {
		if(seed == -1){
			this.rand = new Random();
		}
		else{
			this.rand = new Random(seed);
		}
		
		this.maxfes = maxfes;

		
		
		
		// Create problem instance
		if(problemfile.contains("qap")){
			problem = new DynamicQAP(problemfile, dynamicProfilePath);
		}
		else if (problemfile.contains("fsp")){
			problem = new DynamicPFSP_Makespan(problemfile, dynamicProfilePath);
		}
		
		if(maxfes == -1){
			this.maxfes = (int)(1000*Math.pow(this.problem.problemSize(),2));
		}
		
		this.population = new ArrayList<Solution>();
		
		int thepopsize = popsize;
		if(popsize == -1){
			thepopsize = this.problem.theSize * 10;
		}
		
		for(int i=0; i<thepopsize; i++){
			Solution newsol = new Solution();
//			System.out.println(rand);
//			System.out.println(problem);
//			System.out.println(problem.problemSize());
			newsol.individual = PermutationUtils.generateRandomPermutation(rand, problem.problemSize());
			newsol.fitness = problem.evaluate(newsol.individual);
			newsol.id = i;
			this.population.add(newsol);
		}

		if(mutationtype.startsWith("mallows_cayley")){
			int offset = Integer.parseInt(mutationtype.substring(14));
			this.mutation = new MallowsCayleyMutation(rand, problem.problemSize(), offset);
		}
		else if(mutationtype.equals("direct_cayley")){
			double theta = 0.5;
			this.mutation = new DirectCayleyMutation(rand, problem.problemSize());
		}
		
		this.fixeddistance = (fixeddistance == 1) ? true:false;
		
		this.comparator = new FitnessDescending();

		if(distanceinitialisertype.startsWith("exp")){
			int pow = Integer.parseInt(distanceinitialisertype.substring(3));
			this.distanceinitialiser = new ExponentialDistanceInitialiser(mutation.isNaturalOrder(), mutation.isIntDistance(), dmin, dmax, rand, thepopsize, pow, fixedDistanceVector);
		}
		
	
		if(evolutiontype.equals("hc")){
			this.evolution = new HillClimberForDynamicProblems(this.distanceinitialiser, population, mutation, this.fixeddistance, problem, rand, comparator);
		}
		
//		System.out.println("Distances: ");
//		for(int i=0; i<this.distanceinitialiser.getDistanceVector().length; i++){
//			System.out.println(i+"; "+this.distanceinitialiser.getDistanceVector()[i]);
//		}
		
		this.evolution.evolve(this.maxfes, fixedPopSize);
	}

}
