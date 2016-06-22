/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.simpledb.client;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shams
 * reference https://aws.amazon.com/articles/Amazon-SimpleDB/8829919029640036
 * http://s3.amazonaws.com/awsVideos/AmazonSimpleDBIntro/AmazonSimpleDBIntro.html
 * https://aws.amazon.com/articles/Amazon-SimpleDB/1231
 */
public class SimpleDBHelper {

    private AmazonSimpleDBClient sdbClient;
    private String domainName;
    /**
     * @param args the command line arguments
     */
    public SimpleDBHelper(AmazonCredentials credentials, String domain) {
        // TODO code application logic here
        sdbClient = new AmazonSimpleDBClient(credentials);
        domainName = domain;
        //store(sdbClient,"2");
        //get(sdbClient, "2");
       
    }

    public void put(String item, List attrs) {
        PutAttributesRequest par = new PutAttributesRequest(domainName, item, attrs);
        sdbClient.putAttributes(par);
    }

    public List<Attribute>  get(String item)
    {
        GetAttributesRequest gar = new GetAttributesRequest( domainName, item);
        GetAttributesResult response = sdbClient.getAttributes(gar);
        List<Attribute> attrs = response.getAttributes();
        return attrs;
    }
    
    public void delete(String item)
    {
        DeleteAttributesRequest dar = new DeleteAttributesRequest( domainName, item );
        sdbClient.deleteAttributes( dar );
    }
}
