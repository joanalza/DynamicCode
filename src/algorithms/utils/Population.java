package algorithms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvaluatedCandidate;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.factories.ListPermutationFactory;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import org.uncommons.watchmaker.framework.operators.ListOrderCrossover;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;

import utils.ArrayUtils;
import utils.PermutationUtils;

//import algorithms.comma.CommaAgent;
import algorithms.mutations.*;

public class Population {

	int realFEs; 
	ArrayList<Solution> solutions;
	int problemSize;
	CandidateFactory<ArrayList> factory;
	//	boolean maximization;
	FitnessEvaluator problem;

	boolean countEvals;

	public Population(int n, FitnessEvaluator problem, int pbSize){

		this.realFEs = 0;
		this.countEvals = true;

		//	maximization = problem.isNatural();
		this.problemSize = pbSize;
		this.problem = problem;

		//Candidate Factory
		List allItems = new ArrayList<Integer>();
		for(int i=0; i<=problemSize-1; i++){
			allItems.add(i);
		}
		factory = new ListPermutationFactory(allItems);

		solutions = new ArrayList<Solution>();

		for (int i=0; i<n; i++){
			ArrayList<Integer> sol = factory.generateRandomCandidate(new Random());
			solutions.add(new Solution(sol, problem.getFitness(sol,null)));
			realFEs ++;
		}

		this.sortPopulation(problem.isNatural());

	}

	
	public Population(List<int[]> candidates, FitnessEvaluator problem, int pbSize){

		this.realFEs = 0;
		this.countEvals = true;

		//	maximization = problem.isNatural();
		this.problemSize = pbSize;
		this.problem = problem;

		solutions = new ArrayList<Solution>();

		for (int i=0; i<candidates.size(); i++){
			ArrayList<Integer> sol = new ArrayList<Integer>(); //candidates.get(i);
			for(int j =0;j<candidates.get(i).length; j++){
				sol.add(candidates.get(i)[j]);
			}
			solutions.add(new Solution(sol, problem.getFitness(sol,null)));
			realFEs ++;
		}

		this.sortPopulation(problem.isNatural());

	}

	/**
	 * Sort the population with respect to the fitness
	 * and with respect to the type of problem (maximization/minimization)
	 * */
	public void sortPopulation(boolean max){

		// Order agents list by agents' fitness

		if(max){
			Collections.sort(solutions,new Comparator<Solution>() {

				@Override
				public int compare(Solution arg0, Solution arg1) {
					int val = 0;
					if(arg0.getSolutionFitness() < arg1.getSolutionFitness()){
						val = 1;
					}
					else{
						val = -1;
					}
					return val;
				}
			});
		}
		else{
			Collections.sort(solutions,new Comparator<Solution>() {

				@Override
				public int compare(Solution arg0, Solution arg1) {
					int val = 0;
					if(arg0.getSolutionFitness() > arg1.getSolutionFitness()){
						val = 1;
					}
					else{
						val = -1;
					}
					return val;
				}
			});
		}
	}


	//	public ArrayList<Solution> getSolutions(){
	//		return this.solutions;
	//	}


	public List<EvaluatedCandidate<ArrayList>> getSolutions(){

		List<EvaluatedCandidate<ArrayList>> candidates = new ArrayList<EvaluatedCandidate<ArrayList>>();
		for(Solution s : solutions){
			EvaluatedCandidate cand = new EvaluatedCandidate(s.getGenes(), s.getSolutionFitness());
			candidates.add(cand);
		}
		return candidates;
	}


	public List<EvaluatedCandidate<ArrayList>> evolve(FitnessEvaluator surrogateProblem, int selectionStrategy, int crossoverStrategy, int mutationStrategy, boolean evaluate){

		ArrayList<EvaluatedCandidate<ArrayList>> newcandidates = new ArrayList<EvaluatedCandidate<ArrayList>>();

		FitnessEvaluator tempProblem;
		if(surrogateProblem == null){
			// evolve with true fitness function
			tempProblem = this.problem;
			countEvals = true;
		}
		else{
			// evolve with the specified fitness function (i.e. surrogate)
			tempProblem = surrogateProblem;
			countEvals = false;
		}

		//this.printPopulation();


		// evaluate population
		if(evaluate){
			this.evaluateAllSolutions(tempProblem);
		}

		// selection
		SelectionStrategy selection = new TruncationSelection(0.99);
		int selectionSize = 2;
		switch (selectionStrategy){
		case 1: 
			selection = new TruncationSelection(0.99);
			break;
		case 2:
			Probability tournamentProbability = new Probability(0.51); // tournament size = 2 with watchmaker
			selection = new TournamentSelection(tournamentProbability);
			break;
		}
		List<ArrayList<Integer>> selectedCandidates = new ArrayList<ArrayList<Integer>>();
		selectedCandidates = selection.select(this.getSolutions(), tempProblem.isNatural(), selectionSize, new Random());

		// Xover
		AbstractCrossover crossover = new ListOrderCrossover(); 
		double crossoverProbability = 0.9;
		switch (crossoverStrategy){
		case 1: 
			crossover = new ListOrderCrossover(new Probability(crossoverProbability)); // PMX
			break;
		}
		List<ArrayList<Integer>> crossoveredCandidates = new ArrayList<ArrayList<Integer>>();
//		crossoveredCandidates = crossover.apply(selectedCandidates, new Random());

		crossoveredCandidates = selectedCandidates; // to jump the crossover step

		// mutation
		IPermutationMutation mutation = new InsertMutation();
//		int mutationDistance = (int)this.problemSize/2; // max distance
		int mutationDistance = 1; // max distance
		boolean fixedDistance = false;
		double mutationProbability = 0.9;
		switch(mutationStrategy){
		case 1:
			mutation  = new SwapMutation();
			break;
		}

		List<ArrayList<Integer>> mutatedCandidates = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> s : crossoveredCandidates){
			//			System.out.println("selected_for_mutation: "+s);
			ArrayList<Integer> newSol = null;
			Random rand = new Random();
			double temp = rand.nextDouble();
			if(temp < mutationProbability){
				newSol = mutation.mutate(s, mutationDistance, fixedDistance);

			}
			else{
				newSol = s;
			}
			mutatedCandidates.add(newSol);
		}

