package tech.harrynull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Null on 2017-04-22.
 */
public class HttpServer {

    private ServerSocket serverSocket;
    private RequestHandler handler;

    public HttpServer(int port, RequestHandler handler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.handler=handler;
    }

    public void start(){
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new Connection(socket, handler)).start();
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
