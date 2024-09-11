package org.example.kolos2021canvas;

import javafx.scene.canvas.GraphicsContext;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Client implements Runnable {

    Server server;
    Socket socket;

    private GCanvas gCanvas;

    private double offsetX = 0;
    private double offsetY = 0;
    private static final double MOVE_AMOUNT = 10;


//    private BufferedWriter bufferedWriter;
    public String color = "#000000";

    public String getColor() {
        return color;
    }

    private BufferedReader bufferedReader;

    public static List<Odcinek> odcinekList = new ArrayList<>();

    public static List<Odcinek> getOdcinekList() {
        return odcinekList;
    }

    public Client(Server server, Socket socket, GCanvas gCanvas) throws IOException {
        this.socket = socket;
        this.server = server;
        this.gCanvas = gCanvas;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }

    public void parseMessage(String message){
        if(message.startsWith("#")){
            this.color = message;
            System.out.println(color);
        }

            //position
            System.out.println("dupa odcinek");
            String[] parts = message.split(" ",4);
            if(parts.length == 4){
                Double[] point1 = {Double.valueOf(parts[0]), Double.valueOf(parts[1])};
                Double[] point2 = {Double.valueOf(parts[2]), Double.valueOf(parts[3])};
                Odcinek odcinek = new Odcinek(point1[0],point1[1],point2[0],point2[1],this.color);
//                Odcinek odcinek = new Odcinek(1,5,5,5);
                gCanvas.draw(odcinek);
                odcinekList.add(odcinek);
            }
            else{
                //dupa
            }

    }

    @Override
    public void run() {
        try {
            String message;
            while((message = bufferedReader.readLine()) != null){
                parseMessage(message);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
        }

    }
}
