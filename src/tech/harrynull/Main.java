package tech.harrynull;

import java.io.*;

public class Main {
    public static void main(String [] args) {
        int port=8080;
        try {
            System.out.println("Waiting for client on port " + port + "...");
            HttpServer server = new HttpServer(port);
            server.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}