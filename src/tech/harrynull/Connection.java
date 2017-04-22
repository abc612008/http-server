package tech.harrynull;

import java.io.*;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Null on 2017-04-22.
 */
public class Connection implements Runnable {
    private Socket socket;

    Connection(Socket socket){
        this.socket=socket;
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

                if(line.startsWith("GET")){ // GET /path HTTP/1.1
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

        return requestValues;
    }

    private static String getHttpTimeDate(){
        return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }

    private String makeResponse(String body, String statusCode){
        return "HTTP/1.1 " + statusCode + "\n" +
                "Date: " + getHttpTimeDate() + "\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "Content-Encoding: UTF-8\n" +
                "Content-Length: " + body.length() + "\n" +
                "Last-Modified: " + getHttpTimeDate() + "\n" +
                "Server: SimpleHttpServer/1.0\n" +
                "Accept-Ranges: bytes\n" +
                "Connection: close\n" +
                "\n" + body;
    }

    public void run() {
        try {
            Map<String, String> request;
            try {
                request = getRequest(socket.getInputStream());

            } catch (BadRequestHeader badRequestHeader) {
                badRequestHeader.printStackTrace();
                return; // Ignore the request if fail to read the request.
            }

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(makeResponse(request.get("Url"),"200 OK"));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
