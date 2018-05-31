package NetworkUtilities.Configuration;

import java.io.File;

import NetworkUtilities.Data.ApproximationInterpreter;
import NetworkUtilities.Data.ClassificationInterpreter;

public class NetworkConfigurationFactory {
    public static NetworkConfiguration createNetworkConfiguration(SettingsFromUserTransportObject settings) {
        NetworkConfiguration configuration = new NetworkConfiguration();
        configuration.epochLimit = settings.epochLimit;
        configuration.errorLimit = settings.errorLimit;
        
        if (settings.type == SettingsType.Type1 ||
                settings.type == SettingsType.Type3a) {
            configuration.interpreter = new ApproximationInterpreter();
        } else {
            configuration.interpreter = new ClassificationInterpreter(1);
        }
        configuration.networkProperties = NeuralLayerPropertiesFactory.createProperties(settings);
        configuration.trainingFile = new File(settings.trainingFile);
        configuration.testingFile = new File(settings.testingFile);
        
        return configuration;
    }
}
