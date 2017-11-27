package Wordgraph;

import java.util.Map;
import java.util.Vector;

public class QueryBridgeWords {

    Vector<String> queryBridgeWords( Map<String, Node> nodes, String word1, String word2) {
        Vector<String> result = new Vector<String>();
        if (!nodes.containsKey(word1) || !nodes.containsKey(word2))
            return result;
        for (String son : nodes.get(word1).child.keySet())
            if (nodes.get(son).child.containsKey(word2))
                result.add(son);
        return result;
    }
}

