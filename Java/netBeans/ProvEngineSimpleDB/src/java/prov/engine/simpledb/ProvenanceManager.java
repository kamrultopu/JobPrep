/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.simpledb;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.dal.ProvenanceRecordHandler;
import prov.engine.crypto.CryptoUtil;
import prov.engine.crypto.HandleKey;
import prov.engine.dal.EpochHandler;
import prov.engine.dal.ProvenanceBlockHandler;
import prov.engine.dal.SystemProvHandler;
import prov.engine.model.Epoch;
import prov.engine.model.ProvenanceBlock;
import prov.engine.model.ProvenanceRecord;
import prov.engine.util.Config;
import prov.engine.util.Utility;

/**
 *
 * @author Shams
 */
public class ProvenanceManager {
    
    private SimpleDBHelper simpleDbHelper;
    private ProvenanceBlockHandler provenanceBlockHandler;
    public ProvenanceManager(SimpleDBHelper helper)
    {
        simpleDbHelper = helper;
        provenanceBlockHandler = new ProvenanceBlockHandler();
    }
    
    public String manageProvRecord(String userId, String objectId, HashMap<String, String> inputMap) throws Exception
    {
            ProvenanceRecord provRecord = genProvenanceRecord(userId, objectId, inputMap);
           // System.out.println("provRecord = " + provRecord.toString());
            ProvenanceRecordHandler provHandler = new ProvenanceRecordHandler();
            provHandler.insertProvRecord(genProvenanceRecord(userId, objectId, inputMap));
            
            String currentHCofBlock = provenanceBlockHandler.getCurrentHC(objectId);
            StringBuilder response= new StringBuilder();
            response.append(provRecord.getHash());
            response.append(currentHCofBlock);
            response.append("#");
            response.append(provRecord.getBlockId());
            return response.toString();
            
        
        
    }
    
    private ProvenanceRecord genProvenanceRecord(String userId, String objectId, HashMap<String, String> inputMap) throws Exception
    {
            
            ProvenanceRecord provRecord = new ProvenanceRecord();
            provRecord.setObjectId(objectId);
            String changeState = getChangeofState(objectId, inputMap);
            provRecord.setStateChange(changeState);
            provRecord.setStateChangeEnc(getEncryptedCH(changeState));
            provRecord.setCurrentStateHash(getCurrentStateHash(inputMap));
            provRecord.setUserId(userId);
            provRecord.setProvTime(Utility.DBDateFormat().format(new Date()));
            
            Long blockId = getProvBlockID(objectId, provRecord);
            if(!blockId.equals(Long.MIN_VALUE))
                provRecord.setBlockId(blockId);
            else
                throw new Exception("Block Generation Failed!!");
            return provRecord;
        
    }
    
    private Long getProvBlockID(String objectId, ProvenanceRecord provRecord) throws Exception
    {
        Long provBlockId = Long.MIN_VALUE;
        EpochHandler epochHandler = new EpochHandler();
        
        Epoch epoch = epochHandler.getLatestEpoch();
        //create new epoch and new provblock if new epoch is required according to current time
        if(epochHandler.isNewEpochRequired(epoch))
        {
            SystemProvHandler sp = new SystemProvHandler();
            if(!sp.epochHandled(Config.Instanc().AccumulatorType(), epoch.getEpochId())){
            //create system provenance for last epoch if already not handled
                Thread t = new Thread(new SystemProvManager(epoch));
                t.start();
            }
            epochHandler.createNewEpoch();
            provBlockId = provenanceBlockHandler.createNewProvBlock(objectId, provRecord.getHash());
        }
        else
        {
            provBlockId = provenanceBlockHandler.getLatestProvBlockIdofObject(objectId);
            if(provBlockId == Long.MIN_VALUE) //no block exist for the object
            {
                provBlockId = provenanceBlockHandler.createNewProvBlock(objectId, provRecord.getHash());
            }
        }
        return provBlockId;
    }
    public String getChangeofState(String objectId, HashMap<String, String> inputMap)
    {
        List<Attribute> oldValueList = simpleDbHelper.get(objectId);
        if(oldValueList == null || oldValueList.isEmpty())
            return "New Data";
        
        StringBuilder changeState = new StringBuilder();
        for(Attribute attr : oldValueList)
        {
            String newVal = inputMap.get(attr.getName());
            changeState.append(attr.getName());
            changeState.append(":");
            changeState.append(attr.getValue());
            changeState.append("->");
            changeState.append(newVal);
            changeState.append("#");
        }
        //System.out.println("changeState = " + changeState.toString());
        return changeState.toString();
    }
    private String getEncryptedCH(String changeState)
    {
        try {
            PublicKey pubKey = HandleKey.readPublicKeyFromFile(Config.Instanc().AuditorPubKey());
            return CryptoUtil.getLongCipherMessage(changeState, pubKey);
        } catch (IOException ex) {
            Logger.getLogger(ProvenanceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String getCurrentStateHash(HashMap<String, String> inputMap)
    {
        Set<String> keySet = inputMap.keySet();
        StringBuilder state = new StringBuilder();
        for(String key : keySet)
        {
            state.append(key);
            state.append(":");
            state.append(inputMap.get(key));
            state.append("#");
        }
        //System.out.println("state = " + state.toString());
        return CryptoUtil.getHash(state.toString(), Config.Instanc().HashAlgorithm());
    }
}

