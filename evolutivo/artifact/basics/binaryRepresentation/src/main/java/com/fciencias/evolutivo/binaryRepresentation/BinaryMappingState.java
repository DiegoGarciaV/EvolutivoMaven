package com.fciencias.evolutivo.binaryRepresentation;

import java.security.InvalidParameterException;

public class BinaryMappingState {
    
    private int representationalBits;
    private String binaryString;
    private boolean[][] binaryArray;
    private double[] realValue;
    private double[] interval;

    public BinaryMappingState(double[] realValue)
    {
        this.realValue = realValue;
        interval = new double[]{-10,10};
        representationalBits = 8;
        binaryArray = new boolean[realValue.length][representationalBits];
        encodeToBinary();
    }

    public BinaryMappingState(double[] realValue, int representationalBits)
    {
        this.realValue = realValue;
        interval = new double[]{-10,10};
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
        encodeBinaryArray();
    }

    public BinaryMappingState(String binarString , int representationalBits, double[] interval)
    {
        this.interval = interval;
        this.representationalBits = representationalBits;
        this.binaryString = binarString;
        encodeBinaryString();
        decodeToReal();
        

    }
    public int getRepresentationalBits() {
        return representationalBits;
    }
    public void setRepresentationalBits(int representationalBits) {
        this.representationalBits = representationalBits;
    }
    public String getBinaryString() {
        return binaryString;
    }
    public void setBinaryString(String binaryString) {
        this.binaryString = binaryString;
    }
    public boolean[][] getBinaryArray() {
        return binaryArray;
    }
    public void setBinaryArray(boolean[][] binaryArray) {
        this.binaryArray = binaryArray;
    }
    public double[] getRealValue() {
        return realValue;
    }
    public void setRealValue(double[] realValue) {
        this.realValue = realValue;
    }

    public double[] getInterval() {
        return interval;
    }
    public void setInterval(double[] interval) {
        this.interval = interval;
    }

    public void encodeToBinary()
    {
        StringBuilder stringRep = new StringBuilder("(");
        for(int i = 0; i < realValue.length; i ++)
        {
            double a0 = interval[0];
            double b0 = interval[1];
            
            for(int j = representationalBits - 1; j >= 0; j --)
            {
                double midPoint = (a0 + b0)/2;
                if(realValue[i] > b0)
                {
                    binaryArray[i][j] = true;
                    stringRep.append("1");
                }
                else if(realValue[i] < a0)
                {
                    binaryArray[i][j] = false;
                    stringRep.append("0");
                }
                else if(realValue[i] > midPoint)
                {
                    a0 = midPoint;
                    binaryArray[i][j] = true;
                    stringRep.append("1");
                }
                else
                {
                    b0 = midPoint;
                    binaryArray[i][j] = false;
                    stringRep.append("0");
                }
                
            }
            if(i < realValue.length - 1)
                stringRep.append(",");
        }
        stringRep.append(")");
        binaryString = stringRep.toString();
    }

    public void decodeToReal()
    {
        realValue = new double[binaryArray.length];
        for(int i = 0; i < binaryArray.length; i ++)
        {   
            realValue[i] = 0;
            for(int j = 0; j < representationalBits; j++)
            {
                realValue[i] += (binaryArray[i][j] ? Math.pow(2, j) : 0);
            }
            realValue[i] = (interval[1] - interval[0])*realValue[i]/Math.pow(2, representationalBits) + interval[0];
        }

    }

    private boolean[] stringToArray(String stringValue) throws InvalidParameterException
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

    private String arrayToString(boolean[] booleanString)
    {
        StringBuilder stringRep = new StringBuilder();
    
        for(boolean bit : booleanString)
        
            stringRep.append(bit ? "1" : "0");

        return stringRep.toString();
        
    }

    private void encodeBinaryArray()
    {
        StringBuilder stringArray = new StringBuilder((representationalBits + 1)*binaryArray.length + 2).append("(");
        for(int i = 0; i < binaryArray.length; i ++)
        {
            stringArray.append(arrayToString(binaryArray[i]));
            if(i < binaryArray.length - 1)
                stringArray.append(",");
        }
        stringArray.append(")");
        this.binaryString = stringArray.toString();
    }


    private void encodeBinaryString()
    {
        String[] binaryStrings = (binaryString
                                    .replace("(", "")
                                    .replace(")", "")
                                    .split(","));

        StringBuilder correctBinaryString = new StringBuilder("(");
        for(int i = 0; i < binaryStrings.length; i++)
        {
            int stringLen = binaryStrings[i].length();
            if(stringLen < representationalBits)
            {
                for(int j = 0; j < (representationalBits - stringLen); j++)
                    binaryStrings[i] = "0" + binaryStrings[i];
            }
            else if(stringLen > representationalBits)
                binaryStrings[i] = binaryStrings[i].substring(0,representationalBits);
            correctBinaryString.append(binaryStrings[i]);
            if(i < binaryStrings.length - 1)
                correctBinaryString.append(",");
        }
        correctBinaryString.append(")");

        boolean[][] booleanArray = new boolean[binaryStrings.length][];
        for(int i = 0; i < booleanArray.length; i ++)
        {
            try {
                booleanArray[i] = stringToArray(binaryStrings[i]);
            } catch (InvalidParameterException e) {
                booleanArray[i] = new boolean[representationalBits];
                System.err.println("No se ha recibido una cadena valida");
            }
        }
        this.binaryArray = booleanArray;
        this.binaryString = correctBinaryString.toString();
    }

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

