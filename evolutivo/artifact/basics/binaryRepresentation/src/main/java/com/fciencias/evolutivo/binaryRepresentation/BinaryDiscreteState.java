package com.fciencias.evolutivo.binaryRepresentation;

import java.util.ArrayList;

import java.util.List;

public class BinaryDiscreteState extends AbstractBinaryRepresentation{

    private int[] elementos;

    public BinaryDiscreteState(int representationalBits)
    {
        this.representationalBits = representationalBits;
        this.binaryArray = new boolean[1][this.representationalBits];
        this.binaryString = arrayToString(this.binaryArray[0]);
    }

    public BinaryDiscreteState(int[] elementos)
    {
        this.elementos = elementos;
        this.representationalBits = elementos.length;
        encodeToBinary();
        this.binaryString = arrayToString(this.binaryArray[0]);
    }
    
    public BinaryDiscreteState(String binaryStrig)
    {
        super(binaryStrig);
        representationalBits = binaryStrig.length();
        binaryArray = new boolean[1][representationalBits];
        binaryArray[0] = stringToArray(binaryStrig);
        List<Integer> idElementos = new ArrayList<>();

        for(int i = 0; i < representationalBits; i++)
        {
            if(binaryArray[0][i])
                idElementos.add(i);
        }
        elementos = new int[idElementos.size()];
        for(int i = 0; i < elementos.length; i++)
            elementos[i] = idElementos.get(i);
    }

    @Override
    public void encodeToBinary() {
        
        binaryArray = new boolean[1][representationalBits];
        for(int elemento : elementos)
            binaryArray[0][elemento] = true;
        
    }

    @Override
    public String toString() {
        
        StringBuilder stringState = new StringBuilder(representationalBits*2 + 2).append("[");
        for(int i = 0; i < representationalBits; i++)
        {
            stringState.append(binaryArray[0][i] ? "1" : "0").append(i == (representationalBits - 1) ? "" : ",");
        }
        stringState.append("]");
        return stringState.toString();
    }
    
}
