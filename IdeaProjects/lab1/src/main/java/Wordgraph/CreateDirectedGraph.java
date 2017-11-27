package Wordgraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Vector;

public class CreateDirectedGraph {
    public void createDirectedGraph(Map<String, Node> nodes,String filename,boolean onlinetag) throws Exception {

        File file = new File(filename);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        Vector<String> reads = new Vector<String>();
        SearchWordFromInternet seach=new SearchWordFromInternet();
        while (temp != null) {
            reads.add(temp);
            temp = br.readLine();
        }
        br.close();

        for (int i = 0; i < reads.size(); i++) {
            String line = "";
            for (int j = 0; j < reads.elementAt(i).length(); j++) {
                if (Character.isLetter(reads.elementAt(i).charAt(j))) {
                    line += reads.elementAt(i).charAt(j);
                } else {
                    line += ' ';
                }
            }
            reads.setElementAt(line, i);
        }

        Vector<Vector<String>> data = new Vector<Vector<String>>();

        Vector<String> sentence = new Vector<String>();
        for (String oneline : reads) {
            for (String word : oneline.split(" ")) {

                if(onlinetag){
                    System.out.println("online");
                    if (word.length() >= 1) {

                        sentence.add(seach.solution(word.toLowerCase()));
                    }
                }
                else{
                    if (word.length() > 3) {
                        if (word.charAt(word.length() - 1) == 's' && word.charAt(word.length() - 2) == 'e' && word.charAt(word.length() - 3) == 'i') {
                            StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - 3; i++) {
                                newword.append(word.charAt(i));
                            }
                            newword.append('y');
                            word = newword.toString();
                        }
                        if (word.charAt(word.length() - 1) == 'd' && word.charAt(word.length() - 2) == 'e' && word.charAt(word.length() - 3) == 'i') {
                            StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - 3; i++) {
                                newword.append(word.charAt(i));
                            }
                            newword.append('y');
                            word = newword.toString();
                        }
                        if (word.charAt(word.length() - 1) == 'g' && word.charAt(word.length() - 2) == 'n' && word.charAt(word.length() - 3) == 'i') {
                            StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - 3; i++) {
                                newword.append(word.charAt(i));
                            }
                            word = newword.toString();
                        }
                    }

                    if (word.length() >= 1) {

                        sentence.add(word.toLowerCase());
                    }


                }

            }
        }
        if (sentence.size() >= 1) {
            data.add(sentence);
        }

        for (Vector<String> line : data) {
            Node cur = null;
            if (nodes.containsKey(line.elementAt(0))) {
                cur = nodes.get(line.elementAt(0));
            } else {
                cur = new Node(line.elementAt(0));
                nodes.put(line.elementAt(0), cur);
            }
            if (line.size() > 1) {
                for (int i = 1; i < line.size(); i++) {
                    if (cur.child.containsKey(line.elementAt(i))) {
                        cur.child.put(line.elementAt(i), cur.child.get(line.elementAt(i)) + 1);
                        cur = nodes.get(line.elementAt(i));
                    } else {
                        if (nodes.containsKey(line.elementAt(i))) {
                            cur.child.put(line.elementAt(i), 1);
                            cur = nodes.get(line.elementAt(i));
                        } else {
                            Node n = new Node(line.elementAt(i));
                            nodes.put(line.elementAt(i), n);
                            cur.child.put(line.elementAt(i), 1);
                            cur = n;
                        }
                    }
                }
            }
        }
    }
}
