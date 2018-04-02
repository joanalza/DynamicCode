package algorithms.mutations;

import java.util.ArrayList;
import java.util.Random;

import utils.ArrayUtils;

public class SwapMutation implements IPermutationMutation{

	@Override
	public ArrayList<Integer> mutate(ArrayList<Integer> sol, int distance, boolean fixedDistance) {

		Random rand = new Random();
		int startPosition = rand.nextInt(sol.size());
		int endPosition = 0;
		
		int dist = 0;
		
		if(fixedDistance){ // Fixed distance: distance will be the one specified
			dist = distance;
		}
		else{ // Unfixed distance: distance will be chosen in the interval [0 - specified distance]
			Random rand2 = new Random();
			dist = rand2.nextInt(distance)+1;
		}

		boolean toTheLeft = true;
		
		if (startPosition - dist < 0){ // Sometimes, we need to force the gene to be selected to be the one on the RIGHT of the starting gene
			toTheLeft = false;
		}
		else if (startPosition + dist > sol.size()-1){ // Sometimes, we need to force the gene to be selected to be the one on the LEFT of the starting gene
			toTheLeft = true;
		}
		else{ // The rest of the time, we can either select the gene to be selected on the RIGHT OR LEFT at random
			Random rand3 = new Random();
			toTheLeft = rand3.nextBoolean();
		}
		
		int[] oldElements = ArrayUtils.ArrayListToArray(sol);
		int[] newElements = new int[oldElements.length];
		
		if (toTheLeft){
			endPosition = startPosition - dist;
		}
		else{
			endPosition = startPosition + dist;
		}
					
		for (int i=0; i<oldElements.length; i++){
			if (i == startPosition){
				newElements[i] = oldElements[endPosition];
			}
			else if (i == endPosition){
				newElements[i] = oldElements[startPosition];
			}
			else{
				newElements[i] = oldElements[i];
			}
		}
		
		ArrayList<Integer> c = new ArrayList<Integer>();
		for(int i=0; i<newElements.length; i++){
			c.add(newElements[i]);
		}
		
		return c;
	}

	@Override
	public int getLowerAlterationLevel(int d, int pbSize) {
		int rho = 0;
		rho = d + (d-1);
		return rho;
	}

	@Override
	public int getUpperAlterationLevel(int d, int pbSize) {
		return getLowerAlterationLevel(d, pbSize);
	}

}
