package NetworkUtilities.Configuration;

public class SettingsFromUserTransportObject {
    public SettingsType type;
    
    public int neuronCount;
    public double learningRate;
    public double inertialInfluence;
    public double errorLimit;
    public int epochLimit;
    
    public String trainingFile;
    public String testingFile;
    
    public boolean checkIfCorrect() {
        if (neuronCount < 0) {
            return false;
        }
        if (learningRate < 0 || inertialInfluence < 0) {
            return false;
        }
        if (errorLimit < 0 || epochLimit < 0) {
            return false;
        }
        if (trainingFile.isEmpty() || testingFile.isEmpty()) {
            return false;
        }
        
        return true;
    }
}
