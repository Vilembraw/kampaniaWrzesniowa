package chat.server;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Server server;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String login;
    private Socket socket;


    public Client(Server server, Socket socket) throws IOException {
        this.socket = socket;
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

    private void authenticate() throws IOException {
        login = bufferedReader.readLine();
        server.clientLogged(this);
    }

    public String getLogin() {
        return login;
    }

    private void leave() throws IOException {
        socket.close();
        server.clientLeft(this);
    }

    @Override
    public void run() {
        try {
            this.authenticate();
            String message;
            while((message= bufferedReader.readLine()) != null){
                String temp = this.getLogin() + ":  " + message;

                server.broadcast(temp);
            }
            this.leave();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
