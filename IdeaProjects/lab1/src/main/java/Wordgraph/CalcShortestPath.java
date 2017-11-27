package Wordgraph;

import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class CalcShortestPath {
    Vector<String> calcShortestPath(Map<String, Node> nodes, String word1, String word2,ShortestPath shortestPath) {
        Map<String, Vector<String>> path = shortestPath.ShortestPath(nodes, word1);
        Vector<Vector<String>> result = new Vector<Vector<String>>();

        Stack<String> stk = new Stack<String>();
        String cur = word2;
        stk.push(cur);
        Stack<Vector<String>> stkv = new Stack<Vector<String>>();
        Vector<String> temp = new Vector<String>();
        while (stk.size() > 0) {
            cur = stk.pop();
            temp.add(cur);
            //System.out.println(cur);
            for (int i = 0; i < path.get(cur).size(); i++) {
                if (path.get(cur).elementAt(i) == word1) {
                    temp.add(word1);
                    result.add(temp);
                    if (stkv.size() >= 1) {
                        temp = stkv.pop();
                    }
                } else {
                    stk.push(path.get(cur).elementAt(i));
                    if (i >= 1) {
                        Vector<String> tt = new Vector<String>(temp);
                        stkv.push(tt);
                    }
                }
            }
        }
        Vector<String> reStrings = new Vector<String>();
        for (Vector<String> lineStrings : result) {
            StringBuilder tempBuilder = new StringBuilder();
            for(int i=lineStrings.size()-1;i>=0;i--)
                tempBuilder.append(lineStrings.elementAt(i)+" ");
            reStrings.add(tempBuilder.toString());
        }
        return reStrings;
    }
}
