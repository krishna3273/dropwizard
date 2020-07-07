package dropwizard;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class calculateThreshold {
    Map<String, Map<String,MetricData>> data;
    Map<String,Map<String,Map<Integer, Pair<Double,Double>>>> Threshold;
    String ses;
    public calculateThreshold(Map<String, Map<String, MetricData>> data,String seasonality){
        this.data = data;
        this.ses=seasonality;
        this.Threshold=new HashMap<String, Map<String, Map<Integer, Pair<Double, Double>>>>();
    }

    public Map<String, Map<String, Map<Integer, Pair<Double, Double>>>> getThreshold() {
        return Threshold;
    }


    public void calculate(){
        for (String entity : data.keySet()) {
            Map<String,MetricData> value=data.get(entity);
            for (String metricName : value.keySet()) {
                MetricData metricData=value.get(metricName);
                List<Map<String,MetricValues>> metricDataList=metricData.metricData;
                Map<Integer,List<Integer>> Buckets=new HashMap<Integer, List<Integer>>();
                for (Map<String, MetricValues> metricValuesMap : metricDataList) {
                    metricValuesMap.forEach((k,v) ->{
                        int hashKey=Helper.hash(k,this.ses);
                        Buckets.computeIfAbsent(hashKey, k1 -> new ArrayList<Integer>());
                        List<Integer> temp=Buckets.get(hashKey);
                        temp.add(v.getValue());
                    });
                }
                System.out.println(Buckets);
                Map<Integer, Pair<Double,Double>> currThreshold=new HashMap<Integer,Pair<Double, Double>>();
                for (Integer key : Buckets.keySet()) {
                    int multiplier=8;
                    List<Integer> l=Buckets.get(key);
                    double mean;
                    double variance;
                    double sd;
                    mean = Math.round((l.stream().mapToDouble(v -> v).sum())/l.size()*100)/100.0;
                    variance=(l.stream().mapToDouble(v -> (v-mean)*(v-mean)).sum())/l.size();
                    sd=Math.round(Math.sqrt(variance)*100)/100.0;
                    currThreshold.put(key,new Pair<Double, Double>(mean,sd));
                }
                System.out.println(currThreshold);
                Threshold.put(entity, new HashMap<String, Map<Integer, Pair<Double, Double>>>());
                Threshold.get(entity).put(metricName,currThreshold);
            }
        }
    }


//    public static void main(String[] args) {
//        calculateThreshold o=new calculateThreshold(ReadJson.read("metric_data_1.json"),"week");
//        o.calculate();
//    }
}
