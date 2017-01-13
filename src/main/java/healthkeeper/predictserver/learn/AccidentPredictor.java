package main.java.healthkeeper.predictserver.learn;

import java.util.ArrayList;
import java.util.List;

public class AccidentPredictor {
    public AccidentPredictor(){
        
    }
    
    public void train(List<DataPairRaw> dataset){
    }
    
    public List<PredictionResult> predict(List<DataPairRaw> measures){
        List<PredictionResult> results = new ArrayList<>();
        for(DataPairRaw measure : measures){
            results.add(predict(measure));
        }
        return results;
    }
    
    public PredictionResult predict(DataPairRaw measures){
        PredictionResult pr = new PredictionResult();
        return pr;
    }
}
