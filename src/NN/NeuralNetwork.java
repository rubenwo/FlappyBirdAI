package NN;

import NN.ActivationFunctions.IActivationFunction;
import NN.ActivationFunctions.SigmoidActivationFunction;

public class NeuralNetwork {
    private Matrix weights_ih, weights_ho, bias_h, bias_o;
    private int input_nodes, hidden_nodes, output_nodes;
    private float learning_rate;
    private IActivationFunction activationFunction;

    public NeuralNetwork(int input_nodes, int hidden_nodes, int output_nodes) {
        this.input_nodes = input_nodes;
        this.hidden_nodes = hidden_nodes;
        this.output_nodes = output_nodes;

        this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
        this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);

        this.weights_ih.randomize();
        this.weights_ho.randomize();

        this.bias_h = new Matrix(this.hidden_nodes, 1);
        this.bias_o = new Matrix(this.output_nodes, 1);
        this.bias_h.randomize();
        this.bias_o.randomize();

        this.setLearning_rate(0.1f);
        this.setActivationFunction(new SigmoidActivationFunction());
    }

    public NeuralNetwork(NeuralNetwork nn) {
        this.input_nodes = nn.input_nodes;
        this.hidden_nodes = nn.hidden_nodes;
        this.output_nodes = nn.output_nodes;

        this.weights_ih = nn.weights_ih.copy();
        this.weights_ho = nn.weights_ho.copy();

        this.bias_h = nn.bias_h.copy();
        this.bias_o = nn.bias_o.copy();

        this.setLearning_rate(0.1f);
        this.setActivationFunction(new SigmoidActivationFunction());
    }

    public float[] predict(float[] input_array) {
        Matrix inputs = Matrix.fromArray(input_array);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden = hidden.add(this.bias_h);
        hidden.map(((e, i, j) -> (float) (1 / (1 + Math.exp(-e)))));

        if (hidden == null) {
            System.out.println("Panic! @ hidden");
        }
        Matrix output = Matrix.multiply(this.weights_ho, hidden);
        output = output.add(this.bias_o);
        output.map(((e, i, j) -> (float) (1 / (1 + Math.exp(-e)))));

        if (output == null) {
            System.out.println("Panic! @ output");
        }
        float[] result = output.toArray();
        if (result == null) {
            System.out.println("Panic! @ result");

        }
        return result;

    }

    public void setLearning_rate(float learning_rate) {
        this.learning_rate = learning_rate;
    }

    public void setActivationFunction(IActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }


    public void train(float[] input_array, float[] target_array) {
        Matrix inputs = Matrix.fromArray(input_array);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        hidden.map(((e, i, j) -> (float) (1 / (1 + Math.exp(-e)))));

        Matrix outputs = Matrix.multiply(this.weights_ho, hidden);
        outputs.add(this.bias_o);
        outputs.map(((e, i, j) -> (float) (1 / (1 + Math.exp(-e)))));

        Matrix targets = Matrix.fromArray(target_array);
        Matrix output_errors = Matrix.subtract(targets, outputs);

        Matrix gradients = Matrix.map(outputs, (e, i, j) -> e * (1 - e));
        gradients.multiply(output_errors);
        gradients.multiply(this.learning_rate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix weight_ho_deltas = Matrix.multiply(gradients, hidden_T);
        this.weights_ho.add(weight_ho_deltas);
        this.bias_o.add(gradients);

        Matrix who_t = Matrix.transpose(this.weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_t, output_errors);

        Matrix hidden_gradient = Matrix.map(hidden, (e, i, j) -> e * (1 - e));
        hidden_gradient.multiply(hidden_errors);
        hidden_gradient.multiply(this.learning_rate);

        Matrix inputs_T = Matrix.transpose(inputs);
        Matrix weight_ih_deltas = Matrix.multiply(hidden_gradient, inputs_T);

        this.weights_ih.add(weight_ih_deltas);
        this.bias_h.add(hidden_gradient);

    }

    public NeuralNetwork copy() {
        return new NeuralNetwork(this);
    }

    public void mutate(MatrixMappingFunction func) {
        this.weights_ih.map(func);
        this.weights_ho.map(func);
        this.bias_h.map(func);
        this.bias_o.map(func);
    }
}
