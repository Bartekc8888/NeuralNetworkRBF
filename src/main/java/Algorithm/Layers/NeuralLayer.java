package Algorithm.Layers;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import NetworkUtilities.Data.DataContainer;

public interface NeuralLayer {
    public RealVector calculateOutputValue(RealVector inputValues);
    public RealMatrix calculateCorrections(RealVector input, RealVector errors);
    public void applyCorrection(RealMatrix corrections);
    
    public RealVector calculateErrorsForPreviousLayer(RealVector errors);
    
    public void initLayer(List<DataContainer> trainingData);
}
