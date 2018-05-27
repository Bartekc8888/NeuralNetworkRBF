package Algorithm.Layers;

import NetworkUtilities.Configuration.NeuralLayerProperties;

public class LayerFactory {
    public enum LayerType {
        Gaussian, Linear;
    }
    
    public static NeuralLayer createLayer(NeuralLayerProperties properties) {
        switch (properties.getLayerType()) {
        case Gaussian:
            return new GaussianLayer(properties);
        case Linear:
            return new LinearLayer(properties);
        default:
            return null;
        }
    }
}
