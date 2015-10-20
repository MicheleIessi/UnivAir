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

/**
 *
 * @author Michele
 */
public class FConnect {
    static Config con = new Config();
    static Connection db;

    public FConnect() throws SQLException {
        String url = "jdbc:postgresql://"+con.getHost()+":"+con.getPort()+"/"+con.getDB();
        this.db = DriverManager.getConnection(url, con.getDBUsername(), con.getDBPassword());
    }
    public static void query(String query) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
    
    public void store(String table, ArrayList values) throws SQLException {
        int j = values.size();
        String val = "";
        String item = "";
        for(int i = 0; i < j; i++) {
            if(values.get(i).toString().equals("DEFAULT")) {
                item = values.get(i).toString();
            }
            else {
                item = "\'" + values.get(i).toString() + "\'";
            }
            if(i < j-1) {
                item = item + ",";
            }
            val = val + item;
        }        
        String sql = "INSERT INTO " + table + " VALUES (" + val + ");";
        //System.out.println(sql);
        query(sql);
    }
    
    
    public boolean exists(String table, String key, String value) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + key + " = '" + value + "'");
        boolean result = false;
        if(rs.last()) {
            result = true;
        }
        return result;
    }
    
    public boolean existsMultipleKey(String table, String[] keys, String[] values) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "";
        Boolean result = false;
        if(keys.length == 2) {
            sql = "SELECT * FROM " + table + " WHERE " + keys[0] + " = '" + values[0] + "' AND " + keys[1] + " = '" + values[1] + "'";
            }
        else if (keys.length == 3) {
            sql = "SELECT * FROM " + table + " WHERE " + keys[0] + " = '" + values[0] + "' AND " + keys[1] + " = '" + values[1] + "' AND " + keys[2] + " = '" + values[2] + "'";
        }
        //System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.last()) {
            result = true;
        }
        return result;
    }
    
    public ResultSet load(String table, String condition) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM " + table + " WHERE " + condition;
//        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;        
    }
    
    public ResultSet search(String table, String[] keys, String[] values) throws SQLException {        
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String cond = "";
        int i = 0;
        int len = keys.length;
        for(String key : keys) {
            if(values[i] instanceof String) {
                cond = cond + key + " = '" + values[i] + "'";
            }
            if(i < len-1) {
                cond = cond + " AND ";
                i++;
            }
        }        
        String sql = "SELECT * FROM " + table + " WHERE " + cond;  
        //System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    
    public void delete(String table, String key, String value) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "DELETE FROM " + table + " WHERE " + key + " = " + value;
        stmt.executeUpdate(sql);
    }
}
