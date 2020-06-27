package graph;

import java.util.Objects;

public class Vertex {
    private String name;
    private int number;

    public Vertex() {
    }

    public Vertex(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public Vertex(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vertex vertex = (Vertex) object;
        return name.equals(vertex.name) || number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{" + number + "}";
    }
}
