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
    public Address(String ci, String v, String n, String c, String p) {
        this.città = ci;
        this.via = v;
        this.numero = n;
        this.cap = c;
        this.prov = p;
    }
    /* getter & setter */
    public String getCittà()  { return this.città; }
    public String getVia()    { return this.via; }
    public String getNumero() { return this.numero; }
    public String getCAP()    { return this.cap; }
    public String getProv()   { return this.prov; }
    public void setCittà(String c)  { this.città = c; }
    public void setVia(String v)    { this.via = v; }
    public void setNumero(String n) { this.numero = n; }
    public void setCAP(String c)    { this.cap = c; }
    public void setProv(String p)   { this.prov = p; }
    /* metodi di classe */
    public String[] getAddress() {
        String[] s = new String[5];
        s[0] = this.città;
        s[1] = this.via;
        s[2] = this.numero;
        s[3] = this.cap;
        s[4] = this.prov;
        return s;
    }
    /* attributi */
    private String città;
    private String via;
    private String numero;
    private String cap;
    private String prov;
}