package algorithms.mutations;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import utils.ArrayUtils;

public class ScrambleMutation implements IPermutationMutation{

	@Override
	public ArrayList<Integer> mutate(ArrayList<Integer> sol, int distance,
			boolean fixedDistance) {

		int[] oldElements = ArrayUtils.ArrayListToArray(sol);
		int[] newElements = new int[oldElements.length];

		/** Define distance between the two cutting points, ie. the size of the substring to invert*/
		int dist = 0;

		if(fixedDistance){ // Fixed distance: distance will be the one specified
			dist = distance;
		}
		else{ // Unfixed distance: distance will be chosen in the interval [0 - specified distance]
			Random rand2 = new Random();
			dist = rand2.nextInt(distance)+1;
		}

		/** Define first inverting point*/
		Random rand = new Random();
		int startPosition = rand.nextInt(sol.size());
		int endPosition = 0;


		/** Define if the second cutting point is on the right or left of the first cutting point*/
		boolean toTheLeft = true;

		if (startPosition - dist  < 0){ // Sometimes, we need to force the gene to be selected to be the one on the RIGHT of the starting gene
			toTheLeft = false;
		}
		else if (startPosition + dist> sol.size() - 1){ // Sometimes, we need to force the gene to be selected to be the one on the LEFT of the starting gene
			toTheLeft = true;
		}
		else{ // The rest of the time, we can either select the gene to be selected on the RIGHT OR LEFT at random
			Random rand3 = new Random();
			toTheLeft = rand3.nextBoolean();
		}

		/** Define second inverting point based on first point and distance*/

		if (toTheLeft){
			endPosition = startPosition - dist;
		}
		else{
			endPosition = startPosition + dist;
		}

		/** If endPosition is lower than startPosition, their values are swapped*/
		if(endPosition<startPosition){
			int tempStart = endPosition;
			int tempEnd = startPosition;
			startPosition = tempStart;
			endPosition = tempEnd;
		}


		/** Extract the sub-permutation*/
		int[] sub = new int[dist + 1];
		for(int i=0; i<dist + 1; i++){
			sub[i] = oldElements[startPosition+i];
		}

		/**Scramble the genes in the sub-permutation*/
		int[] temp_sub = new int[sub.length];
		Vector<Integer> indices = new Vector<Integer>();
						
		for(int i=0; i<sub.length; i++){
			indices.add(sub[i]);
		}

		Random rand4 = new Random();
		for (int i =0; i<sub.length;i++){
			int index = rand4.nextInt(indices.size());
			temp_sub[i] = indices.get(index); 
			indices.removeElementAt(index);
		}
						
		sub = temp_sub;


		/** Loop through oldElements and sub to fill in newElement*/
		for(int i=0; i<startPosition; i++){
			newElements[i] = oldElements[i];
		}

		for(int i =0; i <sub.length; i++){
			newElements[startPosition+i] = sub[i];
		}

		for(int i=1; i<(sol.size() - startPosition - dist); i++){
			newElements[startPosition+dist+i] = oldElements[startPosition+dist+i];
		}


		ArrayList<Integer> c = new ArrayList<Integer>();
		for(int i=0; i<newElements.length; i++){
			c.add(newElements[i]);
		}
		
		return c;
	}

	@Override
	public int getLowerAlterationLevel(int d, int pbSize) {
		int rho =0;
		rho = 1;
		return rho;
	}

	@Override
	public int getUpperAlterationLevel(int d, int pbSize) {
		int rho =0;
		rho = (d*(d+1))/2;
		return rho;
	}

}
