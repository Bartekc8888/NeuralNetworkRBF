package Algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Algorithm.Layers.LayerFactory;
import Algorithm.Layers.NeuralLayer;
import GUI.ErrorPlotter;
import NetworkUtilities.EndResultProcessor;
import NetworkUtilities.Configuration.NetworkConfiguration;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Data.DataContainer;
import NetworkUtilities.Data.DataInterpreter;
import NetworkUtilities.Data.DataLoader;

public class NetworkManager implements Runnable {
    private NeuralNetwork network;
    private NetworkConfiguration networkConfig;
    private ErrorPlotter errorPlotter;
    private EndResultProcessor endResultProcessor;
    
    public NetworkManager(NetworkConfiguration config, ErrorPlotter plotter, EndResultProcessor resultProcessor) {
        List<NeuralLayer> layers = new ArrayList<NeuralLayer>();
        for (NeuralLayerProperties properties : config.networkProperties) {
            layers.add(LayerFactory.createLayer(properties));
        }
        
        network = new NeuralNetwork(layers);
        networkConfig = config;
        errorPlotter = plotter;
        endResultProcessor = resultProcessor;
    }
    
    public List<DataContainer> loadData(DataInterpreter interpreter, File dataFile) {
        DataLoader loader = new DataLoader();
        return loader.loadData(dataFile, interpreter);
    }

    @Override
    public void run() {
        List<DataContainer> learnData = loadData(networkConfig.interpreter, networkConfig.trainingFile);
        List<DataContainer> testData = loadData(networkConfig.interpreter, networkConfig.testingFile);
        
        network.trainNetwork(learnData, testData, errorPlotter,
                networkConfig.epochLimit, networkConfig.errorLimit);
        endResultProcessor.processResults(network);
    }
}
