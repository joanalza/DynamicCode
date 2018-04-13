/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.rkeda;

import algorithms.rkeda.coolingschedules.AziziAdaptiveCooling;
import algorithms.rkeda.coolingschedules.Cooling;
import algorithms.rkeda.coolingschedules.LinearCooling;
import algorithms.rkeda.coolingschedules.LinearCooling2;
import algorithms.rkeda.edaUtil.EDAUtil;
import algorithms.rkeda.edaUtil.Mutation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import problems.DynamicPFSP_Makespan;
import problems.DynamicPFSP_TotalFlowTime;
import problems.DynamicPermutationProblem;
import problems.DynamicQAP;
import algorithms.rkeda.representation.rk;
import utils.ArrayUtils;
import utils.Orderings;

/**
 *
 * @author 1013288
 */
public class RKEDA {

	
	String coolingSchedule;
	Cooling cooling;
	
//	String coolingSchedule = "original"; 
//	String coolingSchedule = "azizi_adaptive_overallbest"; 
//	String coolingSchedule = "azizi_adaptive_currentbest"; 
	
//	double stdev_min = 0.015;
//	double lambda = 0.004;
	
    int populationSize;
    String problempath;
    String dynamicpath;
    int FEs;
    int truncSize;
    boolean elitism;
    double mutationRate;
    String resultsPath;
    String saveAs;

    public RKEDA(int populationSize, String problempath, String dynamicpath, int FEs, int truncSize, boolean elitism, double mutationRate, String resultsPath, String saveAs, String coolingSchedule) {
        this.populationSize = populationSize;
        this.problempath = problempath;
        this.dynamicpath = dynamicpath;
        this.FEs = FEs;
        this.truncSize = truncSize;
        this.elitism = elitism;
        this.mutationRate = mutationRate;
        this.resultsPath = resultsPath;
        this.saveAs = saveAs;
        this.coolingSchedule = coolingSchedule;
    }
    
     public void runRKEDAforFSSP(double initialStdev) throws ParseException, IOException {

//        FSSP permProb = new FSSP(path + probName);
//
//        int length = permProb.problemSize();
//
//        ArrayList<rk> population = new ArrayList<rk>();
//        ArrayList<rk> populationTemp = new ArrayList<rk>();
//        int count = 0;
//        int countToBest = 0;
//        String out = "ProbName \t BestSolution \t bestFitness \terr \tcountToBest \n";
//        String out1 = "ProbName \t BestSolution \t bestFitness \terr \tcountToBest \n";
//
//        BufferedWriter br = new BufferedWriter(new FileWriter(resultsPath + "" + saveAs));
//        br.write(out1);
//        rk best = new rk(Orderings.generateRandomRK(length));;
//        for (int i = 0; i < populationSize; i++) {
//            rk perm = new rk(Orderings.generateRandomRK(length));
//            perm.setPermutation(Orderings.randomKeyToAL(perm.copyGene()));
//            perm.normalise();
//            perm.setFitness(permProb.evaluate(perm.getPermutation()));
//            count++;
//            if (i == 0) {
//                best = perm.copyOf();
//            } else {
//                if (best.fitness < perm.fitness) {
//                    best = perm;
//                }
//            }
//            population.add(perm.copyOf());
////            System.out.println(perm.print());
//
//        }
//        double[] matrix;
//
//        matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
//
//        long gens = Math.round((double) FEs / populationSize);
//      
//        double Variance = initialStdev;
//        int j = 0;
//        int interval = Math.round(gens/100);
//        int z = 0;
//        outer:
//        do {
//            
//            if (j%interval == 0) {
//                z++;
//               double c = 1/(Math.log10(z+1));
//                 Variance = initialStdev * c;  
//            }
//               j++;  
////                 System.out.println("c: " + c);
////                 System.out.println("stdev: " + stdev);
//            for (int i = 0; i < populationSize; i++) {
//              
//                rk child = new rk(EDAUtil.getChild(matrix, Variance));
//
//                child.setPermutation(Orderings.randomKeyToAL(child.copyGene()));
//                child.normalise();
//                child.setFitness(permProb.evaluate(child.getPermutation()));
//                count++;
//                populationTemp.add(child.copyOf());
//                if (child.fitness < best.fitness) {
//                    best = child.copyOf();
//                    countToBest = count;
//                    out1 = probName + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n";
//                    br.write(out1);
////                    System.out.println("Gen: " + j);
//                          System.out.println("gen: " + j + ", fitness: " + best.fitness + ", stdev: " + Variance);
//                }
//                if (count == FEs) {
//                    countToBest = count;
//                    break outer;
//                }
//
//            }
////            if (j%100==0) {
////           
////             int number =0;
////            double sum = 0;
////            for (int i = 0; i < populationSize; i++) {
////                for (int k = i+1; k < populationSize; k++) {
////                   number++; 
////                   int[] ordering1 =Arrays.copyOf(populationTemp.get(i).getPermutation(), length);
////                    int[] ordering2 =Arrays.copyOf(populationTemp.get(k).getPermutation(), length);
////                   sum+= EDAUtil.IndividualNormalizedKendallTau(ordering1, ordering2);
////                }
////            }
////            sum = sum/(double)number;
////            System.out.println(j+ "\t" + sum);
////                 
////            }
//
//            population = new ArrayList<rk>();
//            population.addAll(populationTemp);
//            populationTemp = new ArrayList<rk>();
//
////            weight = 1 - ((double) j / gens);
////            stdev = initialStdev * weight;
////            j++;
//
//            if (population.size() == populationSize) {
//                matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
//
//            }
//      
////                    double err = ((double) best.fitness - (double) optimal) / (double) optimal;
//        } while (count < FEs);
//
//        br.close();
//        out += probName + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n";
//        BufferedWriter br1 = new BufferedWriter(new FileWriter((resultsPath + "" + saveAs).replace(".txt", "all.txt")));
//        br1.write(out);
//        br1.close();
    }


