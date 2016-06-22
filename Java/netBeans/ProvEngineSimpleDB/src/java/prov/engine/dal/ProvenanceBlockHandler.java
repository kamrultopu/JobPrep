/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.model.Epoch;
import prov.engine.model.ProvenanceBlock;
import prov.engine.model.ProvenanceRecord;
import prov.engine.util.Config;
import prov.engine.util.Utility;

/**
 *
 * @author Shams
 */
public class ProvenanceBlockHandler {

    private Connection con;
    private PreparedStatement insertProvBlockStmt;

    public ProvenanceBlockHandler() {
        try {
            con = DBConnection.DB(Config.Instanc().DBHost(), Config.Instanc().DBPort(),
                    Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
            prepareStatements();
        } catch (SQLException ex) {
            Logger.getLogger(ProvenanceBlockHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareStatements() throws SQLException {
        String sql;

        sql = "insert into prov_block(object_id, current_hc,block_chain, prov_chain_sigma, "
                + "block_hash, one_way_id, gen_time) "
                + "values (?, ?, ?, ?, ?, ?, cast(? as timestamp without time zone));";

        this.insertProvBlockStmt = this.con.prepareStatement(sql);

    }

    public void insertProvBlock(ProvenanceBlock provBlock) throws SQLException {
        this.insertProvBlockStmt.setString(1, provBlock.getObjectId());
        this.insertProvBlockStmt.setString(2, provBlock.getCurrentHC());
        this.insertProvBlockStmt.setString(3, provBlock.getBlockChain());
        this.insertProvBlockStmt.setString(4, provBlock.getProvChainSigma());
        this.insertProvBlockStmt.setString(5, provBlock.getBlockHash());
        this.insertProvBlockStmt.setLong(6, provBlock.getOnewayAccId());//message_id bigint
        this.insertProvBlockStmt.setString(7, provBlock.getBlockGenTime());

        this.insertProvBlockStmt.execute();
    }

    public ProvenanceBlock getLatestProvBlockOfObject(String objectId) {
        try {
            Statement sql = con.createStatement();
            String query = "select * from prov_block where block_id = (select max(block_id) from prov_block where object_id='" + objectId + "');";
            ResultSet rs = sql.executeQuery(query);
            ProvenanceBlock provBlock = new ProvenanceBlock();
            DateFormat formatter = Utility.DBDateFormat();
            while (rs.next()) {
                provBlock.setBlockId(rs.getLong("block_id"));
                provBlock.setBlockChain(rs.getString("block_chain"));
                provBlock.setBlockGenTime(rs.getString("gen_time"));
                provBlock.setBlockHash(rs.getString("block_hash"));
                provBlock.setCurrentHC(rs.getString("current_hc"));
                provBlock.setObjectId(rs.getString("object_id"));
                provBlock.setProvChainSigma(rs.getString("object_id"));
                provBlock.setOnewayAccId(rs.getLong("prov_chain_sigma"));
            }
            return provBlock;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Long getLatestProvBlockIdofObject(String objectId) {
        Long value = Long.MIN_VALUE;

        try {
            Statement sql = con.createStatement();
            String query = "select block_id from prov_block where block_id = (select max(block_id) from prov_block where object_id='" + objectId + "');";
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                value = rs.getLong("block_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    public String getLatesProvBlockHash(String objectId) {
        return getValueofLatestProvBlock(objectId, "block_hash");
    }

    public String getCurrentHC(String objectId) {
        return getValueofLatestProvBlock(objectId, "current_hc");
    }

    public String getProvChainSigmaofBlock(Long blockId) {
        String value = "";

        try {
            Statement sql = con.createStatement();
            String query = "select prov_chain_sigma from prov_block where block_id = " + blockId + ";";
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                value = rs.getString("prov_chain_sigma");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;

    }

    private String getValueofLatestProvBlock(String objectId, String attribute) {
        String value = "";

        try {
            Statement sql = con.createStatement();
            String query = "select " + attribute + " from prov_block where block_id = (select max(block_id) from prov_block where object_id='" + objectId + "');";
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                value = rs.getString(attribute);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    public void updateAggregateSignatur(String signature, Long blockId) throws SQLException {
        Statement sql = con.createStatement();
        String query = "update prov_block set prov_chain_sigma = '" + signature + "' where block_id = " + blockId + ";";
        sql.executeUpdate(query);
        sql.close();
    }

    public List<String> getHashofBlocksinEpoch(Epoch epoch)
    {
        List<String> blockHashList = new ArrayList<String>();
        try {
            Statement sql = con.createStatement();
            String query = "select block_hash from prov_block where gen_time between '"+epoch.getStartTimeStr()+"' and '"+epoch.getEndTimeStr()+"';";
            ResultSet rs = sql.executeQuery(query);
            while (rs.next()) {
                blockHashList.add(rs.getString("block_hash"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return blockHashList;
    }
    public Long createNewProvBlock(String objectId, String provRecordHash) {
        try {
            ProvenanceBlock provBlockNew = new ProvenanceBlock();
            String lastBlockHash = getLatesProvBlockHash(objectId);
            // if no previous block hash, i.e. first provenance block for the document BC0 = hash(PR0)
            //else BCi = hash (PBi-1)
            if (lastBlockHash.isEmpty()) {
                provBlockNew.setBlockChain(provRecordHash);
            } else {
                provBlockNew.setBlockChain(lastBlockHash);
            }
            provBlockNew.setObjectId(objectId);
            provBlockNew.setCurrentHC(provBlockNew.getBlockChain()); //HC0= BC
            provBlockNew.setBlockGenTime(Utility.DBDateFormat().format(new Date()));
            provBlockNew.setProvChainSigma("");
            provBlockNew.setOnewayAccId(new Long("0"));
            provBlockNew.setBlockHash(provBlockNew.getHash());

            insertProvBlock(provBlockNew);
            return getLatestProvBlockIdofObject(objectId);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
