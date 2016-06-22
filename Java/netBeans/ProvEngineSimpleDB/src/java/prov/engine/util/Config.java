package prov.engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public static enum EnumAccumulatorType {
	Bloom,
        Oneway
}
    private Config() {
        // TODO Auto-generated method stub
         systemProperties = new Properties();
        notAnalyzer = new ArrayList<String>();
        try {
            File userHome = new File(System.getProperty("user.home"));
            File propertiesFile = new File(userHome, "ProvEngine.properties");
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

    public String DBName() {
        return systemProperties.getProperty("dbname");
    }

    public  String DBHost() {
        return systemProperties.getProperty("dbhost","");
    }

    public String DBUserName() {
        return systemProperties.getProperty("dbuser","");
    }

    public String DBPassword() {
        return systemProperties.getProperty("dbpassword","");
    }

    public  String DBPort() {
        return  systemProperties.getProperty("dbport","");
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
    
    public int EpochDuration()
    {
        return Integer.parseInt(systemProperties.getProperty("epoch.duration"));
    }
    
    public EnumEpochUnit EpochUnit()
    {
        return EnumEpochUnit.valueOf(systemProperties.getProperty("epoch.unit"));
    }
    public double BloomFP()
    {
        return Double.parseDouble(systemProperties.getProperty("bloom.fp"));
    }
    public int BloomNumberofElement()
    {
        return Integer.parseInt(systemProperties.getProperty("bloom.elementno"));
    }
    public String BASPrpertyFile()
    {
        return systemProperties.getProperty("bas.property");
    }
    public EnumAccumulatorType AccumulatorType()
    {
        return EnumAccumulatorType.valueOf(systemProperties.getProperty("accumulator.type"));
    }
}