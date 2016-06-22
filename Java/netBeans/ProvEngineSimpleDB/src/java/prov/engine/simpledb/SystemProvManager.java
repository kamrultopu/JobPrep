/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.simpledb;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.crypto.BloomFilter;
import prov.engine.crypto.RSAAccumulator;
import prov.engine.dal.ProvenanceBlockHandler;
import prov.engine.dal.SystemProvHandler;
import prov.engine.model.Epoch;
import prov.engine.util.Config;

/**
 *
 * @author Shams
 */
public class SystemProvManager implements Runnable {

    private Epoch epoch;
    private Config config = Config.Instanc();
    private String n_32 = "6354176164747384539";
    private String pub_32 = "971837337";
    private String n64 = "235829342603704757464741210318220146371";
    private String pub64 = "9847370710097522469";
    public SystemProvManager(Epoch e) {
        this.epoch = e;
    }

    @Override
    public void run() {
        try {
            ProvenanceBlockHandler bh = new ProvenanceBlockHandler();
            List<String> hashList = bh.getHashofBlocksinEpoch(epoch);
            
            Config.EnumAccumulatorType accType = config.AccumulatorType();
            switch(accType){
                    case Bloom:
                        genBloomSP(hashList, epoch.getEpochId());
                        break;
                    case Oneway:
                        genOneWaySP(hashList, epoch.getEpochId());
                        break;
                    default:
                        genBloomSP(hashList, epoch.getEpochId());
                        break;
            }
            
           
        } catch (SQLException ex) {
            Logger.getLogger(SystemProvManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void genBloomSP(List<String> data, long epochId) throws SQLException
    {
        BloomFilter<String> bmfilter = new BloomFilter<String>(config.BloomFP(), config.BloomNumberofElement());
        for (String d : data) {
            bmfilter.add(d);
        }
        SystemProvHandler sp = new SystemProvHandler();
        sp.insertBloomSP(bmfilter.getBitSetString(), epochId);
    }
    
     private  void genOneWaySP(List<String> data, long epochId) throws SQLException {
        
        RSAAccumulator rsaAccumulator = new RSAAccumulator(
            new BigInteger(n_32),
            new BigInteger(pub_32)); 
        SystemProvHandler sp = new SystemProvHandler();
        for (String d : data) {
            rsaAccumulator.insertElement(d);
        }
        sp.insertOnewWaySP(rsaAccumulator.getAccumulator(), epochId);
        
        rsaAccumulator = new RSAAccumulator(
            new BigInteger(n_32),
            new BigInteger(pub_32));
        rsaAccumulator.sethashSet(data);
        for (String d : data) {
            BigInteger identity = rsaAccumulator.getIdentity(d);
            sp.updateIdentifyofBlock(d,identity);
        }
    }
}
