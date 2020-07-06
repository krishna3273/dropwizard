package dropwizard;

import java.util.List;
import java.util.Map;

public class MetricData {
    List<Map<String,MetricValues>> metricData;
    int granularity;


    public MetricData(List<Map<String, MetricValues>> metricData) {
        this.metricData = metricData;
    }

    public List<Map<String, MetricValues>> getMetricData() {
        return metricData;
    }

    public void setMetricData(List<Map<String, MetricValues>> metricData) {
        this.metricData = metricData;
    }

    public int getGranularity() {
        return granularity;
    }

    public void setGranularity(int granularity) {
        this.granularity = granularity;
    }
}
