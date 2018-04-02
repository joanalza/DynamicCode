package problems.generators.dynamicprofilegenerators;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import utils.ArrayUtils;

public class DynamicBenchmarkFromScenario {

	public static void main(String[] args) throws IOException {

		String path = "./data/dynamic/";
		String saveAs = null;
		
		String bigScenario = args[0]; //"./data/dynamic/dynProfile-n20-c1000-Cayley3.txt"
		int nChanges = Integer.parseInt(args[1]); // 4
		
		File file = new File(bigScenario);

	    try {
	    	String[] save = file.getName().split("-");
	    	save[2] = "c" + nChanges;
	    	saveAs = convertStringArrayToString(save, "-");
	    	System.out.println(saveAs);
	    	BufferedWriter br1 = new BufferedWriter(new FileWriter(path + "" + saveAs));
			
	        @SuppressWarnings("resource")
			Scanner input = new Scanner(file);
		    int counter = 0;
			DecimalFormat df = new DecimalFormat("#.####");
		    String out = nChanges + "\n";
	        while (input.hasNext() && counter < nChanges + 1 ) {
		        String line = input.nextLine();
		        if (counter != 0){
		        	String[] frac = line.split(";");

		        	out += df.format((double)counter/(nChanges + 1))+";"+frac[1]+"\n";
		        }
		        counter++;
	        }
	        System.out.print(out);
	        br1.write(out);
	        br1.close();

	    } catch (FileNotFoundException e) {
	        System.err.format("File Does Not Exist\n");
	    }
	}
	
	private static String convertStringArrayToString(String[] strArr, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (String str : strArr)
			sb.append(str).append(delimiter);
		return sb.substring(0, sb.length() - 1);
	}

}
