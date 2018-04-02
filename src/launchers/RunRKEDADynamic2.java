package launchers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import algorithms.rkeda.coolingschedules.LinearCooling2;

public class RunRKEDADynamic2 {

	public static void main(String[] args) {

		double[] arrayinitialStdev = { 0, 0.01, 0.02, 0.03, 0.04 };
		double[] arraycoolingParam2 = { 0.01, 0.02, 0.03, 0.04, 0.05, 0.06 };
		
		int runs = 10;
		
//		for (int a = 0; a<10; a++){
			for (int i = 0; i < arraycoolingParam2.length; i++) {
				for (int j = 0; j < arrayinitialStdev.length; j++) {
					for (int k = 0; k < runs; k++) {
	
						
//						String problempath = "data/taillard_instances/tai20_10_0.fsp";
//						String problempath = "data/taillard_instances/tai20_10_" + a +".fsp";
//						String problempath = "data/taillard_instances/tai50_10_0.fsp";
//						String problempath = "data/taillard_instances/tai50_10_" + a +".fsp";
						String problempath = "data/taillard_instances/tai100_10_0.fsp";
	
//						String dynamicFile = "data/dynamic/dynProfile-n20-c10-Cayley10.txt";
//						String dynamicFile = "data/dynamic/dynProfile-n20-c3-Cayley5.txt";
//						String dynamicFile = "data/dynamic/dynProfile-n50-c10-Cayley10.txt";
	//					String dynamicFile = "data/dynamic/dynProfile-n100-c3-Cayley5.txt";
						String dynamicFile = "data/dynamic/dynProfile-n100-c10-Cayley10.txt";
	
//						String resultsPath = "./results/diffDynRKEDA/tai50_10/";
//						String resultsPath = "./results/testRKEDA/tai50_10_0(0-0.2)/";
						String resultsPath = "./results/testRKEDA/tai100_10_0-diversity/";
						
						String coolingSchedule = "azizi_adaptive_currentbest";
						double coolingParam2 = arraycoolingParam2[i];
						double initialStdev = arrayinitialStdev[j];
						
				        String diversity = "cayley"; // null, "cayley" or "hamming".
	
				        int elitism = 0;
						int populationSize = 500;
						Integer.toString(populationSize);
						int truncSize = 5;
						int fes = -1; // 2500000
	
						int nameInstance = problempath.split("/").length - 1;
						int nameDynFile = dynamicFile.split("/").length - 1;						
	
						String saveAs = null;
						if (coolingSchedule.equals("original")) {
							saveAs = problempath.split("/")[nameInstance].substring(0,
									problempath.split("/")[nameInstance].lastIndexOf("."))
									+ "-"
									+ dynamicFile.split("/")[nameDynFile].substring(0,
											dynamicFile.split("/")[nameDynFile].lastIndexOf("."))
									+ "-" + "original_" + initialStdev + "_" + coolingParam2 + "-" + "elt" + elitism + "--"
									+ k;
						} else {
							saveAs = problempath.split("/")[nameInstance].substring(0,
									problempath.split("/")[nameInstance].lastIndexOf("."))
									+ "-"
									+ dynamicFile.split("/")[nameDynFile].substring(0,
											dynamicFile.split("/")[nameDynFile].lastIndexOf("."))
									+ "-" + coolingSchedule.substring(15, coolingSchedule.length()) +  "_" + initialStdev  + "_" +
											coolingParam2 + "-" + "elt" + elitism + "--" + k;
						}
	
						String[] str = new String[] { Integer.toString(populationSize), problempath, dynamicFile,
								Integer.toString(fes), Integer.toString(truncSize), Integer.toString(elitism),
								Double.toString(initialStdev), resultsPath, saveAs, coolingSchedule,
								Double.toString(coolingParam2), diversity };
						
						
						
						RKEDADynamicMain.main(str);
					}
				}
			}
//		}
	}

}
