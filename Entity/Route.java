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
import java.util.List;
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
    /* metodi di controllo */
    public static boolean controlCity(String city) {
        for(City c : City.values()) {
            if (c.toString().equals(city))
                return true;
        }
        return false;
    }
    /* metodi per il DB */
    public void store(int id) throws SQLException{
        ArrayList<String> values = new ArrayList<>();
        FConnect con = new FConnect();
        con.exists(this.table, this.key, Integer.toString(id));
        values.add(Integer.toString(id));
        values.add(this.departure);
        values.add(this.destination);
        con.store(this.table, values);  
    }
    public List retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(this.table,condition + Integer.toString(id));
        int columns = rs.getMetaData().getColumnCount();
        Map<String,String> map;
        List<Map<String, String>> data = new ArrayList<>();
        int i = 0;
        while(rs.next()) {
            map = new HashMap<>();
            map.put("id", Integer.toString(rs.getInt(1)));
            map.put("departure", rs.getString(2));
            map.put("destination", rs.getString(3));
            data.add(i, map);
            i++;
        }
        return data;
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
    private final String table = "tratta";
    private final String key = "id";
    private final String condition = "id = ";
}