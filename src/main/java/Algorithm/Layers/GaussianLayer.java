package Algorithm.Layers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import Algorithm.NeuralGasSelector;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Data.DataContainer;

public class GaussianLayer implements NeuralLayer {
    private NeuralLayerProperties layerProperties;
    private RealMatrix centers;
    private RealVector coefficients;
    
    private RealMatrix previousCentersChange;
    private RealVector previousCoefficientsChange;
    
    public GaussianLayer(NeuralLayerProperties properties) {
        layerProperties = properties;
        centers = new Array2DRowRealMatrix(properties.getNeuronCount(), properties.getInputCount());
    }
    
    @Override
    public RealVector calculateOutputValue(RealVector inputValues) {
        RealVector output = layerProperties.getRadialActivationFunction().functionValue(inputValues, centers, coefficients);
        
        return output;
    }

    @Override
    public RealMatrix calculateCorrections(RealVector input, RealVector errors) {
        if (!layerProperties.isBackpropagationUsed()) {
            return null;
        }
        
        RealVector output = layerProperties.getRadialActivationFunction().functionValue(input, centers, coefficients);
        
        RealMatrix corrections = new Array2DRowRealMatrix(layerProperties.getNeuronCount(),
                                                          layerProperties.getInputCount() + 1);
        
        RealMatrix correctionsForWeights = new Array2DRowRealMatrix(layerProperties.getNeuronCount(),
                                                                    layerProperties.getInputCount());
        
        for (int i = 0; i < layerProperties.getNeuronCount(); i++) {
            double coefficiency = coefficients.getEntry(i);
            coefficiency = coefficiency * coefficiency;
            
            RealVector rowOfCorrections = input.subtract(centers.getRowVector(i)).mapDivide(coefficiency);
            rowOfCorrections = rowOfCorrections.mapMultiply(2 * output.getEntry(i) * errors.getEntry(i));
            
            correctionsForWeights.setRowVector(i, rowOfCorrections);
        }
        corrections.setSubMatrix(correctionsForWeights.getData(), 0, 0);
        
        RealVector coefficiencyCorrections = new ArrayRealVector(layerProperties.getNeuronCount());
        for (int i = 0; i < layerProperties.getNeuronCount(); i++) {
            double distance = input.getDistance(centers.getRowVector(i));
            distance = distance * distance;
            
            double coefficiency = coefficients.getEntry(i);
            distance = distance / (coefficiency * coefficiency * coefficiency);
            
            coefficiencyCorrections.setEntry(i, distance);
        }
        
        RealVector correctionsForBiases = output.ebeMultiply(errors).ebeMultiply(coefficiencyCorrections).mapMultiply(2);
        corrections.setColumnVector(layerProperties.getInputCount(), correctionsForBiases); // set bias correction in last column

        return corrections;
    }

    @Override
    public void applyCorrection(RealMatrix corrections) {
        if (!layerProperties.isBackpropagationUsed()) {
            return;
        }

        corrections = corrections.scalarMultiply((layerProperties.getLearningRate()) * (1 - layerProperties.getInertia()));
        int inputCount = layerProperties.getInputCount();
        
        RealMatrix centerChanges = corrections.getSubMatrix(0, layerProperties.getNeuronCount() - 1, 0, inputCount - 1);
        RealVector coefficientChanges = corrections.getColumnVector(inputCount);
        
        if (previousCoefficientsChange != null) {
            coefficientChanges = coefficientChanges.add(previousCoefficientsChange.mapMultiply(layerProperties.getInertia()));
        }
        if (previousCentersChange != null) {
            centerChanges = centerChanges.add(previousCentersChange.scalarMultiply(layerProperties.getInertia()));
        }
        
        centers = centers.add(centerChanges);
        coefficients = coefficients.add(coefficientChanges);
        
        previousCentersChange = centerChanges;
        previousCoefficientsChange = coefficientChanges;
    }

    @Override
    public RealVector calculateErrorsForPreviousLayer(RealVector errors) {
        return null;
    }
    
    @Override
    public void initLayer(List<DataContainer> trainingData) {
        switch (layerProperties.getCenterSelectorType()) {
        case NeuralGas:
            initCentersByNeuralGas(trainingData);
            break;
        case Backpropagation:
        case Random:
        default:
            initRandomCentersByInput(trainingData);
            break;
        }
        
        initCoefficients();
    }
    
    private void initRandomCentersByInput(List<DataContainer> trainingData) {
        Random rand = new Random();
        for (int i = 0; i < layerProperties.getNeuronCount(); i++) {
            int randomIndex = (rand.nextInt() & 0x0ffffff) % trainingData.size();
            centers.setRowVector(i, trainingData.get(randomIndex).getData());
        }
    }
    
    private void initCentersByNeuralGas(List<DataContainer> trainingData) {
        List<RealVector> dataPoints = new ArrayList<RealVector>();
        for (DataContainer container : trainingData) {
            dataPoints.add(container.getData());
        }
        
        NeuralGasSelector selector = new NeuralGasSelector(dataPoints, layerProperties.getNeuronCount(), 300, 30, 0.8);
        centers = selector.getSelectedCenters();
    }
    
    private void initCoefficients() {
        RealVector distances = new ArrayRealVector(centers.getRowDimension());
        
        for (int i = 0; i < centers.getRowDimension(); i++) {
            for (int j = 0; j < centers.getRowDimension(); j++) {
                double distance = centers.getRowVector(i).getDistance(centers.getRowVector(j));
                distances.setEntry(i, distance + distances.getEntry(i));
            }
        }
        
        distances.mapDivideToSelf(centers.getRowDimension());
        coefficients = distances;
    }
}
