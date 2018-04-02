/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launchers;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import algorithms.rkeda.RKEDA;

/**
 *
 * @author mayowaayodele
 */
public class RKEDADynamicMain {

     public static void main(String[] args) {

        int populationSize;
        String problempath;
        String dynamicpath;
        int FEs;
        int truncSize;
        boolean elitism;
        double mutationRate;
        String resultsPath;
        String saveAs;
        Double initialStdev;
        
        populationSize = Integer.parseInt(args[0]);
        problempath = args[1];
        dynamicpath = args[2];
        FEs = Integer.parseInt(args[3]);
        truncSize = Integer.parseInt(args[4]);
        elitism = (Integer.parseInt(args[5]) != 0);
     
        initialStdev = Double.parseDouble(args[6]);
        resultsPath = args[7];
        saveAs = args[8];
        String cooling = args[9];
        Double coolingParam2 = Double.parseDouble(args[10]);
        String diversity = args[11];
        mutationRate = 0;
        RKEDA alg1 = new RKEDA(populationSize, problempath, dynamicpath, FEs, truncSize, elitism, mutationRate, resultsPath, saveAs, cooling);
        

         try {
             //alg1.runRKEDAforFSSP(initialStdev);
             alg1.runRKEDA(initialStdev, coolingParam2, diversity);
         } catch (ParseException ex) {
             Logger.getLogger(RKEDADynamicMain.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(RKEDADynamicMain.class.getName()).log(Level.SEVERE, null, ex);
         }


    }


}
