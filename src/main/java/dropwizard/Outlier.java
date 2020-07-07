package dropwizard;

import java.util.HashMap;
import java.util.Map;

public class Outlier {

    public static Map<String, Map<String, MetricData>> evaluate(Map<String, Map<String, MetricData>> data, String ses) {
        Map<String, Map<String, MetricData>> res=new HashMap<String, Map<String, MetricData>>();

        calculateThreshold c=new calculateThreshold(data,ses);

        c.calculate();

        System.out.println(c.getThreshold());

        return res;
    }
    public static void main(String[] args) {
        String sesonality="daily";
//        String sesonality="weekly";
//        String sesonality="monthly";
        evaluate(ReadJson.read("metric_data_1.json"),sesonality);

    }
}
