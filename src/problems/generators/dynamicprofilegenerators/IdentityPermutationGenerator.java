package problems.generators.dynamicprofilegenerators;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import utils.ArrayUtils;

public abstract class IdentityPermutationGenerator {

	int nChanges;
	int size;
	Random rand;
	int distance;
	boolean fixedDistance = true;
	
	double[] changesFes;
	int[][] permutations;
	
	public abstract void generate();
	
	public void generateChangeFes(){
		// generate changes in a periodic way for a start
		
		changesFes = new double[nChanges];
		double step = (double)1 / (double)(nChanges+1);
		double val = 0.0;
		for(int i = 0; i<nChanges; i++){
			val += step;
			changesFes[i] = val;
		}
	}

	public void printInstance(){
		DecimalFormat df = new DecimalFormat("#.####");
		System.out.println(nChanges);
		for(int i = 0; i<nChanges; i++){
			//System.out.println(changesFes[i]+";"+ArrayUtils.tableToString(permutations[i]));
			System.out.println(df.format(changesFes[i])+";"+ArrayUtils.tableToString(permutations[i]));
		}
	}
	
	public void createInstance(String path, String saveAs) throws IOException{
        BufferedWriter br1 = new BufferedWriter(new FileWriter(path + "" + saveAs));
		DecimalFormat df = new DecimalFormat("#.####");
		String out = nChanges + "\n" ;
//		System.out.println(nChanges);
		for(int i = 0; i<nChanges; i++){
			//System.out.println(changesFes[i]+";"+ArrayUtils.tableToString(permutations[i]));
			out += df.format(changesFes[i])+";"+ArrayUtils.tableToString(permutations[i])+"\n";
		}
        br1.write(out);
        br1.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getCayleyDistance(int[] sigma, int[] tau){
		int n = sigma.length ;
		Vector<Integer> cycle = new Vector<Integer>();
		Vector<Vector<Integer>> totalCycles = new Vector<Vector<Integer>>();
		int indexSigma = 0;
		boolean contains = false;
		if (n == tau.length) {
			while (indexSigma < n) {
				Iterator<Vector<Integer>> it = totalCycles.iterator();
				while (it.hasNext() && contains == false)
					contains = ((Vector) it.next()).contains(tau[indexSigma]);
				if ( !contains){
					while ((cycle.isEmpty() || !cycle.contains( tau[indexSigma] ))){
						cycle.add(tau[indexSigma]);
						indexSigma = Arrays.stream(sigma).boxed().collect(Collectors.toList()).indexOf(tau[indexSigma]);
						//indexSigma = ArrayUtils.indexOf(sigma, tau[indexSigma]);
					}
					totalCycles.add((Vector<Integer>) cycle.clone());
					cycle.clear();
				}
				contains = false;
				indexSigma++;
			}
			return n - totalCycles.size();
		}
		return 0;
	}
	
}

