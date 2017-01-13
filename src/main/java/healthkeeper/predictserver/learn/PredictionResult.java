package main.java.healthkeeper.predictserver.learn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictionResult {
    private Date date;
    private List<AccidentProbability> accidents;
    
    public PredictionResult() {
        super();
        
        accidents = new ArrayList<>();
    }

    public PredictionResult(Date date,
            List<AccidentProbability> accidents) {
        super();
        this.date = date;
        this.accidents = accidents;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public Date getDate(){
        return date;
    }

    public List<AccidentProbability> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<AccidentProbability> accidents) {
        this.accidents = accidents;
    }
}
