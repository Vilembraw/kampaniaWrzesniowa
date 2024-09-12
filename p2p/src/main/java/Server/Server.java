package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    ServerSocket serverSocket;
    public static List<ClientHandler> clients = new ArrayList<>();


    public static List<ClientHandler> getClients() {
        return clients;
    }

    public Server(){
        try {
            serverSocket = new ServerSocket(2137);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void listen(){
        try {
            System.out.println("Started Server");

            while(true){
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(this,clientSocket);
            new Thread(clientHandler).start();
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void clientLogged(ClientHandler loggedClient){
        System.out.println(loggedClient.getLogin() + " joined");
        clients.add(loggedClient);

    }


    public void clientLeft(ClientHandler leavingClient){
        System.out.println(leavingClient.getLogin() + " left");
        clients.remove(leavingClient);
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
