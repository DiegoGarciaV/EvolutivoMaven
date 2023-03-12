package com.fciencias.evolutivo.binaryRepresentation;

public interface BinaryRepresentation {
    
    public BinaryRepresentation getNeighborhoods(double radius);
    
    public double[] getRealValue();

    public int getRepresentationalBits();

    public String getBinaryString();

    public boolean[][] getBinaryArray(); 
    
    public double realDifference(BinaryRepresentation binaryRepresentation);

    public int discreteDifference(BinaryRepresentation binaryRepresentation);
}
