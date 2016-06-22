/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.dal;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.model.ProvenanceRecord;
import prov.engine.util.Config;

/**
 *
 * @author Shams
 */
public class SystemProvHandler {

    private Connection con;
    private PreparedStatement insertSPBloom;

    public SystemProvHandler() {
        try {
            con = DBConnection.DB(Config.Instanc().DBHost(), Config.Instanc().DBPort(),
                    Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
            prepareStatements();
        } catch (Exception ex) {
            Logger.getLogger(ProvenanceRecordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareStatements() throws SQLException {
        String sql = "insert into bloom_sp(epoch_id, proof) "
                + "values (?, ?);";

        this.insertSPBloom = this.con.prepareStatement(sql);

    }

    public void insertBloomSP(String proof, long epochId) throws SQLException {
        this.insertSPBloom.setLong(1, epochId);//message_id bigint
        this.insertSPBloom.setString(2, proof);
        this.insertSPBloom.execute();
    }

    public void insertOnewWaySP(BigInteger proof, long epochId) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "insert into oneway_sp (epoch_id,proof) values"
                + " (" + epochId + ",'" + proof + "')";
        stmt.executeUpdate(sql);
    }

    public void updateIdentifyofBlock(String d, BigInteger identity) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "update prov_block set one_way_id = '" + identity + "' where block_hash='" + d + "';";
        stmt.executeUpdate(sql);
    }

    public boolean epochHandled(Config.EnumAccumulatorType acctype, long epochId) throws SQLException {
        String query = "";

        if (acctype == Config.EnumAccumulatorType.Bloom) {
            query = "select id from bloom_sp where epoch_id=" + epochId + ";";
        } else if (acctype == Config.EnumAccumulatorType.Oneway) {
            query = "select id from oneway_sp where epoch_id=" + epochId + ";";
        }
        Statement sql = con.createStatement();
        Long id = Long.MIN_VALUE;
        ResultSet rs = sql.executeQuery(query);
        while (rs.next()) 
        {
            id = rs.getLong("id");
        }
        if(id.equals(Long.MIN_VALUE))
            return false;
        else
            return true;
    }
}
