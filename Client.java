package client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    private int token;
    LocalDateTime regTime;
    boolean active;




    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client(int token, LocalDateTime regTime) {
        this.token = token;
        this.regTime = regTime;
        this.active = false;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(regTime.plusMinutes(5));
    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 2135);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while((userInput = reader.readLine()) != null){
            writer.write(userInput + "\n");
            writer.flush();
        }
    }
}
