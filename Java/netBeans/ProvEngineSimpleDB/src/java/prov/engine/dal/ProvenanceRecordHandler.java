/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import prov.engine.model.ProvenanceRecord;
import prov.engine.util.Config;

/**
 *
 * @author Shams
 */
public class ProvenanceRecordHandler {

    private Connection con;
    private PreparedStatement insertProvRecord;

    public ProvenanceRecordHandler() {
        try {
            con = DBConnection.DB(Config.Instanc().DBHost(), Config.Instanc().DBPort(),
                    Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
            prepareStatements();
        } catch (SQLException ex) {
            Logger.getLogger(ProvenanceRecordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareStatements() throws SQLException {
        String sql;

        sql = "insert into prov_record(object_id, user_id,current_state_hash, state_change_enc, "
                + "state_chanee, block_id,prov_time) "
                + "values (?, ?, ?, ?, ?, ?, cast(? as timestamp without time zone));";

        this.insertProvRecord = this.con.prepareStatement(sql);

    }
    
    public void insertProvRecord(ProvenanceRecord provRecord) throws SQLException
    {
        this.insertProvRecord.setString(1, provRecord.getObjectId());
        this.insertProvRecord.setString(2, provRecord.getUserId());
        this.insertProvRecord.setString(3, provRecord.getCurrentStateHash());
        this.insertProvRecord.setString(4, provRecord.getStateChangeEnc());
        this.insertProvRecord.setString(5, provRecord.getStateChange());
        this.insertProvRecord.setLong(6, provRecord.getBlockId());//message_id bigint
        this.insertProvRecord.setString(7, provRecord.getProvTime());
        this.insertProvRecord.execute();
    }
    
}
