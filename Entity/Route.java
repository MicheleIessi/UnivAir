/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;


/**
 *
 * @author Michele
 */
public class Route {
    /* costruttori */
    public Route() {};
    public Route(String dep, String des) {
        boolean controlCity = (controlCity(des) && controlCity(dep));
        if(controlCity) {
            this.departure = dep;
            this.destination = des;
        }
        else throw new IllegalArgumentException("Luogo di partenza o destinazione non riconosciuto");
    }
    /* getter & setter */
    public String getDeparture()   { return this.departure; }
    public String getDestination() { return this.destination; }
    public void setDeparture(String dep) { 
        if(!controlCity(dep)) {
            throw new IllegalArgumentException("Città di partenza non valida");
        }
        this.departure = dep; 
    }
    public void setDestination(String des) { 
        if(!controlCity(des)) {
            throw new IllegalArgumentException("Città di destinazione non valida");
        }
        this.destination = des; 
    }
    /* metodi di classe */
    public static boolean controlCity(String city) {
        for(City c : City.values()) {
            if (c.toString().equals(city))
                return true;
        }
        return false;
    }
    /* metodi per il DB */
    public void createFromDB(int id) throws SQLException {
        Map<String,String> map = retrieve(id);
        String dep = map.get("departure").trim();
        String des = map.get("destination").trim();
        this.setDeparture(dep);
        this.setDestination(des);
    }
    public void store(int id) throws SQLException{
        ArrayList<String> values = new ArrayList<>();
        FConnect con = new FConnect();
        if(!con.exists(table, key, Integer.toString(id))) {
            values.add(Integer.toString(id));
            values.add(this.departure);
            values.add(this.destination);
            con.store(table, values);
        }
        else {
            System.out.println("route già presente nel db con lo stesso id gestire error frame Route.store()");
        }
    }
    public Map retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table,condition + Integer.toString(id));
        Map<String,String> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            map.put("id", Integer.toString(rs.getInt(1)));
            map.put("departure", rs.getString(2));
            map.put("destination", rs.getString(3));
        }
        return map;
    }
    public int getIdFromDB() throws SQLException {
        FConnect con = new FConnect();
        String dep = this.departure;
        String des = this.destination;
        String[] keys = {"departure","destination"};
        String[] vals = {dep,des};
        ResultSet rs = null;
        int id = 0;
        try {
            rs = con.search(table, keys, vals);
        } catch (SQLException e) {
            System.out.println("La tratta " + dep + "-" + des + " non è presente nel DB, è necessario aggiungerla");
        }
        if(rs.last()) {
            id = rs.getInt(1);
        }
        return id;
    }
    public static void delete(int id) throws SQLException {
        FConnect con = new FConnect();
        con.delete(table, key, Integer.toString(id));
    }
    /* metodi di debug */
    @Override
    public String toString() {
        return this.departure + "-" + this.destination;
    }
    /* attributi */
    private String departure;
    private String destination;
    /* attributi per il db */
    private static final String table = "tratta";
    private static final String key = "id";
    private static final String condition = "id = ";
}