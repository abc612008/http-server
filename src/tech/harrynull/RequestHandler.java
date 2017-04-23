package tech.harrynull;

import javafx.util.Pair;

import java.util.Map;

/**
 * Created by Null on 2017/4/23.
 */
public interface RequestHandler {
    String STATUS_200="200 OK";
    String STATUS_301="301 Moved Permanently";
    String ERROR_400="400 Bad Request";
    String ERROR_404="404 Not Found";
    Response handle(Map<String,String> request);
}
