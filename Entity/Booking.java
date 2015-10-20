/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;

/**
 *
 * @author Michele
 */
public class Booking implements DBInterface {
    
    public Booking() {};
    public Booking(Persona p, Flight f, String c, 
                   boolean red, boolean meal, boolean pet, 
                   boolean lug, boolean mgzn, double dis) {
        
        if(!controlClass(c)) { throw new IllegalArgumentException("Classe non valida, deve essere 'first' o 'second'"); }
        else if((dis < 0 && dis > 99)) { throw new IllegalArgumentException("Valore di sconto non valido, deve essere compreso tra 0 e 99"); }
        else {
            this.per = p;
            this.fl = f;
            this.clss = c;
            this.priceReduction = red;
            this.mealSupplement = meal;
            this.petSupplement = pet;
            this.luggageSupplement = lug;
            this.magazinesSupplement = mgzn;
            this.discount = dis; // percentuale
            double prc = computePrice(f);
            this.price = prc*(1-(dis/100));  // prezzo già scontato
        }
    }
    /* getter & setter */
    public Persona getPersona() { return this.per; }
    public Flight getFlight() { return this.fl; }
    public String getClss() { return this.clss; }
    public double getPrice() { return this.price; }
    public boolean getPriceRed() { return this.priceReduction; }
    public boolean getMealSupp() { return this.mealSupplement; }
    public boolean getPetSupp() { return this.petSupplement; }
    public boolean getLuggageSupp() { return this.luggageSupplement; }
    public boolean getMagazinesSupp() { return this.magazinesSupplement; }
    public double getDiscount() { return this.discount; }
    public void setPersona(Persona p) {
        this.per = p;
    }
    public void setFlight(Flight f) {
        this.fl = f;
    }
    public void setClss(String c) {
        if(controlClass(c)) {
            this.clss = c;
        }
        else throw new IllegalArgumentException("Classe non valida, deve essere 'first' o 'second'");
    }
    public void setPrice(double pr) {
        this.price = pr;
    }
    public void setPriceRed(boolean red) { 
        this.priceReduction = red;
    }
    public void setMealSupp(boolean meal) {
        this.mealSupplement = meal;
    }
    public void setPetSupp(boolean pet) {
        this.petSupplement = pet;
    }
    public void setLuggageSupp(boolean lugg) {
        this.luggageSupplement = lugg;
    }
    public void setMagazinesSupp(boolean mgzn) {
        this.magazinesSupplement = mgzn;
    }
    public void setDiscount(double newDis) {
        double oldPrc = this.price;
        double oldDis = this.discount;
        
        double newPrc = oldPrc*((100-newDis)/(100-oldDis));
        this.discount = newDis;
        this.price = (double) Math.round(newPrc * 100) / 100;   // arrotondamento alla seconda cifra decimale
    }
    /* metodi di classe */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist;      
    }
    private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
    }
    private static double arrotonda(double x) {
        DecimalFormat df = new DecimalFormat("#####.00");
        df.setRoundingMode(RoundingMode.CEILING);    
        double newValue = Double.parseDouble(df.format(x).replace(',','.'));
        return newValue;
    }  
    /* metodi di supporto */
    private boolean controlClass(String c) {
        return (c.toLowerCase().equals("first") || c.toLowerCase().equals("second"));
    }   
    public double computePrice(Flight f) {
        String dep = f.getRoute().getDeparture();
        String des = f.getRoute().getDestination();
        double lat1 = City.valueOf(dep).lat();
        double lon1 = City.valueOf(dep).lon();
        double lat2 = City.valueOf(des).lat();
        double lon2 = City.valueOf(des).lon();        
        double dis = distance(lat1, lon1, lat2, lon2);
        double prc = dis/12.5;
        if(this.clss.equals("first")) prc +=50;
        if(this.luggageSupplement) prc += 20;
        if(this.mealSupplement) prc += 15;
        if(this.petSupplement) prc += 20;
        if(this.magazinesSupplement) prc += 10;
        if(this.priceReduction) prc *= 0.5;
        prc = arrotonda(prc);
        return prc;
    }
    /* metodi per il db */
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }

    @Override
    public void createFromDB(int id) throws SQLException {
        Map<String,Object> map = retrieve(id);
        Persona p = (Persona) map.get("persona");
        Flight f = (Flight) map.get("flight");
        String c = (String) map.get("clss");
        boolean pred = (boolean) map.get("priceReduction");
        boolean s1 = (boolean) map.get("mealSupplement");
        boolean s2 = (boolean) map.get("petSupplement");
        boolean s3 = (boolean) map.get("luggageSupplement");
        boolean s4 = (boolean) map.get("magazinesSupplement");
        double dis = (Double) map.get("discount");
        double pr = (Double) map.get("price");
        this.setPersona(p);
        this.setFlight(f);
        this.setClss(c.trim());
        this.setPriceRed(pred);
        this.setMealSupp(s1);
        this.setPetSupp(s2);
        this.setLuggageSupp(s3);
        this.setMagazinesSupp(s4);
        this.setDiscount(dis);
        System.out.println(pr);
        this.setPrice(pr);
    }
    @Override
    public void store() throws SQLException {
        FConnect con = new FConnect();
        String persID = Integer.toString(this.per.getIDFromDB());
        String[] keys = {"idpersona","idvolo","datavolo"};
        String[] vals = {persID,this.fl.getID(),this.fl.getDateString()};
        if(!con.existsMultipleKey(table, keys, vals)) {         
            ArrayList<String> values = new ArrayList<>();
            values.add("DEFAULT");
            values.add(persID);
            values.add(this.fl.getID());
            values.add(this.fl.getDateString());
            values.add(this.clss);
            values.add(Double.toString(this.price));
            values.add(Boolean.toString(this.priceReduction));
            values.add(Boolean.toString(this.mealSupplement));
            values.add(Boolean.toString(this.petSupplement));
            values.add(Boolean.toString(this.luggageSupplement));
            values.add(Boolean.toString(this.magazinesSupplement));
            String a = String.valueOf(this.discount);
            int ind = a.lastIndexOf(".");
            a = a.substring(0, ind);
            values.add(a);
            con.store(table, values);
        }
        else {
            System.out.println("BOOKING GIà PRESENTE NEL DB GESTIRE ERROR FRAME");
        }
    }
    @Override
    public Map retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, condition + Integer.toString(id));
        Map<String,Object> map = new HashMap<>();
        while(rs.next()) {
            Persona p = new Persona();
            p.createFromDB(rs.getInt(2));
            Flight f = new Flight();
            GregorianCalendar gc = getDateFromString(rs.getString(4));
            f.createFromDB(rs.getInt(3), gc);
            map.put("persona", p);
            map.put("flight", f);
            map.put("clss", rs.getString(5));
            map.put("price", rs.getDouble(6));
            map.put("priceReduction", rs.getBoolean(7));
            map.put("mealSupplement", rs.getBoolean(8));
            map.put("petSupplement", rs.getBoolean(9));
            map.put("luggageSupplement", rs.getBoolean(10));
            map.put("magazinesSupplement", rs.getBoolean(11));
            map.put("discount", Double.parseDouble(String.valueOf(rs.getInt(12))));
        }
        System.out.println("Prenotazione trovata e caricata dal db");
        return map;
    }
    public static void delete(int id) throws SQLException {
        FConnect con = new FConnect();
        con.delete(table, key, Integer.toString(id));
    }
    /* attributi */
    private Persona per;
    private Flight fl;
    private String clss;
    private double price;
    private boolean priceReduction;
    private boolean mealSupplement;
    private boolean petSupplement;
    private boolean luggageSupplement;
    private boolean magazinesSupplement;
    private double discount;
    /* attributi di classe (per il db) */
    private static final String table = "prenotazione";
    private static final String key = "id";
    private static final String condition = "id = ";
}