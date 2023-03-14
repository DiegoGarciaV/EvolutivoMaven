package com.fciencias.evolutivo.basics;

public class NormalRandomDistribution extends AbstractRandomDistribution{

    @Override
    protected double densityFunction(double x) {
        
        double c = Math.sqrt(2*Math.PI)*s;
        double ex = Math.exp(-Math.pow(x - mu,2)/(2*s*s));
        return ex/c;
    }

    @Override
    protected double distributionFunction(double x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'distributionFunction'");
    }
    
}
