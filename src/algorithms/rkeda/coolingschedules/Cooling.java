package algorithms.rkeda.coolingschedules;

public abstract class Cooling {

	double currentTemp;
	
	public abstract double getNewTemperature(int currentGen);
	
}
