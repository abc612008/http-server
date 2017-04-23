package tech.harrynull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Null on 2017/4/23.
 */
public class Response {
    public String body;
    public String statusCode;
    public Map<String, String> header = new HashMap<>();
    public Response(String body, String statusCode){
        this.body=body;
        this.statusCode=statusCode;
    }
}
