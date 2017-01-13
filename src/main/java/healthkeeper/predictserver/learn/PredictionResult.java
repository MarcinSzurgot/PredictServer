package main.java.healthkeeper.predictserver.learn;

import java.util.List;

public class PredictionResult {
    private List<AccidentProbability> accidents;
    
    
    public PredictionResult() {
        super();
    }

    public PredictionResult(List<AccidentProbability> accidents) {
        super();
        this.accidents = accidents;
    }

    public List<AccidentProbability> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<AccidentProbability> accidents) {
        this.accidents = accidents;
    }
    
    
}
