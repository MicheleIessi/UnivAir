/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.util.Date;
/**
 *
 * @author Michele
 */
public class Persona {
    /* costruttori */
    public Persona() {};
    public Persona(String n, String c, Date d, String s, String cf, String e, String ln, Address lr) {
        if(controlField(n, "alpha") && controlField(c, "alpha") && controlField(s, "sex") && cf.length()==16 && controlField(ln, "alpha")) {
            this.nome = n;
            this.cognome = c;
            this.ddn = d;
            this.sex = s;
            this.CF = cf;
            this.email = e;
            this.ldn = ln;
            this.ldr = lr;
        }
        else throw new IllegalArgumentException("Uno o più valori in input sono errati");
    }
    /* getter & setter */
    public String getNome()    { return this.nome; }
    public String getCognome() { return this.cognome; }
    public Date getDDN()       { return this.ddn; }
    public String getSex()     { return this.sex; }
    public String getCF()      { return this.CF; }
    public String getEmail()   { return this.email; }
    public String getLDN()     { return this.ldn; }
    public Address getLDR()    { return this.ldr; }
    public void setNome(String n)    { this.nome = n; }
    public void setCognome(String c) { this.cognome = c; }
    public void setDDN(Date d)       { this.ddn = d; }
    public void setSec(String s)     { this.sex = s; }
    public void setCF(String cf)     { this.CF = cf; }
    public void SetEmail(String e)   { this.email = e; }
    public void setLDN(String ln)    { this.ldn = ln; }
    public void setLDR(Address lr)   { this.ldr = lr; }
    /* metodi di controllo */
    public static boolean controlField(String s, String tipo) {
        char[] c = s.toCharArray();
        boolean esito = true;
        if(tipo.equals("alpha")) {
            for(int i = 0; i < c.length; i++) {
                if(Character.isDigit(c[i])) {
                    throw new IllegalArgumentException("Nome, cognome o luogo di nascita errati.");
                }
                 
            }
        }
        if(tipo.equals("digit")) {
            for(int i = 0; i < c.length; i++) {
                if(!Character.isDigit(c[i])) {
                    throw new IllegalArgumentException("Valori numerici errati");
                }
            }
        }
        if(tipo.equals("sex")) {
            if(c.length != 1 || (c[0] != 'm' && c[0] != 'f' && c[0] != 'M' && c[0] != 'F')) {
                throw new IllegalArgumentException("Sesso non valido");
            } 
        }
        return esito;
    }
    /* attributi */
    protected String nome;
    protected String cognome;
    protected Date ddn;
    protected String sex;
    protected String CF;
    protected String email;
    protected String ldn; //luogo di nascita
    protected Address ldr;//luogo di residenza
}