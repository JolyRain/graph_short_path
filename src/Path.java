import java.util.*;

public class Path {
    private Graph graph;
    private TypeRoad typeRoad;
    private Stack<Vertex> path;
    private int sum = 0;

    public Path(Graph graph, TypeRoad typeRoad) {
        this.graph = graph;
        this.typeRoad = typeRoad;
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

    public int countSum(Vertex startVertex, Vertex endVertex) {
        Map<Vertex, Boolean> visited = new HashMap<>();
        Map<Vertex, Integer> length = new HashMap<>();
        Map<Vertex, Vertex> previous = new HashMap<>();
        Set<Vertex> vertices = graph.getVertices();
        path.add(startVertex);
        int unvisited = vertices.size();

        for (Vertex currentVertex : vertices) {
            visited.put(currentVertex, false);
            length.put(currentVertex, currentVertex.equals(startVertex) ? 0 : Integer.MAX_VALUE);
        }

        for (Vertex adjacentVertex : graph.adjacent(startVertex)) {
            length.put(adjacentVertex, getWeight(startVertex, adjacentVertex, typeRoad));
        }

        while (unvisited > 0) {
            Vertex currentVertex = null;
            int min = Integer.MAX_VALUE;
            for (Map.Entry<Vertex, Integer> item : length.entrySet()) {
                if (item.getValue() < min && !visited.get(item.getKey())) {
                    min = item.getValue();
                    currentVertex = item.getKey();
                }
            }
            for (Vertex vertexTo : graph.adjacent(currentVertex)) {
                int weight = getWeight(currentVertex, vertexTo, typeRoad);
                if (length.get(vertexTo) > length.get(currentVertex) + weight) {
                    length.put(vertexTo, length.get(currentVertex) + weight);
                    previous.put(vertexTo, currentVertex);
                }
            }
            visited.put(currentVertex, true);
            unvisited--;
        }
        restorePath(previous, startVertex, endVertex);
        return sum = length.get(endVertex);
    }

    private void restorePath(Map<Vertex, Vertex> previous, Vertex startVertex, Vertex finalVertex) {
        while (!finalVertex.equals(startVertex)) {
            path.push(finalVertex);
            finalVertex = previous.get(finalVertex);
        }
        path.push(startVertex);
//        System.out.println(path);
    }



    public Stack<Vertex> getPath() {
        return path;
    }

    public void setPath(Stack<Vertex> path) {
        this.path = path;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
