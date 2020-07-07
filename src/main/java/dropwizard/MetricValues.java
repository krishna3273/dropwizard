package dropwizard;

public class MetricValues {
    int sum;
    int current;
    int count;
    int group_count;
    int weight_value;
    int weight_value_square;
    int value;

    public MetricValues(int sum, int count, int current, int group_count, int weight_value, int weight_value_square) {
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

    public int getSum() {
        return sum;
    }

    public int getCurrent() {
        return current;
    }

    public int getCount() {
        return count;
    }

    public int getGroup_count() {
        return group_count;
    }

    public int getWeight_value() {
        return weight_value;
    }

    public int getWeight_value_square() {
        return weight_value_square;
    }
}
