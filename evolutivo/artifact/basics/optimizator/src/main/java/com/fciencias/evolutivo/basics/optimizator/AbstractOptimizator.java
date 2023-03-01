package com.fciencias.evolutivo.basics.optimizator;

import java.util.Map;

import com.fciencias.evolutivo.binaryRepresentation.BinaryRepresentation;
import com.fciencias.evolutivo.evalFunctions.EvalFunction;

/**
 * Hello world!
 *
 */
public abstract class AbstractOptimizator implements Optimizator, Runnable
{
    protected EvalFunction evalFunction;
    protected double[] interval;
    protected long iterations;
    protected int representationalBits;
    protected int dimension;

    protected BinaryRepresentation globalBinaryMappingState;

    protected Map<String,Object> globalParams;

    protected int hilo;

    protected static final String FINALIZED_THREADS = "Finalized threads";
    
    protected static final String MINIMUN_VALUE = "Min value";

    protected static final String MAXIMUN_VALUE = "Maximun value";

    protected static final String MEAN_VALUE = "Mean value";

    protected static final String TOTAL_ITERATIONS = "Total iterations";

    protected static final int TOTAL_THREADS = 25;

    protected static final String OUTPUT_FILE_NAME = "evaluationTracking.txt";

    protected static final String PROGRESS_INDICATOR = "progress indicator";
    

    protected AbstractOptimizator(EvalFunction evalFunction, double[] interval, long iterations, int representationalBits, int dimension) {
        this.evalFunction = evalFunction;
        this.interval = interval;
        this.iterations = iterations;
        this.representationalBits = representationalBits;
        this.dimension = dimension;
    }

    @Override
    public void run() {
        
        optimize();
        ((boolean[])(globalParams.get(FINALIZED_THREADS)))[hilo-1] = true;
    }

    public void resetGlobalParams()
    {
        if(!globalParams.containsKey(MAXIMUN_VALUE))
            globalParams.put(MAXIMUN_VALUE,0.0);
        else
            globalParams.replace(MAXIMUN_VALUE, 0.0);

        
        if(!globalParams.containsKey(MINIMUN_VALUE))
            globalParams.put(MINIMUN_VALUE,0.0);
        else
            globalParams.replace(MINIMUN_VALUE, 0.0);


        if(!globalParams.containsKey(MEAN_VALUE))
            globalParams.put(MEAN_VALUE,0.0);
        else
            globalParams.replace(MEAN_VALUE, 0.0);


        if(!globalParams.containsKey(TOTAL_ITERATIONS))
            globalParams.put(TOTAL_ITERATIONS,0);
        else
            globalParams.replace(TOTAL_ITERATIONS, 0);


        if(!globalParams.containsKey(PROGRESS_INDICATOR))
            globalParams.put(PROGRESS_INDICATOR,0.0);
        else
            globalParams.replace(PROGRESS_INDICATOR, 0.0);


        if(!globalParams.containsKey(FINALIZED_THREADS))
            globalParams.put(FINALIZED_THREADS,new boolean[TOTAL_THREADS]);
        else
            globalParams.replace(FINALIZED_THREADS, new boolean[TOTAL_THREADS]);

    }


}
