import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Node {
    private static final Font NAME_FONT = new Font("monospace", Font.PLAIN, 12);
    private Vertex vertex;
    private Circle circle;

    Node(Vertex vertex, Circle circle) {
        this.vertex = vertex;
        this.circle = circle;
    }

    float getNumberX() {
        return (float) (circle.getX() + Circle.RADIUS / 3.0);
    }

    float getNumberY() {
        return (float) (circle.getY() + Circle.RADIUS / 1.5);
    }

    Vertex getVertex() {
        return vertex;
    }

    Circle getCircle() {
        return circle;
    }

    Rectangle2D.Double getRectName(Graphics2D graphics2D) {
        return new Rectangle2D.Double((float) circle.getX(), (float) circle.getY() - NAME_FONT.getSize(),
                graphics2D.getFontMetrics().stringWidth(vertex.getName()),
                NAME_FONT.getSize());
    }

    @Override
    public String toString() {
        return "Node: "
                + vertex.toString()
                + " <" + vertex.getName() + ">"
                + " (" + circle.getX() + ", " + circle.getY() + ") ";
    }
}
