/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Foundation;

import java.util.GregorianCalendar;

/**
 *
 * @author Michele
 */
public class Utility {
     /**
     * Il metodo getDateFromString è il duale di getStringFromDate e converte una 
     * stringa formattata secondo un pattern yyyy-mm-dd in un GregorianCalendar
     * @param date la stringa da convertire
     * @return il GregorianCalendar corrispondente alla stringa
     */
    public static GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }
    /**
     * Il metodo getStringFromDate converte una data in una stringa formattata 
     * secondo il pattern yyyy-mm-dd.
     * @param ddn Il GregorianCalendar da convertire in stringa
     * @return una stringa "traduzione" della data.
     */
    public static String getStringFromDate(GregorianCalendar ddn) {
        return (ddn.get(1)+1900) + "-" + (ddn.get(2)+1) + "-" + ddn.get(5);}

}