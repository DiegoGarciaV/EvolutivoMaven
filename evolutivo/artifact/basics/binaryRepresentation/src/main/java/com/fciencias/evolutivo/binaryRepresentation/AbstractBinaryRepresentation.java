package com.fciencias.evolutivo.binaryRepresentation;

import java.security.InvalidParameterException;

public abstract class AbstractBinaryRepresentation implements BinaryRepresentation {

    protected double[] realValue;
    protected int representationalBits;
    protected String binaryString;
    protected boolean[][] binaryArray;

    protected AbstractBinaryRepresentation(){
        representationalBits = 8;
    }
    
    protected AbstractBinaryRepresentation(String binaryString)
    {
        this.binaryString = binaryString;

    }

    public double[] getRealValue() {
        return realValue;
    }

    public int getRepresentationalBits() {
        return representationalBits;
    }

    public String getBinaryString() {
        return binaryString;
    }

    public boolean[][] getBinaryArray() {
        return binaryArray;
    }

    protected boolean[] stringToArray(String stringValue) throws InvalidParameterException
    {
        int i = stringValue.length() - 1;
        boolean[] booleanArray = new boolean[stringValue.length()];
        for(char bit : stringValue.toCharArray())
        {
            if(bit != '1' && bit != '0')
                throw new InvalidParameterException();

            booleanArray[i--] = (bit == '1');
            
        }
        return booleanArray;
    }
    
    protected String arrayToString(boolean[] booleanString)
    {
        StringBuilder stringRep = new StringBuilder();
    
        for(boolean bit : booleanString)
        
            stringRep.append(bit ? "1" : "0");

        return stringRep.toString();
        
    }

    @Override
    public double realDifference(BinaryRepresentation binaryRepresentation) {
        
        double sx = 0;
        for(int i = 0; i < realValue.length; i++)
            sx+= Math.pow(realValue[i] - binaryRepresentation.getRealValue()[i],2.0);

        return Math.sqrt(sx);
    }

    @Override
    public int discreteDifference(BinaryRepresentation binaryRepresentation) {
        
        int differentBitsCounter = 0;
        for(int i = 0; i < binaryArray.length; i++)
        {
            for(int j = 0; j < binaryArray[i].length; j++)
            {
                differentBitsCounter += (binaryArray[i][j] == binaryRepresentation.getBinaryArray()[i][j] ? 1 : 0);
            }
        }
        return differentBitsCounter;
            
    }

    
}
