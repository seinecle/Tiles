/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.persistence;

import net.clementlevallois.tiles.model.TilePersist;
import com.google.code.morphia.query.Query;
import java.util.List;
import net.clementlevallois.tiles.controller.ControllerBean;

/**
 *
 * @author C. Levallois
 */
public class TileSavingOps {

    public static void saveTile(TilePersist tilePersist) {
        Query<TilePersist> queryProject = ControllerBean.getDsTiles().createQuery(TilePersist.class).field("uuid").equal(tilePersist.getUuid());
        ControllerBean.getDsTiles().findAndDelete(queryProject);
        ControllerBean.getDsTiles().save(tilePersist);
    }

    public static List<TilePersist> retrieveTilesOfAUser(String screenName) {
        Query<TilePersist> queryProject = ControllerBean.getDsTiles().createQuery(TilePersist.class).field("screenName").equal(screenName);
        return queryProject.asList();
    }

    public static TilePersist retrieveGivenTileOfAUser(String screenName, String tileId) {
        Query<TilePersist> queryProject = ControllerBean.getDsTiles().createQuery(TilePersist.class).field("screenName").equal(screenName).field("uuid").equal(tileId);
        return queryProject.get();
    }

}
