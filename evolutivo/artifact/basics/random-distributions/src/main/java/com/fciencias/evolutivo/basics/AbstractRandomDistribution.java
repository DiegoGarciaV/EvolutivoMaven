package com.fciencias.evolutivo.basics;

public abstract class AbstractRandomDistribution implements RandomDistribution {
    
    protected double[] interval;
    protected int steps;
    protected int tableRows;
    protected double mu;
    protected double s;
    protected double[] valuesTable;

    protected AbstractRandomDistribution()
    {
        mu = 0;
        s = 1;
        tableRows = 10000;
        steps = 5*tableRows;
        valuesTable = new double[tableRows];
        interval = new double[]{-3*s,3*s};
        fillTable();
    }

    @Override
    public double getRandomValue() {
        
        double ux = Math.random();
        return inverseDistribution(ux);
    }

    protected double inverseDistribution(double p) {
        
        int row = (int)(p*tableRows);
        return valuesTable[row];

    }
    
    protected void fillTable() {
        
        double acumulator = 0;
        double delta = (interval[1] - interval[0])/steps;
        int i = 0;
        
        double x = 0;
        for(int j = 0; j < tableRows; j++)
        {
            while(acumulator < ((double)j)/((double)tableRows) && i < steps)
            {
                x = interval[0] + i*delta;
                acumulator += densityFunction(x)*delta;
                i++;
            }
            valuesTable[j] = x;
        } 
    }

    protected abstract double densityFunction(double x);

    protected abstract double distributionFunction(double x);
}
