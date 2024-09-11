package org.example.kolos2021canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static org.example.kolos2021canvas.Client.getOdcinekList;


public class Server extends Application {
    private double offsetX = 0;
    private double offsetY = 0;
    private final double MOVE_AMOUNT = 10;

    private GCanvas gCanvas;
    ServerSocket serverSocket;
    Canvas canvas = new Canvas(800, 600);

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            BorderPane root = new BorderPane();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gCanvas = new GCanvas(gc);

            serverSocket = new ServerSocket(3222);
            gCanvas.drawBackground();

            root.setCenter(canvas);

            Scene scene = new Scene(root, 500, 500);


            scene.setOnKeyPressed(event -> {
                handleKeyPress(event);
                gCanvas.setOffsetX(-offsetX);
                gCanvas.setOffsetY(-offsetY);
                gCanvas.drawLines();
                stage.setTitle(String.format("X: %f \t Y: %f",offsetX,offsetY));
                    });

            stage.setScene(scene);
            stage.show();

            new Thread(this::listen).start();

    }catch (IOException e){
            System.out.println(e);
        }

    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                offsetY -= MOVE_AMOUNT;
                System.out.println(offsetY);
                break;
            case DOWN:
                offsetY += MOVE_AMOUNT;
                System.out.println(offsetY);
                break;
            case LEFT:
                offsetX -= MOVE_AMOUNT;
                System.out.println(offsetX);
                break;
            case RIGHT:
                offsetX += MOVE_AMOUNT;
                System.out.println(offsetX);
                break;
        }
    }




    public void listen() {
        try {
            System.out.println("Server started");
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                Client client = new Client(this,clientSocket,gCanvas);
                new Thread(client).start();
            }
//        System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
            e.printStackTrace();
        }
    }


}