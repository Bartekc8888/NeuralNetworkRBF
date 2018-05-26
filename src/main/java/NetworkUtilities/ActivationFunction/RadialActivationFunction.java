package NetworkUtilities.ActivationFunction;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface RadialActivationFunction {
    public double functionValue(RealVector input);
    public default RealVector functionValue(RealMatrix input) {
        RealVector resultVector = new ArrayRealVector(input.getRowDimension());
        
        for (int i = 0; i < resultVector.getDimension(); i++) {
            resultVector.setEntry(i, functionValue(input.getRowVector(i)));
        }
        return resultVector;
    }
    
    public double derivativeValue(RealVector input);
    public default RealVector derivativeValue(RealMatrix input) {
        RealVector resultVector = new ArrayRealVector(input.getRowDimension());
        
        for (int i = 0; i < resultVector.getDimension(); i++) {
            resultVector.setEntry(i, derivativeValue(input.getRowVector(i)));
        }
        return resultVector;
    }
}
