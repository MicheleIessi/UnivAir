/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

/**
 *
 * @author Michele
 */
public class Address {
    /* costruttori */
    public Address() {};
    public Address(String v, String n, String c, String p) {
        this.via = v;
        this.numero = n;
        this.cap = c;
        this.prov = p;
    }
    /* getter & setter */
    public String getVia()    { return this.via; }
    public String getNumero() { return this.numero; }
    public String getCAP()    { return this.cap; }
    public String getProv()   { return this.prov; }
    public void setVia(String v)    { this.via = v; }
    public void setNumero(String n) { this.numero = n; }
    public void setCAP(String c)    { this.cap = c; }
    public void setProv(String p)   { this.prov = p; }
    /* metodi di classe */
    public String[] getAddress() {
        String[] s = new String[4];
        s[0] = this.via;
        s[1] = this.numero;
        s[2] = this.cap;
        s[3] = this.prov;
        return s;
    }
    /* attributi */    
    private String via;
    private String numero;
    private String cap;
    private String prov;
}