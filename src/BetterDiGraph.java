import java.util.*;

/**
 * Better Directed Graph implementing EditableDiGraph interface
 * 
 * @author Gomez, Acuna
 * @version 1.0
 */


public class BetterDiGraph implements EditableDiGraph {
    private Hashtable<Integer, LinkedList<Integer>> adj;
    private int V;
    private int E;

    public BetterDiGraph() {
        V = 0;
        E = 0;
        adj = new Hashtable<>();
    }
    /**
     * Adds an edge between two vertices, v and w. If vertices do not exist,
     * adds them first.
     *
     * @param v source vertex
     * @param w destination vertex
     */
    public void addEdge(int v, int w) {
        if(!adj.containsKey(v))
            addVertex(v);
        if(!adj.containsKey(w))
            addVertex(w);
        adj.get(v).add(w);
        E++;
    }

    /**
     * Adds a vertex to the graph. Does not allow duplicate vertices.
     *
     * @param v vertex number
     */
    public void addVertex(int v) {
        if(!adj.containsKey(v)) {
            adj.put(v, new LinkedList<Integer>());
            V++;
        }
    }

    /**
     * Returns the direct successors of a vertex v.
     *
     * @param v vertex
     * @return successors of v
     */
    public Iterable<Integer> getAdj(int v) {
        return adj.get(v);
    }
    
    /**
     * Number of edges.
     *
     * @return edge count
     */
    public int getEdgeCount() {
        return this.E;
    }
    
    /**
     * Returns the in-degree of a vertex.
     * @param v vertex
     * @return in-degree.
     * @throws NoSuchElementException exception thrown if vertex does not exist.
     */
    public int getIndegree(int v) throws NoSuchElementException {
        if(!adj.containsKey(v))
            throw new NoSuchElementException();
        int degree = 0;
        for(Integer w : adj.keySet()) {
            if(adj.get(w).contains(v))
                degree++;
        }
        return degree;
    }
    
    /**
     * Returns number of vertices.
     * @return vertex count
     */
    public int getVertexCount() {
        return V;
    }
    
    /**
     * Removes edge from graph. If vertices do not exist, does not remove edge.
     *
     * @param v source vertex
     * @param w destination vertex
     */
    public void removeEdge(int v, int w) {
        if(adj.containsKey(v) && adj.containsKey(w)) {
            adj.get(v).removeFirstOccurrence(w);
            E--;
        }
    }

    /**
     * Removes vertex from graph. If vertex does not exist, does not try to
     * remove it.
     *
     * @param v vertex
     */
    public void removeVertex(int v) {
        for(int w : adj.keySet()) {
            while(adj.get(w).contains(v)){
                adj.get(w).removeFirstOccurrence(v);
                this.E--;
            }
        }
        if(adj.containsKey(v)) {
            adj.remove(new Integer(v));
            V--;
        }
    }

    /**
     * Returns iterable object containing all vertices in graph.
     *
     * @return iterable object of vertices
     */
    public Iterable<Integer> vertices() {
        return adj.keySet();
    }

    public static void main(String[] args) {
        //test addedge
        BetterDiGraph bd = new BetterDiGraph();
        System.out.println("adding edge 1->2");
        bd.addEdge(1,2);
        //test addvertex
        System.out.println("adding vertex: 5");
        bd.addVertex(5);
        //test getAdj
        System.out.println("adj to 1: " + bd.getAdj(1));
        //test getedgecount
        System.out.println("edge count: "+bd.getEdgeCount());
        //test getindegree
        System.out.println("Indegree of 2: " + bd.getIndegree(2));
        //test getvertexcount
        System.out.println("vertexcount: " +bd.getVertexCount());
        //test remove edge
        System.out.println("removing edge 1->2");
        bd.removeEdge(1,2);
        //test remove vertex
        System.out.println("removing vertex 5: ");
        bd.removeVertex(5);
        System.out.println("vertex count; " + bd.getVertexCount());
        System.out.println("edge count: " + bd.getEdgeCount());

        System.out.println("removing vertex and checking edgecount");
        bd.addEdge(1,2);
        bd.addEdge(2,1);
        bd.removeVertex(2);
        System.out.println(bd.getEdgeCount());
        for(int i : bd.vertices()) {
            System.out.println(i);
        }
    }

}