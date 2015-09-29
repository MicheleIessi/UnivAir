/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Michele
 */
public class FConnect {
    Config con = new Config();
    Connection db;

    public FConnect() throws SQLException {
        String url = "jdbc:postgresql://"+con.getHost()+":"+con.getPort()+"/"+con.getDB();
        this.db = DriverManager.getConnection(url, con.getDBUsername(), con.getDBPassword());
    }
    public void query(String query) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
    
    public void store(String table, ArrayList values) throws SQLException {
        int j = values.size();
        String val = "";
        for(int i = 0; i < j; i++) {   
            String item = "\'" + values.get(i).toString() + "\'";
            if(i < j-1) {
                item = item + ",";
            }
            val = val + item;
        }        
        String sql = "INSERT INTO " + table + " VALUES (" + val + ");";
        query(sql);
    }
    
    public void exists(String table, String key, String value) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + key + " = " + value);
        if(rs.last()) {
            throw new SQLException("Errore, una ennupla con lo stesso valore come chiave (" + value + ") è già presente nel DB.");
        }
    }
    
    public ResultSet load(String table, String condition) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String query = "SELECT * FROM " + table + " WHERE " + condition;
        ResultSet rs = stmt.executeQuery(query);
        return rs;        
    }
    
    private void resultSetToAssocArray(ResultSet rs) {
        
    }
    
    
}
