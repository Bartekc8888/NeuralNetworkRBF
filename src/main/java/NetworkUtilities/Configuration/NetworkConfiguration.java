package NetworkUtilities.Configuration;

import java.io.File;

import NetworkUtilities.Data.DataInterpreter;

public class NetworkConfiguration {
    public File trainingPath;
    public File testingPath;
    
    public NeuralLayerProperties[] networkProperties;
    
    public double errorLimit;
    public int epochLimit;
    
    public DataInterpreter interpreter;
}
