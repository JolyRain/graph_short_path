import java.util.Objects;

public class Characteristic {
    private int distance;
    private int time;
    private int cost;

    Characteristic(int distance, int time, int cost) {
        this.distance = distance;
        this.time = time;
        this.cost = cost;
    }

    Characteristic() {
    }

    int getDistance() {
        return distance;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }

    int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    boolean isValid() {
        return isCorrect(distance) && isCorrect(time) && isCorrect(cost);
    }

    private boolean isCorrect(int number) {
        return number > 0 && number < Integer.MAX_VALUE;
    }

    int getCost() {
        return cost;
    }

    void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return distance == that.distance
                && time == that.time
                && cost == that.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, time, cost);
    }

    @Override
    public String toString() {
        return "d-" + distance + ", t-" + time + ", c-" + cost;
    }
}
