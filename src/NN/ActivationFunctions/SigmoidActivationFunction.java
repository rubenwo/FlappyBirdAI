package NN.ActivationFunctions;

public class SigmoidActivationFunction implements IActivationFunction {
    @Override
    public float func(float e, float j, float i) {
        return (float) (1 / (1 + Math.exp(-e)));
    }

    @Override
    public float dfunc(float e, float j, float i) {
        return e * (1 - e);
    }
}
