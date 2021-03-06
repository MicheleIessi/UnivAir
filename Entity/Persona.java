package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;
import univair.Foundation.Utility;

/**
 * La classe Persona rappresenta una persona e contiene parametri relativi alle
 * sue generalità anagrafiche
 * @author Michele
 */

public class Persona implements DBInterface {
    /* costruttori */
    /**
     * Crea una Persona con i parametri settati ai valori di default.
     */
    public Persona() {};
    /**
     * Crea una Persona con i seguenti parametri
     * @param n il nome
     * @param c il cognome
     * @param d la data di nascita (di tipo GregorianCalendar)
     * @param s il sesso (M o F)
     * @param cf il codice fiscale
     * @param e l'email
     * @param ln la città di nascita
     * @param lr l'indirizzo di residenza (di tipo Address)
     */
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
    /**
     * Ritorna il nome della Persona
     * @return il nome della Persona
     */
    public String getNome() { return this.nome; }
    /**
     * Ritorna il cognome della Persona
     * @return il cognome della Persona
     */
    public String getCognome() { return this.cognome; }
    /**
     * Ritorna la data di nascita della Persona in un GregorianCalendar
     * @return la data di nascita della Persona
     */
    public GregorianCalendar getDDN() { return this.ddn; }
    /**
     * Ritorna il sesso della Persona
     * @return il sesso della Persona
     */
    public String getSex()     { return this.sex; }
    /**
     * Ritorna il codice fiscale della Persona
     * @return il codice fiscale della Persona
     */
    public String getCF()      { return this.CF; }
    /**
     * Ritorna l'email della Persona
     * @return l'email della Persona
     */
    public String getEmail()   { return this.email; }
    /**
     * Ritorna il luogo di nascita della Persona
     * @return il luogo di nascita della Persona
     */
    public String getLDN()     { return this.ldn; }
    /** 
     * Ritorna il luogo di residenza della persona in un Address
     * @return il luogo di residenza della persona in un Address
     */
    public Address getLDR()    { return this.ldr; }
    /**
     * Imposta il nome della Persona
     * @param n il nome che si vuole impostare
     */
    public void setNome(String n)           { this.nome = n; }
    /** 
     * Imposta il cognome della Persona
     * @param c il cognome che si vuole impostare
     */
    public void setCognome(String c)        { this.cognome = c; }
    /** 
     * Imposta la data di nascita della Persona
     * @param d la data di nascita che si vuole impostare
     */
    public void setDDN(GregorianCalendar d) { this.ddn = d; }
    /** 
     * Imposta il sesso della Persona
     * @param s il sesso che si vuole impostare
     */
    public void setSec(String s)            { this.sex = s; }
    /**
     * Imposta il codice fiscale della Persona
     * @param cf il codice fiscale che si vuole impostare
     */
    public void setCF(String cf)            { this.CF = cf; }
    /** 
     * Imposta l'email della Persona
     * @param e l'email che si vuole impostare
     */
    public void SetEmail(String e)          { this.email = e; }
    /** 
     * Imposta il luogo di nascita della Persona
     * @param ln il luogo di nascita che si vuole impostare
     */
    public void setLDN(String ln)           { this.ldn = ln; }
    /** 
     * Imposta il luogo di residenza
     * @param lr il luogo di residenza che si vuole impostare
     */
    public void setLDR(Address lr)          { this.ldr = lr; }
    /* metodi di classe */
    /**
     * Il metodo controlField controlla l'esatta impostazione di diversi attributi
     * della Persona in base a tre criteri che l'utente deve scegliere
     * @param s la stringa che si deve controllare
     * @param tipo indica i criteri di controllo e può essere di tre tipi:<br>
     * - alpha : serve per controllare che i campi Nome, Cognome e Luogo di Nascita
     * non contengano alcun numero;<br>
     * - digit : serve per controllare che i campi con valori prettamente numerici
     * non contengano caratteri non numerici;<br>
     * - sex: serve per controllare che il sesso sia un carattere tra 'm','M','f'
     * o 'F'.
     * @return 
     */
    private static boolean controlField(String s, String tipo) {
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
     * Il metodo static getIDIfExists restituisce l'ID di una persona presente nel DB
     * (se esiste) cercando il suo codice fiscale (univoco). Se non esiste ritorna 0.
     * @param CF Il codice fiscale della persona di cui si vuole ricavare l'ID
     * @return L'ID della persona trovata (se esiste), o 0 (se non esiste)
     * @throws SQLException 
     */
    public static int getIDIfExists(String CF) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, "codfis = '" + CF + "'");
        int ID = 0;
        if(rs.last()) {
            ID = rs.getInt(1);
        }
        con.close();
        return ID;
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
    public HashMap retrieve(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, condition + Integer.toString(id));
        HashMap<String,Object> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            map.put("id", Integer.toString(rs.getInt(1)));
            map.put("nome", rs.getString(2));
            map.put("cognome", rs.getString(3));
            map.put("ddn", Utility.getDateFromString(rs.getString(4)));
            map.put("sex", rs.getString(5));
            map.put("CF", rs.getString(6));
            map.put("email", rs.getString(7));
            map.put("ldn", rs.getString(8));
            Address temp = new Address(rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
            map.put("ldr", temp);            
        }
        con.close();
        return map;
    }
    @Override
    public void store() throws SQLException {
        FConnect con = new FConnect();
        ArrayList<String> values = new ArrayList<>();
        boolean cf = con.exists(table, "codfis", this.CF);
        if(cf) {
            System.out.println("persona già presente nel db con lo stesso cf o mail gestire error frame Persona.store()");
        }
        else {
            values.add("DEFAULT");
            values.add(this.nome);
            values.add(this.cognome);
            values.add(Utility.getStringFromDate(this.ddn));
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
            con.close();
        }
    }
    public static void delete(int id) throws SQLException {
        FConnect con = new FConnect();
        con.delete(table, key, Integer.toString(id));
        con.close();
    }
    /**
     * Il metodo getIDFromDB restituisce l'ID della Persona che chiama il
     * metodo cercando nel DB usando il codice fiscale della Persona. Viene 
     * chiamato solo se c'è certezza che la Persona abbia una corrispattiva
     * ennupla già salvata nel DB.
     * @return l'id della Persona chiamante il metodo
     * @throws SQLException 
     */
    public int getIDFromDB() throws SQLException {
        FConnect con = new FConnect();
        String cf = this.CF;
        ResultSet rs = null;
        try {
            rs = con.load(table, "codfis = '" + cf.trim()+"'");
        } catch (SQLException e) {
            System.out.println("Errore, la persona non è presente nel DB, è necessario aggiungerla.");
        }
        rs.last();
        int id = rs.getInt(1);
        return id;
    }
    /* attributi */
    private String nome;
    private String cognome;
    private GregorianCalendar ddn;
    private String sex;
    private String CF;
    private String email;
    private String ldn; //luogo di nascita
    private Address ldr;//luogo di residenza
    /* attributi di classe (per il db) */
    private static final String table = "persona";
    private static final String key = "id";
    private static final String condition = "id = ";
}