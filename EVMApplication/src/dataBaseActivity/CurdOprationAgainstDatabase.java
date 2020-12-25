/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBaseActivity;

import evmapplication.MySQL_Coonection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mdsad
 */
public class CurdOprationAgainstDatabase {

    public static void insertDataToDatabase(String insertQuery) throws SQLException {
        String flagActionType = "insert";
        CurdOprationAgainstDatabase.dbService(insertQuery, flagActionType);
    }

    public static void updateDataToDatabase(String updateQuery) throws SQLException {
        String flagActionType = "update";
        CurdOprationAgainstDatabase.dbService(updateQuery, flagActionType);

    }

    public static int selectDataFromDatabase(String selectQuery) throws SQLException {
        System.err.println("query"+selectQuery);
        int currentCount = 0;
        Connection connect = MySQL_Coonection.ConnectDb();
        PreparedStatement ps = connect.prepareStatement(selectQuery);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            currentCount = rs.getInt("vote_count");
        }
        //
        return currentCount;
    }

    public static void dbService(String query, String actionType) throws SQLException {
        Connection connect = MySQL_Coonection.ConnectDb();
        PreparedStatement ps = connect.prepareStatement(query);
        ResultSet rs = null;
        if (actionType.equals("insert")) {
            ps.executeUpdate();
        } else {
             ps.executeUpdate();
        }
       
    }
}
