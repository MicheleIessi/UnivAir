/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;

/**
 *
 * @author Michele
 */
public class Employer extends Persona implements DBInterface {
    /* costruttori */
    public Employer() {};
    public Employer(String id, String ty) {
        this.ID = id;
        this.type = ty;
    }/* per autenticazione */
    public Employer(Persona p, String id, String s, Date f) {
        this.nome = p.nome;
        this.cognome = p.cognome;
        this.ddn = p.ddn;
        this.sex = p.sex;
        this.CF = p.CF;
        this.email = p.email;
        this.ldn = p.ldn;
        this.ldr = p.ldr;
        this.ID = id;
        this.salary = s;
        this.from = f;
    }
    /* getter & setter */
    public String getID()     { return this.ID; }
    public String getSalary() { return this.salary; }
    public Date getFrom()     { return this.from; }
    public Date getTo()       { return this.to; }
    public String getType()   { return this.type; }
    public void setID(String id)    { this.ID = id; }
    public void setSalary(String s) { this.salary = s; }
    public void setFrom(Date f)     { this.from = f; }
    public void setTo(Date t)       { this.to = t; }
    public void setType(String ty)  { this.type = ty; }
    /* metodi di controllo */
    public static void controlType() {
        
        
        
        
        
        
    }
    /* metodi per il DB */
    @Override
    public void createFromDB(int id) throws SQLException {
        
    }
    @Override
    public Map retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        Map<String,Object> map = new HashMap<>();
    }
    /* attributi */
    private String ID;
    private String salary;
    private Date from;
    private Date to;
    protected String type;
}
