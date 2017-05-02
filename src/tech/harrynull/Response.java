package tech.harrynull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP Response.
 */
public class Response {
    private byte[] body;
    private String statusCode;
    public Map<String, String> header = new HashMap<>();

    public Response(String body, String statusCode){
        this.body=body.getBytes();
        this.statusCode=statusCode;
    }
    public Response(byte[] body, String statusCode){
        this.body=body;
        this.statusCode=statusCode;
    }
    private static String getHttpTimeDate(){
        return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }
    public byte[] makeHeader(){
        StringBuilder headerStr= new StringBuilder();
        boolean contentTypeSet = false;
        for (Map.Entry<String, String> entry : header.entrySet()) {
            headerStr.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            if(entry.getKey().equals("Content-Type")) contentTypeSet=true;
        }
        if(!contentTypeSet){
            headerStr.append("Content-Type: text/html\n");
        }
        return ("HTTP/1.1 " + statusCode + "\n" +
                "Date: " + getHttpTimeDate() + "\n" +
                "Content-Encoding: UTF-8\n" +
                "Content-Length: " + body.length + "\n" +
                "Last-Modified: " + getHttpTimeDate() + "\n" +
                "Server: SimpleHttpServer/1.0\n" +
                "Accept-Ranges: bytes\n" +
                "Connection: close\n" + headerStr.toString() +
                "\n").getBytes();
    }
    public void writeToStream(DataOutputStream out) throws IOException {
        out.write(makeHeader());
        out.write(body);
    }
}
