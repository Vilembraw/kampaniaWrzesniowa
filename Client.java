package org.example.kolos2022intef;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private HelloController controller;

    private LinkedHashMap sortedWords  = new LinkedHashMap<>();


    private HashMap<String, String> words = new HashMap<>();

    public Client() throws IOException {
        this.socket = new Socket("localhost",2333);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public HashMap<String, String> getWords() {
        return words;
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }


    public LinkedHashMap getSortedWords() {
        return sortedWords;
    }

    public  LinkedHashMap<String,String> sortHashMap(){
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : words.entrySet()) {
            list.add(entry.getValue());
        }



//        Z KOMPARATOREM
        Collections.sort(list, new Comparator<String>() {
            public int compare(String str, String str1) {
                return (str).compareTo(str1);
            }
        });
        for (String str : list) {
            for (Map.Entry<String, String> entry : words.entrySet()) {
                if (entry.getValue().equals(str)) {
                    sortedMap.put(entry.getKey(), str);
                }
            }
        }
        return sortedMap;
    }



    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            String message;
            while((message = bufferedReader.readLine()) != null){
                System.out.println(message);
                String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                words.put(currentTime,message);
                sortedWords = sortHashMap();
//
                Platform.runLater(() -> {
                        controller.updateWordCounter();
                    try {
                        controller.filterChar();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    try {
//
//                        controller.emptyFilterList(sortedWords);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                });
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
