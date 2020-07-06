package dropwizard;

import com.google.inject.internal.cglib.core.internal.$Function;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class Outlier {
    Map<String, Map<String,MetricData>> data;
    Map<Integer,List<Integer>> Buckets;
    Map<Integer, Pair<Double,Double>> Threshold;
    public Outlier(Map<String, Map<String, MetricData>> data) {
        this.data = data;
        this.Buckets=new HashMap<Integer, List<Integer>>();
        this.Threshold=new HashMap<Integer,Pair<Double, Double>>();
    }

    public Map<String, Map<String,MetricData>> daily(){
        return data;
    }

    public Map<String, Map<String,MetricData>> weekly(){
        for (Map<String, MetricData> value : data.values()) {
            for (MetricData metricData : value.values()) {
                List<Map<String,MetricValues>> metricDataList=metricData.metricData;
                for (Map<String, MetricValues> metricValuesMap : metricDataList) {
                    metricValuesMap.forEach((k,v) ->{
                        int [] parsedTime=Helper.parseTimeStamp(k);
                        int hashKey=parsedTime[3]*24+parsedTime[4];
//                        int hashKey=parsedTime[4];
                        Buckets.computeIfAbsent(hashKey, k1 -> new ArrayList<Integer>());
                        List<Integer> temp=Buckets.get(hashKey);
                        temp.add(v.getValue());
                    });
                }
                for (Integer key : Buckets.keySet()) {
                    int multiplier=8;
                    List<Integer> l=Buckets.get(key);
                    double mean;
                    double variance;
                    double sd;
                    mean = Math.round((l.stream().mapToDouble(v -> v).sum())/l.size()*100)/100.0;
                    variance=(l.stream().mapToDouble(v -> (v-mean)*(v-mean)).sum())/l.size();
                    sd=Math.round(Math.sqrt(variance)*100)/100.0;
                    Threshold.put(key,new Pair<Double, Double>(mean,sd));
                }
            }
        }

        System.out.println(Buckets);
        System.out.println(Threshold);
        return data;
    }

    public Map<String, Map<String,MetricData>> monthly(){
        return data;
    }

    public static void main(String[] args) {
        Outlier o=new Outlier(ReadJson.read("metric_data_1.json"));
        o.weekly();
    }
}
