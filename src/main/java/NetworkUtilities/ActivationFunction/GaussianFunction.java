package NetworkUtilities.ActivationFunction;

import org.apache.commons.math3.linear.RealVector;

public class GaussianFunction implements RadialActivationFunction {
    RealVector functionCenter;
    double spread;
    
    public GaussianFunction(RealVector functionCenter, double spreadParameter) {
        this.functionCenter = functionCenter;
        this.spread = spreadParameter;
    }
    
    @Override
    public double functionValue(RealVector input) {
        double spreadAffector = 2 * spread * spread;
        
        double distance = input.getDistance(functionCenter);
        distance = distance * distance;
        double result = Math.exp(- distance / spreadAffector);
        
        return result;
    }

    @Override
    public double derivativeValue(RealVector input) {
        // TODO check derivative equation correctness
        double derivativeParameter = - 2 * input.getDistance(functionCenter);
        double result = derivativeParameter * functionValue(input);
        
        return result;
    }
}