     public void runRKEDAforFSSP1(double initialStdev) throws ParseException, IOException {

//        FSSP permProb = new FSSP(path + probName);
//
//        int length = permProb.problemSize();
//
//        ArrayList<rk> population = new ArrayList<rk>();
//        ArrayList<rk> populationTemp = new ArrayList<rk>();
//        int count = 0;
//        int countToBest = 0;
//        String out = "ProbName \t BestSolution \t bestFitness \terr \tcountToBest \n";
//        String out1 = "ProbName \t BestSolution \t bestFitness \terr \tcountToBest \n";
//
//        BufferedWriter br = new BufferedWriter(new FileWriter(resultsPath + "" + saveAs));
//        br.write(out1);
//        rk best = new rk(Orderings.generateRandomRK(length));;
//        for (int i = 0; i < populationSize; i++) {
//            rk perm = new rk(Orderings.generateRandomRK(length));
//            perm.setPermutation(Orderings.randomKeyToAL(perm.copyGene()));
//            perm.normalise();
//            perm.setFitness(permProb.evaluate(perm.getPermutation()));
//            count++;
//            if (i == 0) {
//                best = perm.copyOf();
//            } else {
//                if (best.fitness < perm.fitness) {
//                    best = perm;
//                }
//            }
//            population.add(perm.copyOf());
//
//        }
//        double[] matrix;
//
//        matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
//
//        long gens = Math.round((double) FEs / populationSize);
//        double weight = 1;
//        double stdev = initialStdev * weight;
//        int j = 0;
//        outer:
//        do {
//            for (int i = 0; i < populationSize; i++) {
//
//                rk child = new rk(EDAUtil.getChild(matrix, stdev));
//
//                child.setPermutation(Orderings.randomKeyToAL(child.copyGene()));
//                child.normalise();
//                child.setFitness(permProb.evaluate(child.getPermutation()));
//                count++;
//                populationTemp.add(child.copyOf());
//                if (child.fitness < best.fitness) {
//                    best = child.copyOf();
//                    countToBest = count;
//                    out1 = probName + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n";
//                    br.write(out1);
////                    System.out.println("Gen: " + j);
//                }
//                if (count == FEs) {
//                    countToBest = count;
//                    break outer;
//                }
//
//            }
//  
//
//            population = new ArrayList<rk>();
//            population.addAll(populationTemp);
//            populationTemp = new ArrayList<rk>();
//
//            weight = 1 - ((double) j / gens);
//            stdev = initialStdev * weight;
//            j++;
//
//            if (population.size() == populationSize) {
//                matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
//
//            }
//
////                    double err = ((double) best.fitness - (double) optimal) / (double) optimal;
//        } while (count < FEs);
//
//        br.close();
//        out += probName + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n";
//        BufferedWriter br1 = new BufferedWriter(new FileWriter((resultsPath + "" + saveAs).replace(".txt", "all.txt")));
//        br1.write(out);
//        br1.close();
    }

