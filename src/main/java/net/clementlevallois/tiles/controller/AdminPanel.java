/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.controller;

/**
 *
 * @author C. Levallois
 */
public class AdminPanel {

    public static String backgroundColorGood = "gold;";
    public static String backgroundColorBad = "lightsalmon;";

    public static boolean isDebugMode() {
        return false;
    }

//    //the twitter callback returns to localhost
//    public static boolean isLocal() {
//        return true;
//    }

    //is the Mongo instance local?
    public static boolean isMongoLocal() {
        return false;
    }
}
