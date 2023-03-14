package com.fciencias.evolutivo.implementations.tarea2;

import java.util.HashMap;
import java.util.Map;

import com.fciencias.evolutivo.basics.optimizator.AbstractOptimizator;
import com.fciencias.evolutivo.binaryRepresentation.*;
import com.fciencias.evolutivo.evalFunctions.*;
import com.fciencias.evolutivo.libraries.FileManager;
import com.fciencias.evolutivo.libraries.ParamsValidator;
/**
 * Hello world!
 *
 */
public class KnapsackSearch extends AbstractOptimizator
{
    private double maxCost;
    private EvalFunction gainCalculator;

    public KnapsackSearch(EvalFunction evalFunction,long iterations,int representationalBits, int dimension, Map<String,Object> globalParams, int hilo)
    {
        super(evalFunction, new double[]{0,1}, iterations, representationalBits, dimension, globalParams,hilo);
    }

    public void setMaxCost(double maxCost)
    {
        this.maxCost = maxCost;
    }

    public void setGainCalculator(EvalFunction gainCalculator) {
        this.gainCalculator = gainCalculator;
    }

    @Override
    public void initOptimizator() {
        
        globalBinaryRepresentationState = new BinaryDiscreteState(representationalBits);
        bestValue = evalFunction.evalSoution(globalBinaryRepresentationState.getRealValue());
    }


    @Override
    public BinaryRepresentation[] getNewStates() {

        return globalBinaryRepresentationState.getNeighborhoods(1, representationalBits); 

    }


    @Override
    public boolean compareStates(BinaryRepresentation state1, BinaryRepresentation state2) {
        double valuation1 = evalFunction.evalSoution(state1.getRealValue());
        double valuation2 = evalFunction.evalSoution(state2.getRealValue());
        return ( (optimizeDirection && (valuation1 > valuation2)) || (!optimizeDirection && (valuation1 < valuation2)) );
    }

    @Override
    public boolean isMoreOptimunState(BinaryRepresentation state) {
        
        double valuation = evalFunction.evalSoution(state.getRealValue());
        boolean isMoreOptimun = false;
        if(valuation < maxCost)
        {
            double combinationGain = gainCalculator.evalSoution(state.getRealValue());
            if(combinationGain > (double)globalParams.get(MAXIMUN_VALUE))
            {
                globalParams.replace(MAXIMUN_VALUE, combinationGain);
                bestValue = combinationGain;
                isMoreOptimun = true;
                
                maximumValue = combinationGain;

            }
        }
        return isMoreOptimun;
    }

    @Override
    public AbstractOptimizator createOptimizator(int hilo, boolean logTrack) {
        
        KnapsackSearch knapsackSearch = new KnapsackSearch(evalFunction, iterations, representationalBits, dimension, globalParams, hilo);
        knapsackSearch.globalParams.replace(MINIMUN_VALUE, bestValue);
        knapsackSearch.setLogTrack(logTrack);
        knapsackSearch.setMaxCost(maxCost);
        knapsackSearch.setGainCalculator(gainCalculator);
        return knapsackSearch;
    }

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
       
        double maxCost = Integer.parseInt(fileManager.readFileLine(fileIndex, touples + 1));

        ParamsValidator.validate(args);
        long iterations = ParamsValidator.getIterations();
        System.out.println("Parametros de ejecucion: ");
        System.out.println("\tIteraciones:  " + iterations);

        Map<String,Object> globalParams = new HashMap<>();
        KnapsackSearch knapsackSearch = new KnapsackSearch(new DiscreteWeightFunction(w), iterations, touples, touples, globalParams, 0);
        knapsackSearch.setMaxCost(maxCost);
        knapsackSearch.setGainCalculator(new DiscreteWeightFunction(p));
        long deltaTime = knapsackSearch.startMultiThreadOptimization(false, true);
        System.out.println(deltaTime/1000.0 + "s");
    }
}
