package com.fciencias.evolutivo.basics.optimizator;

import com.fciencias.evolutivo.binaryRepresentation.BinaryRepresentation;

public interface Optimizator {

    public BinaryRepresentation optimize();

    public BinaryRepresentation getNewstate(); 
    
}
