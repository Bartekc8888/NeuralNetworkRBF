package NetworkUtilities.Configuration;

import java.io.File;

import NetworkUtilities.Data.DataInterpreter;

public class NetworkConfiguration {
    public File trainingFile;
    public File testingFile;
    
    public NeuralLayerProperties[] networkProperties;
    
    public double errorLimit;
    public int epochLimit;
    
    public DataInterpreter interpreter;
}
