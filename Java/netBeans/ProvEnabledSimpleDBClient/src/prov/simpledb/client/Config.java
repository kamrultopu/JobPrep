package prov.simpledb.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {

    private List<String> notAnalyzer;
    private static Config configInstance;
    private Properties systemProperties;

    
    public static enum EnumEpochUnit {
	Second,
        Minute,
        Hour,
        Day
}
    private Config() {
        // TODO Auto-generated method stub
        systemProperties = new Properties();
        notAnalyzer = new ArrayList<String>();
        try {
            File userHome = new File(System.getProperty("user.home"));
            //System.out.println(userHome);
            File propertiesFile = new File(userHome, "ProvClient.properties");
            //System.out.println(propertiesFile);
            systemProperties.load(new FileInputStream(propertiesFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void forceToReloadPropery() {
        configInstance = null;
    }
    public static Config Instanc()
    {
        if (configInstance == null) {
            configInstance = new Config();
        }
        return configInstance;
    }

   

    public String LogLevel() {
        
        return systemProperties.getProperty("loglevel");
    }
    public String AuditorPubKey() {
        
        return systemProperties.getProperty("auditor.pubkey");
    }
    public String HashAlgorithm()
    {
        return systemProperties.getProperty("hash.algorithm");
    }
    public String ApiHost()
    {
        return systemProperties.getProperty("api.host");
    }
    public String ApiPort()
    {
        return systemProperties.getProperty("api.port");
    }
    
    public String PutUrl()
     {
            return "http://"+ApiHost()+":"+ApiPort()+"/PutAttr";
     }
    public String AggregateUrl()
     {
            return "http://"+ApiHost()+":"+ApiPort()+"/Aggregate";
     }
    
    public String BASPrpertyFile()
    {
        return systemProperties.getProperty("bas.property");
    }
    public String KeyFolder()
    {
        return systemProperties.getProperty("key.folder");
    }
}