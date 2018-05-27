package NetworkUtilities.Configuration;

import Algorithm.CenterSelectorType;
import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.ActivationFunction.ActivationFunction;
import NetworkUtilities.ActivationFunction.RadialActivationFunction;

public class NeuralLayerProperties {
	private int inputCount;
	private int neuronCount;
	private double learningRate;
	private double inertialInfluence;
	private boolean isBiasUsed;
	private boolean isBackpropagationUsed;
	private CenterSelectorType centerSelectorType;
    private ActivationFunction activationFunction;
    private RadialActivationFunction radialActivationFunction;
    private LayerType layerType;
	
	public NeuralLayerProperties(NeuralLayerPropertiesBuilder builder) {
		inputCount = builder.inputCount;
		neuronCount = builder.neuronCount;
		learningRate = builder.learningRate;
		inertialInfluence = builder.inertialInfluence;
		activationFunction = builder.activationFunction;
		isBiasUsed = builder.isBiasUsed;
		isBackpropagationUsed = builder.isBackpropagationUsed;
		centerSelectorType = builder.centerSelectorType;
		radialActivationFunction = builder.radialActivationFunction;
		layerType = builder.layerType;
	}

	public int getInputCount() {
		return inputCount;
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
	
	public boolean isBackpropagationUsed() {
        return isBackpropagationUsed;
    }
	
    public CenterSelectorType getCenterSelectorType() {
        return centerSelectorType;
    }

    public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

    public RadialActivationFunction getRadialActivationFunction() {
        return radialActivationFunction;
    }

    public LayerType getLayerType() {
        return layerType;
    }
	
}
