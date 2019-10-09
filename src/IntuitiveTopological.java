import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class IntuitiveTopological implements TopologicalSort{
    private BetterDiGraph g;
    private HashMap<Integer, Boolean> marked;
    private Stack<Integer> cycle;
    private HashMap<Integer, Boolean> onStack;
    private HashMap<Integer, Integer> edgeTo;
    /**
     * Returns an iterable object containing a topological sort.
     * @return a topological sort.
     */
    public IntuitiveTopological(BetterDiGraph g) {
        this.g = g;
        marked = new HashMap<>(g.getVertexCount());


        onStack = new HashMap<>(g.getVertexCount());
        edgeTo = new HashMap<>(g.getVertexCount());
        for(int v : g.vertices()){
            marked.put(v, false);
            onStack.put(v, false);
        }
        for(int v : g.vertices()){
            if(!marked.get(v))
                dfs(g,v);
        }
    }
    public Iterable<Integer> order(){
        if(isDAG()) return null;
        LinkedList<Integer> order = new LinkedList<>();
        //LinkedList<Integer> toRemove = new LinkedList<>();
        while(g.getVertexCount() >0) {
            for (int v : g.vertices()) {
                if (g.getIndegree(v) == 0) {
                    order.add(v);
                    g.removeVertex(v);
                    break;
                }
            }
        }

        return order;
    }

    /**
     * Returns true if the graph being sorted is a DAG, false otherwise.
     * @return is graph a DAG
     */
    public boolean isDAG() {
        return cycle != null;
    }

    private void dfs(BetterDiGraph G, int v) {
        onStack.put(v, true);
        marked.put(v, true);
        for(int w: g.getAdj(v)){
            if(this.isDAG()) return;
            else if(!marked.get(w)){
                edgeTo.put(w, v);
                dfs(g, w);
            }
            else if(onStack.get(w)) {
                cycle = new Stack<Integer>();
                for(int x = v; x!=w; x = edgeTo.get(x))
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack.put(v, false);
    }

    //test intuitivetopological sort
    public static void main(String[] args) {
        BetterDiGraph g = new BetterDiGraph();
        g.addEdge(1,3);
        g.addEdge(3,8);
        g.addEdge(8,27);
        g.addEdge(27,127);
        //g.addEdge(127,1);
        IntuitiveTopological tps = new IntuitiveTopological(g);
        System.out.println("cycle: " + tps.isDAG());
        for(int i : tps.order()) {
            System.out.print(i);
        }

    }
}