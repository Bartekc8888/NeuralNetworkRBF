package GUI;

import java.net.URL;

import Algorithm.NetworkManager;
import NetworkUtilities.Configuration.NetworkConfiguration;
import NetworkUtilities.Configuration.NetworkConfigurationFactory;
import NetworkUtilities.Configuration.SettingsFromUserTransportObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final String mainWindowFXML = "SettingsWindow.fxml";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void startLearning(SettingsFromUserTransportObject settings) {
        try {
            NetworkConfiguration configuration = 
                    NetworkConfigurationFactory.createNetworkConfiguration(settings);
            
            NetworkManager manager = new NetworkManager(configuration,
                    new ErrorPlotter(settings.epochLimit), settings.type);
            
            Thread algorithmThread = new Thread(manager);
            algorithmThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource(mainWindowFXML);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        loader.getController();
        
        primaryStage.setTitle("Sieci RBF");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(440);
        primaryStage.show();
    }
}
