package algorithms.utils;

import java.util.ArrayList;

public class Solution {

	private ArrayList<Integer> genes;
	private double fitness;
	
	public Solution(ArrayList<Integer> sol, double fit){
		this.genes = sol;
		this.fitness = fit;
	}
	
	public ArrayList<Integer> getGenes(){
		return this.genes;
	} 
	
	public double getSolutionFitness(){
		return this.fitness;
	}
	
	public void setSolution(ArrayList<Integer> sol, double fit){
		this.genes = sol;
		this.fitness = fit;
	}
	
	public void setSolutionFitness(double fit){
		this.fitness = fit;
	}
	
	
}
