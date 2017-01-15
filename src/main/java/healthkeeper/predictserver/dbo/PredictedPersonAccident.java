package main.java.healthkeeper.predictserver.dbo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PredictedPersonAccident {
    private String timestamp;
    private int person_id;
    private int accident_id;
    private double probability;
    
    public PredictedPersonAccident() {
        super();
    }
    
    public PredictedPersonAccident(String timestamp, int person_id, int accident_id, double probability) {
        this();
        this.timestamp = timestamp;
        this.person_id = person_id;
        this.accident_id = accident_id;
        this.probability = probability;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date date){
        SimpleDateFormat format = new SimpleDateFormat(
                PersonAccident.dateTimeStringFormat);
        setTimestamp(format.format(date));
    }
    
    public Date getTimestampAsDate(){
        SimpleDateFormat format = new SimpleDateFormat(
                PersonAccident.dateTimeStringFormat);
        
        Date date = null;
        try {
            date = format.parse(getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public int getPerson_id() {
        return person_id;
    }
    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
    public int getAccident_id() {
        return accident_id;
    }
    public void setAccident_id(int accident_id) {
        this.accident_id = accident_id;
    }
    public double getProbability() {
        return probability;
    }
    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "PredictedPersonAccident [timestamp=" + timestamp + ", person_id=" + person_id + ", accident_id="
                + accident_id + ", probability=" + probability + "]";
    }
}
