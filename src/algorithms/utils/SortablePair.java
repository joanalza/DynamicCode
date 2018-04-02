package algorithms.utils;


public class SortablePair implements Comparable<SortablePair>{

	int key;
	double value;
	
	public SortablePair(int k, double v){
		this.key = k;
		this.value = v;	
	}

	@Override
	public int compareTo(SortablePair arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getKey(){
		return this.key;
	}
	
	public double getValue(){
		return this.value;
	}
	
	@Override
	public String toString(){
		String str = "["+this.key+"-"+this.value+"]";
		return str;
		
	}
	
}
