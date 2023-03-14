package com.fciencias.evolutivo.binaryRepresentation;

import com.fciencias.evolutivo.basics.RandomDistribution;

public class BinaryMappingState extends AbstractBinaryRepresentation{

    private double[] interval;

    public BinaryMappingState(double[] realValue)
    {
        this.realValue = realValue;
        interval = new double[]{-1,1};
        representationalBits = 8;
        binaryArray = new boolean[realValue.length][representationalBits];
        binaryString = arrayToString(binaryArray[0]);
    }

    public BinaryMappingState(double[] realValue, int representationalBits)
    {
        this.realValue = realValue;
        interval = new double[]{-1,1};
        this.representationalBits = representationalBits;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeBinaryArrayToString();
    }

    public BinaryMappingState(double[] realValue, int representationalBits, double[] interval, RandomDistribution randomDistribution)
    {
        super(randomDistribution);
        this.realValue = realValue;
        this.interval = interval;
        this.representationalBits = representationalBits;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeBinaryArrayToString();
    }

    public BinaryMappingState(int representationalBits, double[] interval,int dimension)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        binaryArray = new boolean[dimension][representationalBits];
        realValue = new double[dimension];
        double midPoint = interval[1] - interval[0];
        for(int i = 0; i < dimension; i++)
            realValue[i] = midPoint;
        encodeBinaryArrayToString();
    }


    public BinaryMappingState(boolean[][] binaryArray, int representationalBits, double[] interval)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        this.binaryArray = binaryArray;
        encodeBinaryArrayToString();
        encodeBinaryArrayToReal();
        
    }

    public BinaryMappingState(String binaryString , int representationalBits, double[] interval)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        this.binaryString = binaryString;
        encodeBinaryStringToArray();
        encodeBinaryArrayToReal();
    }

    @Override
    public BinaryRepresentation[] getNeighborhoods(double radius, int n) {
        
        BinaryRepresentation[] neighborhoods = new BinaryRepresentation[n];
        for(int i = 0; i < neighborhoods.length; i++)
        {
            neighborhoods[i] = getRandomState(radius,realValue);
        }
        return neighborhoods;
    }

    private void encodeBinaryArrayToString()
    {
        StringBuilder stringRepresentation = new StringBuilder("[");
        for(int i = 0; i < binaryArray.length; i ++)
        {
            stringRepresentation.append(arrayToString(binaryArray[i]));
            if(i< binaryArray.length -1)
                stringRepresentation.append(",");
        }
        stringRepresentation.append("]");
        binaryString = stringRepresentation.toString();
    }

    private void encodeBinaryStringToArray()
    {
        String[] binaryStrings = binaryString.replace("[", "").replace("]", "").split(",");
        for(int i = 0; i < binaryArray.length; i ++)
        
            binaryArray[i] = stringToArray(binaryStrings[i]);
    }

    private void encodeBinaryArrayToReal()
    {
        for(int i = 0; i < binaryArray.length; i ++)

            realValue[i] = (interval[1] - interval[0])*arrayToInteger(binaryArray[i])/Math.pow(2, representationalBits) + interval[0];

    }

    @Override
    public BinaryRepresentation getRandomState(double radius, double[] mu) {
        
        int dimension = mu.length;
        double[] newRealValue = new double[dimension];
        for(int i = 0; i < dimension; i++)
        {
            newRealValue[i] = randomDistribution.getRandomValue()*radius/3 + mu[i];
        }
        return new BinaryMappingState(newRealValue, representationalBits, interval,randomDistribution);
    }

    @Override
    public String toString()
    {
        String endline = ",\n";
        StringBuilder objectToString = new StringBuilder("{\n");
        objectToString.append("\trealValue: [");
        for(double xi : realValue)
            objectToString.append(xi).append("  ");
   
        objectToString.append("]");
        objectToString.append(endline);

        objectToString.append("\tbinaryEncoded: ")
        .append(binaryString)
        .append(endline);

        objectToString.append("\tinterval: [")
        .append(interval[0]).append(", ")
        .append(interval[1]).append("]")
        .append(endline);

        objectToString.append("\trepresentationalBits: ")
        .append(representationalBits)
        .append(endline);

        objectToString.append("\ttotalRepresentationalBits: ")
        .append(realValue.length*representationalBits)
        .append(endline);
       
        return objectToString.append("}").toString();
    }
}

