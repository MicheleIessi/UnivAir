/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.SQLException;
import java.util.Map;

/**
 * L'interfaccia DBInterface contiene i 4 metodi comuni delle classi che si
 * interfacciano con la classe FConnect.
 * @author Michele
 */
public interface DBInterface {
    /**
     * Il metodo createFromDB crea un oggetto dal Database prendendo come input
     * un intero che rappresenta l'ID. L'ID è la chiave primaria delle tabelle 
     * corrispondenti alle classi che implementano questa interfaccia. Usa i 
     * metodi della classe FConnect.
     * @param id l'ID dell'oggetto che si vuole creare dal DB
     * @throws SQLException se la query non trova risultati, o se il Database non 
     * è raggiungibile
     */
    public void createFromDB(int id) throws SQLException;
    /** 
     * Il metodo delete cancella una ennupla dal Database prendendo come input un
     * intero che rappresenta l'ID. L'ID è la chiave primaria delle tabelle 
     * corrispondenti alle classi che implementano questa interfaccia. Usa i
     * metodi della classe FConnect.
     * @param id l'ID dell'oggetto che si vuole eliminare dal DB
     * @throws SQLException se la query non trova risultati, o se il Database non
     * è raggiungibile
     */
    public static void delete(int id) throws SQLException {};
    /**
     * Il metodo store inserisce una ennupla nel Database. La tabella e le variabili
     * per l'inserimento cambiano in base all'oggetto (che implementa questa
     * interfaccia) che chiama questo metodo. Usa i metodi della classe FConnect.
     * @throws SQLException se il Database non è raggiungibile
     */
    public void store() throws SQLException;
    /**
     * Il metodo retrieve prende dal Database una ennupla, prendendo come input un
     * intero che rappresenta l'ID, e inserisce tutti i suoi attributi in una HashMap.
     * L'ID è la chiave primaria delle tabelle corrispondenti alle classi che 
     * implementano questa interfaccia. Usa i metodi della classe FConnect.
     * @param id l'ID della ennupla che si vuole caricare dal DB
     * @return una HashMap contenente i campi della ennupla ricavata dal DB
     * @throws SQLException se il DB non è raggiungibile, o se la query non trova
     * risultati
     */
    public Map retrieve(int id) throws SQLException;
}
