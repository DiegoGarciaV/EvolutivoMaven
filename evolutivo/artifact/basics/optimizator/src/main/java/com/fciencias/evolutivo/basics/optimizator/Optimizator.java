package com.fciencias.evolutivo.basics.optimizator;

import com.fciencias.evolutivo.binaryRepresentation.BinaryRepresentation;

public interface Optimizator {

    public void initOptimizator();

    public void optimize();

    public BinaryRepresentation[] getNewStates(); 

    public boolean compareStates(BinaryRepresentation state1, BinaryRepresentation state2);

    //Compara los estados y si el primer estado mejora el modelo, se cagan valores en variables de optimizacion
    public boolean isMoreOptimunState(BinaryRepresentation state);

    public long startMultiThreadOptimization(boolean appendFile, boolean logTrack);

}
