package algorithms.comma;

import java.util.Collections;
import java.util.Comparator;

public class FitnessDescending implements Comparator<Solution>{

	public FitnessDescending() {
	}

	@Override
	public int compare(Solution arg0, Solution arg1) {
		int val = 0;
		if(arg0.isNatural){ // maximisation
			if(arg0.fitness != arg1.fitness && arg0.fitness < arg1.fitness){
				val = 1;
			}
			else{
				if(arg0.fitness == arg1.fitness){
					if(arg0.id < arg1.id){
						val = 1;
					}
					else{
						val = -1;
					}
				}
				else{
					val = -1;
				}
			}
		}
		else{ // minimisation
			if(arg0.fitness != arg1.fitness && arg0.fitness > arg1.fitness){
				val = 1;
			}
			else{
				if(arg0.fitness == arg1.fitness){
					if(arg0.id < arg1.id){
						val = 1;
					}
					else{
						val = -1;
					}
				}
				else{
					val = -1;
				}
			}
		}

		return val;
	}

}
