package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import Algorithm.Layers.NeuralLayer;
import GUI.ErrorPlotter;
import NetworkUtilities.Data.DataContainer;

public class NeuralNetwork {
	private NeuralLayer[] layers;
	private volatile boolean isCanceled;
	
	public NeuralNetwork(List<NeuralLayer> networkLayers) {
		layers = new NeuralLayer[networkLayers.size()];
		networkLayers.toArray(layers);
	}
	
	public void trainNetwork(List<DataContainer> learnData, List<DataContainer> testData, ErrorPlotter errorPlotter,
	        int epochLimit, double errorLimit) {
        List<DataContainer> dataCopy = new ArrayList<DataContainer>(learnData);
        RealVector outputVector;
        
        for (NeuralLayer layer : layers) {
            layer.initLayer(learnData);
        }
        
        double errorAfterEpoch = 0;
        int currentEpoch = 0;
        while (currentEpoch < epochLimit) {
            synchronized (this) {
                if (isCanceled) {
                    break;
                }
            }
            
            Collections.shuffle(dataCopy);
            RealMatrix[] correctionsAccumulator = new RealMatrix[getLayerCount()];
            
            double errorAccumulator = 0;
            for (DataContainer dataBit : dataCopy) {
                outputVector = calculateOutput(dataBit.getData());
                
                if (dataBit.getTarget() != null) {
                    RealVector errorVector = dataBit.getTarget().subtract(outputVector);
                    RealMatrix[] corrections = calculateCorrections(dataBit.getData(), errorVector);

                    errorVector = errorVector.ebeMultiply(errorVector);
                    errorVector = errorVector.mapDivide(2);
                    errorAccumulator += errorVector.getL1Norm();
                    
                    if (corrections.length == correctionsAccumulator.length) {
                        for (int i = 0; i < correctionsAccumulator.length; i++) {
                            if (correctionsAccumulator[i] != null) {
                                correctionsAccumulator[i] = correctionsAccumulator[i].add(corrections[i]);
                            } else {
                                correctionsAccumulator[i] = corrections[i];
                            }
                        }
                    } else {
                        throw new UnsupportedOperationException("Corrections dimensions don't match");
                    }
                }
            }
            
            if (currentEpoch > 5) { // skip drawing first few errors so we dont mess up plot scale
                errorAfterEpoch = errorAccumulator / dataCopy.size(); // divide to get average error on all samples
                Double errorOnTest = calculateErrorOverDataRange(testData);
                
                System.out.println("Epoch: " + currentEpoch + "trainErr: " + errorAfterEpoch + " testErr: " + errorOnTest);
                
                errorPlotter.newErrorData(currentEpoch + 1, errorAfterEpoch, errorOnTest);
                
                if (errorAfterEpoch < errorLimit) {
                    return;
                }
            }

            for (int i = 0; i < correctionsAccumulator.length; i++) {
                if (correctionsAccumulator[i] != null) {
                    correctionsAccumulator[i] = correctionsAccumulator[i].scalarMultiply(1.d / dataCopy.size());
                }
            }

            applyCorrections(correctionsAccumulator);
            currentEpoch++;
            
            if (errorPlotter.isClosed()) {
                cancel();
            }
        }
	}
	
	public RealVector calculateOutput(RealVector input) {
		return getOutputOfLayer(input, layers.length);
	}
	
	public RealMatrix[] calculateCorrections(RealVector input, RealVector errors) {
		RealMatrix[] corrections = new RealMatrix[layers.length];
		RealVector errorsForPreviousLayer = null;
		
		if (layers.length > 1) {
		    RealVector inputForCurrentLayer = getOutputOfLayer(input, layers.length - 1);
    		corrections[layers.length - 1] = layers[layers.length - 1].calculateCorrections(inputForCurrentLayer, errors);
    		errorsForPreviousLayer = layers[layers.length - 1].calculateErrorsForPreviousLayer(errors);
    		
    		for (int i = layers.length - 2; i > 0; i--) {
    			inputForCurrentLayer = getOutputOfLayer(input, i);
    			corrections[i] = layers[i].calculateCorrections(inputForCurrentLayer, errorsForPreviousLayer);
    			
    			errorsForPreviousLayer = layers[i].calculateErrorsForPreviousLayer(errorsForPreviousLayer);
    		}
		}
		
		if (errorsForPreviousLayer == null) {
		    errorsForPreviousLayer = errors;
		}
		corrections[0] = layers[0].calculateCorrections(input, errorsForPreviousLayer);
		
		return corrections;
	}
	
	public void applyCorrections(RealMatrix[] corrections) {
		for (int i = 0; i < layers.length; i++) {
			layers[i].applyCorrection(corrections[i]);
		}
	}
	
    public double calculateErrorOverDataRange(List<DataContainer> data) {
        List<DataContainer> dataCopy = new ArrayList<DataContainer>(data);
        RealVector outputVector;
        double errorAccumulator = 0.0;
        
        for (DataContainer dataBit : dataCopy) {
            outputVector = calculateOutput(dataBit.getData());
            
            RealVector errorVector = dataBit.getTarget().subtract(outputVector);
            errorVector = errorVector.ebeMultiply(errorVector);
            errorVector = errorVector.mapDivide(2);
            errorAccumulator += errorVector.getL1Norm();
        }
        
        double errorOnDataRange = errorAccumulator / dataCopy.size();
        return errorOnDataRange;
    }
	
	public int getLayerCount() {
		return layers.length;
	}
	
	public synchronized void cancel() {
	    isCanceled = true;
	}
	
	public boolean isCanceled() {
	    return isCanceled;
	}
	
	private RealVector getOutputOfLayer(RealVector input, int layerLevel) { // layerLevel counting from 1
		RealVector output = layers[0].calculateOutputValue(input);
		
		for (int i = 1; i < layerLevel; i++) {
			output = layers[i].calculateOutputValue(output);
		}
		
		return output;
	}
}
