package algorithms.operators.mutations;

import java.util.HashMap;
import java.util.Random;

public abstract class PermutationMutation {

	static Random rand;
	
	public HashMap<Double,HashMap<Integer, Integer>> countSamplesAtKCycles;

	public PermutationMutation() {
	}

	public abstract int[] mutate(int[] solution, double distance, boolean fixeddistance);
	
	public abstract boolean isIntDistance();
	
	public abstract boolean isNaturalOrder(); //tells if the smallest distance relates to the smallest changes to a solution (true) or to a big change (false)
}
