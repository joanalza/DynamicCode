package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import utils.ArrayUtils;

public class DynamicPFSP_Makespan extends DynamicPermutationProblem {

	public int[][] processingTimes;

	public DynamicPFSP_Makespan(String path, String dynProfilePath) {
		this.dynProfilePath = dynProfilePath;

		ArrayList<ArrayList<Integer>> proc = new ArrayList<ArrayList<Integer>>();

		try {
			BufferedReader reader = new BufferedReader (new FileReader (new File (path)));

			String templine = " ";
			String separator = ",";
			int line=0;

			optimum = Double.parseDouble(reader.readLine().trim());
			templine = reader.readLine().trim(); // blank line


			while(reader.ready()){

				ArrayList<Integer> arr = new ArrayList<Integer>();
				templine = reader.readLine().trim();
				StringTokenizer t = new StringTokenizer(templine,separator);
				String[] lineArray = templine.split(separator);
				for(int i=0; i<lineArray.length; i++){
					int val = Integer.parseInt(lineArray[i]);
					arr.add(val);
				}
				proc.add(arr);

				line++;
			}

		}   
		catch (IOException e) {System.out.println("Couldn't find file: " + path);}          

		processingTimes = new int[proc.size()][proc.get(0).size()];
		for(int i=0; i<proc.size(); i++){
			processingTimes[i] = ArrayUtils.ArrayListToArray(proc.get(i));
		}

		this.theSize = this.processingTimes[0].length;
		
		// Identity permutation initialised to 01234....
    	this.identityPerm = new int[this.theSize];
    	for(int i=0; i<this.theSize; i++){
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
	public double evaluate(int[] solution) {
		double score = 0.0;

		int[][] completionTimes= new int[processingTimes.length][processingTimes[0].length];
		boolean[][] completionTimesEvaluated= new boolean[processingTimes.length][processingTimes[0].length];
		score =  getCompletionTimeJobOnMachineWithCache(solution.length-1, processingTimes.length-1, solution, completionTimesEvaluated, completionTimes);

		return score;
	}

	public int getCompletionTimeJobOnMachine(int pos, int machine, int[] permutation){
		int c = 0;
		int c1=0;
		int c2 =0;

		if(pos == 0 && machine == 0){
			c = processingTimes[0][this.identityPerm[permutation[0]]];
		}

		else{

			//has previous job finished on same machine?
			if(pos != 0){
				c1 = getCompletionTimeJobOnMachine(pos-1,machine,permutation);
			} 
			else{
				c1 = 0;
			}

			//has current job finished on previous machine?
			if(machine != 0){
				c2 = getCompletionTimeJobOnMachine(pos,machine-1,permutation);
			}
			else{
				c2 = 0;	
			}

			// compare both c1 and c2: the largest value represent the time the job can actually start being processed on the specified machine
			// it is then needed to add the processing time.
			if (c1 >c2){
				c = c1+processingTimes[machine][this.identityPerm[permutation[pos]]];
			}else{
				c = c2+processingTimes[machine][this.identityPerm[permutation[pos]]];	
			}
		}

		return c;
	}
	
	
	public int getCompletionTimeJobOnMachineWithCache(int pos, int machine, int[] permutation, boolean[][] completionTimesEvaluated, int[][] completionTimes){
		int c = 0;
		int c1 = 0;
		int c2 = 0;

		//total += this.matrixA[i][j] * this.matrixB[this.identityPerm[solution[i]]][this.identityPerm[solution[j]]];
//		this.identityPerm[permutation[pos]]
	//	System.out.println("**"+pos+"/"+machine+"/");
//		String out = pos+"/"+machine+"/";
		if(completionTimesEvaluated[machine][this.identityPerm[permutation[pos]]]){
//			out+="cache";
			c = completionTimes[machine][this.identityPerm[permutation[pos]]];
		}
		else{
//			out+="nocache";
			if(pos == 0 && machine == 0){
				c = processingTimes[0][this.identityPerm[permutation[pos]]];
			}

			else{

				//has previous job finished on same machine?
				if(pos != 0){
					c1 = getCompletionTimeJobOnMachineWithCache(pos-1,machine,permutation, completionTimesEvaluated, completionTimes);
				} 
				else{
					c1 = 0;
				}

				//has current job finished on previous machine?
				if(machine != 0){
					c2 = getCompletionTimeJobOnMachineWithCache(pos,machine-1,permutation, completionTimesEvaluated, completionTimes);
				}
				else{
					c2 = 0;	
				}

				// compare both c1 and c2: the largest value represent the time the job can actually start being processed on the specified machine
				// it is then needed to add the processing time.
				if (c1 >c2){
					c = c1+processingTimes[machine][this.identityPerm[permutation[pos]]];
				}else{
					c = c2+processingTimes[machine][this.identityPerm[permutation[pos]]];	
				}
			}

			completionTimesEvaluated[machine][this.identityPerm[permutation[pos]]] = true;
			completionTimes[machine][this.identityPerm[permutation[pos]]]  = c;
		}

	//	System.out.print(out+"/"+c+"\n");
		return c;
	}

	
}
