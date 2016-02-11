/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import univair.Foundation.Config;
import univair.Presentation.AdminPanel;
/**
 *
 * @author Michele
 */
public class LoginControl {
    
    static Config con = new Config();

    public LoginControl(String user, String pass) throws Exception {
//        System.out.println("Username: "+user+", password: "+pass);
//        System.out.println("Credenziali giuste: "+con.getAdminUsername()+","+con.getAdminPassword());
        if(user.equals(con.getAdminUsername()) && pass.equals(con.getAdminPassword())) {
            new AdminPanel(con.getAdminUsername());
        }
        else {
            throw new Exception("Credenziali di login errate");
        }
    }
}
