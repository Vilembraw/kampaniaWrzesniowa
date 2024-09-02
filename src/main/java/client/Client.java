package client;

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


}
