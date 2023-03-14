package com.fciencias.evolutivo.implementations.tarea2;


import java.util.HashMap;
import java.util.Map;

import com.fciencias.evolutivo.basics.HeatDistribution;
import com.fciencias.evolutivo.basics.RandomDistribution;
import com.fciencias.evolutivo.basics.optimizator.AbstractOptimizator;
import com.fciencias.evolutivo.binaryRepresentation.BinaryRepresentation;
import com.fciencias.evolutivo.evalFunctions.DiscreteWeightFunction;
import com.fciencias.evolutivo.evalFunctions.EvalFunction;
import com.fciencias.evolutivo.libraries.FileManager;
import com.fciencias.evolutivo.libraries.ParamsValidator;


public class RecocidoSimulado extends KnapsackSearch
{

    private static final double INIT_TEMP = 15.0;

    private static final String TEMPERATURA = "TEMP";

    protected double temp;

    public RecocidoSimulado(EvalFunction evalFunction, long iterations, int representationalBits, int dimension, Map<String, Object> globalParams, int hilo) {
        
        super(evalFunction, iterations, representationalBits, dimension, globalParams, hilo);
        temp = INIT_TEMP;
    }

    public RecocidoSimulado(EvalFunction evalFunction, long iterations, int representationalBits, int dimension, Map<String, Object> globalParams, BinaryRepresentation globalBinaryRepresentationState, int hilo) {
        
        super(evalFunction, iterations, representationalBits, dimension, globalParams, globalBinaryRepresentationState, hilo);
        temp = INIT_TEMP;
    }

    @Override
    public void optimize() {
        
        for(int k = 0; k < iterations; k++)
        {
            BinaryRepresentation[] newElements = getNewStates();
            for(BinaryRepresentation element : newElements)
            {    
                if(isMoreOptimunState(element))
                {
                    globalBinaryRepresentationState = element;
                    globalParams.replace(OPTIMUM_OBJECT, element);
                    if(logTrack)
                    {
                        FileManager fileManager = new FileManager();
                        String fileName = OUTPUT_FILE_NAME;
                        long fileIndex = fileManager.openFile(fileName,true);
                        fileManager.writeFile(fileIndex,"Function: "+ evalFunction.getFunctionName() + ", Thread: "+ hilo + ",Iteration: " + k + ",Vector: " + globalBinaryRepresentationState.printRealValue()  + ",Value: " + bestValue + ", Temperatura: " + globalParams.get(TEMPERATURA) + "\n",true);
                    }
                    
                }
            }
            if(((double)globalParams.get(TEMPERATURA)) < 0.0005)
            {
                break;
            }
                
        }
    }

    @Override
    public boolean isMoreOptimunState(BinaryRepresentation state) {
        
        double valuation = evalFunction.evalSoution(state.getRealValue());
        boolean isMoreOptimun = false;
        RandomDistribution heatProbability = new HeatDistribution(new double[]{temp});
        double p = heatProbability.getRandomValue();
        FileManager fileManager = new FileManager();
        long fileIndex = fileManager.openFile("JumpUpState.txt", true);
        if(valuation < maxCost)
        {
            double combinationGain = gainCalculator.evalSoution(state.getRealValue());
                
            if(combinationGain > (double)globalParams.get(MAXIMUN_VALUE))
            {
                globalParams.replace(MAXIMUN_VALUE, combinationGain);
                
                globalParams.replace(TEMPERATURA, (double)globalParams.get(TEMPERATURA)*0.97);
                bestValue = combinationGain;
                isMoreOptimun = true;
                
                maximumValue = combinationGain;

            }
            else
            {
                if(p > ((double)globalParams.get(MAXIMUN_VALUE) - combinationGain))
                {
                    globalParams.replace(MAXIMUN_VALUE, combinationGain);
                    globalParams.replace(TEMPERATURA, (double)globalParams.get(TEMPERATURA)*0.97);
                    bestValue = combinationGain;
                    isMoreOptimun = true;
                    
                    maximumValue = combinationGain;
                    temp = temp*0.97;
                    fileManager.writeLine(fileIndex,temp + ", " + globalBinaryRepresentationState + " -> " + state);
                }
            }
        }
        return isMoreOptimun;
    }

    @Override
    public AbstractOptimizator createOptimizator(int hilo, boolean logTrack) {
        
        RecocidoSimulado recocidoSimulado = new RecocidoSimulado(evalFunction, iterations, representationalBits, dimension, globalParams, globalBinaryRepresentationState, hilo);
        recocidoSimulado.globalParams.replace(MINIMUN_VALUE, bestValue);
        recocidoSimulado.globalParams.replace(TEMPERATURA, temp);
        recocidoSimulado.setLogTrack(logTrack);
        recocidoSimulado.setMaxCost(maxCost);
        recocidoSimulado.setGainCalculator(gainCalculator);
        return recocidoSimulado;
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
        

        Map<String,Object> globalParams = new HashMap<>();
        globalParams.put(TEMPERATURA, INIT_TEMP);
        RecocidoSimulado recocidoSimulado = new RecocidoSimulado(new DiscreteWeightFunction(w), iterations, touples, touples, globalParams, 0);
        String initialState = recocidoSimulado.globalBinaryRepresentationState.getBinaryString();
        recocidoSimulado.setMaxCost(maxCost);
        recocidoSimulado.setGainCalculator(new DiscreteWeightFunction(p));
        long jumpStateIndex = fileManager.openFile("JumpUpState.txt", false);
        fileManager.writeLine(jumpStateIndex,"Registro de salto entre estados");
        long deltaTime = recocidoSimulado.startMultiThreadOptimization(false, true);
        fileIndex = fileManager.openFile( "Results.txt", false);
        fileManager.writeLine(fileIndex,"Parametros de ejecucion: ");
        fileManager.writeLine(fileIndex, "\tIteraciones:  " + iterations);
        fileManager.writeLine(fileIndex, "\tBits de representacion:  " + touples);
        fileManager.writeLine(fileIndex, "\tDimension:  " + touples);
        fileManager.writeLine(fileIndex, "\nEstado Inicial: " + initialState);
        fileManager.writeLine(fileIndex, "Resultado Optimo: " + globalParams.get(OPTIMUM_OBJECT));
        fileManager.writeLine(fileIndex, "Ganancia: " + globalParams.get(MAXIMUN_VALUE));
        fileManager.writeLine(fileIndex, "Costo: " + new DiscreteWeightFunction(w).evalSoution(((BinaryRepresentation)globalParams.get(OPTIMUM_OBJECT)).getRealValue()));
        fileManager.writeLine(fileIndex, "\nTiempo de ejecucion: " + deltaTime/1000.0 + "s");
        
        
    }
    
}