		// re-insertion in the population
		for(ArrayList<Integer> s : mutatedCandidates){
			Solution newSol = new Solution(s, tempProblem.getFitness(s, null));
			if(countEvals){
				realFEs ++;
			}

//			System.out.println("newsol: "+newSol.getSolutionFitness());
			
			for (int i = 0; i<this.getSolutions().size(); i++){
				if(tempProblem.isNatural()){
//					System.out.println("worse: "+this.getSolutions().get(i).getFitness()+" - new: "+newSol.getSolutionFitness());
					if(newSol.getSolutionFitness() >= this.getSolutions().get(i).getFitness()){
//						System.out.println("Replace "+i);
						this.insertNewSolutionInPopulation(newSol, tempProblem);
						ArrayList<Integer> tempgenes =  new ArrayList<Integer>();
						tempgenes.addAll(newSol.getGenes());
						double tempfit = newSol.getSolutionFitness();
						EvaluatedCandidate<ArrayList> tempcandidate = new EvaluatedCandidate<ArrayList>(tempgenes, tempfit);
						newcandidates.add(tempcandidate);
						break;
					}
				}
				else{
//					System.out.println("worse: "+this.getSolutions().get(i).getFitness()+" - new: "+newSol.getSolutionFitness());
					if(newSol.getSolutionFitness() <= this.getSolutions().get(i).getFitness()){
						this.insertNewSolutionInPopulation(newSol, tempProblem);
						ArrayList<Integer> tempgenes =  new ArrayList<Integer>();
						tempgenes.addAll(newSol.getGenes());
						double tempfit = newSol.getSolutionFitness();
						EvaluatedCandidate<ArrayList> tempcandidate = new EvaluatedCandidate<ArrayList>(tempgenes, tempfit);
						newcandidates.add(tempcandidate);
						break;
					}
				}
			}
		}
		
		this.sortPopulation(tempProblem.isNatural());
		
		
		
		if(evaluate){
			return this.getSolutions(); // when the whole population needs to be compared with the already seen solutions, this can take some time. Only done when the whole population is re-evaluated following the use of the surrogate fitness
		}
		else{
			return newcandidates; // the rest of the time, we only compare the new offsprings
		}

		
		
	}


	public void evaluateAllSolutions(FitnessEvaluator eval){
		for(Solution s : solutions){
			s.setSolutionFitness(eval.getFitness(s.getGenes(),null));
			if(countEvals){
				realFEs ++;
			}
		}
		this.sortPopulation(eval.isNatural());
	}

	public void insertNewSolutionInPopulation(Solution newSol, FitnessEvaluator eval){
		/*if(!PermutationUtils.isSolutionInPopulation(newSol, this)){ // prevent duplicate individuals in the population
			this.solutions.get(this.solutions.size()-1).setSolution(newSol.getGenes(), newSol.getSolutionFitness());
			this.sortPopulation(eval.isNatural());
		}*/
	}

	public void printPopulation(){
		System.out.println("-------------");
		for(Solution ind : this.solutions){
			System.out.println(ind.getGenes() +" - "+ ind.getSolutionFitness());
		}
		System.out.println("-------------");
	}


	public int getNumberOfRealFitnessEvaluations(){
		return this.realFEs;
	}

	
	public Solution getBestSolution(boolean max){
		this.sortPopulation(max);
		return this.solutions.get(0);
	}
	
	public double getMeanFitness(){
		double val = 0.0;
		
		double[] allfit = new double[this.solutions.size()];
		for(int i=0; i<this.solutions.size(); i++){
			allfit[i] = this.problem.getFitness(this.solutions.get(i).getGenes(), null);
		}
		val = ArrayUtils.mean(allfit);
		
		return val;
	}
	
	public double getMeanFitness(FitnessEvaluator prob){
		double val = 0.0;
		
		double[] allfit = new double[this.solutions.size()];
		for(int i=0; i<this.solutions.size(); i++){
			allfit[i] = prob.getFitness(this.solutions.get(i).getGenes(), null);
		}
		val = ArrayUtils.mean(allfit);
		
		return val;
	}
	
}
