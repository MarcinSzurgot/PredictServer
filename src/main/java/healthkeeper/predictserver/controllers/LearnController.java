package main.java.healthkeeper.predictserver.controllers;

import java.lang.reflect.Type;
import java.util.List;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.java.healthkeeper.predictserver.dbo.Accident;
import main.java.healthkeeper.predictserver.dbo.Measurement;
import main.java.healthkeeper.predictserver.dbo.Person;
import main.java.healthkeeper.predictserver.dbo.PersonAccident;

@RestController
public class LearnController {
    public static final String DATA_SERVER_URL     = "http://health-keeper-api.gear.host/api/";
    public static final String USERS_URL           = DATA_SERVER_URL + "/Person";
    public static final String MEASURES_URL        = DATA_SERVER_URL + "/Measurement";
    public static final String ACCIDENT_URL        = DATA_SERVER_URL + "/Accident";
    public static final String PERSON_ACCIDENT_URL = DATA_SERVER_URL + "/PersonAccident";
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping(value = "/train", method = RequestMethod.GET)
    public String train(Model model){
        for(PersonAccident pa : getPersonAccidents()){
            System.out.println(pa);
        }
        return "success";
    }
    
    public List<PersonAccident> getPersonAccidents(){
        return getJsonObject(PERSON_ACCIDENT_URL, 
                new TypeToken<List<PersonAccident>>(){}.getType());
    }
    
    public List<Accident> getAccidents(){
        return getJsonObject(ACCIDENT_URL, 
                new TypeToken<List<Accident>>(){}.getType());
    }
    
    public List<Measurement> getMeasurements(){
        return getJsonObject(MEASURES_URL, 
                new TypeToken<List<Measurement>>(){}.getType());
    }
    public List<Person> getUsers(){
        return getJsonObject(USERS_URL, 
                new TypeToken<List<Person>>(){}.getType());
    }
    
    private <T> T getJsonObject(String url, Type type){
        String data = restTemplate.getForObject(url, String.class);
        data = data.substring(1, data.length() - 1).replaceAll("\\\\", "");
        return new Gson().fromJson(data, type);
    }
    
}
