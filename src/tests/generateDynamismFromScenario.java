package tests;

import java.io.IOException;
import problems.generators.dynamicprofilegenerators.DynamicBenchmarkFromScenario;

public class generateDynamismFromScenario {

	public static void main(String[] args) throws IOException {
        
        String[] bigScenario = {"./data/dynamic/dynProfile-n500-c1000-Cayley5.txt"};
        int[] nChanges = {3};
        
        for(String i:bigScenario){
        	for(int j:nChanges){
        		String[] str = new String[] {i, Integer.toString(j)};
        		
        		DynamicBenchmarkFromScenario.main(str);
        	}
        }
	}
}
