package Algorithm;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class NeuralGasSelector {
    private int groups;
    private int inputDimensions;
    private int epochLimit;
    private double startLearningRate;
    private double currentRadius;
    private double currentLearningRate;
    private List<RealVector> inputs = new ArrayList<>();
    private RealMatrix weights;
    
    public NeuralGasSelector(List<RealVector> inputs, int groups, int epoch, double startRadius, double startLearningRate) {
        this.inputs = inputs;
        this.groups = groups;
        this.inputDimensions = inputs.get(0).getDimension();
        this.epochLimit = epoch;
        this.currentRadius = startRadius;
        this.startLearningRate = startLearningRate;
        this.currentLearningRate = startLearningRate;
    }
    
    public RealMatrix getSelectedCenters() {
        neuralGasAlgorithm();
        return weights;
    }
    
    private void neuralGasAlgorithm() {
        double[][] arrayOfWeights = new double[groups][inputDimensions];

        Random random = new Random();
        for (int i = 0; i < groups; i++) {
            for (int j = 0; j < inputDimensions; j++) {
                arrayOfWeights[i][j] = random.nextDouble() - 0.5;
            }
        }

        weights = new Array2DRowRealMatrix(arrayOfWeights);
        for (int currentEpoch = 0; currentEpoch < epochLimit; currentEpoch++) {

            if (currentEpoch != 0) {
                currentLearningRate = calculateCurrentLearningRate(currentEpoch);
                currentRadius = calculateCurrentRadius(currentEpoch);
                Collections.shuffle(inputs);
            }

            for (int j = 0; j < inputs.size(); j++) {
                double minDistance = weights.getRowVector(0).getDistance(inputs.get(j));
                int index = 0;
                for (int i = 1; i < groups; i++) {
                    RealVector weight = weights.getRowVector(i);
                    if (weight.getDistance(inputs.get(j)) < minDistance) {
                        minDistance = weight.getDistance(inputs.get(j));
                        index = i;
                    }
                }

                List<Pair<Integer, Double>> seriesOfDistance = new ArrayList<>();
                for (int i = 0; i < groups; i++) {
                    RealVector weight = weights.getRowVector(i);
                    seriesOfDistance.add(i, new Pair<>(i, weight.getDistance(weights.getRowVector(index))));
                }

                seriesOfDistance.sort(new Comparator<Pair<Integer, Double>>() {
                    @Override
                    public int compare(Pair<Integer, Double> p1, Pair<Integer, Double> p2) {
                        return p1.getSecond() < p2.getSecond() ? -1 : p1.getSecond() == p2.getSecond() ? 0 : 1;
                    }
                });

                for (int i = 0; i < groups; i++) {
                    RealVector weight = weights.getRowVector(i);
                    int positionInSeries = 0;
                    for (int k = 0; k < seriesOfDistance.size(); k++) {
                        if (seriesOfDistance.get(k).getFirst() == i) {
                            positionInSeries = k;
                        }
                    }

                    if (seriesOfDistance.get(positionInSeries).getSecond() < currentRadius) {
                        weights.setRowVector(i, calculateUpdate(inputs.get(j), weight, positionInSeries));
                    }
                }
            }
        }
    }

    private double calculateCurrentLearningRate(int currentEpoch) {
        double kDivideBykMax = ((double) currentEpoch) / ((double) epochLimit);
        return (startLearningRate) * pow(0.01 / startLearningRate, kDivideBykMax);
    }

    private double calculateCurrentRadius(int currentEpoch) {
        double lambdaMax = ((double) groups) / 2.0;
        double kDivideBykMax = ((double) currentEpoch) / ((double) epochLimit);
        return (lambdaMax) * pow(0.01 / lambdaMax, kDivideBykMax);
    }

    private double gaussiannNeighborhoodFunction(double distanceOrPosition) {
        return exp((-1) * (distanceOrPosition / this.currentRadius));
    }

    private RealVector calculateUpdate(RealVector input, RealVector weight, double distanceOrPosition) {
        RealVector newVector = weight.add(input.subtract(weight).mapMultiply(gaussiannNeighborhoodFunction(distanceOrPosition) * currentLearningRate));
        return newVector;
    }
}
