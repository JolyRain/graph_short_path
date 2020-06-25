import java.util.Objects;

public class Characteristic {
    private int distance;
    private int time;
    private int cost;

    public Characteristic(int distance, int time, int cost) {
        this.distance = distance;
        this.time = time;
        this.cost = cost;
    }

    public Characteristic(){}

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public boolean isValid() {
        return distance > 0
                && time > 0
                && cost >= 0;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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
        return "distance = " + distance + ", time = " + time + ", cost = " + cost;
    }
}
