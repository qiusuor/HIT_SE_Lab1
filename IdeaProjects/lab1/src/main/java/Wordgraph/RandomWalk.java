package Wordgraph;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.util.*;

public class RandomWalk extends Thread {
    ImageView imgaarea=null;
    String pathString = "";
    TextArea inforarea=null;
    Map<String, Node> nodes=null;
    public volatile boolean exit = true;

    void MyThread_set(ImageView imagearea,String pathString,TextArea textArea,Map<String, Node> nodes){
        this.imgaarea=imagearea;
        this.inforarea=textArea;
        this.pathString=pathString;
        this.nodes=nodes;
    }

    public RandomWalk() {
    }
    public void set_stop () {
        this.exit=false;
    }
    public void set_start () {
        this.exit=true;
    }
    @Override
    public void run() {
        while(exit){
            String trace=randomWalk(nodes);
            Vector<String> traceString = new Vector<String>();
            for(String wordString:trace.split(" ")){
                if(wordString.length()>0)
                    traceString.add(wordString);
            }

            String toshowString = "";
            for (String wordString : traceString) {
                toshowString += wordString + " ";
            }
            Vector<Vector<String>> result = new Vector<Vector<String>>();
            Vector<String> traceStringreverse = new Vector<String >();
            for (int i = traceString.size() - 1; i >= 0; i--) {
                traceStringreverse.add(traceString.elementAt(i));
            }
            try {
                result.add(traceStringreverse);
                RebuildGraph rebuildGraph = new RebuildGraph();
                rebuildGraph.solution(result, nodes);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));
            } catch (Exception e) {
            }
            inforarea.setText(toshowString);

            System.out.println("Wordgraph.MyThread.run()");
            try{
                sleep(1000);
            }
            catch(Exception e){

            }
        }
    }

    String randomWalk(Map<String, Node> nodes) {
        Set<String> visit = new TreeSet<String>();
        Random r1 = new Random();
        int pos = (int) (Math.abs(r1.nextInt()) % nodes.size());
        String cur = null;
        int i = 0;
        for (String key : nodes.keySet()) {
            if (i == pos) {
                cur = key;
                break;
            }
            i++;
        }
        Vector<String> result = new Vector<String>();
        result.add(cur);

        while (true) {
            if (nodes.get(cur).child.size() == 0) {
                break;
            }
            int size = nodes.get(cur).child.keySet().size();
            pos = (int) (Math.abs(r1.nextInt()) % size);
            i = 0;
            String next = "";
            for (String key : nodes.get(cur).child.keySet()) {
                if (i == pos) {
                    next = key;
                    break;
                }
                i++;
            }
            String edge = cur + " " + next;
            if (!visit.contains(edge)) {
                visit.add(edge);
                //  System.out.println(edge);
            } else {
                break;
            }
            cur = next;
            result.add(next);
        }
        StringBuilder tempBuilder = new StringBuilder();
        for (String word : result) {
            tempBuilder.append(word + " ");
        }
        return tempBuilder.toString();
    }

}