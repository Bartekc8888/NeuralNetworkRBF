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
    }
    
    public static void startLearning(SettingsFromUserTransportObject settings) {
        try {
            NetworkConfiguration configuration = 
                    NetworkConfigurationFactory.createNetworkConfiguration(settings);
            
            NetworkManager manager = new NetworkManager(configuration, new ErrorPlotter(settings.epochLimit),
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource(mainWindowFXML);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        mainWindowController = loader.getController();
        
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sieci RBF");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(440);
        primaryStage.show();
    }
}
