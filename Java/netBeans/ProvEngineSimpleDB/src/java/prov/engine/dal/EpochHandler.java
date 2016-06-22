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
import java.text.ParseException;
import java.util.Date;
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
public class EpochHandler {

    private Connection con;
    private PreparedStatement insertEpochStmt;

    public EpochHandler() {
        try {
            con = DBConnection.DB(Config.Instanc().DBHost(), Config.Instanc().DBPort(),
                    Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
            prepareStatements();
        } catch (SQLException ex) {
            Logger.getLogger(EpochHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareStatements() throws SQLException {
        String sql;

        sql = "insert into epoch(start_time, end_time) "
                + "values (cast(? as timestamp without time zone),cast(? as timestamp without time zone));";

        this.insertEpochStmt = this.con.prepareStatement(sql);

    }

    public void insertEpoch(Epoch epoch) throws SQLException {
        this.insertEpochStmt.setString(1, epoch.getStartTimeStr());
        this.insertEpochStmt.setString(2, epoch.getEndTimeStr());
        this.insertEpochStmt.execute();
    }

    public Epoch getLatestEpoch() {
        try {
            Statement sql = con.createStatement();
            String query = "select * from epoch where epoch_id = (select max(epoch_id) from epoch);";
            ResultSet rs = sql.executeQuery(query);
            Epoch epoch = new Epoch();
            DateFormat formatter = Utility.DBDateFormat();
            while (rs.next()) {
                epoch.setEpochId(rs.getLong("epoch_id"));
                epoch.setStartTimeStr(rs.getString("start_time"));
                epoch.setEndTimeStr(rs.getString("end_time"));
                epoch.setEndTime(formatter.parse(rs.getString("end_time")));
                epoch.setStartTime(formatter.parse(rs.getString("start_time")));

            }
            return epoch;
        } catch (SQLException ex) {
            Logger.getLogger(EpochHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EpochHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isNewEpochRequired(Epoch epoch) {
        Date now = new Date();
        return now.after(epoch.getEndTime());
    }

    public void createNewEpoch() throws SQLException {
        Date startTime = new Date();
        Config config = Config.Instanc();
        Date endTime = Utility.addTime(startTime, config.EpochDuration(), config.EpochUnit());
        Epoch epoch = new Epoch();
        epoch.setStartTimeStr(Utility.DBDateFormat().format(startTime));
        epoch.setEndTimeStr(Utility.DBDateFormat().format(endTime));
        insertEpoch(epoch);

    }

}
