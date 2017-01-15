package main.java.healthkeeper.predictserver.learn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import main.java.healthkeeper.predictserver.controllers.HealthKeeperGlobals;
import main.java.healthkeeper.predictserver.dbo.Accident;
import main.java.healthkeeper.predictserver.dbo.AverageMeasurement;
import main.java.healthkeeper.predictserver.dbo.PredictedPersonAccident;

public class AccidentPredictor {
    public static final double MIN_SYSTOLIC_PRESS  = 0;
    public static final double MAX_SYSTOLIC_PRESS  = 250;
    public static final double MIN_DIASTOLIC_PRESS = 0;
    public static final double MAX_DIASTOLIC_PRESS = 250;
    public static final double MIN_HEART_RATE      = 0;
    public static final double MAX_HEART_RATE      = 250;
    public static final double MIN_TEMPERATURE     = 0;
    public static final double MAX_TEMPERATURE     = 45;
    public static final double MIN_SATURATION      = 0;
    public static final double MAX_SATURATION      = 100;
    public static final double MIN_GLUCOSE         = 0;
    public static final double MAX_GLUCOSE         = 300;
    public static final double MIN_CHOLESTEROL     = 0;
    public static final double MAX_CHOLESTEROL     = 300;
    public static final double MIN_ALCOHOL         = 0;
    public static final double MAX_ALCOHOL         = 100;
    public static final double MIN_SKIN_RESISTANCE = 0;
    public static final double MAX_SKIN_RESISTANCE = 100;
    public static final double MIN_STEP_RATE       = 0;
    public static final double MAX_STEP_RATE       = 200;
    
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
    
    private static final int INPUT_PER_MEASURE = RANGE.length;
    private static final int MEASURE_PER_PREDICT = HealthKeeperGlobals.MEASURES_COUNT;
    private static final int INPUT_COUNT = 
            INPUT_PER_MEASURE * MEASURE_PER_PREDICT;
    
    private static final double TARGET_ERROR = 0.03;
    private static final double EPOCHS_LIMIT = 10;
    
    private Map<Integer, Accident> accidentTypes; 
    private BasicNetwork network;
    
    public AccidentPredictor(){
    }
    
    public Map<Integer, Accident> getAccidentTypes(){
        return accidentTypes;
    }
    
    public void train(
            List<Accident> accidentTypes,
            List<DataPairRaw> dataset){
        this.accidentTypes = new HashMap<>();
        for(Accident accident : accidentTypes){
            this.accidentTypes.put(accident.getId(), accident);
        }
        
        int inp = INPUT_COUNT;
        int out = accidentTypes.size();
        int hid = (int) Math.sqrt(inp * out);
        
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, inp));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, hid));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, out));
        network.getStructure().finalizeStructure();
        network.reset();
        
        MLDataSet trainSet = getDataPairs(dataset);
        final Train train = new ResilientPropagation(network, trainSet);

        int epoch = 0;
        do{
            train.iteration();
            epoch++;
        }while(train.getError() > TARGET_ERROR && epoch < EPOCHS_LIMIT);
    }
    
    public List<PredictedPersonAccident> predict(List<DataPairRaw> measures){
        List<PredictedPersonAccident> results = new ArrayList<>();
        for(DataPairRaw measure : measures){
            results.add(predict(measure));
        }
        return results;
    }
    
    public PredictedPersonAccident predict(DataPairRaw measures){
        MLDataPair data = convert(measures);
        List<AverageMeasurement> avMeasures = measures.getAvMeasures();
        PredictedPersonAccident pr = new PredictedPersonAccident();
        pr.setTimestamp(getCenterDate(avMeasures));
        
        MLData output = network.compute(data.getInput());
        
        double max = Double.MIN_VALUE;
        int index = -1;
        for(int i = 0; i < output.size(); ++i){
            if(output.getData(i) > max){
                max = output.getData(i);
                index = i;
            }
        }
        int personId = avMeasures.size() == 0 ? 1 : avMeasures.get(0).getPerson_id();
        pr.setPerson_id(personId);
        pr.setAccident_id(accidentTypes.get(index + 1).getId());
        return pr;
    }
    
    private MLDataSet getDataPairs(List<DataPairRaw> raws){
        MLDataSet pairs = new BasicMLDataSet();
        for(DataPairRaw raw : raws){
            if(!raw.isEmpty()){
                pairs.add(convert(raw));
            }
        }
        return pairs;
    }
    
    private Date getCenterDate(List<AverageMeasurement> avMeasures){
        long millis = 0;
        for(AverageMeasurement av : avMeasures){
            millis = Math.max(av.getEndPeriodAsDate().getTime(), millis);
        }
        return new Date(millis + HealthKeeperGlobals.UPPER_BOUND_SECONDS * 1000);
    }
    
    private MLDataPair convert(DataPairRaw raw){
        double[] input = new double[INPUT_COUNT];
        double[] output = new double[accidentTypes.size()];
        
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
                    av.getSkin_resistance(),
                    av.getStep_rate()
            };
            normalize(input, data, MEASURE_PER_PREDICT * i);
            output[raw.getAccident().getId() - 1] = 1;
        }
        return new BasicMLDataPair(
                new BasicMLData(input), 
                new BasicMLData(output));
    }
    
    private void normalize(double[] input, double[] data,  int srcIndex){
        for(int i = 0; i < data.length; ++i){
            input[srcIndex + i] = (data[i] - RANGE[i][0]) / (RANGE[i][1] - RANGE[i][0]);
        }
    }
}
