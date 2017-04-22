package tech.harrynull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Null on 2017-04-22.
 */
public class HttpServer {

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start(){
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new Connection(socket)).start();
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
