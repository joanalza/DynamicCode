package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import utils.ArrayUtils;

public class DynamicPFSP_TotalFlowTime extends DynamicPermutationProblem {

	public int[][] processingTimes;

	public DynamicPFSP_TotalFlowTime(String path, String dynProfilePath) {
		// super(path);
		this.dynProfilePath = dynProfilePath;

//		Double optimum;
		ArrayList<ArrayList<Integer>> proc = new ArrayList<ArrayList<Integer>>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

			String line = " ";
			int counter = 0;
			int jobs = 0;
			int machines = 0;
			while (reader.ready()) {
				line = reader.readLine().trim();
				if (counter == 1) {
					String parts1[] = line.trim().split("\\s+");
					jobs = Integer.parseInt(parts1[0]);
					machines = Integer.parseInt(parts1[1]);
					optimum = Double.parseDouble(parts1[3]);
					// System.out.println("jobs: " + jobs);
					// System.out.println("machines: " + machines);
				}
				if (counter > 2 && counter <= (machines + 2)) {
					ArrayList<Integer> arr = new ArrayList<Integer>();
					// System.out.println("line");
					// System.out.println(line);
					String lineArray[] = line.trim().split("\\s+");
					for (int i = 0; i < jobs; i++) {
						// System.out.println("arrays");
						// System.out.println(lineArray[i]);
						int val = Integer.parseInt(lineArray[i]);
						arr.add(val);
					}
					proc.add(arr);

				}
				counter++;
			}
		} catch (IOException e) {
			System.out.println("Couldn't find file: " + path);
		}

		processingTimes = new int[proc.size()][proc.get(0).size()];
		for (int i = 0; i < proc.size(); i++) {
			processingTimes[i] = ArrayUtils.ArrayListToArray(proc.get(i));
			// System.out.println(Arrays.toString(processingTimes[i]));
		}

		this.theSize = this.processingTimes[0].length;

		// Identity permutation initialised to 01234....
		this.identityPerm = new int[this.theSize];
		for (int i = 0; i < this.theSize; i++) {
			this.identityPerm[i] = i;
		}
		// Set change of identity schedule
		this.setIdentityPermutationChanges();
	}

	@Override
	public boolean isNatural() {
		return false;
	}

	@Override

	public double evaluate(int[] genes) {
		int m_machines = processingTimes.length;
		int m_jobs = processingTimes[0].length;
		int[] m_timeTable = new int[m_machines];
		// int[] m_aux= new int[m_jobs];

		for (int i = 0; i < m_machines; i++)
			m_timeTable[i] = 0;
		int j, z, job;
		int machine;
		int prev_machine = 0;

		// int first_gene=genes[0];
		int first_gene = this.identityPerm[genes[0]];

		m_timeTable[0] = processingTimes[0][first_gene];
		for (j = 1; j < m_machines; j++) {
			m_timeTable[j] = m_timeTable[j - 1] + processingTimes[j][first_gene];
		}

		double fitness = m_timeTable[m_machines - 1];
		for (z = 1; z < m_jobs; z++) {
			// job=genes[z];
			job = this.identityPerm[genes[z]];

			// machine 0 is always incremental, so:
			m_timeTable[0] += processingTimes[0][job];
			prev_machine = m_timeTable[0];
			for (machine = 1; machine < m_machines; machine++) {
				m_timeTable[machine] = Math.max(prev_machine, m_timeTable[machine]) + processingTimes[machine][job];
				prev_machine = m_timeTable[machine];
			}

			fitness += m_timeTable[m_machines - 1];
		}

		// return -fitness;
		return fitness;
	}

	// public int getCompletionTimeJobOnMachine(int pos, int machine, int[]
	// permutation){
	// int c = 0;
	// int c1=0;
	// int c2 =0;
	//
	// if(pos == 0 && machine == 0){
	// c = processingTimes[0][permutation[0]];
	// }
	//
	// else{
	//
	// //has previous job finished on same machine?
	// if(pos != 0){
	// c1 = getCompletionTimeJobOnMachine(pos-1,machine,permutation);
	// }
	// else{
	// c1 = 0;
	// }
	//
	// //has current job finished on previous machine?
	// if(machine != 0){
	// c2 = getCompletionTimeJobOnMachine(pos,machine-1,permutation);
	// }
	// else{
	// c2 = 0;
	// }
	//
	// // compare both c1 and c2: the largest value represent the time the job
	// can actually start being processed on the specified machine
	// // it is then needed to add the processing time.
	// if (c1 >c2){
	// c = c1+processingTimes[machine][permutation[pos]];
	// }else{
	// c = c2+processingTimes[machine][permutation[pos]];
	// }
	// }
	//
	// return c;
	// }
	//
	//
	// public int getCompletionTimeJobOnMachineWithCache(int pos, int machine,
	// int[] permutation, boolean[][] completionTimesEvaluated, int[][]
	// completionTimes){
	// int c = 0;
	// int c1 = 0;
	// int c2 = 0;
	//
	// // System.out.println("**"+pos+"/"+machine+"/");
	// String out = pos+"/"+machine+"/";
	// if(completionTimesEvaluated[machine][permutation[pos]]){
	// out+="cache";
	// c = completionTimes[machine][permutation[pos]];
	// }
	// else{
	// out+="nocache";
	// if(pos == 0 && machine == 0){
	// c = processingTimes[0][permutation[pos]];
	// }
	//
	// else{
	//
	// //has previous job finished on same machine?
	// if(pos != 0){
	// c1 = getCompletionTimeJobOnMachineWithCache(pos-1,machine,permutation,
	// completionTimesEvaluated, completionTimes);
	// }
	// else{
	// c1 = 0;
	// }
	//
	// //has current job finished on previous machine?
	// if(machine != 0){
	// c2 = getCompletionTimeJobOnMachineWithCache(pos,machine-1,permutation,
	// completionTimesEvaluated, completionTimes);
	// }
	// else{
	// c2 = 0;
	// }
	//
	// // compare both c1 and c2: the largest value represent the time the job
	// can actually start being processed on the specified machine
	// // it is then needed to add the processing time.
	// if (c1 >c2){
	// c = c1+processingTimes[machine][permutation[pos]];
	// }else{
	// c = c2+processingTimes[machine][permutation[pos]];
	// }
	// }
	//
	// completionTimesEvaluated[machine][permutation[pos]] = true;
	// completionTimes[machine][permutation[pos]] = c;
	// }
	//
	// // System.out.print(out+"/"+c+"\n");
	// return c;
	// }

}
