package Wordgraph;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javafx.scene.image.Image;

/**
 *
 * @author qiusuo
 */
public class Interface implements Initializable {

    final String pathString = "C:\\Users\\qiusuo\\IdeaProjects\\lab1\\picture.gif"; //有向图图片的存放位置
    boolean firstwalk = true;
    boolean onlinetag = false;


    static Map<String, Node> nodes = new TreeMap<String, Node>();
    CreateDirectedGraph createDirectedGraph = new CreateDirectedGraph();
    ShowDirectedGraph showDirectedGraph = new ShowDirectedGraph();
    GenerateNewText generateNewText = new GenerateNewText();
    QueryBridgeWords queryBridgeWords = new QueryBridgeWords();
    CalcShortestPath calcShortestPath = new CalcShortestPath();
    ShortestPath shortestPath=new ShortestPath();


    @FXML
    private TextArea textarea;
    @FXML
    private Button generate;
    @FXML
    private Button join;
    @FXML
    private TextField filepath;
    @FXML
    private TextField word1;
    @FXML
    private TextField word2;
    @FXML
    private Button search;
    @FXML
    private Button randomwalk;
    @FXML
    private Button distance;
    @FXML
    private ImageView imgaarea;
    @FXML
    private TextArea inforarea;
    @FXML
    private Button stopwalk;

    RandomWalk myThread = new RandomWalk();
    @FXML
    private Button offline;
    @FXML
    private Button online;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == generate) {
            String textString = filepath.getText();
            try {
                nodes.clear();
                System.out.println(textString);
                createDirectedGraph.createDirectedGraph(nodes,textString,onlinetag);
                showDirectedGraph.showDirectedGraph(nodes);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));

            } catch (Exception e) {
            }

        }
        if (event.getSource() == join) {
            String textString = textarea.getText();
            inforarea.setText(generateNewText.generateNewText(nodes, textString, onlinetag,queryBridgeWords));
        }
        if (event.getSource() == search) {
            String word_1 = word1.getText();
            String word_2 = word2.getText();
            if (word_2.length() > 0 && word_1.length() > 0) {

                Vector<String> bridgeStrings = queryBridgeWords.queryBridgeWords(nodes, word_1, word_2);
                String toshowString = "The bridge word: ";
                for (String wordString : bridgeStrings) {
                    toshowString += (wordString + "  ");
                }
                if (bridgeStrings.size() > 0) {
                    inforarea.setText(toshowString);
                    try {
                        MarkPoint markPoint = new MarkPoint();
                        markPoint.solution(bridgeStrings, nodes);
                        imgaarea.setImage(new Image(new FileInputStream(pathString)));
                    } catch (Exception e) {
                    }

                } else {
                    inforarea.setText("No bridge words from word1 to word2!");
                }

            } else {
                inforarea.setText("No word1 or word2 in the graph!");
            }
        }
        if (event.getSource() == distance) {
            String word_1 = word1.getText();
            String word_2 = word2.getText();
            if (word_1.length() > 0 && word_2.length() > 0) {
                Vector<String> resultStrings = calcShortestPath.calcShortestPath(nodes, word_1, word_2,shortestPath);

                String toshowString = "";
                for (int j = 0; j < resultStrings.size(); j++) {
                    toshowString += (resultStrings.elementAt(j) + " \n");
                }

                inforarea.setText(toshowString);
                try {
                    RebuildGraph rebuildGraph = new RebuildGraph();
                    Vector<Vector<String>> result = new Vector<Vector<String>>();
                    for (String sen : resultStrings) {
                        Vector<String> tempStrings = new Vector<String>();
                        for (String word : sen.split(" "))
                            if (word.length() > 0)
                                tempStrings.add(0, word);
                        result.add(tempStrings);
                    }

                    rebuildGraph.solution(result, nodes);
                    imgaarea.setImage(new Image(new FileInputStream(pathString)));
                } catch (Exception e) {
                }
            } else if (word_1.length() > 0) {

                String toshowString = "";
                for (String wordString : nodes.keySet()) {
                    if (!wordString.equals(word_1)) {
                        toshowString += (calcShortestPath.calcShortestPath(nodes, word_1, wordString,shortestPath) + "\n");
                    }
                }
                inforarea.setText(toshowString);
            } else {
                inforarea.setText("No input word!");
            }

        }
        if (event.getSource() == offline) {
            System.out.println("offline");
            onlinetag = false;
            System.out.println(onlinetag);
        }
        if (event.getSource() == online) {
            System.out.println(online);
            onlinetag = true;
            System.out.println(onlinetag);
        }
        if (event.getSource() == randomwalk) {

            myThread.MyThread_set(imgaarea, pathString, inforarea, nodes);
            if (firstwalk)
                myThread.start();
            myThread.resume();
            firstwalk = false;
        }
        if (event.getSource() == stopwalk) {
            myThread.suspend();
            System.out.println("Wordgraph.Interface.handleButtonAction()");
        }
    }

//    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgaarea.setFitWidth(1000);
        imgaarea.setFitHeight(780);
    }
}









