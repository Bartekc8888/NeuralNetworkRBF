package NetworkUtilities.ActivationFunction;

public class LinearFunction implements ActivationFunction {

	@Override
	public double functionValue(double x) {
		return x;
	}
	
	@Override
	public double derivativeValue(double x) {
		return 1;
	}
}
