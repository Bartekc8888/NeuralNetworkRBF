package NetworkUtilities.Configuration;

import Algorithm.CenterSelectorType;
import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.ActivationFunction.ActivationFunction;
import NetworkUtilities.ActivationFunction.RadialActivationFunction;

public class NeuralLayerPropertiesBuilder {
    public int inputCount;
    public int neuronCount;
    public double learningRate;
    public double inertialInfluence;
    public boolean isBiasUsed;
    public ActivationFunction activationFunction;
    public LayerType layerType;
    public RadialActivationFunction radialActivationFunction;
    public boolean isBackpropagationUsed;
    public CenterSelectorType centerSelectorType;
    
    public NeuralLayerPropertiesBuilder setInputCount(int inputCount) {
        this.inputCount = inputCount;
        return this;
    }
    public NeuralLayerPropertiesBuilder setNeuronCount(int neuronCount) {
        this.neuronCount = neuronCount;
        return this;
    }
    public NeuralLayerPropertiesBuilder setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }
    public NeuralLayerPropertiesBuilder setInertialInfluence(double inertialInfluence) {
        this.inertialInfluence = inertialInfluence;
        return this;
    }
    public NeuralLayerPropertiesBuilder setBiasUsed(boolean isBiasUsed) {
        this.isBiasUsed = isBiasUsed;
        return this;
    }
    public NeuralLayerPropertiesBuilder setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        return this;
    }
    public NeuralLayerPropertiesBuilder setLayerType(LayerType layerType) {
        this.layerType = layerType;
        return this;
    }
    public void setRadialActivationFunction(RadialActivationFunction radialActivationFunction) {
        this.radialActivationFunction = radialActivationFunction;
    }
    public void setBackpropagationUsed(boolean isBackpropagationUsed) {
        this.isBackpropagationUsed = isBackpropagationUsed;
    }
    public void setCenterSelectorType(CenterSelectorType centerSelectorType) {
        this.centerSelectorType = centerSelectorType;
    }
    
    public NeuralLayerProperties build() {
        return new NeuralLayerProperties(this);
    }
}
