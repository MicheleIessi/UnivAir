/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * La classe FConnect gestisce tutti gli scambi di informazioni tra le classi
 * Entity e il Database, usando come configurazione le variabili di connessione
 * contenute all'interno della classe di supporto Config.
 * @author Michele
 */
public class FConnect {
    static Config con = new Config();
    static Connection db;

    /**
     * Costruisce un oggetto FConnect e crea una connessione al DB usando come 
     * parametri di connessione gli attributi della classe Config.
     * @throws SQLException Se non si riesce a creare una connessione
     */
    public FConnect() throws SQLException {
        String url = "jdbc:postgresql://"+con.getHost()+":"+con.getPort()+"/"+con.getDB();
        this.db = DriverManager.getConnection(url, con.getDBUsername(), con.getDBPassword());
    }
    /**
     * Il metodo query() esegue una query sul Database prendendo come input una stringa
     * già formattata. Il metodo è usato solo da altri metodi della classe (che
     * provvedono a formattare la stringa parametro della funzione) e quindi è 
     * un metodo privato. Il metodo usa il metodo executeUpdate della classe
     * Statement, quindi è usato solo per query di tipo INSERT/DELETE/UPDATE.
     * @param query La stringa 
     * @throws SQLException 
     */
    private void query(String query) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
    /**
     * Il metodo store() prende in input una stringa e un ArrayList e costruisce
     * una stringa che poi servirà a fare una query di tipo INSERT usando il 
     * metodo query() della classe FConnect. La stringa 'table' cambia in base 
     * alla classe che chiama questo metodo.
     * @param table La tabella sulla quale effettuare la INSERT.
     * @param values ArrayList contenente i valori da inserire nel DB.
     * @throws SQLException 
     */
    public void store(String table, ArrayList values) throws SQLException {
        int j = values.size();
        String val = "";
        String item = "";
        for(int i = 0; i < j; i++) {
            if(values.get(i).toString().equals("DEFAULT")) {
                item = values.get(i).toString();
            }
            else {
                item = "\'" + values.get(i).toString() + "\'";
            }
            if(i < j-1) {
                item = item + ",";
            }
            val = val + item;
        }        
        String sql = "INSERT INTO " + table + " VALUES (" + val + ");";
        //System.out.println(sql);
        query(sql);
    }
    /**
     * Il metodo exists() verifica l'esistenza di una ennupla nel DB usando come
     * input tre stringhe contenenti la tabella su cui effettuare la query (di 
     * tipo SELECT), la chiave su cui cercare e il valore da cercare. I primi 2
     * parametri cambiano a seconda della classe che chiama il metodo.
     * @param table La tabella su cui effettuare la ricerca
     * @param key La chiave primaria della tabella
     * @param value Il valore da cercare
     * @return true se la ennupla esiste, false altrimenti
     * @throws SQLException 
     */
    public boolean exists(String table, String key, String value) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + key + " = '" + value + "'");
        boolean result = false;
        if(rs.last()) {
            result = true;
        }
        return result;
    }
    /**
     * Il metodo existsMultipleKey() è simile al metodo exists() ma pensato per
     * le tabelle che hanno una chiave primaria composta da due o più attributi.
     * @param table Stringa contenente la tabella su cui effettuare la ricerca
     * @param keys Array di stringhe contenente le chiavi della tabella
     * @param values Array di stringhe contenente i valori da cercare
     * @return true se la ennupla esiste, false altrimenti
     * @throws SQLException 
     */
    public boolean existsMultipleKey(String table, String[] keys, String[] values) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "";
        Boolean result = false;
        if(keys.length == 2) {
            sql = "SELECT * FROM " + table + " WHERE " + keys[0] + " = '" + values[0] + "' AND " + keys[1] + " = '" + values[1] + "'";
            }
        else if (keys.length == 3) {
            sql = "SELECT * FROM " + table + " WHERE " + keys[0] + " = '" + values[0] + "' AND " + keys[1] + " = '" + values[1] + "' AND " + keys[2] + " = '" + values[2] + "'";
        }
        //System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.last()) {
            result = true;
        }
        return result;
    }
    /**
     * Il metodo load() effettua una ricerca sul DB e restituisce un ResultSet
     * contenente i risultati.
     * @param table La tabella su cui effettuare la ricerca
     * @param condition Stringa specificante i parametri della ricerca
     * @return Un ResultSet contenente il risultato (o i risultati) della ricerca
     * @throws SQLException 
     */
    public ResultSet load(String table, String condition) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM " + table + " WHERE " + condition;
        //System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;        
    }
    /**
     * Il metodo search() è simile al metodo load() ma pensato per le tabelle che
     * hanno una chiave primaria composta da due o più attributi o per ricerche
     * basate su più di un attributo.
     * @param table Stringa contenente la tabella su cui effettuare la ricerca
     * @param keys Array di stringhe contenente le chiavi della tabella
     * @param values Array di stringhe contenente i valori da cercare
     * @return Un ResultSet contenente il risultato (o i risultati) della ricerca
     * @throws SQLException 
     */
    public ResultSet search(String table, String[] keys, String[] values) throws SQLException {        
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String cond = "";
        int i = 0;
        int len = keys.length;
        for(String key : keys) {
            if(values[i] instanceof String) {
                cond = cond + key + " = '" + values[i] + "'";
            }
            if(i < len-1) {
                cond = cond + " AND ";
                i++;
            }
        }        
        String sql = "SELECT * FROM " + table + " WHERE " + cond;  
        //System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    /**
     * Il metodo delete() effettua una query di tipo DELETE su di una ennupla
     * del DB.
     * @param table Stringa contenente la tabella su cui effettuare la query
     * @param key Stringa contenente la chiave primaria della tabella
     * @param value Stringa specificante il valore da cercare della ennupla da 
     *              cancellare
     * @throws SQLException 
     */
    public void delete(String table, String key, String value) throws SQLException {
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "DELETE FROM " + table + " WHERE " + key + " = " + value;
        stmt.executeUpdate(sql);
    }
    
    public void close() throws SQLException {
        db.close();
    }
}
