package tests;
import utils.ArrayUtils;

public class distances {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] e = {0,1,2,3,4,5};
//		int[] sigma = {10,9,8,7,6,5,4,3,2,1,0};
		int[] pi = {3,0,5,4,2,1};
		int[] sigma = {5,2,1,0,3,4};
		
		System.out.println("Cayley distance between two permutations:" + ArrayUtils.getCayleyDistance(pi, sigma));
		System.out.println("Hamming distance between two permutations:" + ArrayUtils.getHammingDistance(e, sigma));
	}

}
