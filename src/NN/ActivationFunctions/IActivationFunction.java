package NN.ActivationFunctions;

public interface IActivationFunction {
    float func(float e, float j, float i);

    float dfunc(float e, float j, float i);
}
