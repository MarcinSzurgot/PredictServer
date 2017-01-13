package main.java.healthkeeper.predictserver.learn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.healthkeeper.predictserver.dbo.Accident;
import main.java.healthkeeper.predictserver.dbo.AverageMeasurement;

public class AccidentPredictor {
    private static final double MIN_SYSTOLIC_PRESS  = 0;
    private static final double MAX_SYSTOLIC_PRESS  = 250;
    private static final double MIN_DIASTOLIC_PRESS = 0;
    private static final double MAX_DIASTOLIC_PRESS = 250;
    private static final double MIN_HEART_RATE      = 0;
    private static final double MAX_HEART_RATE      = 250;
    private static final double MIN_TEMPERATURE     = 0;
    private static final double MAX_TEMPERATURE     = 45;
    private static final double MIN_SATURATION      = 0;
    private static final double MAX_SATURATION      = 100;
    private static final double MIN_GLUCOSE         = 0;
    private static final double MAX_GLUCOSE         = 300;
    private static final double MIN_CHOLESTEROL     = 0;
    private static final double MAX_CHOLESTEROL     = 300;
    private static final double MIN_ALCOHOL         = 0;
    private static final double MAX_ALCOHOL         = 100;
    private static final double MIN_SKIN_RESISTANCE = 0;
    private static final double MAX_SKIN_RESISTANCE = 100;
    private static final double MIN_STEP_RATE       = 0;
    private static final double MAX_STEP_RATE       = 200;
    
    private static final double[][] RANGE = {
            {MIN_SYSTOLIC_PRESS,  MAX_SYSTOLIC_PRESS},
            {MIN_DIASTOLIC_PRESS, MAX_DIASTOLIC_PRESS},
            {MIN_HEART_RATE,      MAX_HEART_RATE},
            {MIN_TEMPERATURE,     MAX_TEMPERATURE},
            {MIN_SATURATION,      MAX_SATURATION},
            {MIN_GLUCOSE,         MAX_GLUCOSE},
            {MIN_CHOLESTEROL,     MAX_CHOLESTEROL},
            {MIN_ALCOHOL,         MAX_ALCOHOL},
            {MIN_SKIN_RESISTANCE, MAX_SKIN_RESISTANCE},
            {MIN_STEP_RATE,       MAX_STEP_RATE}
    };
    
    private static final int INPUT_PER_MEASURE = 10;
    private static final int MEASURE_PER_PREDICT = 24;
    private static final int INPUT_COUNT = 
            INPUT_PER_MEASURE * MEASURE_PER_PREDICT;
    
    private List<Accident> accidentTypes; 
    private Predictor predictor;
    
    public AccidentPredictor(){
    }
    
    public void train(
            List<Accident> accidentTypes,
            List<DataPairRaw> dataset){
        this.accidentTypes = accidentTypes;
        System.out.println(accidentTypes);
        
        predictor = new Predictor(INPUT_COUNT, accidentTypes.size());
        predictor.train(getDataPairs(dataset));
    }
    
    public List<PredictionResult> predict(List<DataPairRaw> measures){
        List<PredictionResult> results = new ArrayList<>();
        for(DataPairRaw measure : measures){
            results.add(predict(measure));
        }
        return results;
    }
    
    public PredictionResult predict(DataPairRaw measures){
        DataPair data = convert(measures);
        List<AverageMeasurement> avMeasures = measures.getAvMeasures();
        PredictionResult pr = new PredictionResult();
        pr.setDate(getCenterDate(avMeasures));
        double[] output = predictor.predict(data.getInput());
        
        int personId = avMeasures.size() == 0 ? 1 : avMeasures.get(0).getPersonId();
        for(int i = 0; i < output.length; ++i){
            pr.getAccidents().add(new AccidentProbability(
                    output[i], accidentTypes.get(i), personId));
        }
        return pr;
    }
    
    private List<DataPair> getDataPairs(List<DataPairRaw> raws){
        List<DataPair> pairs = new ArrayList<>();
        for(DataPairRaw raw : raws){
            pairs.add(convert(raw));
        }
        return pairs;
    }
    
    private Date getCenterDate(List<AverageMeasurement> avMeasures){
        long millis = 0;
        for(AverageMeasurement av : avMeasures){
            millis += av.getStartingPeriodAsDate().getTime();
            millis += av.getEndPeriodAsDate()     .getTime();
        }
        int count = avMeasures.size() == 0 ? 1 : avMeasures.size();
        return new Date(millis / count);
    }
    
    private DataPair convert(DataPairRaw raw){
        double[] input = new double[INPUT_COUNT];
        double[] output = new double[accidentTypes.size()];
        DataPair pair = new DataPair(input, output);
        
        List<AverageMeasurement> avMeasures = raw.getAvMeasures();
        for(int i = 0; i < avMeasures.size(); ++i){
            AverageMeasurement av = avMeasures.get(i);
            double[] data = {
                    av.getSystolic_press(),
                    av.getDiastolic_press(),
                    av.getHeart_rate(),
                    av.getTemperature(),
                    av.getSaturation(),
                    av.getGlucose(),
                    av.getCholesterol(),
                    av.getAlcohol(),
                    av.getSkin_resistance()
            };
            
            normalize(input, data, MEASURE_PER_PREDICT * i);
            output[raw.getAccident().getId() - 1] = 1;
        }
        return pair;
    }
    
    private void normalize(double[] input, double[] data,  int srcIndex){
        for(int i = 0; i < input.length; ++i){
            input[srcIndex + i] = (data[i] - RANGE[i][0]) / (RANGE[i][1] - RANGE[i][0]);
        }
    }
}
