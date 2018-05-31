package NetworkUtilities.ActivationFunction;

import org.apache.commons.math3.linear.RealVector;

public class GaussianFunction implements RadialActivationFunction {
    
    @Override
    public double functionValue(RealVector input, RealVector center, double coefficient) {
        double spreadAffector = 2 * coefficient * coefficient;
        
        double distance = input.getDistance(center);
        distance = distance * distance;
        double result = Math.exp(- distance / spreadAffector);
        
        return result;
    }

    @Override
    public double derivativeValue(RealVector input, RealVector center, double coefficient) {
        double derivativeParameter = 2 / (coefficient * coefficient);
        double result = derivativeParameter * functionValue(input, center, coefficient);
        
        return result;
    }
}
