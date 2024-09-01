package chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();
    private Map<String, Client> clientMap = new HashMap<>();

    public Server() throws IOException {
        serverSocket = new ServerSocket(2137);
    }


    public void listen() throws IOException {
        System.out.println("Server started");
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            Client client = new Client(this,clientSocket);
            new Thread(client).start();
            clients.add(client);
        }
//        System.out.println("Client disconnected");

    }


    public void clientLogged(Client loggedClient){
        clientMap.put(loggedClient.getLogin(),loggedClient);
        for(Client currentClient : clients) {
            if(currentClient != loggedClient) {
                currentClient.send(String.format("%s zalogował się", loggedClient.getLogin()));
            }
        }
    }


    public void clientLeft(Client leavingClient){
        clients.remove(leavingClient);
        try {
            clientMap.remove(leavingClient.getLogin());
        } catch (Exception e){}

        for(Client currentClient : clients) {
            currentClient.send(String.format("%s wylogował się", leavingClient.getLogin()));
        }

    }

//    public void serveClient() throws IOException {
//
//        Socket clientSocket = serverSocket.accept();
//        InputStream inputStream = clientSocket.getInputStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        OutputStream outputStream = clientSocket.getOutputStream();
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//
//        String message;
//        while((message= bufferedReader.readLine()) != null){
//            System.out.println("Received: " + message);
//            bufferedWriter.write("Echo: " + message + "\n");
//            bufferedWriter.flush();
//        }
//
//        clientSocket.close();
//    }

    public void broadcast(String message){
        for(Client client : clients){
            client.send(message);
        }
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }
}
