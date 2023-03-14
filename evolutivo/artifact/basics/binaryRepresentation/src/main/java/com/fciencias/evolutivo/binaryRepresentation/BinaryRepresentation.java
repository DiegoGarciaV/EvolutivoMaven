package com.fciencias.evolutivo.binaryRepresentation;

public interface BinaryRepresentation {
    
    public double[] getRealValue();

    public int getRepresentationalBits();

    public String getBinaryString();

    public boolean[][] getBinaryArray(); 
    
    public BinaryRepresentation[] getNeighborhoods(double radius, int n);

    public double realDifference(BinaryRepresentation binaryRepresentation);

    public int discreteDifference(BinaryRepresentation binaryRepresentation);
    
    public BinaryRepresentation getRandomState(double radius, double[] mu);

    public String printRealValue();
}
