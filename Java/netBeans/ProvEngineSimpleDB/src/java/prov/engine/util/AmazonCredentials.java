package prov.engine.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;

public class AmazonCredentials implements AWSCredentials{

	Properties prop;
        String AccessKeyID;
	public AmazonCredentials(String accessKey)
	{
                AccessKeyID = accessKey;
		prop = new Properties();

		try {
			File userHome = new File(System.getProperty("user.home"));
			File propertiesFile = new File(userHome, "AwsCredentialsVolt.properties");
		
			prop.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String getAWSAccessKeyId() {
		// TODO Auto-generated method stub
		return AccessKeyID;
	}

	@Override
	public String getAWSSecretKey() {
		// TODO Auto-generated method stub
		return prop.getProperty(AccessKeyID);
	}

}
