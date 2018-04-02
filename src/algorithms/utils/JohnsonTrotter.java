package algorithms.utils;

import java.util.ArrayList;

/*************************************************************************
 *  Compilation:  javac Perm.java
 *  Execution:    java Permutations N k
 *  
 *  Generate permutations by transposing adjacent elements using the
 *  Johnson-Trotter algorithm.
 *
 *  This program is a Java version based on the program SJT.c
 *  writen by Frank Ruskey.
 *  
 *     http://theory.cs.uvic.ca/inf/perm/PermInfo.html
 * 
 *  % java JohnsonTrotter 3
 *  012   (2 1)
 *  021   (1 0)
 *  201   (2 1)
 *  210   (0 1)
 *  120   (1 2)
 *  102   (0 1)
 *
 *************************************************************************/


public class JohnsonTrotter {

	ArrayList<int[]> orderedPermutations;
	
	public JohnsonTrotter(int N){
		orderedPermutations = new ArrayList<int[]>();
		perm(N);
	}
	
	public ArrayList<int[]> getJohnsonTrotterPermutations(){
		return this.orderedPermutations;
	}
	
	public int[] getJohnsonTrotterPermutationAtIndex(int index){
		return this.orderedPermutations.get(index);
	}
	
    public void perm(int N) {
     
    	//ArrayList<int[]> orderedPermutations = new ArrayList<int[]>();
    	
    	int[] p   = new int[N];     // permutation
        int[] pi  = new int[N];     // inverse permutation
        int[] dir = new int[N];     // direction = +1 or -1
        for (int i = 0; i < N; i++) {
            dir[i] = -1;
            p[i]  = i;
            pi[i] = i;
        }
        perm(0, p, pi, dir);
//        System.out.printf("   (0 1)\n");
    }

    public void perm(int n, int[] p, int[] pi, int[] dir) { 

        // base case - print out permutation
        if (n >= p.length) {
        	int[] thepermutation = new int[n];
            for (int i = 0; i < p.length; i++){
//                System.out.print(p[i]);
                thepermutation[i] = p[i];
            }
//            System.out.println("thepermutation: "+ArrayUtils.tableToString(thepermutation));
            this.orderedPermutations.add(thepermutation);
            return;
        }

        perm(n+1, p, pi, dir);
        for (int i = 0; i <= n-1; i++) {

            // swap 
//            System.out.printf("   (%d %d)\n", pi[n], pi[n] + dir[n]);
            int z = p[pi[n] + dir[n]];
            p[pi[n]] = z;
            p[pi[n] + dir[n]] = n;
            pi[z] = pi[n];
            pi[n] = pi[n] + dir[n];  

            perm(n+1, p, pi, dir); 
        }
        dir[n] = -dir[n];
    }


    public static void main(String[] args) {

       int N = 10;
       JohnsonTrotter jt = new JohnsonTrotter(N);
       
       for(int i=0; i<10; i++){
    	   System.out.println(i+"th: "+ArrayUtils.tableToString(jt.getJohnsonTrotterPermutationAtIndex(i)));
       }
    }
}
