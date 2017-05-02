package tech.harrynull;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Null on 2017-04-22.
 */
public class Connection implements Runnable {
    private Socket socket;
    private RequestHandler handler;
    Connection(Socket socket, RequestHandler handler){
        this.socket=socket;
        this.handler=handler;
    }

    class BadRequestHeader extends Exception{
    }

    private Map<String, String> getRequest(InputStream inputStream) throws BadRequestHeader {
        Map<String, String> requestValues = new HashMap<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while((line=in.readLine())!=null) {
                if(line.isEmpty()) break;

                if(line.startsWith("GET")||line.startsWith("POST")){ // GET /path HTTP/1.1
                    requestValues.put("Url",line.split(" ")[1]);
                }else { // XXX: XXX
                    int position = line.indexOf(":");
                    if (position == -1) throw new BadRequestHeader();
                    String name=line.substring(0, position);
                    String value=line.substring(position + 1);
                    if(value.charAt(0)==' ')value=value.substring(1);
                    requestValues.put(name, value);
                }
            }
        } catch (IOException e) {
            throw new BadRequestHeader();
        }
        if(!requestValues.containsKey("Url")) throw new BadRequestHeader();
        return requestValues;
    }

    public void run() {
        try {
            Map<String, String> request;

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            try {
                request = getRequest(socket.getInputStream());
            } catch (BadRequestHeader badRequestHeader) {
                new Response(RequestHandler.ERROR_400, RequestHandler.ERROR_400).writeToStream(out);
                return; // Return 400 if fail to read the request.
            }

            Response response=handler.handle(request);
            response.writeToStream(out);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
