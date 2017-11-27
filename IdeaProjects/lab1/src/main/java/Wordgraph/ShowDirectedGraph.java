package Wordgraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ShowDirectedGraph {
    void showDirectedGraph(Map<String, Node> nodes) throws IOException, InterruptedException {

        Set<String> visit = new TreeSet<String>();

        StringBuilder content = new StringBuilder();
        content.append("digraph graphname{ \n");
        Stack<String> stk = new Stack<String>();
        for (String word : nodes.keySet()) {
            if (nodes.get(word).child.size() == 0) {
                content.append(word + "; \n");
            }
            if (visit.contains(word)) {
                //System.out.println(word);
                continue;
            } else {
                stk.push(word);
                String temp = "";
                while (stk.size() >= 1) {
                    temp = stk.pop();
                    visit.add(temp);
                    //System.out.println(temp);
//					DataInputStream in=new DataInputStream(System.in);
//					char ch = in.readChar();
                    for (String son : nodes.get(temp).child.keySet()) {
                        System.out.println(temp + "->" + son + ";");
                        content.append(temp + "->" + son + "[ label = " + nodes.get(temp).child.get(son) + " ]; \n");
                        if (!visit.contains(son)) {
                            stk.push(son);
                        }
                    }
                }
            }
        }
        content.append("}\n");

        try {
            String cont = content.toString();
            File file = new File("dotfile");

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(cont);
            bufferWritter.close();
            System.out.println("Done");
        } catch (IOException e) {
        }

        try {
            String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe  -Tgif dotfile -o picture.gif";
            Runtime.getRuntime().exec(cmd).waitFor();

        } catch (IOException e) {
        }
    }

    Vector<String> queryBridgeWords(Map<String, Node> nodes, String word1, String word2) {
        Vector<String> result = new Vector<String>();
        if (!nodes.containsKey(word1) || !nodes.containsKey(word2)) {
            return result;
        }
        for (String son : nodes.get(word1).child.keySet()) {
            if (nodes.get(son).child.containsKey(word2)) {
                result.add(son);
            }
        }

        return result;
    }
}
