package com.fciencias.evolutivo.implementations.tarea1;

import java.security.InvalidParameterException;

import com.fciencias.evolutivo.evalFunctions.EvalFunction;
import com.fciencias.evolutivo.libraries.ParamsValidator;

public class NeighborhoodOptimization 
{

    private EvalFunction evalFunction;
    private double[] interval;
    private long iterations;
    private int representationalBits;
    private int dimension;

    

    public NeighborhoodOptimization(EvalFunction evalFunction, double[] interval, long iterations, int representationalBits, int dimension) {
        this.evalFunction = evalFunction;
        this.interval = interval;
        this.iterations = iterations;
        this.representationalBits = representationalBits;
        this.dimension = dimension;
    }



    public static void main( String[] args )
    {
        

        ParamsValidator.validate(args);
        int dimension = ParamsValidator.getDimension();
        int representationalBits = ParamsValidator.getRepresentationalBits();
        long iterations = ParamsValidator.getIterations();
        
        System.out.println("Parametros de ejecucion: ");
        System.out.println("\tDimension:  " + dimension);
        System.out.println("\tLongitud de bits:  " + representationalBits);
        System.out.println("\tIteraciones:  " + iterations);
        
    }
}
