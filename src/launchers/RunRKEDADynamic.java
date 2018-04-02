package launchers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import algorithms.rkeda.coolingschedules.LinearCooling2;

public class RunRKEDADynamic {

	public static void main(String[] args) {

		// String filePath = "./params/s20.sh";
		// String filePath = "./src/fssp50.sh";
		// String filePath = "./src/fssp100.sh";
		// String filePath = "./src/fssp200.sh";
		// String filePath = "./src/fssp500.sh";
		
//		String problempath = "data/qap-tai12a.dat";
//		String problempath = "data/fsp-tai20-5-0.txt";
		String problempath = "data/taillard_instances/tai20_10_0.fsp";
//		String problempath = "data/qap-tai25a.dat";
//		String problempath = "data/taillard_instances/tai50_10_0.fsp";
//		String problempath = "data/taillard_instances/tai100_20_0.fsp";
		
//		String dynamicFile = "data/dynamic/dynProfile-noChange.txt";
//		String dynamicFile = "data/dynamic/dynProfile-n12-c5-Cayley5.txt";
		String dynamicFile = "data/dynamic/dynProfile-n20-c3-Cayley5.txt";
//		String dynamicFile = "data/dynamic/dynProfile-n25-c5-Cayley5.txt";
//		String dynamicFile = "data/dynamic/dynProfile-n50-c5-Cayley5.txt";
//		String dynamicFile = "data/dynamic/dynProfile-n100-c15-Cayley30STIRLING.txt";
		
		
		int elitism = 0;
		
//		String coolingSchedule = "original";
//		double initialStdev = 1.0;
//		double coolingParam2 = 0.0; // minstd for linear cooling - lambda
		// heating coefficient for azizi cooling

//		 String coolingSchedule = "azizi_adaptive_overallbest";
		// double initialStdev = 0.015;
		// double coolingParam2 = 0.004; // minstd for linear cooling - lambda
		// heating coefficient for azizi cooling

		String coolingSchedule = "azizi_adaptive_currentbest";
		double coolingParam2 = 0.03; // minstd for linear cooling - lambda or heating coefficient for azizi cooling
		double initialStdev = 0.015;
		
		
		int populationSize = 500;
		Integer.toString(populationSize);
		int truncSize = 3;
		int fes = -1; // 2500000
		
		String diversity = "cayley";
		
        int nameInstance = problempath.split("/").length - 1;
        int nameDynFile = dynamicFile.split("/").length - 1;
		String resultsPath = "./results/";
		
		String saveAs = null;
		if(coolingSchedule.equals("original")){
			saveAs = problempath.split("/")[nameInstance].substring(0, problempath.split("/")[nameInstance].lastIndexOf(".")) + "-" +
        			dynamicFile.split("/")[nameDynFile].substring(0, dynamicFile.split("/")[nameDynFile].lastIndexOf("."))+ "-" +
        			"original_" + initialStdev + "_" + coolingParam2  + "-" +"elt" +elitism ;
    	}else{
    		saveAs = problempath.split("/")[nameInstance].substring(0, problempath.split("/")[nameInstance].lastIndexOf(".")) + "-" +
        			dynamicFile.split("/")[nameDynFile].substring(0, dynamicFile.split("/")[nameDynFile].lastIndexOf(".")) + "-" +
        			coolingSchedule.substring(15, coolingSchedule.length())+ "_" + initialStdev + "_" + coolingParam2 + "elt" +elitism;
    	}

		
		String[] str = new String[] { Integer.toString(populationSize), problempath, dynamicFile,
				Integer.toString(fes), Integer.toString(truncSize), Integer.toString(elitism),
				Double.toString(initialStdev), resultsPath, saveAs, coolingSchedule,
				Double.toString(coolingParam2), diversity };
		RKEDADynamicMain.main(str);
		//
		// try {
		// BufferedReader br = new BufferedReader(new FileReader(filePath));
		// String line = null;
		// while ((line = br.readLine()) != null) {
		//
		// if (line.contains("PARAMS") && !line.contains("java")) {
		// String line1 = line.substring(line.indexOf("\"") + 1, line.length() -
		// 1);
		//
		// String[] parts = line1.split("\\s");
		// for (int i = 0; i < parts.length; i++) {
		// if (parts[i].contains("..")) {
		// parts[i] = parts[i].replace("..", ".");
		// }
		//
		// }
		// System.out.println(Arrays.toString(parts));
		// RKEDADynamicMain.main(parts);
		// }
		// }
		// br.close();
		// } catch (Exception ex) {
		// Logger.getLogger(RunRKEDADynamic.class.getName()).log(Level.SEVERE,
		// null, ex);
		// }
	}

}
