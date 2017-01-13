package main.java.healthkeeper.predictserver.learn;

import java.util.List;

public class Predictor {
    
    private int inputs;
    private int outputs;
    
    public Predictor(){
        
    }
    
    public Predictor(int in, int out){
        inputs = in;
        outputs = out;
    }
    
    public double train(List<DataPair> trainData){
        return 0;
    }
    
    public double[] predict(double[] input){
        return new double[outputs];
    }
}
