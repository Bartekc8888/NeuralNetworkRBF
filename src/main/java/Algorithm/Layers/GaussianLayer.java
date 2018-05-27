package Algorithm.Layers;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import NetworkUtilities.ActivationFunction.RadialActivationFunction;
import NetworkUtilities.Configuration.NeuralLayerProperties;
import NetworkUtilities.Data.DataContainer;

public class GaussianLayer implements NeuralLayer {
    private NeuralLayerProperties layerProperties;
    private RealMatrix centers;
    private RealVector coefficients;
    private RealVector previousCoefficientsChange;
    
    public GaussianLayer(NeuralLayerProperties properties) {
        layerProperties = properties;
        centers = new Array2DRowRealMatrix(properties.getNeuronCount(), properties.getInputCount());
    }
    
    @Override
    public RealVector calculateOutputValue(RealVector inputValues) {
        RealVector output = centers.preMultiply(inputValues);
        output.ebeMultiply(coefficients);
        
        return output;
    }

    @Override
    public RealMatrix calculateCorrections(RealVector input, RealVector errors) {
        RadialActivationFunction activationFunction = layerProperties.getRadialActivationFunction();
        RealVector deriativeValues = activationFunction.derivativeValue(input, centers, coefficients);

        RealMatrix corrections = new Array2DRowRealMatrix(layerProperties.getNeuronCount(),
                                                          layerProperties.getInputCount() + 1);
        
        RealMatrix correctionsForWeights = new Array2DRowRealMatrix(layerProperties.getNeuronCount(),
                                                                    layerProperties.getInputCount());
        
        for (int i = 0; i < layerProperties.getNeuronCount(); i++) {
            RealVector rowOfCorrections = input.mapMultiply(deriativeValues.getEntry(i)).mapMultiply(errors.getEntry(i));

            correctionsForWeights.setRowVector(i, rowOfCorrections);
        }
        corrections.setSubMatrix(correctionsForWeights.getData(), 0, 0);
        
        RealVector correctionsForBiases = deriativeValues.ebeMultiply(errors);
        corrections.setColumnVector(layerProperties.getInputCount(), correctionsForBiases); // set bias correction in last column

        return corrections;
    }

    @Override
    public void applyCorrection(RealMatrix corrections) {
        corrections = corrections.scalarMultiply((layerProperties.getLearningRate()) * (1 - layerProperties.getInertia()));
        RealVector coefficientChanges = corrections.getRowVector(0);
        if (previousCoefficientsChange != null) {
            coefficientChanges = coefficientChanges.add(previousCoefficientsChange.mapMultiply(layerProperties.getInertia()));
        }
        
        coefficients = coefficients.add(coefficientChanges);
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
        case Random:
        default:
            initRandomCenters(trainingData);
            break;
        }
        initCoefficients();
    }
    
    private void initRandomCenters(List<DataContainer> trainingData) {
        Random rand = new Random();
        for (int i = 0; i < layerProperties.getNeuronCount(); i++) {
            int randomIndex = rand.nextInt() % layerProperties.getNeuronCount();
            centers.setColumnVector(i, trainingData.get(randomIndex).getData());
        }
    }
    
    private void initCentersByNeuralGas(List<DataContainer> trainingData) {
        
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
