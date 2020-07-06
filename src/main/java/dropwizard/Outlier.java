package dropwizard;

import java.util.HashMap;
import java.util.Map;

public class Evaluate {

    public static Map<String, Map<String, MetricData>> evaluate(Map<String, Map<String, MetricData>> data, String ses) {
        Map<String, Map<String, MetricData>> res=new HashMap<String, Map<String, MetricData>>();
        Outlier out=new Outlier(data);
        switch (ses){
            case "Daily":
                res=out.daily();
                break;
            case "Week":
                res=out.weekly();
                break;
            case "Month":
                res=out.monthly();
                break;
            default:
                break;
        }
        return res;
    }
    public static void main(String[] args) {
        String sesonality="Daily";
//        String sesonality="Week";
//        String sesonality="Month";
        evaluate(ReadJson.read("metric_data_1"),sesonality);

    }
}
