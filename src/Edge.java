import java.util.Objects;

public class Edge {
    private Vertex startVertex;
    private Vertex endVertex;
    private Characteristic characteristic;

    int getNeedType(TypeRoad typeRoad) {
        switch (typeRoad) {
            case CHEAP:
                return characteristic.getCost();
            case SHORT:
                return characteristic.getDistance();
            case QUICK:
                return characteristic.getTime();
        }
        return 0;
    }

    boolean contains(Vertex vertex) {
        return startVertex.equals(vertex) || endVertex.equals(vertex);
    }

    Edge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    Vertex getStartVertex() {
        return startVertex;
    }

    Vertex getEndVertex() {
        return endVertex;
    }

    Characteristic getCharacteristic() {
        return characteristic;
    }

    void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Edge edge = (Edge) object;
        return ((startVertex.equals(edge.startVertex) && endVertex.equals(edge.endVertex))
                || (startVertex.equals(edge.endVertex) && endVertex.equals(edge.startVertex)))
                && characteristic.equals(edge.characteristic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startVertex, endVertex, characteristic);
    }

    @Override
    public String toString() {
        return "<" + startVertex + ", " + endVertex + ">" + " {" + characteristic + "}";
    }
}
