package Algorithm.Layers;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface NeuralLayer {
    public RealVector calculateOutputValue(RealVector inputValues);
    public RealMatrix calculateCorrections(RealVector input, RealVector errors);
    public void applyCorrection(RealMatrix corrections);
    
    public RealVector calculateErrorsForPreviousLayer(RealVector errors);
    
    public RealMatrix getWeights();
    public void setWeights(RealMatrix parameters);
}
