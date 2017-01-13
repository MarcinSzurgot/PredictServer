package main.java.healthkeeper.predictserver.dbo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AverageMeasurement {
    public static final String DATE_FORMAT = "MM/dd/yyyy KK:mm:ss a";
    
    private int id;
    private int personId;
    private double systolic_press;
    private double diastolic_press;
    private double heart_rate; 
    private double temperature;
    private double saturation;
    private double glucose;
    private double cholesterol;
    private double alcohol;
    private double skin_resistance;
    private double step_rate;
    private String starting_period;
    private String end_period;
    
    public AverageMeasurement(){
        super();
    }
    
    public AverageMeasurement(int id, int personId, double systolic_press, double diastolic_press, double heart_rate,
            double temperature, double saturation, double glucose, double cholesterol, double alcohol,
            double skin_resistance, double step_rate, String starting_period, String end_period) {
        super();
        this.id = id;
        this.personId = personId;
        this.systolic_press = systolic_press;
        this.diastolic_press = diastolic_press;
        this.heart_rate = heart_rate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.glucose = glucose;
        this.cholesterol = cholesterol;
        this.alcohol = alcohol;
        this.skin_resistance = skin_resistance;
        this.step_rate = step_rate;
        this.starting_period = starting_period;
        this.end_period = end_period;
    }
    
    private static Date getAsDate(String strDate){
        DateFormat df = new SimpleDateFormat(
                DATE_FORMAT, Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getStartingPeriodAsDate(){
        return getAsDate(starting_period);
    }
    
    public Date getEndPeriodAsDate(){
        return getAsDate(end_period);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public double getSystolic_press() {
        return systolic_press;
    }
    public void setSystolic_press(double systolic_press) {
        this.systolic_press = systolic_press;
    }
    public double getDiastolic_press() {
        return diastolic_press;
    }
    public void setDiastolic_press(double diastolic_press) {
        this.diastolic_press = diastolic_press;
    }
    public double getHeart_rate() {
        return heart_rate;
    }
    public void setHeart_rate(double heart_rate) {
        this.heart_rate = heart_rate;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getSaturation() {
        return saturation;
    }
    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }
    public double getGlucose() {
        return glucose;
    }
    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }
    public double getCholesterol() {
        return cholesterol;
    }
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
    public double getAlcohol() {
        return alcohol;
    }
    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }
    public double getSkin_resistance() {
        return skin_resistance;
    }
    public void setSkin_resistance(double skin_resistance) {
        this.skin_resistance = skin_resistance;
    }
    public double getStep_rate() {
        return step_rate;
    }
    public void setStep_rate(double step_rate) {
        this.step_rate = step_rate;
    }
    public String getStarting_period() {
        return starting_period;
    }
    public void setStarting_period(String starting_period) {
        this.starting_period = starting_period;
    }
    public String getEnd_period() {
        return end_period;
    }
    public void setEnd_period(String end_period) {
        this.end_period = end_period;
    }

    @Override
    public String toString() {
        return "AverageMeasurement [id=" + id + ", personId=" + personId + ", systolic_press=" + systolic_press
                + ", diastolic_press=" + diastolic_press + ", heart_rate=" + heart_rate + ", temperature=" + temperature
                + ", saturation=" + saturation + ", glucose=" + glucose + ", cholesterol=" + cholesterol + ", alcohol="
                + alcohol + ", skin_resistance=" + skin_resistance + ", step_rate=" + step_rate + ", starting_period="
                + starting_period + ", end_period=" + end_period + "]";
    }
 
    
}
