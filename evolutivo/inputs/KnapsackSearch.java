package com.fciencias.evolutivo.implementations.tarea2;

import com.fciencias.evolutivo.binaryRepresentation.*;
import com.fciencias.evolutivo.evalFunctions.*;
import com.fciencias.evolutivo.libraries.FileManager;
/**
 * Hello world!
 *
 */
public class KnapsackSearch 
{
    private FileManager fileManager;
    
    
    public static void main( String[] args )
    {
        FileManager fileManager = new FileManager();
        long fileIndex = fileManager.openFile("inputs/ejeL1n10.txt", true);
        int touples = Integer.parseInt(fileManager.readFileLine(fileIndex, 0));
        double[] w = new double[touples];
        double[] p = new double[touples];
        

        for(int i = 0; i < touples; i++)
        {
            String[] toupleValues = fileManager.readFileLine(fileIndex, i+1).split(" ");
            p[i] = Integer.parseInt(toupleValues[1]);
            w[i] = Double.parseDouble(toupleValues[2]);
            
        }
        DiscreteWeightFunction evalFunction = new DiscreteWeightFunction();
        evalFunction.setWeights(w);
        BinaryRepresentation binaryState = new BinaryDiscreteState(new int[]{0,1,2,3});
        System.out.println(binaryState);
        double[] realVals = new double[4];
        for(int i = 0; i < realVals.length;i ++)
        {
            realVals[i] = 1.0;
        }
        System.out.println(evalFunction.evalSoution(realVals));

    }
}
