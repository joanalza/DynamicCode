package launchers;
public class RunCommaDynamic {

	public static void main(String[] args) {

		// COMMA
//		String problemfile = "data/tft-tai50-10-0.txt";
//		String problemfile = "data/fsp-tai50-10-0.txt";
//		String problemfile = "data/qap-tai12a.dat";
//		String problemfile = "data/qap-tai25a.dat";
		String problemfile = "data/fsp-tai50-10-0.txt";
		
		// Dynamic Profile
//		String dynamicProfilePath  = "data/dynProfile-n12-c5-Cayley5.txt";
//		String dynamicProfilePath  = "data/dynProfile-n25-c5-Cayley5.txt";
		String dynamicProfilePath  = "data/dynProfile-n50-c5-Cayley5.txt";
		
		String[] str = new String[]{"-1","mallows_cayley1","1",problemfile,"exp1","3.7","6.9","1","-1", "true", "true", dynamicProfilePath};
//		String[] str = new String[]{"10","mallows_cayley0","1",problemfile,"exp1","5.25","9.25","5","-1"};
//		String[] str = new String[]{"10","mallows_cayley","1",problemfile,"exp1","6","10","1","-1"};

		CommaPermutationDynamicMain.main(str);
		
		
		// VLS
//		String problemfile = "data/fsp-tai20-10-0.txt";
//		double dmin = 0.01; // 0.1
//		double dmax = 0.01; // 2
//		double step = 0.1;
//		int maxUns = 1000000000;
//		int maxfes = -1; //-1;
//		int runs = 5;
//		String[] str = new String[]{"mallows_cayley","1",problemfile,Double.toString(dmin),Double.toString(dmax),Double.toString(step),Integer.toString(maxUns),Integer.toString(maxfes),Integer.toString(runs)};
//		VariableVarianceLocalSearchMain.main(str);
		
	}

}
