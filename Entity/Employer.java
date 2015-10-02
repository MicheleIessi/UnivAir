/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
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
    public Employer(Persona p, String id, String s, GregorianCalendar f) {
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
    public GregorianCalendar getFrom()     { return this.from; }
    public GregorianCalendar getTo()       { return this.to; }
    public String getType()   { return this.type; }
    public void setID(String id)    { this.ID = id; }
    public void setSalary(String s) { this.salary = s; }
    public void setFrom(GregorianCalendar f)     { this.from = f; }
    public void setTo(GregorianCalendar t)       { this.to = t; }
    public void setType(String ty)  { this.type = ty; }
    /* metodi di controllo */
    public static void controlType() {
        
        
        
        
        
        
    }
    /* metodi per il DB */
    public GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }
    @Override
    public void createFromDB(int id) throws SQLException {
        Map<String,Object> map = super.retrieve(id);
        Map<String,Object> map2 = retrieve(id);
        String nom = (String) map.get("nome");
        String cog = (String) map.get("cognome");
        GregorianCalendar nas = (GregorianCalendar) map.get("ddn");
        String ses = (String) map.get("sex");
        String cod = (String) map.get("CF");
        String eml = (String) map.get("email");
        String nsc = (String) map.get("ldn");
        Address rsd = (Address) map.get("ldr");
        String nid =  Integer.toString((int)map2.get("ID"));
        String nsalary = Integer.toString((int)map2.get("salary"));
        String ntype = (String) map2.get("type");
        GregorianCalendar nfrom = (GregorianCalendar) map2.get("from");
        GregorianCalendar nto = (GregorianCalendar) map2.get("to");
        this.setNome(nom.trim());
        this.setCognome(cog.trim());
        this.setDDN(nas);
        this.setSec(ses.trim());
        this.setCF(cod.trim());
        this.SetEmail(eml.trim());
        this.setLDN(nsc.trim());
        this.setLDR(rsd);
        this.setID(nid.trim());
        this.setSalary(nsalary.trim());
        this.setType(ntype.trim());
        this.setFrom(nfrom);
        this.setTo(nto);
    }
    @Override
    public Map retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        Map<String,Object> map = new HashMap<>();
        ResultSet rs = con.load(table, condition + Integer.toString(id));
        while(rs.next()) {
            map.put("ID", rs.getInt(1));
            map.put("type", rs.getString(2));
            map.put("salary", rs.getInt(3));
            map.put("from", getDateFromString(rs.getString(4)));
            map.put("to", getDateFromString(rs.getString(5)));
        }
        return map;
    }
    /* attributi */
    private String ID;
    private String salary;
    private GregorianCalendar from;
    private GregorianCalendar to;
    protected String type;
    /* attributi per il db */
    private final String table = "impiegato";
    private final String id = "id";
    private final String condition = "id = ";
}
