import cardozapavlik.cs146.project3.AdjacencyList;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    public void graph() {
        AdjacencyList<String> graph = new AdjacencyList<>();

        graph.addEdge("PHX", "LAX");
        graph.addEdge("PHX", "JFK");
        graph.addEdge("JFK", "OKC");
        graph.addEdge("JFK", "HEL");
        graph.addEdge("JFK", "LOS");
        graph.addEdge("MEX", "LAX");
        graph.addEdge("MEX", "BKK");
        graph.addEdge("MEX", "LIM");
        graph.addEdge("MEX", "EZE");
        graph.addEdge("LIM", "BKK");

        assertTrue(graph.searchEdge("MEX", "LAX"));
        assertFalse(graph.searchEdge("LAX", "MEX"));

        assertFalse(graph.searchEdge("JFK", "LAX"));

        assertFalse(graph.searchEdge("LOL", "LOL"));

        Iterator<String> iter = graph.iterateEdges("PHX");

        HashSet<String> edges = new HashSet<>();
        edges.add(iter.next());
        edges.add(iter.next());

        assertTrue(edges.contains("LAX"));
        assertTrue(edges.contains("JFK"));

        assertFalse(graph.iterateEdges("LAP").hasNext());

        System.out.println(graph);
        System.out.println(graph.exportForVisualizer());
    }
}