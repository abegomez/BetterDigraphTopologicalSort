import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Program for generating kanji component dependency order via topological sort.
 *
 * @author Gomez, Acuna
 * @version 1.0
 */

public class Main {
    private static BufferedReader indexReader;
    private static BufferedReader indexComponentReader;
    /**
     * Entry point for testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BetterDiGraph bd = new BetterDiGraph();

        Hashtable<Integer, String> kanjiHT = new Hashtable<>();
        Hashtable<Integer, LinkedList<Integer>> edgesHT = new Hashtable<Integer, LinkedList<Integer>>();

        //load data-kanji.txt, hashtable id, character
        //load data-components.txt, use it to add edges to the graph
        try {
            indexReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("data-kanji.txt")), "UTF8"));
            indexComponentReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("data-components.txt")), "UTF8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String s;
        try {
            while((s = indexReader.readLine()) != null) {
                s.trim();
                if(Character.isDigit(s.charAt(0))){
                    String[] items = s.split("\\s+");
                    kanjiHT.put(Integer.parseInt(items[0]), items[1]);
                    //System.out.println("code: " + items[0] + " char: " + items[1]);
                }
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }

        String c;
        try{
            c = indexComponentReader.readLine();
            while((c = indexComponentReader.readLine()) != null) {
                c.trim();
                String[] comps = c.split("\\s+");
                int v1 = Integer.parseInt(comps[0]);
                int v2 = Integer.parseInt(comps[1]);
                if(!edgesHT.containsKey(v1))
                    edgesHT.put(v1, new LinkedList<>());
                edgesHT.get(v1).add(v2);

            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        //add ID's as nodes on the graph
        for(int i : kanjiHT.keySet()){
            bd.addVertex(i);
            //System.out.println("key: " + i + " : character: " + kanjiHT.get(i));
        }
        for(int i : edgesHT.keySet()){
            //bd.addVertex(i);
            for(int w : edgesHT.get(i))
                bd.addEdge(i, w);
            //System.out.println("v1: " + i + " : v2: " + edgesHT.get(i));
        }

        //Generate GraphViz file.
        try (PrintWriter pw = new PrintWriter("graph.gv")){
            pw.print("Digraph g {\n");
            for(int i : edgesHT.keySet()) {
                for(int w : edgesHT.get(i))
                    pw.println(kanjiHT.get(i) + "->" + kanjiHT.get(w) + ";");
            }
            pw.println("}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //create intuitive topological object, sort the graph
        IntuitiveTopological its = new IntuitiveTopological(bd);
        System.out.println("\n");
        for(int i : its.order()) {
            //display characters in the ordering.
            //take id and look up the correct character in the hashtable
            System.out.print(kanjiHT.get(i));
        }

    }
}

