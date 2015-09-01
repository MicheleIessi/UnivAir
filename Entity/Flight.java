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
public class Flight {
    /* costruttori */
    public Flight() {};
    public Flight(Route r, String id, Employer[] e, int s) {
        controlCrew(e);
        this.route = r;
        this.ID = id;
        this.crew = e;
        this.seats = s;
        System.out.println("Volo creato: ID: " + id + ", Tratta: " + r.getDeparture() + " - " + r.getDestination() + ", posti: " + s);
    }
    /* getter & setter */
    public Route getRoute()     { return this.route; }
    public String getID()       { return this.ID; }
    public Employer[] getCrew() { return this.crew; }
    public int getSeats()       { return this.seats; }
    public void setRoute(Route r)     { this.route = r; }
    public void setID(String id)      { this.ID = id; }
    public void setCrew(Employer[] e) { this.crew = e; }
    public void setSeats(int s)       { this.seats = s; }
    /* metodi di classe */
    public void setPilot(Employer e)   { this.crew[0] = e; }
    public void setCopilot(Employer e) { this.crew[1] = e; }
    public void setHost1(Employer e)   { this.crew[2] = e; }
    public void setHost2(Employer e)   { this.crew[3] = e; }
    public void setHost3(Employer e)   { this.crew[4] = e; }
    /* metodi di controllo */
    public static void controlCrew(Employer[] e) {
        if(!e[0].type.equals("Pilot")) throw new IllegalArgumentException("Errore, pilota mancante");
        if(!e[1].type.equals("Copilot")) throw new IllegalArgumentException("Errore, copilota mancante");
        if(!e[2].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 1 mancante");
        if(!e[3].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 2 mancante");
        if(!e[4].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 3 mancante");
    }
    /* attributi */
    private Route route;
    private String ID;
    private Employer[] crew;
    private int seats;
}