package algorithms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ArrayUtils {

	/**
	 * Default separator for tables in flat text files (CSV, mostly)
	 */
	private static String separator = ",";
	
	public static int[] ArrayListToArray(ArrayList<Integer> list){
		
		int[] arr = new int[list.size()];
		for(int i=0; i<list.size(); i++){
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	public static double[] ArrayListToArrayDouble(ArrayList<Double> list){
		
		double[] arr = new double[list.size()];
		for(int i=0; i<list.size(); i++){
			arr[i] = list.get(i);
		}
		return arr;
	}

	/**
	 * Creates a string "flat" representation of the table provided, using the separator provided
	 * @param table
	 * @param separator
	 * @return flat representation of the table
	 */
	public static String tableToString(int[] table, String separator){
		String s="";
		boolean firstLine = true;
		for (int object : table) {
			if(!firstLine)s+=separator;
			else firstLine = false;
			s += object;
		}
		return s;
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the separator provided
	 * @param table
	 * @param separator
	 * @return flat representation of the table
	 */
	public static String tableToString(Object[] table, String separator){
		String s="";
		boolean firstLine = true;
		for (Object object : table) {
			if(!firstLine)s+=separator;
			else firstLine = false;
			s += object;
		}
		return s;
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the separator provided
	 * @param table
	 * @param separator
	 * @return flat representation of the table
	 */
	public static String tableToString(String[] table, String separator){
		String s="";
		boolean firstLine = true;
		for (String object : table) {
			if(!firstLine)s+=separator;
			else firstLine = false;
			s += object;
		}
		return s;
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the separator provided
	 * @param table
	 * @param separator
	 * @return flat representation of the table
	 */
	public static String tableToString(char[] table, String separator){
		String s="";
		boolean firstLine = true;
		for (char object : table) {
			if(!firstLine)s+=separator;
			else firstLine = false;
			s += object;
		}
		return s;
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the separator provided
	 * @param table
	 * @param separator
	 * @return flat representation of the table
	 */
	public static String tableToString(double[] table, String separator){
		String s="";
		boolean firstLine = true;
		for (double object : table) {
			if(!firstLine)s+=separator;
			else firstLine = false;
			s += object;
		}
		return s;
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the default separator
	 * @param table
	 * @return flat representation of the table
	 */
	public static String tableToString(int[] table){
		return tableToString(table, separator);
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the default separator
	 * @param table
	 * @return flat representation of the table
	 */
	public static String tableToString(Object[] table){
		return tableToString(table, separator);
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the default separator
	 * @param table
	 * @return flat representation of the table
	 */
	public static String tableToString(String[] table){
		return tableToString(table, separator);
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the default separator
	 * @param table
	 * @return flat representation of the table
	 */
	public static String tableToString(char[] table){
		return tableToString(table, separator);
	}
	
	/**
	 * Creates a string "flat" representation of the table provided, using the default separator
	 * @param table
	 * @return flat representation of the table
	 */
	public static String tableToString(double[] table){
		return tableToString(table, separator);
	}
	
	/*public static int[][] ArrayListToArray(ArrayList<ArrayList<Integer>> list){
		
		int[][] arr = new int[list.size()][list.get(0).size()];
		for(int i=0; i<list.size(); i++){
			arr[i] = list.get(i);
		}
		return arr;
	}*/
	
	
	/**
     * @return the mean of an array of double values
     */
    public static double mean(double[] vals)
    {
        double rval = 0;
        for (int i = 0; i < vals.length; i++)
            rval += vals[i];
        return rval / vals.length;
    }
    
    public static double stdev(double[] vals)
    {
        double mean = mean(vals);
        double thevalue = 0;
//        System.out.println("The mean is : "+mean);
        for (int i = 0; i<vals.length; i++){
            thevalue=thevalue + Math.pow(vals[i]-mean,2); // Square each value and add it to thevalue
        }
        thevalue=Math.sqrt(thevalue/vals.length);
        return thevalue;
    }
    
    
    /**
     * @return the mean of an array of long values
     */
    public static double mean(long[] vals)
    {
        double rval = 0;
        for (int i = 0; i < vals.length; i++)
            rval += vals[i];
        return rval / vals.length;
    }
    
    public static double stdev(long[] vals)
    {
        double mean = mean(vals);
        double thevalue = 0;
//        System.out.println("The mean is : "+mean);
        for (int i = 0; i<vals.length; i++){
            thevalue=thevalue + Math.pow(vals[i]-mean,2); // Square each value and add it to thevalue
        }
        thevalue=Math.sqrt(thevalue/vals.length);
        return thevalue;
    }
    
    /**
     * @return the mean of an array of long values
     */
    public static double mean(ArrayList<Integer> vals)
    {
        double rval = 0;
        for (int i = 0; i < vals.size(); i++)
            rval += vals.get(i);
        return rval / vals.size();
    }
    
    public static double stdev(ArrayList<Integer> vals)
    {
        double mean = mean(vals);
        double thevalue = 0;
//        System.out.println("The mean is : "+mean);
        for (int i = 0; i<vals.size(); i++){
            thevalue=thevalue + Math.pow(vals.get(i)-mean,2); // Square each value and add it to thevalue
        }
        thevalue=Math.sqrt(thevalue/vals.size());
        return thevalue;
    }
    
    
    public static double meanFromDouble(ArrayList<Double> vals)
    {
        double rval = 0;
        for (int i = 0; i < vals.size(); i++)
            rval += vals.get(i);
        return rval / vals.size();
    }
    
    public static double stdevFromDouble(ArrayList<Double> vals)
    {
        double mean = meanFromDouble(vals);
        double thevalue = 0;
//        System.out.println("The mean is : "+mean);
        for (int i = 0; i<vals.size(); i++){
            thevalue=thevalue + Math.pow(vals.get(i)-mean,2); // Square each value and add it to thevalue
        }
        thevalue=Math.sqrt(thevalue/vals.size());
        return thevalue;
    }
    
    /**
     * @return the mean of an array of int values
     */
    public static double mean(int[] vals)
    {
        double rval = 0;
        for (int i = 0; i < vals.length; i++)
            rval += vals[i];
        return rval / vals.length;
    }
    
    public static double stdev(int[] vals)
    {
        double mean = mean(vals);
        double thevalue = 0;
//        System.out.println("The mean is : "+mean);
        for (int i = 0; i<vals.length; i++){
            thevalue=thevalue + Math.pow(vals[i]-mean,2); // Square each value and add it to thevalue
        }
        thevalue=Math.sqrt(thevalue/vals.length);
        return thevalue;
    }
	
    
    /**
     * @returns the minimum value in an array of doubles
     *          NaN is returned if array length is 0
     */
    public static double min(double[] array)
    {
        double rval = Double.NaN;
        for (int i = 0; i < array.length; i++)
            if (!(rval < array[i])) // doing it this way because anything involving NaN returns false
                rval = array[i];
        
        return rval;
    }
    
    /**
     * @returns the maximum value in an array of doubles
     *          NaN is returned if array length is 0
     */
    public static double max(double[] array)
    {
        double rval = Double.NaN;
        for (int i = 0; i < array.length; i++)
            if (!(rval > array[i])) // doing it this way because anything involving NaN returns false
                rval = array[i];
        
        return rval;
    }
	
    public static int max(ArrayList<Integer> array)
    {
        int rval = Integer.MIN_VALUE;
        for (int i = 0; i < array.size(); i++)
            if (!(rval > array.get(i))) // doing it this way because anything involving NaN returns false
                rval = array.get(i);
        
        return rval;
    }
    
    /**
     * @returns the minimum value in an array of doubles
     *          NaN is returned if array length is 0
     */
    public static double min(Vector<Double> vec)
    {
        double rval = Double.NaN;
        for (int i = 0; i < vec.size(); i++)
            if (!(rval < vec.get(i))) // doing it this way because anything involving NaN returns false
                rval = vec.get(i);
        
        return rval;
    }
    
    public static int min(HashMap<Integer, Double> vec)
    {
        double rval = Double.NaN;
        int index = 0;
        for (int i = 0; i < vec.size(); i++)
            if (!(rval < vec.get(i))) // doing it this way because anything involving NaN returns false
            { rval = vec.get(i);
            	index = i;
            }
        
        return index;
    }
    
    public static int max(HashMap<Integer, Double> vec)
    {
        double rval = Double.MIN_VALUE;
        int index = 0;

        for (int i = 0; i < vec.size(); i++)
            if (!(rval > vec.get(i))) // doing it this way because anything involving NaN returns false
                {
            	rval = vec.get(i);
            	index = i;
            	}
        
        return index;
    }

    public static int[] invertArray(int[] thearray){
    	int[] newarray  = new int[thearray.length]; 
    	for(int i=0; i<newarray.length; i++){
    		newarray[i] = thearray[newarray.length-1-i];
    	}
    	return newarray;
    }
	
}
