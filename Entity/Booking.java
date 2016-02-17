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
import univair.Foundation.Utility;

/**
 * La classe Booking rappresenta una prenotazione.
 * @author Michele
 */
public class Booking implements DBInterface {
    /**
     * Crea una prenotazione con i parametri settati ai valori di default.
     */
    public Booking() {};
    /**
     * Crea una prenotazione con i parametri dati in ingresso. Il prezzo viene
     * calcolato in base alla distanza tra la città di partenza e di destinazione
     * del volo, e in base ai supplementi scelti. Inoltre viene calcolato uno
     * sconto, se presente.
     * @param p La persona che effettua la prenotazione
     * @param f Il volo per il quale si effettua la prenotazione
     * @param c La classe (first o second)
     * @param red Riduzione di prezzo in base all'età
     * @param meal Supplemento per pasto a bordo
     * @param pet Supplemento per trasporto animali
     * @param lug Supplemeneto per bagaglio addizionale
     * @param mgzn Supplemento per riviste particolari
     * @param dis Sconto applicato
     */
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
            double prc = computePrice(f,0);
            this.price = prc;  // prezzo già scontato
        }
    }
    /* getter & setter */
    /**
     * Ritorna la persona che effettua la prenotazione.
     * @return la persona che effettua la prenotazione
     */
    public Persona getPersona() {
        Persona p = new Persona();
        p = this.per;
        return p;
    }
    /**
     * Ritorna il volo per il quale si effettua la prenotazione.
     * @return il volo per il quale si effettua la prenotazione
     */
    public Flight getFlight() {
        Flight f = new Flight();
        f = this.fl;
        return f;
    }
    /**
     * Ritorna la classe scelta al momento della prenotazione.
     * @return la classe scelta al momento della prenotazione
     */
    public String getClss() { return this.clss; }
    /**
     * Ritorna il prezzo pagato per la prenotazione.
     * @return il prezzo pagato per la prenotazione
     */
    public double getPrice() { return this.price; }
    /**
     * Ritorna dettagli riguardo alla riduzione di prezzo legata all'età.
     * @return true se c'è una riduzione di prezzo legata all'età, false altrimenti
     */
    public boolean getPriceRed() { return this.priceReduction; }
    /**
     * Ritorna true se c'è un supplemento per pasto a bordo.
     * @return true se c'è supplemento per pasto a bordo, false altrimenti
     */
    public boolean getMealSupp() { return this.mealSupplement; }
    /**
     * Ritorna dettagli riguardo al supplemento per trasporto animale
     * @return true se c'è un supplemento per trasporto animale, false altrimenti
     */
    public boolean getPetSupp() { return this.petSupplement; }
    /**
     * Ritorna dettagli riguardo al supplemento per bagaglio addizionale.
     * @return true se c'è un supplemento per bagaglio addizionale, false altrimenti
     */
    public boolean getLuggageSupp() { return this.luggageSupplement; }
    /**
     * Ritorna dettagli riguardo al supplemento per riviste a scelta.
     * @return true se c'è un supplemento per riviste a scelta, false altrimenti
     */
    public boolean getMagazinesSupp() { return this.magazinesSupplement; }
    /**
     * Ritorna dettagli riguardo allo sconto applicato.
     * @return Lo sconto applicato in fase di prenotazione
     */
    public double getDiscount() { return this.discount; }
    /**
     * Imposta la persona
     * @param p Oggetto di tipo Persona
     */
    public void setPersona(Persona p) { this.per = p; }
    /**
     * Imposta il volo
     * @param f Oggetto di tipo Flight
     */
    public void setFlight(Flight f) { this.fl = f; }
    /**
     * Imposta la classe ("first" o "second")
     * @param c Stringa che rappresenta la classe (first o second)
     */
    public void setClss(String c) {
        if(controlClass(c)) { this.clss = c; }
        else throw new IllegalArgumentException("Classe non valida, deve essere 'first' o 'second'");
    }
    /**
     * Imposta il prezzo.
     * @param pr Double che rappresenta il prezzo
     */
    public void setPrice(double pr) {
        this.price = pr;
    }
    /**
     * Imposta una riduzione di prezzo
     * @param red Booleano che rappresenta una riduzione di prezzo legata all'età
     */
    public void setPriceRed(boolean red) { 
        this.priceReduction = red;
    }
    /**
     * Imposta un supplemento per pasto a bordo
     * @param meal Booleano che rappresenta il pasto a bordo
     */
    public void setMealSupp(boolean meal) {
        this.mealSupplement = meal;
    }
    /**
     * Imposta un supplemento per animale a bordo
     * @param pet Booleano che rappresenta un animale a bordo
     */
    public void setPetSupp(boolean pet) {
        this.petSupplement = pet;
    }
    /**
     * Imposta un supplemento per bagaglio addizionale.
     * @param lugg Booleano che rappresenta un bagaglio addizionale
     */
    public void setLuggageSupp(boolean lugg) {
        this.luggageSupplement = lugg;
    }
    /**
     * Imposta un supplemento per riviste a scelta
     * @param mgzn Booleano che rappresenta le riviste a scelta
     */
    public void setMagazinesSupp(boolean mgzn) {
        this.magazinesSupplement = mgzn;
    }
    /**
     * Imposta uno sconto arrotondato alla seconda cifra decimale
     * @param newDis Double che rappresenta lo sconto
     */
    public void setDiscount(double newDis) {
        this.discount = newDis;   // arrotondamento alla seconda cifra decimale
    }
    /* metodi di classe */
    /**
     * Il metodo distance calcola la distanza tra due coordinate rappresentate
     * da parametri di latitudine e longitudine per calcolare un costo approssimato
     * del volo.
     * @param lat1 La latitudine della prima città
     * @param lon1 La longitudine della prima città
     * @param lat2 La latitudine della seconda città
     * @param lon2 La longitudine della seconda città
     * @return Double che rappresenta la distanza tra le due città
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist;      
    }
    /**
     * Il metodo deg2rad converte un angolo da gradi in radianti.
     * @param deg L'angolo in gradi
     * @return Double che rappresenta l'angolo in radianti
     */
    private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
    }
    /**
     * Il metodo rad2deg converte un angolo da radianti in gradi.
     * @param rad L'angolo in radianti
     * @return Double che rappresenta l'angolo in gradi
     */
    private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
    }
    /**
     * Il metodo arrotonda arrotonda un valore numerico di tipo Double alla sua
     * seconda cifra decimale
     * @param x Il Double da arrotondare
     * @return Il Double arrotondato alla seconda cifra decimale
     */
    private static double arrotonda(double x) {
        DecimalFormat df = new DecimalFormat("#####.00");
        df.setRoundingMode(RoundingMode.CEILING);    
        double newValue = Double.parseDouble(df.format(x).replace(',','.'));
        return newValue;
    }  
    /* metodi di supporto */
    /**
     * Il metodo controlClass controlla se la stringa in input è "fist" o "second"
     * @param c La stringa da controllare
     * @return true se la stringa è "first" o "second", false altrimenti
     */
    private boolean controlClass(String c) {
        return (c.toLowerCase().equals("first") || c.toLowerCase().equals("second"));
    }   
    /**
     * Il metodo computePrice calcola un prezzo approssimato per il volo considerando
     * la distanza (calcolata con il metodo privato distance) e i vari supplementi/sconti
     * richiesti
     * @param f Il volo per il quale calcolare la distanza
     * @param d Lo sconto richiesto
     * @return Double che rappresenta il prezzo del volo (arrotondato alla seconda
     * cifra decimale)
     */
    public double computePrice(Flight f, double d) {
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
        prc = prc*(1-(d/100));
        prc = arrotonda(prc);
        return prc;
    }
    /* metodi per il db */

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
        this.setPrice(pr);
    }
    @Override
    public void store() throws SQLException {
        GregorianCalendar nd = this.fl.getDate();
        nd.add(1, 1900);
        this.fl.setDate(nd);
        FConnect con = new FConnect();
        String persID = Integer.toString(this.per.getIDFromDB());
        String[] keys = {"idpersona","idvolo","datavolo"};
        String[] vals = {persID,this.fl.getID(),this.fl.getDateString()};
        if(!con.existsMultipleKey(table, keys, vals)) {        
            //aggiungo la prenotazione
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
            //decremento di 1 il numero di posti disponibili per il volo
            con.decrement(this.fl.getID());
        }
        else {
            throw new IllegalArgumentException("Attenzione, prenotazione già presente nel database");
        }
        con.close();
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
            GregorianCalendar gc = Utility.getDateFromString(rs.getString(4));
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
        con.close();
        return map;
    }
    public static void delete(int id) throws SQLException {
        FConnect con = new FConnect();
        con.delete(table, key, Integer.toString(id));
        con.close();
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