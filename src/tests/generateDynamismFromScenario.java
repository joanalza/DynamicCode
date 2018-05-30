package tests;

import java.io.IOException;
import problems.generators.dynamicprofilegenerators.DynamicBenchmarkFromScenario;

public class generateDynamismFromScenario {

	public static void main(String[] args) throws IOException {
        
		int[] sizes = {500};
        int[] bigScenario = {2,100,250,450};
        int[] nChanges = {3,10,20};
        
        for(int i:bigScenario){
        	for(int j:nChanges){
        		for (int size:sizes){
	        		String dynPath = "./data/dynamic/bigScenario/dynProfile-n"+size+"-c1000-Cayley" + i + ".txt";
	        		String[] str = new String[] {dynPath, Integer.toString(j)};
	        		
	        		DynamicBenchmarkFromScenario.main(str);
        		}
        	}
        }
	}
}
