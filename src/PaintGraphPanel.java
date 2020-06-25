import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaintGraphPanel extends JPanel {
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
    private static final int STROKE_WIDTH = 5;
    private static final int LINE_WIDTH = 35;
    private static final int LINE_HEIGHT = 5;
    private static final int ZERO = 0;

    private List<Node> nodes = new LinkedList<>();
    private List<Line> lines = new LinkedList<>();
    private Graph graph = new Graph();

    private CreatingVertexMode creatingVertexMode = new CreatingVertexMode();
    private DeletingMode deletingMode = new DeletingMode();
    private ConnectingVertexMode connectingVertexMode = new ConnectingVertexMode();
    private InputRoadCharacteristics inputRoad = new InputRoadCharacteristics();
    private InputVertex inputVertex = new InputVertex();

    PaintGraphPanel() {
        setCreatingMode();

    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        setPaintSettings(graphics2D);
        paintLines(graphics2D);
        paintVertices(graphics2D);
    }

    private void setPaintSettings(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke(STROKE_WIDTH));
        graphics2D.setFont(FONT);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(ZERO, ZERO, this.getWidth(), this.getHeight());
        graphics2D.setColor(Color.BLACK);
    }

    void clear() {
        graph.clear();
        nodes.clear();
        lines.clear();
        repaint();
    }

    private void paintLines(Graphics2D graphics2D) {
        for (Line road : lines) {
            Line2D line = road.getLine();
            graphics2D.draw(line);
            graphics2D.drawString(road.getEdge().getCharacteristic().toString(),
                    (float) line.getX2() / 2, (float) line.getY2() / 2);
        }
    }

    private void paintVertices(Graphics2D graphics2D) {
        Circle circle;
        Vertex vertex;
        for (Node node : nodes) {
            circle = node.getCircle();
            vertex = node.getVertex();
            if (node.equals(connectingVertexMode.startNode)) graphics2D.setColor(Color.RED);
            else graphics2D.setColor(Color.BLACK);
            graphics2D.draw(circle);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fill(node.getCircle());
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(vertex.getName(), (float) circle.getX(), (float) circle.getY() - 10);
            graphics2D.drawString(String.valueOf(vertex.getNumber()), node.getNumberX(), node.getNumberY());
        }
    }

    void setCreatingMode() {
        removeMouseListener(connectingVertexMode);
        removeMouseListener(deletingMode);
        addMouseListener(creatingVertexMode);
    }

    void setDeletingMode() {
        removeMouseListener(connectingVertexMode);
        removeMouseListener(creatingVertexMode);
        addMouseListener(deletingMode);
    }

    void setModeConnecting() {
        removeMouseListener(deletingMode);
        removeMouseListener(creatingVertexMode);
        addMouseListener(connectingVertexMode);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : nodes) {
            stringBuilder.append(node).append(graph.getAdjacentVerticesMap().get(node.getVertex()));
            stringBuilder.append("\n");
        }
        for (Line line : lines) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private Node readNodeFromFile(String string) throws Exception {
        double x, y;
        int vertexNumber;
        Pattern pattern = Pattern.compile("\\{([\\d]+)}");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) vertexNumber = Integer.parseInt(matcher.group(1));
        else throw new Exception("Wrong format");
        pattern = Pattern.compile("\\(+([\\d]+\\.0), ([\\d]+\\.0)\\)");
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            x = Double.parseDouble(matcher.group(1));
            y = Double.parseDouble(matcher.group(2));
        } else throw new Exception("Wrong format");
        Vertex newVertex = new Vertex(vertexNumber);
        Circle newCircle = new Circle(x, y);
        return new Node(newVertex, newCircle);
    }

    private void readAdjacentListFromFile(Vertex vertex, String string) throws Exception {
        Pattern pattern = Pattern.compile("\\[]");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()) {
            pattern = Pattern.compile("\\[(\\{\\d*}|(, ))*]");
            matcher = pattern.matcher(string);
            if (matcher.find()) {
                pattern = Pattern.compile("\\{([\\d]+)}[, \\]]");
                matcher = pattern.matcher(matcher.group(1));
                while (matcher.find()) {
                    graph.getAdjacentVerticesMap().get(vertex).add(new Vertex(Integer.parseInt(matcher.group(1))));
                }
            } else throw new Exception("Wrong format");
        }
    }

    private void readEdgeFromFile(String string) throws Exception {
//        int startVertex, secondVertex;
//        Pattern pattern = Pattern.compile("<\\{([\\d]+)}, \\{([\\d]+)}>");
//        Matcher matcher = pattern.matcher(string);
//        if (matcher.find()) {
//            startVertex = Integer.parseInt(matcher.group(1));
//            secondVertex = Integer.parseInt(matcher.group(2));
//        } else throw new Exception("Wrong format");
//        Node firstNode = nodes.get(graph.getVertices().get(startVertex).getNumber());
//        Node secondNode = nodes.get(graph.getVertices().get(secondVertex).getNumber());
//        graph.addEdge(graph.getVertices().get(startVertex), graph.getVertices().get(secondVertex));
//        lines.add(setNewLine(firstNode, secondNode));
    }

    void readGraphFromFile(Scanner scanner) throws Exception {
        clear();
        String string;
        while (scanner.hasNextLine()) {
            string = scanner.nextLine();
            if (string.startsWith("Node:")) {
                Node newNode = readNodeFromFile(string);
                Vertex newVertex = newNode.getVertex();
                readAdjacentListFromFile(newVertex, string);
                graph.addVertex(newVertex);
                nodes.add(newNode);
            } else if (string.startsWith("Line:")) readEdgeFromFile(string);
        }
    }

    private Line setNewLine(Node startNode, Node endNode) {
        Circle circleStart = startNode.getCircle();
        Circle circleEnd = endNode.getCircle();
        double radius = correctRadius();
        return new Line(new Line2D.Double(
                circleStart.getX() + radius,
                circleStart.getY() + radius,
                circleEnd.getX() + radius,
                circleEnd.getY() + radius),
                new Edge(startNode.getVertex(), endNode.getVertex()));
    }

    private double correctRadius() {
        UnaryOperator<Double> operator = number -> number / 2.0;
        return operator.apply(Circle.RADIUS);
    }

    private class CreatingVertexMode extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            double x = event.getX() - correctRadius();
            double y = event.getY() - correctRadius();
            String name = inputVertex.getNameVertex();
            if (name == null || name.equals("")) return;
            Vertex newVertex = new Vertex();
            graph.addVertex(newVertex);
            nodes.add(new Node(newVertex, new Circle(x, y)));
            newVertex.setName(name);
            repaint();
        }
    }

    private class DeletingMode extends MouseAdapter {
        Node deletingNode = null;
        Line deletingLine = null;
        boolean vertexClicked = false;
        List<Line> deletingLines = new LinkedList<>();

        @Override
        public void mouseClicked(MouseEvent event) {
            for (Node node : nodes) {
                if (node.getCircle().contains(event.getX(), event.getY())) {
                    deletingNode = node;
                    vertexClicked = true;
                }
            }
            for (Line line : lines) {
                if (vertexClicked) {
                    if (containsLine(deletingNode, line)) deletingLines.add(line);
                } else if (line.getLine().intersects(event.getX(), event.getY(), LINE_WIDTH, LINE_HEIGHT))
                    deletingLine = line;
            }
            if (vertexClicked) removeNode();
            else if (deletingLine != null) removeLine();
            repaint();
            clear();
        }

        private void clear() {
            deletingNode = null;
            deletingLine = null;
            deletingLines.clear();
            vertexClicked = false;
        }

        private void removeNode() {
            graph.removeVertex(deletingNode.getVertex());
            nodes.remove(deletingNode);
            deleteLines(deletingLines);
        }

        private void removeLine() {
            graph.removeEdge(deletingLine.getEdge().getStartVertex(), deletingLine.getEdge().getEndVertex());
            lines.remove(deletingLine);
        }

        private void deleteLines(List<Line> deletingLines) {
            for (Line line : deletingLines) {
                lines.remove(line);
            }
        }

        private boolean containsLine(Node node, Line line) {
            return node.getCircle().contains(line.getLine().getX1(), line.getLine().getY1())
                    || node.getCircle().contains(line.getLine().getX2(), line.getLine().getY2());
        }
    }

    private class ConnectingVertexMode extends MouseAdapter {
        Node startNode;
        Line newLine;
        boolean vertexClicked = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            for (Node node : nodes) {
                if (!vertexClicked) {
                    if (node.getCircle().contains(e.getX(), e.getY())) {
                        startNode = node;
                        vertexClicked = true;
                        break;
                    }
                } else if (startNode.getCircle().contains(e.getX(), e.getY())) {
                    clear();
                    break;
                } else if (node.getCircle().contains(e.getX(), e.getY())) {
                    setNewLine(node);
                    lines.add(newLine);
                    graph.addEdge(newLine.getEdge());
                    clear();
                    break;
                }
            }
            repaint();
        }

        private void setNewLine(Node node) {
            Circle circleStart = startNode.getCircle();
            Circle circleEnd = node.getCircle();
            double radius = correctRadius();
            Edge newEdge = new Edge(startNode.getVertex(), node.getVertex());
            Characteristic characteristic = inputRoad.getCharacteristic();
            if (characteristic == null) return;
            newEdge.setCharacteristic(characteristic);
            newLine = new Line(new Line2D.Double(
                    circleStart.getX() + radius,
                    circleStart.getY() + radius,
                    circleEnd.getX() + radius,
                    circleEnd.getY() + radius),
                    newEdge);
        }

        private void clear() {
            startNode = null;
            newLine = null;
            vertexClicked = false;
        }
    }

    private static class InputRoadCharacteristics extends JPanel {

        private JTextField distanceField = new JTextField(5);
        private JTextField timeField = new JTextField(5);
        private JTextField costField = new JTextField(5);

        InputRoadCharacteristics() {
            this.add(new JLabel("distance:"));
            this.add(distanceField);
            this.add(new JLabel("time:"));
            this.add(timeField);
            this.add(new JLabel("cost:"));
            this.add(costField);
        }
        private void clear(){
            distanceField.setText("");
            timeField.setText("");
            costField.setText("");
        }

        Characteristic getCharacteristic() {
            clear();
            Characteristic characteristic = new Characteristic();
            this.setVisible(true);
            int result = JOptionPane.showConfirmDialog(null, this,
                    "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    characteristic.setDistance(Integer.parseInt(distanceField.getText()));
                    characteristic.setTime(Integer.parseInt(timeField.getText()));
                    characteristic.setCost(Integer.parseInt(costField.getText()));
                    if (!characteristic.isValid()) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else return null;
            return characteristic;
        }
    }

    private static class InputVertex extends JPanel {
        private JTextField nameField = new JTextField(15);

        InputVertex() {
            this.add(new JLabel("Name:"));
            this.add(nameField);
        }

        private void clear(){
            nameField.setText("");
        }

        String getNameVertex() {
            String name = null;
clear();
            this.setVisible(true);
            int result = JOptionPane.showConfirmDialog(null, this,
                    "Please enter the name of city", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                name = nameField.getText();
            }
            return name;
        }
    }
}

