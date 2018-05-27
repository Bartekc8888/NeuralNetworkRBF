package GUI;

import java.io.File;

import Algorithm.CenterSelectorType;
import Algorithm.NetworkManager;
import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.AproximationEndResultProcessor;
import NetworkUtilities.ActivationFunction.GaussianFunction;
import NetworkUtilities.ActivationFunction.LinearFunction;
import NetworkUtilities.Configuration.NetworkConfiguration;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Configuration.NeuralLayerPropertiesBuilder;
import NetworkUtilities.Data.ApproximationInterpreter;

public class MainApp {

    public static void main(String[] args) {
        NetworkConfiguration configuration = getConfigurationOfTask1();
        
        NetworkManager manager = new NetworkManager(configuration, new ErrorPlotter(),
                new AproximationEndResultProcessor());
        
        Thread algorithmThread = new Thread(manager);
        algorithmThread.start();
    }

    public static NeuralLayerProperties[] getPropertiesOfTask1() {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(5)
        .setRadialActivationFunction(new GaussianFunction())
        .setBackpropagationUsed(false)
        .setCenterSelectorType(CenterSelectorType.Random)
        .setInertialInfluence(0.5)
        .setInputCount(1)
        .setLayerType(LayerType.Gaussian)
        .setLearningRate(0.6);
        
        properties[0] = builder.build();
        
        builder.setActivationFunction(new LinearFunction())
        .setLayerType(LayerType.Linear)
        .setNeuronCount(1)
        .setInputCount(5)
        .setBackpropagationUsed(true);
        
        properties[1] = builder.build();
        
        return properties;
    }
    
    public static NetworkConfiguration getConfigurationOfTask1() {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = 300;
        configuration.errorLimit = 0.05;
        configuration.interpreter = new ApproximationInterpreter();
        configuration.networkProperties = getPropertiesOfTask1();
        configuration.trainingFile = new File("data/approximation_test.txt");
        configuration.testingFile = new File("data/approximation_test.txt");
        
        return configuration;
    }
}
