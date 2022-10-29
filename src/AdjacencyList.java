import java.util.*;
import java.util.stream.Collectors;

/**
 * A directed graph data structure internally
 * setup as an adjacency list.
 *
 * @param <T> the type of the nodes
 */
public class AdjacencyList<T> {
    private final HashMap<T, HashSet<T>> vertexes;

    /**
     * Constructs a new adjacency list instance.
     */
    public AdjacencyList() {
        vertexes = new HashMap<>();
    }

    /**
     * Adds a new directed edge to the graph.
     *
     * @param from the vertex the edge points from
     * @param to the vertex the edge points to
     */
    public void addEdge(T from, T to) {
        if (!vertexes.containsKey(from)) {
            vertexes.put(from, new HashSet<>());
        }

        if (!vertexes.containsKey(to)) {
            vertexes.put(to, new HashSet<>());
        }

        vertexes.get(from).add(to);
    }

    public boolean searchEdge(T from, T to) {
        HashSet<T> edges = vertexes.get(from);
        if (edges == null) {
            return false;
        }
        return edges.contains(to);
    }

    /**
     * Creates an iterator of all the edges from a vertex.
     *
     * @param vertex the vertex to collect the edges for
     *
     * @return an iterator to iterate the edges of a vertex
     */
    public Iterator<T> iterateEdges(T vertex) {
        HashSet<T> edges = vertexes.get(vertex);

        if (edges == null) {
            return Collections.emptyIterator();
        }

        return edges.iterator();
    }

    public String toString() {
        return vertexes.entrySet().stream().map(
                edge -> String.format("%s: %s", edge.getKey(), edge.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")))
        ).collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Wraps values in double quotes and makes sure to escape any double quotes within.
     *
     * @return an escaped string ready to be used in the graph visualizer
     */
    static private String formatForVisualizer(String value) {
        return String.format("\"%s\"", value.replaceAll("\"", "\\\\\""));
    }

    /**
     * Formats the graph to be copied into an online graph visualizer for debugging.
     *
     * @link <a href="https://csacademy.com/app/graph_editor/">Graph Viewer</a>
     *
     * @return a string that can be copied into an online graph visualizer
     */
    public String exportForVisualizer() {
        return String.format(
                "%s%n%s",
                vertexes.keySet().stream().map(from -> formatForVisualizer(from.toString())).collect(Collectors.joining(System.lineSeparator())),
                vertexes.entrySet().stream().flatMap(
                        from -> from.getValue()
                                .stream()
                                .map(
                                        to -> String.format(
                                                "%s %s",
                                                formatForVisualizer(from.getKey().toString()),
                                                formatForVisualizer(to.toString())
                                        )
                                )
                ).collect(Collectors.joining(System.lineSeparator()))
        );
    }
}