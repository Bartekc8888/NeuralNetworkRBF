package NetworkUtilities.ActivationFunction;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface RadialActivationFunction {
    public double functionValue(RealVector input, RealVector center, double coefficient);
    public default RealVector functionValue(RealVector input, RealMatrix centers, RealVector coefficients) {
        RealVector resultVector = new ArrayRealVector(input.getDimension());
        
        for (int i = 0; i < resultVector.getDimension(); i++) {
            double functionValue = functionValue(input, centers.getRowVector(i), coefficients.getEntry(i));
            resultVector.setEntry(i, functionValue);
        }
        return resultVector;
    }
    
    public double derivativeValue(RealVector input, RealVector center, double coefficient);
    public default RealVector derivativeValue(RealVector input, RealMatrix centers, RealVector coefficients) {
        RealVector resultVector = new ArrayRealVector(input.getDimension());
        
        for (int i = 0; i < resultVector.getDimension(); i++) {
            double derivative = derivativeValue(input, centers.getRowVector(i), coefficients.getEntry(i));
            resultVector.setEntry(i, derivative);
        }
        return resultVector;
    }
}
