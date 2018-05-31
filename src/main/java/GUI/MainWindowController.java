package GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import NetworkUtilities.Configuration.SettingsFromUserTransportObject;
import NetworkUtilities.Configuration.SettingsType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private Spinner<Double> errorLimitSpinner;
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
        setupCombobox();
        setupSpinners();
        setupButtons();
    }
    
    private void setupCombobox() {
        SettingsType[] values = SettingsType.values();
        ObservableList<SettingsType> settingTypes = FXCollections.observableArrayList(values);
        
        settingsTypeCombobox.setItems(settingTypes);
        settingsTypeCombobox.getSelectionModel().select(0);
    }
    
    private void setupSpinners() {
        neuronCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 10, 1));
        neuronCountSpinner.setEditable(true);
        
        learningRateSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.001, 2, 0.6, 0.1));
        learningRateSpinner.setEditable(true);
        
        inertiaSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2, 0.4, 0.1));
        inertiaSpinner.setEditable(true);
        
        errorLimitSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0.05, 0.1));
        errorLimitSpinner.setEditable(true);
        
        epochLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 5000, 100));
        epochLimitSpinner.setEditable(true);
    }
    
    private void setupButtons() {
        trainingFilePathButton.setOnAction((actionEvent) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Wybierz plik treningowy");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki tekstowe", "*.txt"));
            
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
               trainingFilePathField.setText(selectedFile.getAbsolutePath());
            }
        });
        
        testingFilePathButton.setOnAction((actionEvent) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Wybierz plik testowy");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki tekstowe", "*.txt"));
            
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
               testingFilePathField.setText(selectedFile.getAbsolutePath());
            }
        });
        
        startLearningButton.setOnAction((actionEvent) -> {
            SettingsFromUserTransportObject settings = getCurrentSettings();
            
            if (settings.checkIfCorrect()) {
                MainApp.startLearning(settings);
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Niepoprawne dane");
                alert.setContentText("Przed rozpoczęciem nauki należy wprowadzić poprawne dane");
                alert.showAndWait();
            }
        });
    }
    
    private SettingsFromUserTransportObject getCurrentSettings() {
        SettingsFromUserTransportObject settings = new SettingsFromUserTransportObject();
        settings.epochLimit = epochLimitSpinner.getValue();
        settings.errorLimit = errorLimitSpinner.getValue();
        settings.inertialInfluence = inertiaSpinner.getValue();
        settings.learningRate = learningRateSpinner.getValue();
        settings.neuronCount = neuronCountSpinner.getValue();
        settings.testingFile = testingFilePathField.getText();
        settings.trainingFile = trainingFilePathField.getText();
        settings.type = settingsTypeCombobox.getValue();
        
        return settings;
    }
}
