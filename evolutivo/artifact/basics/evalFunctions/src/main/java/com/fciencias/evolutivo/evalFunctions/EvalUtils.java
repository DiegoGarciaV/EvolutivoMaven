package com.fciencias.evolutivo.evalFunctions;

public abstract class EvalUtils implements EvalFunction{
    
    private double[] interval;

    public void setIntervalLimits(double a, double b)
    {
        interval = new double[]{a,b};
    }

    public double[] getInterval()
    {
        return interval;
    }
    
    protected double powersSum(double[] x, int p)
    {
        double sum = 0;
        for(double xi : x)

            sum += Math.pow(xi,p);

        return sum;
    }

    protected double powersSum(double[] x, int p, double c)
    {
        double sum = 0;
        for(double xi : x)

            sum += c*Math.pow(xi,p);

        return sum;
    }


    protected double trigoSum(double[] x, double a, double b, double wa, double wb)
    {
        double sum = 0;
        for(double xi : x)

            sum += a*Math.cos(2*Math.PI*wa*xi) + b*Math.sin(2*Math.PI*wb*xi);

        return sum;
    }

}
