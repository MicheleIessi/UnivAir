/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;

/**
 *
 * @author Michele
 */
public class Flight {
    /* costruttori */
    public Flight() {};
    /**
     * Il costruttore Flight() costruisce un nuovo oggetto Flight prendendo come
     * input una Route(tratta), un ID, un array di Employer, un numero di posti
     * e una data di partenza
     * @param r La tratta alla quale appartiene il volo
     * @param id L'ID del volo appena creato
     * @param s Il numero di posti disponibili
     * @param d La data di partenza.
     */
    public Flight(Route r, String id, int s, GregorianCalendar d) {
        this.route = r;
        this.ID = id;
        this.seats = s;
        this.date = d;
        System.out.println("Volo creato: ID: " + id + ", Tratta: " + r.getDeparture() + " - " + r.getDestination() + ", posti: " + s + ", data: " + this.getDateString());
    }
    /* getter & setter */
    public Route getRoute()     { return this.route; }
    public String getID()       { return this.ID; }
    public int getSeats()       { return this.seats; }
    public GregorianCalendar getDate()       { return this.date; }
    public void setRoute(Route r)     { this.route = r; }
    public void setID(String id)      { this.ID = id; }
    public void setSeats(int s)       { this.seats = s; }
    public void setDate(GregorianCalendar d)       { this.date = d; }
    /**
     * Il metodo getDateString() converte la data di partenza del volo in una 
     * stringa formattata secondo il pattern yyyy-mm-dd.
     * @return una stringa "traduzione" della data di partenza del volo.
     */
    public String getDateString() {
        return (this.date.get(1)+1900) + "-" + (this.date.get(2)+1) + "-" + this.date.get(5);    }
    /* metodi per il DB */
    /**
     * Il metodo getDateFromString crea un oggetto GregorianCalendar a partire
     * da una stringa formattata secondo il pattern yyyy-mm-dd.
     * @param date La stringa che si vuole convertire
     * @return Un GregorianCalendar equivalente alla stringa convertita
     */
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, date.length()));
        return new GregorianCalendar(year, month, day);
    }
    /**
     * Il metodo getStringFromDate è il duale di getDateFromString e converte un
     * GregorianCalendar dato come input in una stringa formattata secondo il 
     * pattern yyyy-mm-dd.
     * @param gc Il GregorianCalendar che si vuole convertire
     * @return Una stringa formattata secondo il pattern yyyy-mm-dd equivalente 
     * al GregorianCalendar in inputs
     */
    private String getStringFromDate(GregorianCalendar gc) {
        String dat = (gc.get(1)+1900) + "-" + (gc.get(2)+1) + "-" + gc.get(5);
        return dat;
    }
    /**
     * Il metodo createFromDB crea un oggetto dal Database prendendo come input
     * un intero che rappresenta l'ID. L'ID è la chiave primaria delle tabelle 
     * corrispondenti alle classi che implementano questa interfaccia. Usa il 
     * metodo retrieve() per recuperare la Map dalla cui poi si crea l'oggetto.
     * @param id L'ID del volo da cercare
     * @param gc La data di decollo del volo
     * @throws SQLException 
     */
    public void createFromDB(int id, GregorianCalendar gc) throws SQLException {
        Map<String,Object> map = retrieve(id, gc);
        Route r = (Route) map.get("route");
        String nid = (String) map.get("ID");
        GregorianCalendar dat = (GregorianCalendar) map.get("date");
        int s = (int) map.get("seats");
        this.setRoute(r);
        this.setID(nid);
        this.setDate(dat);
        this.setSeats(s);
        
    }
    public static int getRouteID(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load("volo", "id = " + id);
        //System.out.println(rs.getRow());
        int rt = 0;
        while(rs.next())
             rt = rs.getInt(3);
        con.close();
        return rt;
    }
    public static ArrayList getFlightsFromRouteID(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, "idtratta = " + id);
        ArrayList list = new ArrayList();
        HashMap<String,Object> map;
        while(rs.next()) {
            map = new HashMap<>();
            map.put("ID", Integer.toString(rs.getInt(1)));
            map.put("date", rs.getString(2));
            map.put("seats", rs.getInt(4));
            list.add(map);
        }
        //System.out.println(list.size());
        if(list.isEmpty()) {
            con.close();
            throw new SQLException("Nessun risultato trovato per la tratta richiesta");
        }
        con.close();
        return list;
    }
    public Map retrieve(int id, GregorianCalendar gc) throws SQLException {
        FConnect con = new FConnect();
        String[] cond = {Integer.toString(id),getStringFromDate(gc)};
        ResultSet rs = con.search(table, key, cond);
        Map<String,Object> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            Route r = new Route();
            r.createFromDB(getRouteID(id));
            map.put("route", r);
            map.put("ID", Integer.toString(rs.getInt(1)));
            map.put("date", getDateFromString(rs.getString(2)));
            map.put("seats", rs.getInt(4));
        }
        con.close();
        return map;
    }
    public void store() throws SQLException {
        FConnect con = new FConnect();
        String[] val = {this.ID,this.getDateString()};
        if(!con.existsMultipleKey(table, key, val)) {        
            ArrayList<String> values = new ArrayList<>();
            values.add(this.ID);
            values.add(this.getDateString());
            values.add(Integer.toString(this.route.getIdFromDB()));
            values.add(Integer.toString(seats));
            con.store(table, values);    
        }
        else {
            con.close();
            System.out.println("volo con stesse keys già presente nel db GESTIRE ERROR FRAME Flight.store()");
        }
        con.close();
    }
    /* attributi */
    private Route route;
    private String ID;
    private int seats;
    private GregorianCalendar date;
    /* attributi di classe (per il db) */
    private static final String table = "volo";
    private static final String[] key = {"id","decollo"};
}