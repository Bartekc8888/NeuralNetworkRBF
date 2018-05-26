package NetworkUtilities.ActivationFunction;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public interface ActivationFunction {
	public double functionValue(double x);
	public default RealVector functionValue(RealVector x) {
		RealVector vector = new ArrayRealVector(x);
		
		for (int i = 0; i < x.getDimension(); i++) {
			vector.setEntry(i, functionValue(x.getEntry(i)));
		}
		return vector;
	}
	
	public double derivativeValue(double x);
	public default RealVector derivativeValue(RealVector x) {
		RealVector vector = new ArrayRealVector(x);
		
		for (int i = 0; i < x.getDimension(); i++) {
			vector.setEntry(i, derivativeValue(x.getEntry(i)));
		}
		return vector;
	}
}
