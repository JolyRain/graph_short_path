import java.util.*;

public class Graph {
    private TreeMap<Vertex, Set<Vertex>> adjacentVerticesMap = new TreeMap<>(Comparator.comparingInt(Vertex::getNumber));
    private Set<Vertex> vertices = new HashSet<>();
    private List<Edge> edges = new LinkedList<>();

    public boolean addVertex(Vertex vertex) {
        vertex.setNumber(vertices.size());
        adjacentVerticesMap.put(vertex, new HashSet<>());
        return vertices.add(vertex);
    }

    private boolean isAdjacent(Vertex firstVertex, Vertex secondVertex) {
        for (Vertex adjacent : adjacent(firstVertex)) {
            if (adjacent.equals(secondVertex)) {
                return true;
            }
        }
        return false;
    }

    public Edge getEdge(Vertex start, Vertex end) {
        for (Edge edge : edges) {
            if (edge.contains(start) && edge.contains(end)) return edge;
        }
        return null;
    }

    public void addEdge(Vertex startVertex, Vertex endVertex) {
        if (!isAdjacent(startVertex, endVertex)) {
            adjacentVerticesMap.get(startVertex).add(endVertex);
            adjacentVerticesMap.get(endVertex).add(startVertex);
            edges.add(new Edge(startVertex, endVertex));
        }
    }

    public void addEdge(Edge edge) {
        Vertex startVertex = edge.getStartVertex();
        Vertex endVertex = edge.getEndVertex();
        if (!isAdjacent(startVertex, endVertex)) {
            adjacentVerticesMap.get(startVertex).add(endVertex);
            adjacentVerticesMap.get(endVertex).add(startVertex);
            edges.add(edge);
        }
    }

    public void removeVertex(Vertex deletingVertex) {
        vertices.remove(deletingVertex);
        for (Vertex adjacentVertex : adjacentVerticesMap.get(deletingVertex)) {
            Set<Vertex> adjacentVertices = adjacentVerticesMap.get(adjacentVertex);
            adjacentVertices.removeIf(currentVertex -> currentVertex.equals(deletingVertex));
        }
        edges.removeIf(deletingEdge -> deletingEdge.getStartVertex().equals(deletingVertex) ||
                deletingEdge.getEndVertex().equals(deletingVertex));
        adjacentVerticesMap.remove(deletingVertex);
        recountIndex(deletingVertex);
    }

    private void recountIndex(Vertex deletingVertex) {
        for (Vertex currentVertex : vertices) {
            if (deletingVertex.getNumber() < currentVertex.getNumber()) {
                currentVertex.setNumber(currentVertex.getNumber() - 1);
            }
        }
    }

    public void removeEdge(Vertex startVertex, Vertex endVertex) {
        Edge deletingEdge = new Edge(startVertex, endVertex);
        edges.removeIf(edge -> edge.equals(deletingEdge));
        adjacentVerticesMap.get(startVertex).removeIf(adjacentVertex -> adjacentVertex.equals(endVertex));
        adjacentVerticesMap.get(endVertex).removeIf(adjacentVertex -> adjacentVertex.equals(startVertex));
    }

    public void clear() {
        if (adjacentVerticesMap != null) adjacentVerticesMap.clear();
        if (vertices != null) vertices.clear();
    }

    Iterable<Vertex> adjacent(Vertex vertex) {
        return adjacentVerticesMap.get(vertex);
    }

    public TreeMap<Vertex, Set<Vertex>> getAdjacentVerticesMap() {
        return adjacentVerticesMap;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
