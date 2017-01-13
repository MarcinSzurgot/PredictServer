package main.java.healthkeeper.predictserver.learn;

public class DataPair {
    private double[] input;
    private double[] output;
    
    public DataPair() {
        super();
    }
    
    public DataPair(double[] input, double[] output) {
        this();
        this.input = input;
        this.output = output;
    }
    public double[] getInput() {
        return input;
    }
    public void setInput(double[] input) {
        this.input = input;
    }
    public double[] getOutput() {
        return output;
    }
    public void setOutput(double[] output) {
        this.output = output;
    }
}
