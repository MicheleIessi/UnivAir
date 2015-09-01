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
        for(Cities c : Cities.values()) {
            if (c.toString().equals(city))
                return true;
        }
        return false;
    }
    /* attributi */
    private String departure;
    private String destination;
}