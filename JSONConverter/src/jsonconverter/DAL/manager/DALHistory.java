/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Mape
 */
public class DALHistory {

    /* OBJECT POOL */
    // Create the ConnectionPool:
    JDBCConnectionPool pool = new JDBCConnectionPool(
            "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
            "CS2017B_27_java", "javajava");

    /* GET ALL HISTORY */
    public List<History> getAllHistory() {
        List<History> history = new ArrayList();

        try (Connection con = pool.checkOut()) {

            PreparedStatement pstmt = con.prepareCall("SELECT * FROM History");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                History h = new History(rs.getDate("history_date_time"),
                        rs.getInt("history_id"),
                        rs.getString("local_username"),
                        rs.getString("action_message"),
                        rs.getBoolean("has_error"),
                        rs.getString("error_message"));

                history.add(h);
                System.out.println(history);
            }

            pool.checkIn(con);
        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return history;
    }

    public void saveConfigToDatabase(Config config) {
        
        try (Connection con = pool.checkOut()) {
            String sql ="INSERT INTO Config "
                    + "(siteName, assetSerialNumber, type, externalWorkOrderId, systemStatus, "
                    + "userStatus, createdOn, createdBy, name, priority, status, latestFinishDate, earliestStartDate, "
                    + "latestStartDate, estimatedTime, config_name, privacy, creator_name) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, config.getSiteName());
            pstmt.setString(2, config.getAssetSerialNumber());
            pstmt.setString(3, config.getType());
            pstmt.setString(4, config.getExternalWorkOrderId());
            pstmt.setString(5, config.getSystemStatus());
            pstmt.setString(6, config.getUserStatus());
            pstmt.setString(7, config.getCreatedOn());
            pstmt.setString(8, config.getCreatedBy());
            pstmt.setString(9, config.getName());
            pstmt.setString(10, config.getPriority());
            pstmt.setString(11, config.getStatus());
            pstmt.setString(12, config.getLatestFinishDate());
            pstmt.setString(13, config.getEarliestStartDate());
            pstmt.setString(14, config.getLatestStartDate());
            pstmt.setString(15, config.getEstimatedTime());
            pstmt.setString(16, config.getConfigName());
            pstmt.setBoolean(17, config.isPrivacy());
            pstmt.setString(18, config.getCreatorName());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Config could not be added");
            }
            pool.checkIn(con);
        } catch (SQLException ex) {
            Logger.getLogger(DALHistory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}