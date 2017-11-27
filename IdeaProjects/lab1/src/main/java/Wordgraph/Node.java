package Wordgraph;

import java.util.Map;
import java.util.TreeMap;

public class Node {
    String data = new String();

    Map<String, Integer> child=new TreeMap<String, Integer>();
    Node(){
        child.clear();
        data="";
        data="";
    }
    Node(String data){
        this.data=data;
        child.clear();
    }

}