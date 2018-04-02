package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class PermutationUtils {

	public static int[] generateRandomPermutation(Random rand, int n) {
		int[] sol = new int[n];
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int j = 0; j < n; j++) {
			temp.add(j);
		}
		int j = 0;
		while (temp.size() > 0) {
			int index = rand.nextInt(temp.size());
			sol[j] = temp.get(index);
			temp.remove(index);
			j++;
		}
		return sol;
	}

	public static double[] generateRandomRK(Random rand, int n) {
		double[] rk = new double[n];

		for (int i = 0; i < rk.length; i++) {
			rk[i] = rand.nextDouble();
		}

		return rk;
	}

	public static int[] randomKeyToPermutation(double[] rk) {
		LinkedList<Double> p = new LinkedList<Double>();
		LinkedList<Integer> AL = new LinkedList<Integer>();

		double[] priorities = Arrays.copyOf(rk, rk.length);
		for (int i = 0; i < priorities.length; i++) {
			p.add(priorities[i]);
		}
		// System.out.println("unsorted: " + p);
		Collections.sort(p);
		// System.out.println("sorted: " + p);

		for (int i = 0; i < p.size(); i++) {
			for (int j = 0; j < priorities.length; j++) {
				if (p.get(i) == priorities[j] && !AL.contains(j)) {
					AL.add(j);
				}

			}

		}

		int[] AL1 = new int[AL.size()];
		for (int i = 0; i < AL.size(); i++) {
			AL1[i] = AL.get(i);
		}
		// System.out.println("AL: " + AL);
		// System.out.println("AL1: " + Arrays.toString(AL1));
		return AL1;
	}

	public static double[] normaliseRanks(int[] ranks1) {
		// System.out.println("ranks: " + Arrays.toString(ranks1));
		LinkedList<Double> p = new LinkedList<Double>();
		int size = ranks1.length;
		double[] AL = new double[size];
		int[] ranks = Arrays.copyOf(ranks1, size);
		for (int i = 0; i < size; i++) {
			// AL[i] = (double)(ranks[i]-1)/(size-1);
			AL[ranks[i]] = (double) (i) / (size - 1);
		}
		// System.out.println("normalised: " + Arrays.toString(AL));
		return AL;
	}
}
