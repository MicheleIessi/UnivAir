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
public enum City {
    Amsterdam   (52.366667,  4.900000),
    Atene       (37.966667, 23.716667), 
    Berlino     (52.518611, 13.408056),
    Bruxelles   (50.846667,  4.352500),
    Dublino     (53.342500, -6.265833), 
    Helsinki    (60.170833, 24.937500), 
    Lisbona     (38.700000, -9.183333), 
    Londra      (51.507222, -0.127500), 
    Madrid      (40.433333, -3.683333), 
    Mosca       (55.751667, 37.617778), 
    Oslo        (59.916667, 10.750000), 
    Parigi      (48.856667, 2.3519440), 
    Praga       (50.083333, 14.416667), 
    Roma        (41.893056, 12.482778), 
    Sofia       (42.697556, 23.323638), 
    Stoccolma   (59.350000, 18.066667),
    Vienna      (48.208330, 16.373064), 
    Varsavia    (52.232300, 21.008433), 
    Zagabria    (45.800000, 15.966667);
    
    private final double lat;
    private final double lon;
    private City(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }   
    public double lat() {
        return lat;
    }
    public double lon() {
        return lon;
    }
}
