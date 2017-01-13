package main.java.healthkeeper.predictserver.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.java.healthkeeper.predictserver.dbo.Accident;
import main.java.healthkeeper.predictserver.dbo.AverageMeasurement;
import main.java.healthkeeper.predictserver.dbo.PersonAccident;
import main.java.healthkeeper.predictserver.learn.AccidentPredictor;
import main.java.healthkeeper.predictserver.learn.DataPairRaw;
import main.java.healthkeeper.predictserver.learn.PredictionResult;

@RestController
public class LearnController {
    private static final String DATA_SERVER_URL     = "http://health-keeper-api.gear.host/api";
    private static final String USERS_URL           = DATA_SERVER_URL + "/Person";
    private static final String MEASURES_URL        = DATA_SERVER_URL + "/Measurement";
    private static final String ACCIDENT_URL        = DATA_SERVER_URL + "/Accident";
    private static final String PERSON_ACCIDENT_URL = DATA_SERVER_URL + "/PersonAccident";
    private static final String AVERAGE_URL         = DATA_SERVER_URL + "/Average/params/all";  
    
    private static final long TIME_PROBE_SECONDS = 60 * 60 * 24; 
    private static final long TIME_RETRAIN_MILLIS = 1000;
    private static final long TIME_MAKE_PREDICTS = 1000;
    
    private AccidentPredictor predictor;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping(value = "/trainNetwork", method = RequestMethod.GET)
    public String trainNetwork(Model model){
        return trainAi();
    }
    
    @Scheduled(fixedRate = TIME_RETRAIN_MILLIS)
    public void retrain(){
        trainAi();
    }
    
    
    @Scheduled(fixedRate = TIME_MAKE_PREDICTS)
    public void makePredictions(){
        if(predictor != null){
            List<PredictionResult> predictions = predictor.predict(getTrainData());
            
            System.out.println(new Gson().toJson(predictions));
        }
    }
    
    private List<AverageMeasurement> getAverageMeasurements(
            int personId,
            Date startPeriod, 
            Date endPeriod,
            long probeSec){
        long start = startPeriod.getTime();
        long end   = endPeriod.getTime();
        String url = AVERAGE_URL + "/" + personId + 
                "/" + start + "/" + end + "/" + probeSec;
        return getJsonObject(url, 
                new TypeToken<List<AverageMeasurement>>(){}.getType());
     }
    
    private List<PersonAccident> getPersonAccidents(){
        return getJsonObject(PERSON_ACCIDENT_URL, 
                new TypeToken<List<PersonAccident>>(){}.getType());
    }
    
    private List<Accident> getAccidents(){
        return getJsonObject(ACCIDENT_URL, 
                new TypeToken<List<Accident>>(){}.getType());
    }
    
    private <T> T getJsonObject(String url, Type type){
        String data = restTemplate.getForObject(url, String.class);
        data = data.substring(1, data.length() - 1).replaceAll("\\\\", "");
        return new Gson().fromJson(data, type);
    }    
    
    private String trainAi(){
        if(predictor == null){
            predictor = new AccidentPredictor();
        }
        predictor.train(getAccidents(), getTrainData());
        return "success";
    }
    
    private List<DataPairRaw> getTrainData(){
        Map<Integer, Accident> accidents = new HashMap<>();
        for(Accident acc : getAccidents()){
            accidents.put(acc.getId(), acc);
        }
        
        List<DataPairRaw> dataPairs = new ArrayList<>();
        for(PersonAccident pa : getPersonAccidents()){
            Calendar c = Calendar.getInstance();
            c.setTime(pa.getTimestampAsDate());
            c.add(Calendar.HOUR, -48);
            
            Date startPeriod = c.getTime();
            c.add(Calendar.HOUR, 24);
            Date endPeriod = c.getTime();
            
            List<AverageMeasurement> avMeasures = getAverageMeasurements(
                    pa.getPerson().getId(), startPeriod, endPeriod, TIME_PROBE_SECONDS);
            
            DataPairRaw data = new DataPairRaw(avMeasures, 
                    accidents.get(pa.getAccident().getId()));
            dataPairs.add(data);
        }
        
        return dataPairs;
    }
}
