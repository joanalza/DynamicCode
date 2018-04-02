package problems.generators.dynamicprofilegenerators;

import java.util.Random;

import algorithms.operators.mutations.PermutationMutation;
import algorithms.operators.mutations.DirectCayleyMutation;

public class CayleyIdentityPermutationGenerator extends IdentityPermutationGenerator{
	
	public CayleyIdentityPermutationGenerator(int nChanges, int size, Random rand, int distance) {
		super();
		this.nChanges = nChanges;
		this.size = size;
		this.rand = rand;
		this.distance = distance;
	}

	@Override
	public void generate() {
		this.generateChangeFes();
		
		permutations = new int[nChanges][size];
		
		// Identity permutation initialised to 01234....
    	int[] identityPerm = new int[this.size];
    	for(int i=0; i<this.size; i++){
    		identityPerm[i] = i;
    	}
		
		PermutationMutation mutation = new DirectCayleyMutation(this.rand, this.size);
		for(int i=0; i<this.nChanges; i++){
//			System.out.println("test: "+ArrayUtils.tableToString(identityPerm));
			identityPerm = mutation.mutate(identityPerm, distance, true);
			permutations[i] = identityPerm;
		}
//		algorithms.operators.mutations.DirectCayleyMutation.getCayleyDistance(permutations[0], permutations[1]);
//		System.out.println("test: "+ArrayUtils.tableToString(identityPerm));

	}

}