    @SuppressWarnings("unused")
	public void runRKEDA(double initialStdev, double coolingParam2, String diversityDistance) throws ParseException, IOException {

        //boolean isMaxProb = false;
        DynamicPermutationProblem permProb = null;

        int diversitySelector = truncSize;
        
        // Choose the problem and create it
        if (problempath.contains("fsp")) {
            //makespan criteria
//            permProb = new DynamicPFSP_Makespan(problempath,dynamicpath);
        	permProb = new DynamicPFSP_TotalFlowTime(problempath,dynamicpath);
           // permProb = new PFSPtotalflowtime(path + probName);
//            System.out.println("fsp");
        } else if (problempath.contains("tsp")) {
        //    permProb = new TSP(path + probName);
//            System.out.println("tsp");
        } else if (problempath.contains("qap")) {
            permProb = new DynamicQAP(problempath,dynamicpath);
//            System.out.println("qap");	
        } else {
//            permProb = new LOP(path + probName);
//            System.out.println("lop");
            //isMaxProb = true;
        }

       
        
        boolean isMaxProb = permProb.isNatural();
        
        int length = permProb.problemSize();
        double optimal = permProb.getOptimum();
        
        if(this.FEs == -1){
			this.FEs = (int)(1000*Math.pow(length,2)); // Evaluations = 1000 * n^2
		}
        
        // MaxGens to use in the LinearCooling2
    	int gens = (int) Math.round((double) FEs / populationSize);

       // Establish the variance parameter (cooling scheme) 
    	if(this.coolingSchedule.equals("original")){
    		this.cooling = new LinearCooling2(initialStdev, gens, coolingParam2);
    	}
    	else if(this.coolingSchedule.equals("azizi_adaptive_overallbest") || this.coolingSchedule.equals("azizi_adaptive_currentbest")){
    		this.cooling = new AziziAdaptiveCooling(initialStdev, coolingParam2);
    	}
    	
    	
        // Initilization
        ArrayList<rk> population = new ArrayList<rk>();
        ArrayList<rk> populationTemp = new ArrayList<rk>();
        int count = 0;
        int countToBest = 0;
        int diversity = 0;
//        String out = "ProbName \t BestSolution \t bestFitness \t err \t countToBest \n ";
//        String out1 = "ProbName \t BestSolution \t bestFitness \t err \t countToBest \n ";
        

//        BufferedWriter br = new BufferedWriter(new FileWriter(resultsPath + "improvement-" + saveAs + ".txt"));
//        br.write(out1);
        
        // Create a random population
        for (int i = 0; i < populationSize; i++) {
            rk perm = new rk(Orderings.generateRandomRK(length));
            perm.setPermutation(Orderings.randomKeyToAL(perm.copyGene()));
            perm.normalise();
            perm.setFitness(permProb.evaluate(perm.getPermutation()));
            count++;
            population.add(perm.copyOf());
        }
        

        int noImprovementCounter = 0;
        int noCurrentImprovementCounter = 0;
        
        double[] matrix;

        // Calculate the average of the best truncated solutions using their normalized RKs and sort the population
        rk best,bestTemp, bestChange;
        if (isMaxProb) {
            best = EDAUtil.getBestSolutionMax(population).copyOf();
            bestTemp  = best;
            bestChange = best;
            matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelectionMaxProb1(population, truncSize), length);
        } else {
            best = EDAUtil.getBestSolutionMin(population).copyOf();
            bestTemp  = best;
            bestChange = best;
            matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
        }

       
        
        double stdev = initialStdev;
        
