package main.java.healthkeeper.predictserver.learn;

import main.java.healthkeeper.predictserver.dbo.Accident;

public class AccidentProbability {
    private double probability;
    private Accident accident;
    private int personId;
    
    public AccidentProbability() {
        super();
    }

    public AccidentProbability(double probability, Accident accident, int personId) {
        super();
        this.probability = probability;
        this.accident = accident;
        this.personId = personId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
    
    
    
}
