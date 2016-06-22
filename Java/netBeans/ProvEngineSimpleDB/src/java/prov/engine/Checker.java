/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.crypto.BloomFilter;
import prov.engine.crypto.RSAAccumulator;
import prov.engine.dal.EpochHandler;
import prov.engine.dal.ProvenanceBlockHandler;
import prov.engine.dal.SystemProvHandler;
import prov.engine.model.Epoch;
import prov.engine.simpledb.SystemProvManager;
import prov.engine.util.Config;
import prov.engine.util.Utility;

/**
 *
 * @author Shams
 */
public class Checker {

    private static String n_32 = "6354176164747384539";
    private static String pub_32 = "971837337";
    private static String n64 = "235829342603704757464741210318220146371";
    private static String pub64 = "9847370710097522469";
    private Connection db;
    public static RSAAccumulator rsaAccumulator = new RSAAccumulator(
            new BigInteger(n_32),
            new BigInteger(pub_32));

    public static void main(String args[]) {
        try {

            EpochHandler eh = new EpochHandler();
            Epoch epoch = eh.getLatestEpoch();
            Thread t = new Thread(new SystemProvManager(epoch));
            t.start();
    //SystemProvHandler sp = new SystemProvHandler();
            //  sp.insertBloomSP(bmfilter.getBitSetString(), epoch.getEpochId());
            //sp.insertOnewWaySP(rsaAccumulator.getAccumulator(), epoch.getEpochId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   

}