        BufferedWriter br2 = new BufferedWriter(new FileWriter(resultsPath + "progress" + "-" + saveAs + "-.csv"));
        String progress = null;
        if (diversityDistance == null){
	        System.out.println("gen\tfes\tbestFit\tavgFit\t\tbestFound\tnoImprov\tnoCurrentImprov\tsd\tchange\tchangeGen\tbestPerChange");
	        progress = "gen,fes,bestFit,avgFit,bestFound,noImprovementCounter,noCurrentImprovementCounter,sd,change,changeGen,bestPerChange\n";
        }else{
	        System.out.println("gen\tfes\tbestFit\tavgFit\t\tbestFound\tnoImprov\tnoCurrentImprov\tsd\tchange\tchangeGen\tbestPerChange\tdiversity\tnBestInd");
	        progress = "gen,fes,bestFit,avgFit,bestFound,noImprovementCounter,noCurrentImprovementCounter,sd,change,changeGen,bestPerChange,diversity,nBestInd\n";
        }
        br2.write(progress);

        DecimalFormat df = new DecimalFormat("0.0000");
        
        int j = 1, iChange =1, genChange = 1;

        // Iterative process
        do {
            
            // If a change is produced, change the structure of the identity permutation
            boolean hasChangedOccured = permProb.changeIdentityPermutation(count, FEs);
			if(hasChangedOccured){
				genChange = 1;
				iChange++;
				System.out.println("#################################################################################################CHANGE################################################################################################");
				// Re-evaluate population fitness
				for(int i=0; i<population.size(); i++){
					population.get(i).fitness = permProb.evaluate(population.get(i).permutation);
				}
				bestChange = EDAUtil.getBestSolutionMin(population).copyOf();
			}
			
	        double avgfit = EDAUtil.getPopulationAverageFitness(population);
	        
//	      	DIVERSITY
	        if (diversityDistance != null){
	        	diversity = EDAUtil.getDiversity(population, diversitySelector, diversityDistance);
	        }
	        
	        if (diversityDistance == null){
				System.out.println(j+"\t"+count+"\t"+EDAUtil.getBestSolutionMin(population).fitness+"\t"+df.format(avgfit)+
						"\t"+best.fitness+"\t\t"+noImprovementCounter+"\t\t"+noCurrentImprovementCounter+"\t\t"+df.format(stdev)+
						"\t"+iChange+"\t"+genChange+"\t\t"+bestChange.fitness);
				progress = j + "," + count + "," + EDAUtil.getBestSolutionMin(population).fitness + "," + avgfit + "," + best.fitness +
						"," + noImprovementCounter + "," + noCurrentImprovementCounter + "," + stdev + "," + iChange +
						"," + genChange + "," + bestChange.fitness + "\n";
	        }else{
	        	System.out.println(j+"\t"+count+"\t"+EDAUtil.getBestSolutionMin(population).fitness+"\t"+df.format(avgfit)+
	        			"\t"+best.fitness+"\t\t"+noImprovementCounter+"\t\t"+noCurrentImprovementCounter+"\t\t"+df.format(stdev)+
	        			"\t"+iChange+"\t"+genChange+"\t\t"+bestChange.fitness+"\t\t"+diversity+"\t\t"+diversitySelector);
				progress = j + "," + count + "," + EDAUtil.getBestSolutionMin(population).fitness + "," + avgfit + "," + best.fitness +
						"," + noImprovementCounter + "," + noCurrentImprovementCounter + "," + stdev + "," + iChange +
						"," + genChange + "," + bestChange.fitness + "," + diversity + "," + diversitySelector + "\n";
	        }
			br2.write(progress);
			
            // Configure the new population
            boolean isfirst = true;
            for (int i = 0; i < populationSize; i++) {
                rk child;
                
                // Copy the first element in the new population, ONLY ONCE
                if (isfirst && elitism) {
                    child = best.copyOf();
                    isfirst = false;
                } else {
                	
                	// Change the averages with the initialStdev and a random number
                    child = new rk(EDAUtil.getChild(matrix, stdev));
                    child.setPermutation(Orderings.randomKeyToAL(child.copyGene()));
                    child.normalise();
                    child.setFitness(permProb.evaluate(child.getPermutation()));
                    count++;
                    if (count == FEs) {
                        break;
                    }
                }
                populationTemp.add(child.copyOf());
            }

            // Update population
            population = new ArrayList<rk>();
            population.addAll(populationTemp);
            populationTemp = new ArrayList<rk>();

            
            // If it is a maximization problem
            if (isMaxProb) {
//                if (population.size() == populationSize) {
//                    matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelectionMaxProb1(population, truncSize), length);
//                }
//                rk previousBest = bestTemp.copyOf();
//                bestTemp = EDAUtil.getBestSolutionMax(population);
//
//                if (bestTemp.fitness > best.fitness) {
//                	noImprovementCounter = 0;
//                    best = bestTemp.copyOf();
//                }
//                else{
//                	noImprovementCounter ++;
//                }
//                
//                if (bestTemp.fitness > previousBest.fitness) {
//                	noCurrentImprovementCounter = 0;
//                }
//                else{
//                	noCurrentImprovementCounter ++;
//                }

            } else {
            	
            	// Calculate the average of the best "truncSize" values
                if (population.size() == populationSize) {
                    matrix = Arrays.copyOf(EDAUtil.getPMTruncationSelection1(population, truncSize), length);
                }
                rk previousBest = bestTemp.copyOf();
                bestTemp = EDAUtil.getBestSolutionMin(population);

                		///////////////
                		//  OVERALL  //
                		///////////////
                // If the min value of new pop is better than the older one change them.  
                if (bestTemp.fitness < best.fitness) {
                    best = bestTemp.copyOf();
                    noImprovementCounter = 0;
                    countToBest = count;
                     
//                    out1 = problempath + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n ";
                    //br.write(out1);
                }
                else{
                	noImprovementCounter ++;
                }
                
        				///////////////////
        				//  CURRENTBEST  //
        				///////////////////
                // If the min value improves the last update, reinitialise the parameter. 
                if (bestTemp.fitness < previousBest.fitness) {
                	noCurrentImprovementCounter = 0;
                }
                else{
                	noCurrentImprovementCounter ++;
                }
                
                // If the min value of new pop is better than the older one change them. 
                if (bestTemp.fitness < bestChange.fitness) {
                    bestChange = bestTemp.copyOf();
                }
            }
            
            // Update the Cooling scheme
            if(this.coolingSchedule.equals("original")){
            	stdev = this.cooling.getNewTemperature(j);
            }
            else if(this.coolingSchedule.equals("azizi_adaptive_overallbest")){
            	stdev = this.cooling.getNewTemperature(noImprovementCounter);
            }
            else if(this.coolingSchedule.equals("azizi_adaptive_currentbest")){
            	stdev = this.cooling.getNewTemperature(noCurrentImprovementCounter);
            }
            
            j++;
            genChange++;
            
            
        } while (count < FEs);

