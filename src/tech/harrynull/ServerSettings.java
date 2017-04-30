package tech.harrynull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Null on 2017/4/30.
 */
public class ServerSettings {
    private Properties serverProp = new Properties();
    private static final String PropertiesPathname="settings.properties";
    public static final String Port = "port";
    private static final String PortDefault = "80";
    public static final String DocumentRoot = "document-root";
    private static final String DocumentRootDefault = "./htdocs";


    @FunctionalInterface
    private interface ApplyFunc {
        void apply(String key, String value);
    }

    private static void setMissingPropertiesToDefault(Properties prop){
        ApplyFunc setDefault=(key, val)->prop.setProperty(key,prop.getProperty(key,val));
        setDefault.apply(Port, PortDefault);
        setDefault.apply(DocumentRoot, DocumentRootDefault);
    }

    public ServerSettings(){
        try {
            serverProp.load(new FileInputStream(new File(PropertiesPathname)));
        } catch (IOException ignored) {}

        setMissingPropertiesToDefault(serverProp);
    }

    public String getProperty(String key){
        return serverProp.getProperty(key);
    }

    public void setProperty(String key, String value){
        serverProp.setProperty(key, value);
    }

    public void save(){
        try {
            serverProp.store(new FileOutputStream(new File(PropertiesPathname)),"Settings");
        } catch (IOException ignored) {}
    }
}
