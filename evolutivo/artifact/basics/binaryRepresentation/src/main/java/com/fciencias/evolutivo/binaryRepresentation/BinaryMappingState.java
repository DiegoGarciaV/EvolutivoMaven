package com.fciencias.evolutivo.binaryRepresentation;

public class BinaryMappingState extends AbstractBinaryRepresentation{

    private double[] interval;

    public BinaryMappingState(double[] realValue)
    {
        this.realValue = realValue;
        interval = new double[]{-1,1};
        representationalBits = 8;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeToBinary();
    }

    public BinaryMappingState(double[] realValue, int representationalBits)
    {
        this.realValue = realValue;
        interval = new double[]{-1,1};
        this.representationalBits = representationalBits;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeToBinary();
    }

    public BinaryMappingState(double[] realValue, int representationalBits, double[] interval)
    {
        this.realValue = realValue;
        this.interval = interval;
        this.representationalBits = representationalBits;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeToBinary();
    }


    public BinaryMappingState(boolean[][] binaryArray, int representationalBits, double[] interval)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        this.binaryArray = binaryArray;
        decodeToReal();
        encodeToBinaryArray();
    }

    public BinaryMappingState(String binaryString , int representationalBits, double[] interval)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        this.binaryString = binarString;
        encodeBinaryString();
        decodeToReal();
        

    }

    @Override
    public BinaryRepresentation getNeighborhoods(double radius) {
        
        
    }

    
    
    

    

}

