/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.ui;

import java.util.Iterator;
import javax.faces.component.UIComponent;

/**
 *
 * @author C. Levallois
 */
public class ComponentsRemover {

    public static UIComponent removeComponentByIdFromTile(UIComponent tile, String fullId) {

        UIComponent comp;

        Iterator<UIComponent> it = tile.getChildren().iterator();
        while (it.hasNext()) {
            comp = it.next();
            if (comp.getId().equals(fullId)) {
                it.remove();
            }
        }
        return tile;

    }
}