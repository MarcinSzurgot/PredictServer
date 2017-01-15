package main.java.healthkeeper.predictserver.controllers;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import main.java.healthkeeper.predictserver.dbo.PredictedPersonAccident;
import main.java.healthkeeper.predictserver.learn.AccidentPredictor;
import main.java.healthkeeper.predictserver.learn.DataPairRaw;

@RestController
public class LearnController {
    private static final String DATA_SERVER_URL     = "http://health-keeper-api.gear.host/api";
    private static final String ACCIDENT_URL        = DATA_SERVER_URL + "/Accident";
    private static final String PERSON_ACCIDENT_URL = DATA_SERVER_URL + "/PersonAccident";
    private static final String AVERAGE_URL         = DATA_SERVER_URL + "/Average/params/all";  
    private static final String PREDICT_POST_URL    = DATA_SERVER_URL + "/PredictedPersonAccident";
    
    private static final int TIME_PROBE_SECONDS  = HealthKeeperGlobals.TIME_PROBE_SCONDS; 
    private static final int UPPER_BOUND_SECONDS = HealthKeeperGlobals.UPPER_BOUND_SECONDS;
    private static final int LOWER_BOUND_SECONDS = HealthKeeperGlobals.LOWER_BOUND_SECONDS;
    private static final int TIME_RETRAIN_MILLIS = 1000000;
    private static final int TIME_MAKE_PREDICTS  = 1000;
    
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
            List<PredictedPersonAccident> predictions = predictor.predict(getTrainDataMock());
            for(PredictedPersonAccident prediction : predictions){
                System.out.println(prediction);
                restTemplate.postForObject(PREDICT_POST_URL, prediction, PredictedPersonAccident.class);
            }
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
        predictor.train(getAccidents(), getTrainDataMock());
        return "success";
    }
    
    private List<DataPairRaw> getTrainDataMock(){
        List<Accident> accidents = getAccidents();
        
        String date = new SimpleDateFormat(AverageMeasurement.DATE_FORMAT).format(new Date());
        Random rnd = new Random();
        List<DataPairRaw> dataPairs = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            List<AverageMeasurement> avs = new ArrayList<>();
            for(int j = 0; j < HealthKeeperGlobals.MEASURES_COUNT; ++j){
                AverageMeasurement av = new AverageMeasurement(
                        i * HealthKeeperGlobals.MEASURES_COUNT + j, i % 4, 
                        rnd.nextDouble() * (AccidentPredictor.MAX_SYSTOLIC_PRESS - AccidentPredictor.MIN_SYSTOLIC_PRESS) + AccidentPredictor.MIN_SYSTOLIC_PRESS,
                        rnd.nextDouble() * (AccidentPredictor.MAX_DIASTOLIC_PRESS - AccidentPredictor.MIN_DIASTOLIC_PRESS) + AccidentPredictor.MIN_DIASTOLIC_PRESS, 
                        rnd.nextDouble() * (AccidentPredictor.MAX_HEART_RATE - AccidentPredictor.MIN_HEART_RATE) + AccidentPredictor.MIN_HEART_RATE,
                        rnd.nextDouble() * (AccidentPredictor.MAX_TEMPERATURE - AccidentPredictor.MIN_TEMPERATURE) + AccidentPredictor.MIN_TEMPERATURE,
                        rnd.nextDouble() * (AccidentPredictor.MAX_SATURATION - AccidentPredictor.MIN_SATURATION) + AccidentPredictor.MIN_SATURATION,
                        rnd.nextDouble() * (AccidentPredictor.MAX_GLUCOSE - AccidentPredictor.MIN_GLUCOSE) + AccidentPredictor.MIN_GLUCOSE,
                        rnd.nextDouble() * (AccidentPredictor.MAX_CHOLESTEROL - AccidentPredictor.MIN_CHOLESTEROL) + AccidentPredictor.MIN_CHOLESTEROL,
                        rnd.nextDouble() * (AccidentPredictor.MAX_ALCOHOL - AccidentPredictor.MIN_ALCOHOL) + AccidentPredictor.MIN_ALCOHOL,
                        rnd.nextDouble() * (AccidentPredictor.MAX_SKIN_RESISTANCE - AccidentPredictor.MIN_SKIN_RESISTANCE) + AccidentPredictor.MIN_SKIN_RESISTANCE,
                        rnd.nextDouble() * (AccidentPredictor.MAX_STEP_RATE - AccidentPredictor.MIN_STEP_RATE) + AccidentPredictor.MIN_STEP_RATE,
                        date, date);
                avs.add(av);
            }
            dataPairs.add(new DataPairRaw(avs, accidents.get(rnd.nextInt(accidents.size()))));
        }
        
        return dataPairs;
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
            c.add(Calendar.SECOND, LOWER_BOUND_SECONDS);
            
            Date startPeriod = c.getTime();
            c.add(Calendar.SECOND, UPPER_BOUND_SECONDS);
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