        double avgfit = EDAUtil.getPopulationAverageFitness(population);

        if (diversityDistance == null){
			System.out.println(j+"\t"+count+"\t"+EDAUtil.getBestSolutionMin(population).fitness+"\t"+df.format(avgfit)+
					"\t"+best.fitness+"\t\t"+noImprovementCounter+"\t\t"+noCurrentImprovementCounter+"\t\t"+df.format(stdev)+
					"\t"+iChange+"\t"+genChange+"\t\t"+bestChange.fitness);
			progress = j + "," + count + "," + EDAUtil.getBestSolutionMin(population).fitness + "," + avgfit + "," + best.fitness +
					"," + noImprovementCounter + "," + noCurrentImprovementCounter + "," + stdev + "," + iChange +
					"," + genChange + "," + bestChange.fitness + "\n";
        }else{
        	System.out.println(j+"\t"+count+"\t"+EDAUtil.getBestSolutionMin(population).fitness+"\t"+df.format(avgfit)+
        			"\t"+best.fitness+"\t\t"+noImprovementCounter+"\t\t"+noCurrentImprovementCounter+"\t\t"+df.format(stdev)+
        			"\t"+iChange+"\t"+genChange+"\t\t"+bestChange.fitness+"\t\t"+diversity+"\t\t"+diversitySelector);
			progress = j + "," + count + "," + EDAUtil.getBestSolutionMin(population).fitness + "," + avgfit + "," + best.fitness +
					"," + noImprovementCounter + "," + noCurrentImprovementCounter + "," + stdev + "," + iChange +
					"," + genChange + "," + bestChange.fitness + "," + diversity + "," + diversitySelector + "\n";
        }
        br2.write(progress);
        br2.close();
//        br.close();
//        out += problempath + "\t" + Arrays.toString(best.permutation) + "\t" + best.getFitness() + "\t" + 0.00 + "\t" + countToBest + "\n ";
//        BufferedWriter br1 = new BufferedWriter(new FileWriter((resultsPath + "best_known-" + saveAs + ".txt")));
//        br1.write(out);
//        br1.close();
    }

}
