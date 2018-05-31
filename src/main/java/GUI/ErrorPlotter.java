package GUI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ErrorPlotter{
    private Stage plotStage;
    
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> trainSeries;
    private XYChart.Series<Number, Number> testSeries;
    
    public ErrorPlotter(int maxEpochCount) {
        plotStage = new Stage();
        
        xAxis = new NumberAxis();
        xAxis.setLabel("Liczba epok");
        xAxis.setTickLabelsVisible(true);
        
        // set fixed range
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(maxEpochCount);
        xAxis.setTickUnit(maxEpochCount/10);
        
        yAxis = new NumberAxis();
        yAxis.setAnimated(false);
        
        lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
            @Override
            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
            }
        };
        lineChart.setAnimated(true);
        lineChart.setTitle("Błąd nauki");
        
        trainSeries = new XYChart.Series<Number, Number>();
        trainSeries.setName("Błąd treningowy");
        lineChart.getData().add(trainSeries);
        
        testSeries = new XYChart.Series<Number, Number>();
        testSeries.setName("Błąd testowy");
        lineChart.getData().add(testSeries);
        
        Scene scene  = new Scene(lineChart, 800, 600);
        plotStage.setScene(scene);
        plotStage.show();
    }
    
    public synchronized void newErrorData(int epoch, double trainError, double testError) {
        Platform.runLater(() -> {
            trainSeries.getData().add(new Data<Number, Number>(epoch, trainError));
            testSeries.getData().add(new Data<Number, Number>(epoch, testError));
        });
    }
}
