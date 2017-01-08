package main.java.healthkeeper.predictserver.dbo;

public class Measurement {
    private GpsPosition gps_position;
    private String      measurement_date;
    private double      alcohol;
    private double      cholesterol;
    private double      diastolic_press;
    private double      systolic_press;
    private double      glucose;
    private double      heart_rate;
    private double      saturation;
    private double      skin_resistance;
    private double      step_rate;
    private double      temperature;
    private int         id;
    private int         person_id;
    
    public Measurement(){
        
    }
    
    public Measurement(GpsPosition gps_position, String measurement_date, double alcohol, double cholesterol,
            double diastolic_press, double systolic_press, double glucose, double heart_rate, double saturation,
            double skin_resistance, double step_rate, double temperature, int id, int person_id) {
        super();
        this.gps_position = gps_position;
        this.measurement_date = measurement_date;
        this.alcohol = alcohol;
        this.cholesterol = cholesterol;
        this.diastolic_press = diastolic_press;
        this.systolic_press = systolic_press;
        this.glucose = glucose;
        this.heart_rate = heart_rate;
        this.saturation = saturation;
        this.skin_resistance = skin_resistance;
        this.step_rate = step_rate;
        this.temperature = temperature;
        this.id = id;
        this.person_id = person_id;
    }
    public GpsPosition getGps_position() {
        return gps_position;
    }
    public void setGps_position(GpsPosition gps_position) {
        this.gps_position = gps_position;
    }
    public String getMeasurement_date() {
        return measurement_date;
    }
    public void setMeasurement_date(String measurement_date) {
        this.measurement_date = measurement_date;
    }
    public double getAlcohol() {
        return alcohol;
    }
    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }
    public double getCholesterol() {
        return cholesterol;
    }
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
    public double getDiastolic_press() {
        return diastolic_press;
    }
    public void setDiastolic_press(double diastolic_press) {
        this.diastolic_press = diastolic_press;
    }
    public double getSystolic_press() {
        return systolic_press;
    }
    public void setSystolic_press(double systolic_press) {
        this.systolic_press = systolic_press;
    }
    public double getGlucose() {
        return glucose;
    }
    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }
    public double getHeart_rate() {
        return heart_rate;
    }
    public void setHeart_rate(double heart_rate) {
        this.heart_rate = heart_rate;
    }
    public double getSaturation() {
        return saturation;
    }
    public void setSaturation(double saturation) {
        this.saturation = saturation;
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
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPerson_id() {
        return person_id;
    }
    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    @Override
    public String toString() {
        return "Measurement [gps_position=" + gps_position + ", measurement_date=" + measurement_date + ", alcohol="
                + alcohol + ", cholesterol=" + cholesterol + ", diastolic_press=" + diastolic_press
                + ", systolic_press=" + systolic_press + ", glucose=" + glucose + ", heart_rate=" + heart_rate
                + ", saturation=" + saturation + ", skin_resistance=" + skin_resistance + ", step_rate=" + step_rate
                + ", temperature=" + temperature + ", id=" + id + ", person_id=" + person_id + "]";
    }
    
}
