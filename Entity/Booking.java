/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Michele
 */
public class Booking {
    
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
    public boolean getPriceRed() { return this.priceReduction; }
    public boolean getMealSupp() { return this.mealSupplement; }
    public boolean getPetSupp() { return this.petSupplement; }
    public boolean getLuggageSupp() { return this.luggageSupplement; }
    public boolean getMagazinesSupp() { return this.magazinesSupplement; }
    public double getDiscount() { return this.discount; }
    private void setPersona(Persona p) {
        this.per = p;
    }
    private void setFlight(Flight f) {
        this.fl = f;
    }
    private void setClss(String c) {
        if(controlClass(c)) {
            this.clss = c;
        }
        else throw new IllegalArgumentException("Classe non valida, deve essere 'first' o 'second'");
    }
    private void setPriceRed(boolean red) { 
        this.priceReduction = red;
    }
    private void setMealSupp(boolean meal) {
        this.mealSupplement = meal;
    }
    private void setPetSupp(boolean pet) {
        this.petSupplement = pet;
    }
    private void setLuggageSupp(boolean lugg) {
        this.luggageSupplement = lugg;
    }
    private void setMagazinesSupp(boolean mgzn) {
        this.magazinesSupplement = mgzn;
    }
    private void setDiscount(double newDis) {
        double oldPrc = this.price;
        double oldDis = this.discount;
        
        double newPrc = oldPrc*((100-newDis)/(100-oldDis));
        this.discount = newDis;
        this.price = (double) Math.round(newPrc * 100) / 100;   // arrotondamento alla seconda cifra decimale
    }
    /* metodi di classe */
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
}