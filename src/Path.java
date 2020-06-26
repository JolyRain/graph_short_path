import java.util.*;

public class Path {
    private Graph graph;
    private TypeRoad typeRoad;
    private Stack<Edge> path = new Stack<>();
    private int sum = 0;

    public Path(Graph graph, TypeRoad typeRoad) {
        this.graph = graph;
        this.typeRoad = typeRoad;
    }

    public Path(Graph graph) {
        this.graph = graph;
    }

    private int getWeight(Vertex start, Vertex end, TypeRoad typeRoad) {
        int value = 0;
        for (Edge edge : graph.getEdges()) {
            if (edge.contains(start) && edge.contains(end)) {
                value = edge.getNeedType(typeRoad);
            }
        }
        return value;
    }

    public boolean contains(Edge edge) {
        return path.contains(edge);
    }

    public void countPath(Vertex startVertex, Vertex endVertex) {
        if (startVertex.equals(endVertex)) {
            sum = -1;
            return;
        }
        Map<Vertex, Boolean> visited = new HashMap<>();
        Map<Vertex, Integer> lengthMap = new HashMap<>();
        Map<Vertex, Vertex> previous = new HashMap<>();
        Set<Vertex> vertices = graph.getVertices();
        int unvisited = vertices.size();

        for (Vertex currentVertex : vertices) {
            visited.put(currentVertex, false);
            lengthMap.put(currentVertex, currentVertex.equals(startVertex) ? 0 : Integer.MAX_VALUE);
        }

        for (Vertex adjacentVertex : graph.adjacent(startVertex)) {
            lengthMap.put(adjacentVertex, getWeight(startVertex, adjacentVertex, typeRoad));
        }

        while (unvisited > 0) {
            Vertex currentVertex = null;
            int min = Integer.MAX_VALUE;
            for (Map.Entry<Vertex, Integer> item : lengthMap.entrySet()) {
                if (item.getValue() < min && !visited.get(item.getKey())) {
                    min = item.getValue();
                    currentVertex = item.getKey();
                }
            }
            if (currentVertex == null){
                sum = -1;
                return;
            }
            for (Vertex vertexTo : graph.adjacent(currentVertex)) {
                int weight = getWeight(currentVertex, vertexTo, typeRoad);
                if (lengthMap.get(vertexTo) >= lengthMap.get(currentVertex) + weight) {
                    lengthMap.put(vertexTo, lengthMap.get(currentVertex) + weight);
                    previous.put(vertexTo, currentVertex);
                }
            }
            visited.put(currentVertex, true);
            unvisited--;
        }
        restorePath(previous, startVertex, endVertex);
        sum = lengthMap.get(endVertex);
    }

    private void restorePath(Map<Vertex, Vertex> previous, Vertex startVertex, Vertex finalVertex) {
        while (!finalVertex.equals(startVertex)) {
            path.push(graph.getEdge(finalVertex, previous.get(finalVertex)));
            finalVertex = previous.get(finalVertex);
        }
    }

    public void clear() {
        typeRoad = null;
        sum = 0;
        path.clear();
    }



    public Stack<Edge> getPath() {
        return path;
    }

    public void setPath(Stack<Edge> path) {
        this.path = path;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
