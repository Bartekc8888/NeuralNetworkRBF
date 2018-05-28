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
import NetworkUtilities.Data.ClassificationInterpreter;

public class MainApp {

    public static void main(String[] args) {
        NetworkConfiguration configuration = getConfigurationOfTask3a();
        
        NetworkManager manager = new NetworkManager(configuration, new ErrorPlotter(),
                new AproximationEndResultProcessor());
        
        Thread algorithmThread = new Thread(manager);
        algorithmThread.start();
    }

    public static NeuralLayerProperties[] getPropertiesOfTask1() {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(10)
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
        .setBiasUsed(true)
        .setInputCount(10)
        .setBackpropagationUsed(true);
        
        properties[1] = builder.build();
        
        return properties;
    }
    
    public static NetworkConfiguration getConfigurationOfTask1() {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = 5000;
        configuration.errorLimit = 0.05;
        configuration.interpreter = new ApproximationInterpreter();
        configuration.networkProperties = getPropertiesOfTask1();
        configuration.trainingFile = new File("data/approximation_train_1.txt");
        configuration.testingFile = new File("data/approximation_test.txt");
        
        return configuration;
    }
    
    public static NeuralLayerProperties[] getPropertiesOfTask2() {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(10)
        .setRadialActivationFunction(new GaussianFunction())
        .setBackpropagationUsed(false)
        .setCenterSelectorType(CenterSelectorType.NeuralGas)
        .setInertialInfluence(0.5)
        .setInputCount(1)
        .setLayerType(LayerType.Gaussian)
        .setLearningRate(0.6);
        
        properties[0] = builder.build();
        
        builder.setActivationFunction(new LinearFunction())
        .setLayerType(LayerType.Linear)
        .setNeuronCount(1)
        .setBiasUsed(true)
        .setInputCount(10)
        .setBackpropagationUsed(true);
        
        properties[1] = builder.build();
        
        return properties;
    }
    
    public static NetworkConfiguration getConfigurationOfTask2() {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = 500;
        configuration.errorLimit = 0.001;
        configuration.interpreter = new ClassificationInterpreter(1);
        configuration.networkProperties = getPropertiesOfTask2();
        configuration.trainingFile = new File("data/classification_train.txt");
        configuration.testingFile = new File("data/classification_test.txt");
        
        return configuration;
    }
    
    public static NeuralLayerProperties[] getPropertiesOfTask3a() {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(10)
        .setRadialActivationFunction(new GaussianFunction())
        .setBackpropagationUsed(true)
        .setCenterSelectorType(CenterSelectorType.Random)
        .setInertialInfluence(0.5)
        .setInputCount(1)
        .setLayerType(LayerType.Gaussian)
        .setLearningRate(0.6);
        
        properties[0] = builder.build();
        
        builder.setActivationFunction(new LinearFunction())
        .setLayerType(LayerType.Linear)
        .setNeuronCount(1)
        .setBiasUsed(true)
        .setInputCount(10)
        .setBackpropagationUsed(true);
        
        properties[1] = builder.build();
        
        return properties;
    }
    
    public static NetworkConfiguration getConfigurationOfTask3a() {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = 5000;
        configuration.errorLimit = 0.05;
        configuration.interpreter = new ApproximationInterpreter();
        configuration.networkProperties = getPropertiesOfTask3a();
        configuration.trainingFile = new File("data/approximation_train_1.txt");
        configuration.testingFile = new File("data/approximation_test.txt");
        
        return configuration;
    }
    
    public static NeuralLayerProperties[] getPropertiesOfTask3b() {
        NeuralLayerProperties[] properties = new NeuralLayerProperties[2];
        
        NeuralLayerPropertiesBuilder builder = new NeuralLayerPropertiesBuilder();
        builder.setNeuronCount(10)
        .setRadialActivationFunction(new GaussianFunction())
        .setBackpropagationUsed(true)
        .setCenterSelectorType(CenterSelectorType.NeuralGas)
        .setInertialInfluence(0.5)
        .setInputCount(1)
        .setLayerType(LayerType.Gaussian)
        .setLearningRate(0.6);
        
        properties[0] = builder.build();
        
        builder.setActivationFunction(new LinearFunction())
        .setLayerType(LayerType.Linear)
        .setNeuronCount(1)
        .setBiasUsed(true)
        .setInputCount(10)
        .setBackpropagationUsed(true);
        
        properties[1] = builder.build();
        
        return properties;
    }
    
    public static NetworkConfiguration getConfigurationOfTask3b() {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = 500;
        configuration.errorLimit = 0.001;
        configuration.interpreter = new ClassificationInterpreter(1);
        configuration.networkProperties = getPropertiesOfTask3b();
        configuration.trainingFile = new File("data/classification_train.txt");
        configuration.testingFile = new File("data/classification_test.txt");
        
        return configuration;
    }
}
