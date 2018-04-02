package algorithms.comma.distances;

import java.util.Random;

public abstract class DistanceVectorInitialiser {

	boolean isNatural;
	boolean isInt;
	double dmin;
	double dmax;
	int popsize;
	boolean fixedVectorDistance;
	
	public DistanceVectorInitialiser() {
	}

	public abstract double[] getDistanceVector();
	
	public abstract double[] getNextDistanceVector(int currentIteration, int totalIteration);
	
}
