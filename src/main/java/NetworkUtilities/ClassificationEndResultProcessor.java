package NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

import Algorithm.NeuralNetwork;
import NetworkUtilities.Data.DataContainer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClassificationEndResultProcessor implements EndResultProcessor {
    private List<DataContainer> testData;
    
    public ClassificationEndResultProcessor(List<DataContainer> testData) {
        this.testData = testData;
    }
    
    @Override
    public void processResults(NeuralNetwork network) {
        List<RealVector> output = new ArrayList<RealVector>();
        
        for (DataContainer dataBit : testData) {
            output.add(network.calculateOutput(dataBit.getData()));
        }
        
        countGuesses(output);
    }
    
    private void countGuesses(List<RealVector> output) {
        int[][] guesses = new int[3][3];
        
        for (int i = 0; i < output.size(); i++) {
            int guess = output.get(i).getMaxIndex();
            int target = testData.get(i).getTarget().getMaxIndex();
            
            guesses[target][guess] += 1;
        }
        
        String results = "";
        System.out.println("Classification results: ");
        for (int i = 0; i < 3; i++) {
            String line = "     " + guesses[i][0] + "   " + guesses[i][1] + "    " + guesses[i][2] + "\n";
            results += line;
            
            System.out.print(line);
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Wyniki klasyfikacji");
        alert.setHeaderText("Klasyfikacja");
        alert.setContentText(results);
        alert.showAndWait();
    }
}
