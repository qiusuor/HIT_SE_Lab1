package Wordgraph;

import java.util.Map;
import java.util.Vector;

public class GenerateNewText {
    String generateNewText(Map<String, Node> nodes, String inputText,boolean onlinetag,QueryBridgeWords queryBridgeWords) {
        StringBuilder builder = new StringBuilder();
        String result = "";

        for (int i = 0; i < inputText.length(); i++) {
            if (Character.isLetter(inputText.charAt(i))) {
                builder.append(inputText.charAt(i));
            } else {
                builder.append(" ");
            }
        }
        Vector<String> temp = new Vector<String>();
        SearchWordFromInternet seach = new SearchWordFromInternet();
        for (String word : builder.toString().split(" ")) {
            if (word.length() >= 1) {
                if (onlinetag == true) {
                    temp.add(seach.solution(word.toLowerCase()));
                } else temp.add(word.toLowerCase());
            }
        }
        StringBuilder result_builder = new StringBuilder();

        Vector<String> bridge = null;
        boolean first_tag = true;
        for (int i = 0; i < temp.size() - 1; i++) {
            if (first_tag) {
                result_builder.append(" " + temp.elementAt(i));
                first_tag = false;
            }
            bridge = queryBridgeWords.queryBridgeWords(nodes, temp.elementAt(i), temp.elementAt(i + 1));
            if ((bridge).size() > 0) {
                result_builder.append(" " + bridge.elementAt(0));
            }
            result_builder.append(" " + temp.elementAt(i + 1));
        }
        result = result_builder.toString();
        return result;
    }
}
