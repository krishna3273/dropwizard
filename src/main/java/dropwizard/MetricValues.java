package dropwizard;

public class MetricValues {
    int sum;
    int current;
    int count;
    int group_count;
    int weight_value;
    int weight_value_square;
    int value;

    public MetricValues(int sum, int current, int count, int group_count, int weight_value, int weight_value_square) {
        this.sum = sum;
        this.current = current;
        this.count = count;
        this.group_count = group_count;
        this.weight_value = weight_value;
        this.weight_value_square = weight_value_square;
        this.value=this.sum/this.count;
    }

    public int getValue() {
        return this.sum/this.count;
    }
}
