package com.fciencias.evolutivo.basics;

/**
 * Hello world!
 *
 */
public interface RandomDistribution 
{
    public double getRandomValue();
    public double[] getValuesTable();
    public double densityFunction(double x);
    public double distributionFunction(double x);
}
