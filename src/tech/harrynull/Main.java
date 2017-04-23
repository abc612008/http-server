package tech.harrynull;

import java.io.*;

public class Main {
    public static void main(String [] args) {
        int port=Integer.parseInt(args[0]);
        try {
            System.out.println("Waiting for client on port " + port + "...");
            HttpServer server = new HttpServer(port, new FileHandler(args[1]));
            server.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}