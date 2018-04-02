package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utils.ArrayUtils;

public abstract class DynamicPermutationProblem
{
    public void setOptimum(double optimum) {
		this.optimum = optimum;
	}

	public int theSize;
    public double optimum;
    public int[] identityPerm;
    public int[][] identityPermutations;
    public double[] identityPermutationChanges; // percentage of the maxFes at which a change happens
    public int nextChangeIndex = 0;
    public String dynProfilePath;
    
    public DynamicPermutationProblem(){
    	
    }
    
    public void setIdentityPermutationChanges(){
//    	// For initial testing, we introduce one change, at which the id permutation is completely inverted (n-1,n-2,n-3,...)
//    	// The change happens after 50% of the maxFEs
//    	
//    	int nChanges = 1;
//    	this.identityPermutations = new int[nChanges][this.theSize];
//    	this.identityPermutationChanges = new double[nChanges];
//    	
//    	this.identityPermutationChanges[0] = 0.5;
//    	for(int i=0; i<this.theSize; i++){
//    		this.identityPermutations[0][i] = (this.theSize-1)-i;
//    	} 
    	
    	try {
			BufferedReader reader = new BufferedReader (new FileReader (new File (dynProfilePath)));

			String templine = " ";
			String separator1 = ";";
			String separator2 = ",";
			
			int n = Integer.parseInt(reader.readLine().trim());
			this.identityPermutations = new int[n][this.theSize];
	    	this.identityPermutationChanges = new double[n];
			
			for(int i = 0; i< n; i++){
				templine = reader.readLine().trim();
				String[] lineArray = templine.split(separator1);
				this.identityPermutationChanges[i] = Double.parseDouble(lineArray[0]);
				String permString = lineArray[1];
				String[] permArray = permString.split(separator2);
				int[] tempPerm = new int[this.theSize];
				for(int j=0; j<tempPerm.length; j++){
					tempPerm[j] = Integer.parseInt(permArray[j]);
				}
				this.identityPermutations[i] = tempPerm;
			}
		}   
		catch (IOException e) {System.out.println("Couldn't find file: " + dynProfilePath);}   
		
    	
    	
    }
    
    public abstract boolean isNatural();
    
    public abstract double evaluate(int[] solution);
    
    public int problemSize()
    {
        return theSize;
    }
    
    public double getOptimum(){
    	return optimum;
    }

    public boolean changeIdentityPermutation(int fes, int maxfes){
//    	System.out.println("fes: "+fes+" - maxfes: "+maxfes);
//    	System.out.println("this.nextChangeIndex: "+this.nextChangeIndex);
//    	System.out.println("this.identityPermutationChanges.length: "+ this.identityPermutationChanges.length);
    	boolean hasChangedOccured = false;
    	if(this.nextChangeIndex < this.identityPermutationChanges.length){
    		int nextChangeFes = (int)Math.rint(this.identityPermutationChanges[this.nextChangeIndex]*(double)maxfes);
    		if(fes>=nextChangeFes){
    			
    			this.identityPerm = this.identityPermutations[this.nextChangeIndex];
    			this.nextChangeIndex ++;
//    			System.out.println("CHANGE OF IDENTITY PERMUTATION: "+ArrayUtils.tableToString(this.identityPerm));
    			hasChangedOccured = true;
    		}
    	}
    	return hasChangedOccured;
    }
    
}
