import java.util.Objects;

public class Vertex {
    private String name;
    private int number;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Vertex() {
    }

    Vertex(String name, int number) {
        this.name = name;
        this.number = number;
    }

    Vertex(int number) {
        this.number = number;
    }


    int getNumber() {
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
