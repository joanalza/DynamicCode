package algorithms.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.uncommons.watchmaker.framework.EvaluatedCandidate;

public class WMUtils {

	public static ArrayList<ArrayList<Integer>> EvaluatedPopulationToArrayPopulation(List<EvaluatedCandidate<ArrayList>> pop) {
		
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>> ();
		
		for (int i=0; i<pop.size(); i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j=0; j<pop.get(i).getCandidate().size(); j++){
				temp.add((Integer) pop.get(i).getCandidate().get(j));
			}
			arr.add(temp);
		}
		
		return arr;
		
	}

public static  Collection<ArrayList> EvaluatedPopulationToCollection(List<EvaluatedCandidate<ArrayList>> pop) {
		
	 	Collection<ArrayList> arr = new  ArrayList<ArrayList> ();
		
		for (int i=0; i<pop.size(); i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j=0; j<pop.get(i).getCandidate().size(); j++){
				temp.add((Integer) pop.get(i).getCandidate().get(j));
			}
			arr.add(temp);
		}
		
		return arr;
		
	}
	

}
