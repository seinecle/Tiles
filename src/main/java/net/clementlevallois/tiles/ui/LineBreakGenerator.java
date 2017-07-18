/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.ui;

import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author C. Levallois
 */
public class LineBreakGenerator {

    public static HtmlOutputText generate() {
        HtmlOutputText lineBreak = new HtmlOutputText();
        lineBreak.setValue("<br/>");
        lineBreak.setEscape(false);

        return lineBreak;

    }

}
