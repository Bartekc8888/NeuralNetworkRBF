package NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

import Algorithm.NeuralNetwork;
import NetworkUtilities.Data.DataContainer;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;

public class AproximationEndResultProcessor implements EndResultProcessor {
    private List<DataContainer> testData;
    
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> trainSeries;
    private XYChart.Series<Number, Number> testSeries;
    
    public AproximationEndResultProcessor(List<DataContainer> testData) {
        this.testData = testData;
    }
    
    @Override
    public void processResults(NeuralNetwork network) {
        List<RealVector> output = new ArrayList<RealVector>();
        
        for (DataContainer dataBit : testData) {
            output.add(network.calculateOutput(dataBit.getData()));
        }
        
        createSeries(output);
        drawChart();
    }
    
    private void createSeries(List<RealVector> output) {
        trainSeries = new XYChart.Series<Number, Number>();
        testSeries = new XYChart.Series<Number, Number>();
        
        for (int i = 0; i < output.size(); i++) {
            double xValue = testData.get(i).getData().getEntry(0);
            double yValue = output.get(i).getEntry(0);
            trainSeries.getData().add(new Data<Number, Number>(xValue, yValue));
            
            yValue = testData.get(i).getTarget().getEntry(0);
            testSeries.getData().add(new Data<Number, Number>(xValue, yValue));
        }
    }

    private void drawChart() {
        Stage plotStage = new Stage();
        
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        chart = new ScatterChart<Number, Number>(xAxis, yAxis);
        chart.setTitle("Wykres aproksymacji");
        
        trainSeries.setName("Dane wyliczone");
        chart.getData().add(trainSeries);
        
        testSeries.setName("Dane testowe");
        chart.getData().add(testSeries);
        
        Scene scene  = new Scene(chart, 800, 600);
        plotStage.setScene(scene);
        plotStage.show();
    }
}
