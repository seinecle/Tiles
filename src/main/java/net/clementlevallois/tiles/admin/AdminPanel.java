/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.admin;

/**
 *
 * @author C. Levallois
 */
public class AdminPanel {

    public static String styleGood = "background:gold;border:#FFC300;";
    public static String styleBad = "background:lightsalmon; border:darksalmon;";

    public static boolean devMode = true;

    public static String getRoot() {
        if (devMode) {
            return "dev";
        } else {
            return "tiles";
        }
    }

    //is the Mongo instance local?
    public static boolean isMongoLocal() {
        return false;
    }
}
