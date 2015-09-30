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
import java.util.List;
import java.util.Map;
import univair.Foundation.FConnect;
/**
 *
 * @author Michele
 */
public class Persona implements DBInterface {
    /* costruttori */
    public Persona() {};
    public Persona(String n, String c, GregorianCalendar d, String s, String cf, String e, String ln, Address lr) {
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
    public GregorianCalendar getDDN()       { return this.ddn; }
    public String getSex()     { return this.sex; }
    public String getCF()      { return this.CF; }
    public String getEmail()   { return this.email; }
    public String getLDN()     { return this.ldn; }
    public Address getLDR()    { return this.ldr; }
    public void setNome(String n)           { this.nome = n; }
    public void setCognome(String c)        { this.cognome = c; }
    public void setDDN(GregorianCalendar d) { this.ddn = d; }
    public void setSec(String s)            { this.sex = s; }
    public void setCF(String cf)            { this.CF = cf; }
    public void SetEmail(String e)          { this.email = e; }
    public void setLDN(String ln)           { this.ldn = ln; }
    public void setLDR(Address lr)          { this.ldr = lr; }
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
    /* metodi per il DB */
    public String getDateString() {
        return (this.ddn.get(1)+1900) + "-" + (this.ddn.get(2)+1) + "-" + this.ddn.get(5);    }
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }
    @Override
    public void createFromDB(int id) throws SQLException {
        Map<String,Object> map = retrieve(id);
        String nom = (String) map.get("nome");
        String cog = (String) map.get("cognome");
        GregorianCalendar nas = (GregorianCalendar) map.get("ddn");
        String ses = (String) map.get("sex");
        String cod = (String) map.get("CF");
        String eml = (String) map.get("email");
        String nsc = (String) map.get("ldn");
        Address rsd = (Address) map.get("ldr");
        this.setNome(nom.trim());
        this.setCognome(cog.trim());
        this.setDDN(nas);
        this.setSec(ses.trim());
        this.setCF(cod.trim());
        this.SetEmail(eml.trim());
        this.setLDN(nsc.trim());
        this.setLDR(rsd);      
    }
    /**
     * Il metodo retrieve restituisce una map contenente i dati caricati dal
     * database. Viene usata una map poiché offre un'associazione chiave-valore.
     * Il fatto che la ricerca è fatta su una PK garantisce sempre 0/1 riscontri.
     * @param id - l'id (univoco) da cercare nel database
     * @return - map, la mappa contenente i dati
     * @throws SQLException -
     */
    @Override
    public Map retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, condition + Integer.toString(id));
        Map<String,Object> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            map.put("id", Integer.toString(rs.getInt(1)));
            map.put("nome", rs.getString(2));
            map.put("cognome", rs.getString(3));
            map.put("ddn", getDateFromString(rs.getString(4)));
            map.put("sex", rs.getString(5));
            map.put("CF", rs.getString(6));
            map.put("email", rs.getString(7));
            map.put("ldn", rs.getString(8));
            Address temp = new Address(rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
            map.put("ldr", temp);            
        }
        return map;
    }
    @Override
    public void store(int id) throws SQLException {
        FConnect con = new FConnect();
        ArrayList<String> values = new ArrayList<>();
        con.exists(table, "codfis", this.CF);
        con.exists(table, "email", this.email);
        values.add("DEFAULT");
        values.add(this.nome);
        values.add(this.cognome);
        values.add(this.getDateString());
        values.add(this.sex);
        values.add(this.CF);
        values.add(this.email);
        values.add(this.ldn);
        values.add(this.ldr.getCittà());
        values.add(this.ldr.getVia());
        values.add(this.ldr.getNumero());
        values.add(this.ldr.getCAP());
        values.add(this.ldr.getProv());
        con.store(table, values);
    }
    public static void delete(int id) throws SQLException {
        FConnect con = new FConnect();
        con.delete(table, key, Integer.toString(id));
    }
    /* metodi di debug */
    @Override
    public String toString() {
        return  "nome: "                + this.nome                                                                  + "; " +
                "cognome: "             + this.cognome                                                               + "; " +
                "data: "                + (this.ddn.get(1)+1900) + "-" + (this.ddn.get(2)+1) + "-" + this.ddn.get(5) + "; " +
                "sesso: "               + this.sex                                                                   + "; " +
                "CF: "                  + this.CF                                                                    + "; " +
                "mail: "                + this.email                                                                 + "; " +
                "città di nascita: "    + this.ldn                                                                   + "; " +
                "città di residenza: "  + this.ldr.getCittà().trim()                                                 + "; " +
                "via: "                 + this.ldr.getVia().trim()                                                   + "; " +
                "numero: "              + this.ldr.getNumero().trim()                                                + "; " +
                "cap: "                 + this.ldr.getCAP().trim()                                                   + "; " +
                "provincia: "           + this.ldr.getProv().trim();
    }
    /* attributi */
    protected String nome;
    protected String cognome;
    protected GregorianCalendar ddn;
    protected String sex;
    protected String CF;
    protected String email;
    protected String ldn; //luogo di nascita
    protected Address ldr;//luogo di residenza
    /* attributi per il db */
    private static final String table = "persona";
    private static final String key = "id";
    private static final String condition = "id = ";
}