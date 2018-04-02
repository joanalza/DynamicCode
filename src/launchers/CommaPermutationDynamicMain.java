package launchers;

import algorithms.comma.CommaPermutationDynamic;
import utils.ArrayUtils;

public class CommaPermutationDynamicMain {

	public static void main(String[] args) {
		int seed = -1;
		String evolutiontype = "hc";


		//		int popsize = 3	;
		//		int runs = 10;
		//		String mutationtype = "mallows_cayley";
		////		String mutationtype = "direct_cayley";
		//		int fixeddistance = 1;
		//		String problemfile = "data/fsp-tai20-5-0.txt";
		////		String problemfile = "data/qap-tai40b.dat";
		////		String problemfile = "data/lop-IO-be75np.txt";
		////		String problemfile = "data/tsp-bays29.tsp";
		//		String distanceinitialisertype = "exp1";
		//		double dmin = 0.01; // 0.1
		//		double dmax = 0.05; // 2
		//		int maxfes = 500; //-1;

		int popsize = Integer.parseInt(args[0]);
		String mutationtype = args[1];
		int fixeddistance = Integer.parseInt(args[2]);
		String problemfile = args[3];
		String distanceinitialisertype = args[4];
		double dmin = Double.parseDouble(args[5]);
		double dmax = Double.parseDouble(args[6]);

		int runs = Integer.parseInt(args[7]);
		//		int maxfes = -1;
		//		if(!args[8].equals("-1")){
		//			maxfes = Integer.parseInt(args[8]);
		//		}		

		int maxfes = Integer.parseInt(args[8]);

		
		boolean fixedDistanceVector = Boolean.valueOf(args[9]);
		boolean fixedPopSize = Boolean.valueOf(args[10]);

		String dynamicProfilePath = args[11];
		
		double[] bestFitnesses = new double[runs];

		for(int i=0; i<runs; i++){
			CommaPermutationDynamic comma = new CommaPermutationDynamic(seed, popsize, mutationtype, fixeddistance, problemfile, dynamicProfilePath, evolutiontype, distanceinitialisertype, dmin, dmax, maxfes, fixedDistanceVector, fixedPopSize);
			bestFitnesses[i] = comma.population.get(0).fitness;

//			System.out.println("Number of samples with different k:\n");
			
//			String out = "";
//			for(int k=0; k<=comma.problem.theSize; k++){
//				out+=";"+k+" cycles";
//			}
//			out+= "\n";
//			for (double theta : comma.mutation.countSamplesAtKCycles.keySet()) {
//				out += "theta = "+theta;
//				for (int kk=0; kk<=comma.problem.theSize; kk++) {
//					if(comma.mutation.countSamplesAtKCycles.get(theta).get(kk) != null){
//						out += ";"+comma.mutation.countSamplesAtKCycles.get(theta).get(kk);
//					}
//					else{
//						out += ";0";
//					}
//				}
//				out += "\n";
//			}

//			System.out.println(out);
		}

		System.out.println(problemfile+","+maxfes+","+popsize+","+mutationtype+","+fixeddistance+","+distanceinitialisertype+","+dmin+","+dmax+
				","+ArrayUtils.mean(bestFitnesses)+","+ArrayUtils.stdev(bestFitnesses)+","+ArrayUtils.min(bestFitnesses)+","+ArrayUtils.max(bestFitnesses)+","+fixedDistanceVector+","+fixedPopSize);
	}

}
