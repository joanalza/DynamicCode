package algorithms.mutations;

import java.util.ArrayList;

public interface IPermutationMutation {

	public ArrayList<Integer> mutate (ArrayList<Integer> sol, int distance, boolean fixedDistance);
	
	public int getLowerAlterationLevel(int distance, int pbSize);
	
	public int getUpperAlterationLevel(int distance, int pbSize);
}
