package com.fciencias.evolutivo.libraries;

import java.security.InvalidParameterException;

/**
 *  Validacion y asignacion de parametros
 *
 */
public class ParamsValidator 
{
    private ParamsValidator(){}
    
    static int dimension = 3;
    static int representationalBits = 64;
    static long iterations = 10000;

    

    public static int getDimension() {
        return dimension;
    }

    public static int getRepresentationalBits() {
        return representationalBits;
    }

    public static long getIterations() {
        return iterations;
    }

    public static void validate(String[] params)
    {
        for(int i = 0; i < params.length; i++)
        {
            if(params[i].equals("-d"))
            {
                dimension = Integer.parseInt(params[i+1]);
                i++;
            }
            else if(params[i].equals("-b"))
            {
                representationalBits = Integer.parseInt(params[i+1]);
                i++;
            }
            else if(params[i].equals("-i"))
            {
                iterations = Integer.parseInt(params[i+1]);
                i++;
            }
            else
            {
                StringBuilder exceptionMessage = new StringBuilder("\nNo se reconoce el comando '")
                .append(params[i])
                .append("' \n")
                .append("Los comandos validos son los siguientes: \n")
                .append("\t-d: Dimension del espacio de busqueda\n")
                .append("\t-b: Logitud de bits de representacion binaria\n")
                .append("\t-i: Numero de iteraciones de la busqueda\n");
                
                throw new InvalidParameterException(exceptionMessage.toString());
            }
                
        }
    }
}
