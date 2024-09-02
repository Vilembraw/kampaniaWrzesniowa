package client;

import java.time.LocalDateTime;

public class Client {
    private int token;
    LocalDateTime regTime;


    public Client(int token, LocalDateTime regTime) {
        this.token = token;
        this.regTime = regTime;
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
}
