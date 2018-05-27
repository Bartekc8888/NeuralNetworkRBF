package NetworkUtilities.Configuration;

import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.ActivationFunction.ActivationFunction;

public class NeuralLayerProperties {
	private int inputCount;
	private int neuronCount;
	private double learningRate;
	private double inertialInfluence;
	private boolean isBiasUsed;
    private ActivationFunction activationFunction;
    private LayerType layerType;
	
	public NeuralLayerProperties(NeuralLayerPropertiesBuilder builder) {
		inputCount = builder.inputCount;
		neuronCount = builder.neuronCount;
		learningRate = builder.learningRate;
		inertialInfluence = builder.inertialInfluence;
		activationFunction = builder.activationFunction;
		isBiasUsed = builder.isBiasUsed;
		layerType = builder.layerType;
	}

	public int getInputCount() {
		return inputCount;
	}
	
	public void setInputCount(int count) {
	    inputCount = count;
	}

	public int getNeuronCount() {
		return neuronCount;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	
	public double getInertia() {
	    return inertialInfluence;
	}
	
	public boolean getIsBiasUsed() {
	    return isBiasUsed;
	}
	
	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

    public LayerType getLayerType() {
        return layerType;
    }
	
}
