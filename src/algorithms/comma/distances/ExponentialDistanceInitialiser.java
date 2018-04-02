package algorithms.comma.distances;

import java.util.Random;

import utils.ArrayUtils;

// Exponential growth (if mutation uses natural distances) or Exponential decay (if mutation uses non-natural distances)
public class ExponentialDistanceInitialiser extends DistanceVectorInitialiser {

	int pow;

	public ExponentialDistanceInitialiser(boolean isNatural, boolean isInt, double dmin, double dmax, Random rand, int popsize, int pow, boolean fixedDistanceVector) {
		this.isNatural = isNatural;
		this.dmax = dmax;
		this.dmin = dmin;
		this.isInt = isInt;
		this.pow = pow;
		this.popsize = popsize;
		this.fixedVectorDistance = fixedDistanceVector;
	}

	@Override
	public double[] getDistanceVector() {
		//		double[] distances = new double[this.popsize];
		//		double themax = Math.pow(distances.length-1, pow);	
		//		double amax = Math.pow(distances.length-1, pow) / themax;
		//
		//		for(int i=0; i<distances.length; i++){
		//			double a = Math.pow(i, pow) / themax;
		//			if(!this.isNatural){
		//				a = amax - a;
		//			}
		//			double b = dmin + a*(dmax-dmin);
		//			distances[i] = this.isInt ? Math.round(b):b;
		//		}		
		////		System.out.println(ArrayUtils.tableToString(distances));
		//		return distances;
		return this.getDistanceVectorFromDminAndDmax(this.dmin, this.dmax);
	}


	public double[] getDistanceVectorFromDminAndDmax(double dmin, double dmax) {
		double[] distances = new double[this.popsize];
		double themax = Math.pow(distances.length-1, pow);	
		double amax = Math.pow(distances.length-1, pow) / themax;

		for(int i=0; i<distances.length; i++){
			double a = Math.pow(i, pow) / themax;
			if(!this.isNatural){
				a = amax - a;
			}
			double b = dmin + a*(dmax-dmin);
			distances[i] = this.isInt ? Math.round(b):b;
		}		
//				System.out.println(ArrayUtils.tableToString(distances));
		return distances;
	}


	@Override
	public double[] getNextDistanceVector(int currentIteration, int totalIteration) {

		if(currentIteration == 0 || this.fixedVectorDistance){
			return this.getDistanceVector();
		}
		else{
			if(this.isNatural){
				double newdmin = (currentIteration+1)*(dmax - dmin) / totalIteration + dmin;
				return this.getDistanceVectorFromDminAndDmax(newdmin, this.dmax);
			}
			else{
				double newdmax = (currentIteration+1)*(dmin - dmax) / totalIteration + dmax;
				return this.getDistanceVectorFromDminAndDmax(this.dmin, newdmax);
			}
		}
	}

}
