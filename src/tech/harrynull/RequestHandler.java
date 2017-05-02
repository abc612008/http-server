package tech.harrynull;

import javafx.util.Pair;

import java.util.Map;

/**
 * Request Handler Interface
 */
public interface RequestHandler {
    String STATUS_200="200 OK";
    String STATUS_301="301 Moved Permanently";
    String ERROR_400="400 Bad Request";
    String ERROR_404="404 Not Found";
    String ERROR_500="500 Internal Server Error";
    Response handle(Map<String,String> request);
}
