package frame;

import graph.Edge;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Line {
    private static final Font LABEL_FONT = new Font("monospace", Font.PLAIN, 12);
    private Line2D line;
    private Edge edge;

    public Line(Line2D line, Edge edge) {
        this.line = line;
        this.edge = edge;
    }

    Line2D getLine() {
        return line;
    }

    Edge getEdge() {
        return edge;
    }

    Rectangle2D.Double getWeightLabel(Graphics2D graphics2D) {
        Rectangle2D rectangle = new  Rectangle2D.Double(((float) ((line.getX1() + line.getX2()) / 2)),
                ((float) (line.getY1() + line.getY2()) / 2),
                graphics2D.getFontMetrics().stringWidth(edge.getCharacteristic().toString()),
                LABEL_FONT.getSize());
        return new Rectangle2D.Double(rectangle.getX() - (rectangle.getCenterX() - rectangle.getX()),
                rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    @Override
    public String toString() {
        return "Line: " + edge;
    }
}
