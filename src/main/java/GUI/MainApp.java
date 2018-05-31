package GUI;

import java.net.URL;

import Algorithm.CenterSelectorType;
import Algorithm.NetworkManager;
import Algorithm.Layers.LayerFactory.LayerType;
import NetworkUtilities.AproximationEndResultProcessor;
import NetworkUtilities.ClassificationEndResultProcessor;
import NetworkUtilities.EndResultProcessor;
import NetworkUtilities.ActivationFunction.GaussianFunction;
import NetworkUtilities.ActivationFunction.LinearFunction;
import NetworkUtilities.Configuration.NetworkConfiguration;
import NetworkUtilities.Configuration.NetworkConfigurationFactory;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Configuration.NeuralLayerPropertiesBuilder;
import NetworkUtilities.Configuration.SettingsFromUserTransportObject;
import NetworkUtilities.Configuration.SettingsType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final String mainWindowFXML = "SettingsWindow.fxml";
    
    private MainWindowController mainWindowController;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
        
        /*
        NetworkConfiguration configuration = getConfigurationOfTask1();
        
        NetworkManager manager = new NetworkManager(configuration, new ErrorPlotter(),
                new AproximationEndResultProcessor());
        
        Thread algorithmThread = new Thread(manager);
        algorithmThread.start();
        */
    }
    
    public static void startLearning(SettingsFromUserTransportObject settings) {
        try {
            NetworkConfiguration configuration = 
                    NetworkConfigurationFactory.createNetworkConfiguration(settings);
            
            NetworkManager manager = new NetworkManager(configuration, new ErrorPlotter(),
                    getProcessor(settings.type));
            
            Thread algorithmThread = new Thread(manager);
            algorithmThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static EndResultProcessor getProcessor(SettingsType type) {
        switch (type) {
        case Type1:
        case Type3a:

            return new AproximationEndResultProcessor();
        case Type2:
        case Type3b:

            return new ClassificationEndResultProcessor();
        default:
            return null;
        }
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
        SettingsFromUserTransportObject settings = new SettingsFromUserTransportObject();
        settings.type = SettingsType.Type3a;
        settings.epochLimit = 5000;
        settings.errorLimit = 0.05;
        settings.neuronCount = 10;
        settings.inertialInfluence = 0.3;
        settings.learningRate = 0.7;
        settings.trainingFile = "data/approximation_train_1.txt";
        settings.testingFile = "data/approximation_test.txt";
        
        NetworkConfiguration configuration = NetworkConfigurationFactory.createNetworkConfiguration(settings);
        
        return configuration;
    }
    
    public static NetworkConfiguration getConfigurationOfTask2() {
        SettingsFromUserTransportObject settings = new SettingsFromUserTransportObject();
        settings.type = SettingsType.Type2;
        settings.epochLimit = 5000;
        settings.errorLimit = 0.001;
        settings.neuronCount = 10;
        settings.inertialInfluence = 0.3;
        settings.learningRate = 0.7;
        settings.trainingFile = "data/classification_train.txt";
        settings.testingFile = "data/classification_test.txt";
        
        NetworkConfiguration configuration = NetworkConfigurationFactory.createNetworkConfiguration(settings);
        
        return configuration;
    }
    
    public static NetworkConfiguration getConfigurationOfTask3a() {
        SettingsFromUserTransportObject settings = new SettingsFromUserTransportObject();
        settings.type = SettingsType.Type3a;
        settings.epochLimit = 5000;
        settings.errorLimit = 0.05;
        settings.neuronCount = 10;
        settings.inertialInfluence = 0.3;
        settings.learningRate = 0.7;
        settings.trainingFile = "data/approximation_train_1.txt";
        settings.testingFile = "data/approximation_test.txt";
        
        NetworkConfiguration configuration = NetworkConfigurationFactory.createNetworkConfiguration(settings);
        
        return configuration;
    }
    
    public static NetworkConfiguration getConfigurationOfTask3b() {
        SettingsFromUserTransportObject settings = new SettingsFromUserTransportObject();
        settings.type = SettingsType.Type3b;
        settings.epochLimit = 5000;
        settings.errorLimit = 0.001;
        settings.neuronCount = 10;
        settings.inertialInfluence = 0.3;
        settings.learningRate = 0.7;
        settings.trainingFile = "data/classification_train.txt";
        settings.testingFile = "data/classification_test.txt";
        
        NetworkConfiguration configuration = NetworkConfigurationFactory.createNetworkConfiguration(settings);
        
        return configuration;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource(mainWindowFXML);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        mainWindowController = loader.getController();
        //initMainWindowEvents(mainWindowController);
        
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sieci RBF");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(440);
        primaryStage.show();
    }
}
