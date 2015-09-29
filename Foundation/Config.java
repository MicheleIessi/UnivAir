/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Foundation;

/**
 *
 * @author Michele
 */
public class Config {
    public Config() {};
    
    public String getHost()         { return this.host; }
    public int getPort()            { return this.port; }
    public String getDB()           { return this.DB; }
    public String getDBUsername()   { return this.DBUsername; }
    public String getDBPassword()   { return this.DBPassword; }
    
    
    
    private String host = "localhost";
    private int port = 5432;
    private String DB = "UnivAir";      
    private String DBUsername = "postgres";
    private String DBPassword = "admin";
    
    private String adminUsername;
    private String adminPassword;
    
}
