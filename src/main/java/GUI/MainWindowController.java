package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import NetworkUtilities.Configuration.SettingsType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class MainWindowController  implements Initializable {

    @FXML
    private ChoiceBox<SettingsType> settingsTypeCombobox;
    @FXML
    private Spinner<Integer> neuronCountSpinner;
    @FXML
    private Spinner<Double> learningRateSpinner;
    @FXML
    private Spinner<Double> inertiaSpinner;
    @FXML
    private Spinner<Integer> errorLimitSpinner;
    @FXML
    private Spinner<Integer> epochLimitSpinner;
    @FXML
    private TextField trainingFilePathField;
    @FXML
    private TextField testingFilePathField;
    
    @FXML
    private Button trainingFilePathButton;
    @FXML
    private Button testingFilePathButton;
    @FXML
    private Button startLearningButton;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
}
