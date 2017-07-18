/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.oauth;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.clementlevallois.tiles.admin.AdminPanel;
import net.clementlevallois.tiles.beans.ControllerBean;
import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author C. Levallois
 */
public class TwitterFactory {

    private static final Properties cfg = new Properties();

    public static Twitter twitterMaker() {
        ClassLoader classLoader = TwitterFactory.class.getClassLoader();

        try {
            cfg.load(classLoader.getResourceAsStream("props.properties"));

        } catch (IOException ex) {
            Logger.getLogger(TwitterFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error(ex);
        }

        String consumerkey = cfg.getProperty("consumerkey");
        String consumersecret = cfg.getProperty("consumersecret");
        String consumerkeyDev = cfg.getProperty("consumerkeydev");
        String consumersecretDev = cfg.getProperty("consumersecretdev");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        if (!AdminPanel.devMode){
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerkey)
                .setOAuthConsumerSecret(consumersecret)
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);
        }else{
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerkeyDev)
                .setOAuthConsumerSecret(consumersecretDev)
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);
            
        }
        twitter4j.TwitterFactory tf = new twitter4j.TwitterFactory(cb.build());
        return tf.getInstance();
    }

}
