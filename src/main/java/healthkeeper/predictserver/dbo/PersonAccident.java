package main.java.healthkeeper.predictserver.dbo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonAccident {
    public static final String dateTimeStringFormat = 
            "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    private int id;
    private String timestamp;
    private Person person;
    private Accident accident;
    
    public PersonAccident(int id, String timestamp, Person person, Accident accident) {
        super();
        this.id = id;
        this.timestamp = timestamp;
        this.person = person;
        this.accident = accident;
    }
    
    public Date getTimestampAsDate(){
        Date date = null;
        try {
            date = new SimpleDateFormat(dateTimeStringFormat, 
                    Locale.US).parse(getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public PersonAccident() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public Accident getAccident() {
        return accident;
    }
    public void setAccident(Accident accident) {
        this.accident = accident;
    }
    @Override
    public String toString() {
        return "PersonAccident [id=" + id + ", timestamp=" + timestamp + ", person=" + person + ", accident=" + accident
                + "]";
    }
}
