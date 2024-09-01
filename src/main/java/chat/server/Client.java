package chat.server;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Server server;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public Client(Server server, Socket socket) throws IOException {
        this.server = server;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        try {
            String message;
            while((message= bufferedReader.readLine()) != null){
                server.broadcast(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
