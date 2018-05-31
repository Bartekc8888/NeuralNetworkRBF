package Algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Algorithm.Layers.LayerFactory;
import Algorithm.Layers.NeuralLayer;
import GUI.ErrorPlotter;
import NetworkUtilities.AproximationEndResultProcessor;
import NetworkUtilities.ClassificationEndResultProcessor;
import NetworkUtilities.EndResultProcessor;
import NetworkUtilities.Configuration.NetworkConfiguration;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Configuration.SettingsType;
import NetworkUtilities.Data.DataContainer;
import NetworkUtilities.Data.DataInterpreter;
import NetworkUtilities.Data.DataLoader;
import javafx.application.Platform;

public class NetworkManager implements Runnable {
    private NeuralNetwork network;
    private NetworkConfiguration networkConfig;
    private ErrorPlotter errorPlotter;
    private SettingsType settingsType;
    
    public NetworkManager(NetworkConfiguration config, ErrorPlotter plotter, SettingsType type) {
        List<NeuralLayer> layers = new ArrayList<NeuralLayer>();
        for (NeuralLayerProperties properties : config.networkProperties) {
            layers.add(LayerFactory.createLayer(properties));
        }
        
        network = new NeuralNetwork(layers);
        networkConfig = config;
        errorPlotter = plotter;
        settingsType = type;
    }

    @Override
    public void run() {
        List<DataContainer> learnData = loadData(networkConfig.interpreter, networkConfig.trainingFile);
        List<DataContainer> testData = loadData(networkConfig.interpreter, networkConfig.testingFile);
        
        try {
            network.trainNetwork(learnData, testData, errorPlotter,
                    networkConfig.epochLimit, networkConfig.errorLimit);
            if (!network.isCanceled()) {
                EndResultProcessor endResultProcessor = getProcessor(settingsType, testData);
                Platform.runLater(() -> endResultProcessor.processResults(network));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<DataContainer> loadData(DataInterpreter interpreter, File dataFile) {
        DataLoader loader = new DataLoader();
        return loader.loadData(dataFile, interpreter);
    }
    
    private static EndResultProcessor getProcessor(SettingsType type, List<DataContainer> testData) {
        switch (type) {
        case Type1:
        case Type3a:

            return new AproximationEndResultProcessor(testData);
        case Type2:
        case Type3b:

            return new ClassificationEndResultProcessor(testData);
        default:
            return null;
        }
    }
}
