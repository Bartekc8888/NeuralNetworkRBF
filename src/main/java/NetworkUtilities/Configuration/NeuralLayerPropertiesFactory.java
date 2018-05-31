package NetworkUtilities.Configuration;

import Algorithm.CenterSelectorType;
import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.ActivationFunction.GaussianFunction;
import NetworkUtilities.ActivationFunction.LinearFunction;

public class NeuralLayerPropertiesFactory {
    public static NeuralLayerProperties[] createProperties(SettingsFromUserTransportObject settings) {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        properties[0] = getBuilderForRbfLayer(settings).build();
        properties[1] = getBuilderForLinearLayer(settings).build();
        
        return properties;
    }
    
    private static NeuralLayerPropertiesBuilder getBuilderForRbfLayer(SettingsFromUserTransportObject settings) {
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(settings.neuronCount)
        .setInertialInfluence(settings.inertialInfluence)
        .setLearningRate(settings.learningRate);
        
        switch (settings.type) {
        case Type1:
            builder.setLayerType(LayerType.Gaussian)
            .setInputCount(1)
            .setRadialActivationFunction(new GaussianFunction())
            .setBackpropagationUsed(false)
            .setCenterSelectorType(CenterSelectorType.Random);
            
            break;
        case Type2:
            builder.setLayerType(LayerType.Gaussian)
            .setInputCount(4)
            .setRadialActivationFunction(new GaussianFunction())
            .setBackpropagationUsed(false)
            .setCenterSelectorType(CenterSelectorType.NeuralGas);
            
            break;
        case Type3a:
            builder.setLayerType(LayerType.Gaussian)
            .setInputCount(1)
            .setRadialActivationFunction(new GaussianFunction())
            .setBackpropagationUsed(false)
            .setCenterSelectorType(CenterSelectorType.Backpropagation);
            
            break;
        case Type3b:
            builder.setLayerType(LayerType.Gaussian)
            .setInputCount(4)
            .setRadialActivationFunction(new GaussianFunction())
            .setBackpropagationUsed(false)
            .setCenterSelectorType(CenterSelectorType.Backpropagation);
            
            break;
        default:
            return null;
        }
        
        return builder;
    }
    
    private static NeuralLayerPropertiesBuilder getBuilderForLinearLayer(SettingsFromUserTransportObject settings) {
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setInertialInfluence(settings.inertialInfluence)
        .setLearningRate(settings.learningRate)
        .setActivationFunction(new LinearFunction())
        .setLayerType(LayerType.Linear)
        .setNeuronCount(1)
        .setInputCount(settings.neuronCount)
        .setBiasUsed(true)
        .setBackpropagationUsed(true);
        
        if (settings.type == SettingsType.Type2 ||
                settings.type == SettingsType.Type3b) {
            builder.setNeuronCount(3);
        }
        
        return builder;
    }
}
