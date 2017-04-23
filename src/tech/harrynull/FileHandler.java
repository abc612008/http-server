package tech.harrynull;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Null on 2017/4/23.
 */
public class FileHandler implements RequestHandler {
    private String httpDocsRoot, serverAbsRoot;
    public FileHandler(String root, String serverAbsRoot){
        this.httpDocsRoot=root;
        this.serverAbsRoot=serverAbsRoot;
    }
    public Response handle(Map<String,String> request){
        String path=request.get("Url");
        File file=new File(httpDocsRoot, path);
        try {
            if(!file.getCanonicalPath().startsWith(new File(httpDocsRoot).getCanonicalPath())){
                return new Response(ERROR_400, ERROR_400);
            }
        } catch (IOException e) {
            return new Response(ERROR_400, ERROR_400);
        }
        if(file.isDirectory()){ // is a directory
            if(!path.endsWith("/")){ // the url of a directory should end with /
                Response response=new Response(STATUS_301, STATUS_301);
                response.header.put("Location", serverAbsRoot+path+"/");
                return response;
            }

            String[] indexList={"index.html","index.htm"};
            for (String indexName : indexList) {
                file=new File(httpDocsRoot,path+indexName);
                if(file.exists()) break;
            }
            if(!file.exists()){ // List files
                StringBuilder result= new StringBuilder(String.format("<!DOCTYPE HTML><html><body><h1>Index of %s</h1><ul>", path));
                File[] files = new File(httpDocsRoot, path).listFiles();
                assert files != null;
                for (File f : files) {
                    if(f.isDirectory())
                        result.append(String.format("<li>(Directory) <a href='%s/'>%s</a></li>", f.getName(), f.getName()));
                    else
                        result.append(String.format("<li><a href='%s'>%s</a></li>", f.getName(), f.getName()));
                }
                result.append("</ul></body></html>");
                return new Response(result.toString(), STATUS_200);
            }
        }
        try {
            return new Response(new Scanner(file).useDelimiter("\\Z").next(), STATUS_200);
        } catch (FileNotFoundException e) {
            return new Response(ERROR_404, ERROR_404);
        }
    }
}
