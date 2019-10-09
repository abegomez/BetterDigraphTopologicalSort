import java.io.*;
import java.util.Hashtable;

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
        Hashtable<Integer, Integer> edgesHT = new Hashtable<>();
        //load data-kanji.txt, hashtable id, character
        //load data-components.txt, use it to add edges to the graph
        try {
            indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-kanji.txt")), "UTF8"));
            indexComponentReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-components.txt")), "UTF8"));
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
                edgesHT.put(Integer.parseInt(comps[0]),
                        Integer.parseInt(comps[1]));

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
            bd.addEdge(i, edgesHT.get(i));
            //System.out.println("v1: " + i + " : v2: " + edgesHT.get(i));
        }



        //TODO: implement this



        //create intuitive topological object, sort the graph
        IntuitiveTopological its = new IntuitiveTopological(bd);
        System.out.println("\n");
        for(int i : its.order()) {
            //display characters in the ordering.
            //take id and look up the correct character in the hashtable
            System.out.print(kanjiHT.get(i));
        }
        
        //Freebie: this is one way to load the UTF8 formated character data.
        //
    }
}

