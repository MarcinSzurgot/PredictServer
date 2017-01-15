package main.java.healthkeeper.predictserver.learn;

import java.util.List;

import main.java.healthkeeper.predictserver.dbo.Accident;
import main.java.healthkeeper.predictserver.dbo.AverageMeasurement;

public class DataPairRaw {
    private List<AverageMeasurement> avMeasures;
    private Accident accident;
    
    public DataPairRaw() {
        super();
    }

    public DataPairRaw(List<AverageMeasurement> avMeasures, Accident accident) {
        this();
        this.avMeasures = avMeasures;
        this.accident = accident;
    }

    public List<AverageMeasurement> getAvMeasures() {
        return avMeasures;
    }

    public void setAvMeasures(List<AverageMeasurement> avMeasures) {
        this.avMeasures = avMeasures;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }
    
    public boolean isEmpty(){
        return avMeasures.isEmpty();
    }

    @Override
    public String toString() {
        return "DataPairRaw [avMeasures=" + avMeasures + ", accident=" + accident + "]";
    }
    
    
}
