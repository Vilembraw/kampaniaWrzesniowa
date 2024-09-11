package org.example.server;

import org.example.server.Server;
import org.example.server.WordBag;

public class Main {
    public static void main(String[] args) {
        WordBag wordBag = new WordBag();
        wordBag.populate();
        Server server = new Server(2333, wordBag);
        server.start();
        server.startSending();
    }
}