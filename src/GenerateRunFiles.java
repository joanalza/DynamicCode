



public class GenerateRunFiles {


	public static void main(String[] args) {

		String problemfile = "data/fsp-tai100-20-0.txt";
//		String dynamicProfilePath  = "data/dynProfile-n25-c5-Cayley5.txt";
//		String dynamicProfilePath  = "data/dynProfile-n50-c5-Cayley10.txt";
		String dynamicProfilePath  = "data/dynProfile-noChange.txt";
		
		String coolingSchedule = "original";
		double initialStdev = 0.05;
	    double coolingParam2 = 0.0; // minstd for linear cooling - lambda heating coefficient for azizi cooling
		
		
//		String coolingSchedule = "azizi_adaptive_overallbest"; 
//	    double initialStdev = 0.015;
//        double coolingParam2 = 0.004; // minstd for linear cooling - lambda heating coefficient for azizi cooling
        
//		String coolingSchedule = "azizi_adaptive_currentbest";
//        double initialStdev = 0.015;
//        double coolingParam2 = 0.004; // minstd for linear cooling - lambda heating coefficient for azizi cooling
        
		int populationSize = 50;
        String problempath = problemfile;
        String dynamicFile = dynamicProfilePath;
        int fes = -1;
        int truncSize = 2;
        int elitism = 0;
     
       
        String resultsPath = "./results/";
        String saveAs = "test_rkeda";
        
        int indexmin = 21;
        int nRuns = 20;
        
        String out = "!/bin/bash\n#$ -N rkedad\n#$ -q serial.q\n#$ -o out/rkedad.$TASK_ID.dat"
				+ "\n#$ -e err/rkedad.$TASK_ID.dat\n#$ -cwd\n#$ -t "+indexmin+"-"+(indexmin+nRuns-1)+"\n\n";
        
        for(int i=0; i<nRuns; i++){
        	out += "PARAMS["+(indexmin+i)+"]=\""+populationSize+" "+problempath+" "+dynamicFile+" "+fes+" "+truncSize+" "+elitism+" "+initialStdev+" "+resultsPath+" "+saveAs+" "+coolingSchedule+" "+coolingParam2+" "+"a"+"\"\n";;

        }
        
        System.out.println(out);
	}

	
	

}
