package algorithms.utils;

public class SolutionQualityUtils {

	public static double getRPD(double bestSolFit, double optimumFit){
		double val = 0.0;
		
		val = (Math.abs((bestSolFit - optimumFit))) / optimumFit;
		
		return val;
	}
	
	public static double getARPD(double[] bestSolFit, double optimumFit){
		double val = 0.0;
		
		for(int i=0; i<bestSolFit.length; i++){
			val += ((bestSolFit[i] - optimumFit)) / optimumFit;
		}
		val = val / bestSolFit.length;
		return val;
	}
}
