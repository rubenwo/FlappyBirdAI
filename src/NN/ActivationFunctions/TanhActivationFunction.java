package NN.ActivationFunctions;

public class TanhActivationFunction implements IActivationFunction {
    @Override
    public float func(float e, float j, float i) {
        return (float) Math.tanh(e);
    }

    @Override
    public float dfunc(float e, float j, float i) {
        return 1 - (e * e);
    }
}
