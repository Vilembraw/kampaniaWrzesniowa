package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp extends Application {
    ServerSocket serverSocket;


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 500);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            serverSocket = new ServerSocket(2137);
            new Thread(this::listen).start();
        } catch (IOException e) {
//            e.printStackTrace();
        }

    }


    public void listen() {
        try {
            System.out.println("server started");
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted");
                    ClientHandler clientHandler = new ClientHandler(this, clientSocket);
//                    clientHandler.handleClient();
                    clientHandler.receiveFile();
//                    clientSocket.close();
                    System.out.println("Client closed");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
