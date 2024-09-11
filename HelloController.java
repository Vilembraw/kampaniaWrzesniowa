package org.example.kolos2022intef;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloController {
    private Client client;

    @FXML
    Label wordCountLabel;

    @FXML
    private TextField filterField;

    @FXML
    private ListView<String> wordList;


    public void bindWithClient(Client client) {
        this.client = client;
        client.setController(this);
    }

    @FXML
    public void updateWordCounter(){
        int wordCount = client.getWords().size();
        wordCountLabel.setText(String.valueOf(wordCount));
    }



    @FXML
    public void filterChar() throws InterruptedException {
        String filter = filterField.getText();
        LinkedHashMap<String,String> words = client.getSortedWords();
        if(!filter.isEmpty()){

            LinkedHashMap<String, String> filteredMap = words.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().startsWith(filter))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));

            wordList.getItems().clear();
            for (String i : filteredMap.keySet()) {
                String word = i+" "+filteredMap.get(i);
                wordList.getItems().add(word);
            }
        }
        else{
            emptyFilterList(words);
        }
    }


    public void emptyFilterList(LinkedHashMap<String,String> list) throws InterruptedException {
        wordList.getItems().clear();
            for (String i : list.keySet()) {
                String word = i+" "+list.get(i);
                wordList.getItems().add(word);
            }
    }

}