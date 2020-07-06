package dropwizard;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class ReadJson {

    public  static Map<String,Map<String,MetricData>> read(String fileName) {
        fileName="C:/Users/KRISHNA/Documents/dropwizard/src/main/resources/"+fileName;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> DataMap;
        try {
            DataMap = mapper.readValue(new File(fileName), Map.class);
            int granularity= (int) DataMap.get("granularitySeconds");
            ArrayList<Map<String,Object>> timeData= (ArrayList<Map<String,Object>>) DataMap.get("timeStampedDimensionsAndMetricsList");
            ArrayList<String> headers=(ArrayList<String>) DataMap.get("headersList");
            HashSet<String> tempSet=new HashSet<String>();
            int len;
            for(int i=1;i<headers.size();i++){
                tempSet.add(headers.get(i).split("[.]")[0]);
            }
            String [] metricNames=tempSet.toArray(new String[0]);
            len=6;
            Map<String,Map<String,MetricData>> datamap=new HashMap<String,Map<String,MetricData>>();
            for (Map<String, Object> curr : timeData) {
                String currTimeStamp = (String) curr.get("timeStampInUtc");
                ArrayList<String> currData = (ArrayList<String>) curr.get("dimensionsAndMetricsValues");
                String currEntity = currData.get(0);
                if (!datamap.containsKey(currEntity)) {
                    datamap.put(currEntity, new HashMap<String, MetricData>());
                    for (String metricName : metricNames) {
                        MetricData metricData = new MetricData(new ArrayList<Map<String, MetricValues>>());
                        metricData.setGranularity(granularity);
                        datamap.get(currEntity).put(metricName, metricData);
                    }
                }
                for (int j = 0; j < metricNames.length; j++) {
                    int currInd=j*len;
                    String metricName=headers.get(currInd+1).split("[.]")[0];
                    ArrayList<Integer> temp=new ArrayList<>();
                    for(int k=0;k<len;k++) {
                        temp.add(parseInt(currData.get(++currInd)));
                    }
                    MetricValues metricValues=new MetricValues(temp.get(0),temp.get(1),temp.get(2),temp.get(3),temp.get(4),temp.get(5));
                    MetricData metricData=datamap.get(currEntity).get(metricName);
                    Map<String,MetricValues> newMap=new HashMap<String, MetricValues>();
                    newMap.put(currTimeStamp,metricValues);
                    metricData.metricData.add(newMap);
                }
            }
            return datamap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
