package tech.harrynull;

import java.io.*;

public class Main {

    public static void main(String [] args) {
        ServerSettings settings=new ServerSettings();
        settings.save();

        int port=Integer.parseInt(settings.getProperty(ServerSettings.Port));
        try {
            System.out.println("Waiting for client on port " + port + "...");
            HttpServer server = new HttpServer(port, new FileHandler(settings.getProperty(ServerSettings.DocumentRoot)));
            server.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}