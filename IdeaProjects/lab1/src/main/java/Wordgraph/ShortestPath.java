package Wordgraph;

import java.util.*;

public class ShortestPath {
    Map<String, Vector<String>> ShortestPath(Map<String, Node> nodes, String word1) {
        if (!nodes.containsKey(word1)) {
            return null;
        }
        Map<String, Integer> lenth = new TreeMap<String, Integer>();
        Set<String> visit = new TreeSet<String>();
        Map<String, Vector<String>> path = new TreeMap<String, Vector<String>>();

        Stack<String> stk = new Stack<String>();
        stk.push(word1);
        visit.add(word1);
        lenth.put(word1, 0);
        String temp = "";
        while (stk.size() >= 1) {
            temp = stk.pop();

            for (String son : nodes.get(temp).child.keySet()) {
                if (!visit.contains(son)) {
                    stk.push(son);
                    Vector<String> value = new Vector<String>();
                    value.add(temp);
                    path.put(son, value);
                    lenth.put(son, lenth.get(temp) + nodes.get(temp).child.get(son));
                    visit.add(son);
                } else {
                    if (lenth.get(temp) + nodes.get(temp).child.get(son) < lenth.get(son)) {
                        Vector<String> value = path.get(son);
                        value.clear();
                        value.add(temp);
                        //if(stk.contains(son)) stk.remove(son);
                        stk.push(son);
                        lenth.put(son, lenth.get(temp) + nodes.get(temp).child.get(son));
                        path.put(son, value);
                    } else if (lenth.get(temp) + nodes.get(temp).child.get(son) == lenth.get(son)) {
                        Vector<String> value = path.get(son);
                        value.add(temp);
                        path.put(son, value);
                    }
                }

            }
        }
        return path;
    }
}
