/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evmapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author mdsad
 */
public class MySQL_Coonection {

    Connection conn = null;
    static ObservableList<condidate> list = FXCollections.observableArrayList();

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/stp", "root", "456951357");
            //JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public static ObservableList<condidate> getDatacondidate() throws SQLException {
        Connection conn = ConnectDb();

        PreparedStatement ps = conn.prepareStatement("select *from condidate_info");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = Integer.parseInt(rs.getString("candidate_id"));
            String name = rs.getString("candidate_name");
            String party = rs.getString("candidate_party");
            String rating = rs.getString("candidate_rating");
           // System.err.println("id: " + id + " name: " + name + " party: " + party + " rating: " + rating);
            condidate k = new condidate(id, name, party, rating);
            list.add(k);

        }
        return list;
    }
}
