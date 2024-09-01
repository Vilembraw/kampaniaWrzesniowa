package chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();

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
