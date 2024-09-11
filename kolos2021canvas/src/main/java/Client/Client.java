package Client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    public String color;
//    private BufferedReader bufferedReader;


    public Client() throws IOException {
        this.socket = new Socket("localhost",3222);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String message){
        try {
            this.bufferedWriter.write(message + "\n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void run() {
        String message;
        while(true){
//                server.broadcast(message);
//                System.out.println(message);
        }
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(client).start();
        while(true){
            client.send(reader.readLine());
        }

    }
}