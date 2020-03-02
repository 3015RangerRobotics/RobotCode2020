package lib.data_analyzer;

import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;
import java.util.Collections;

public class DataAnalyzer {
    private ArrayList<Double> times;
    private ArrayList<Double> data;
    private ArrayList<Double> deriv;

    public DataAnalyzer(){
        this.times = new ArrayList<>();
        this.data = new ArrayList<>();
        this.deriv = new ArrayList<>();
    }

    public void clearData(){
        times.clear();
        data.clear();
        deriv.clear();
    }

    public void addData(double value){
        double time = Timer.getFPGATimestamp();
        times.add(time);
        data.add(value);
        int size = data.size();
        if(size > 1){
            deriv.add((value - data.get(size - 2)) / (time - times.get(size - 2)));
        }else{
            deriv.add(0.0);
        }
    }

    public double getMaxValue(){
        if(data.isEmpty()){
            return 0;
        }
        return Collections.max(data);
    }

    public double getMinValue(){
        if(data.isEmpty()){
            return 0;
        }
        return Collections.min(data);
    }

    public double getAvgValue(){
        if(data.isEmpty()){
            return 0;
        }
        double sum = 0;
        for(double d : data){
            sum += d;
        }
        return sum / data.size();
    }

    public double getDerivativeMax(){
        if(deriv.isEmpty()){
            return 0;
        }
        return Collections.max(deriv);
    }

    public double getDerivativeMin(){
        if(deriv.isEmpty()){
            return 0;
        }
        return Collections.min(deriv);
    }
}
