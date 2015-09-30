/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Michele
 */
public interface DBInterface {
    public void createFromDB(int id) throws SQLException;
    public static void delete(int id) throws SQLException {};
    public void store(int id) throws SQLException;
    public Map retrieve(int id) throws SQLException;
}
