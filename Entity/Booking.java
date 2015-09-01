/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

/**
 *
 * @author Michele
 */
public class Booking {
    
    public Booking() {};
    public Booking(Persona p, Flight f, String c, 
                   double prc, boolean red, boolean meal, 
                   boolean pet, boolean lug, boolean mgzn, double dis) {
        
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
    private static boolean controlClass(String c) {
        return (c.toLowerCase().equals("first") || c.toLowerCase().equals("second"));
    }
    
    
    private static double computePrice(Flight f) {
        
        
        
        
        
        
        
        
        
        return 0;
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
