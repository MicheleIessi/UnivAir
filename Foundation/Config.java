/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Foundation;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import univair.Presentation.MessageFrame;

/**
 *
 * @author Michele
 */
public final class Config {
    public Config() {
        try {
        retrieveConfig();
        } catch (IOException ex) {
            new MessageFrame("File di configurazione non trovato!",0);
        }
    }
    
    public String getHost()         { return this.host; }
    public int getPort()            { return this.port; }
    public String getDB()           { return this.DB; }
    public String getDBUsername()   { return this.DBUsername; }
    public String getDBPassword()   { return this.DBPassword; }
    public String getAdminUsername(){ return this.adminUsername; }
    public String getAdminPassword(){ return this.adminPassword; }
    
    public void retrieveConfig() throws FileNotFoundException, IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("src\\univair\\Foundation\\config.txt"));
        LinkedList<String> textData = new LinkedList<>();
        try {
            String line = br.readLine();
            while (line != null ) {
                if(!line.contains("//")) {
                    textData.add(line);
                }
                line = br.readLine();
            }
            this.host = textData.get(0);
            this.port = Integer.parseInt(textData.get(1));
            this.DB = textData.get(2);
            this.DBUsername = textData.get(3);
            this.DBPassword = textData.get(4);
            this.adminUsername = textData.get(5);
            this.adminPassword = textData.get(6);
        } finally {
            br.close();
            }
    }

    private String host;
    private int port;
    private String DB;      
    private String DBUsername;
    private String DBPassword;
    
    private String adminUsername;
    private String adminPassword;
    
}
