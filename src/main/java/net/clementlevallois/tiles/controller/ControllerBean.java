/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.controller;

import net.clementlevallois.tiles.model.TilePersist;
import net.clementlevallois.tiles.model.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import javax.ejb.Startup;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author C. Levallois
 */
@Singleton
@Startup
public class ControllerBean implements Serializable {

    public static Datastore dsTiles;
    private static MongoClient m;
    private static Morphia morphia;
    private User user;
    Map<String, RequestToken> requestTokens = new HashMap();
    private final Properties cfg = new Properties();

    public ControllerBean() {
    }

    @PostConstruct
    private void init() {
        ClassLoader classLoader = ControllerBean.class.getClassLoader();
        try {
            cfg.load(classLoader.getResourceAsStream("props.properties"));

        } catch (IOException ex) {
            Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error(ex);
        }

        String userName = cfg.getProperty("username");
        String password = cfg.getProperty("password");
        String dbName = cfg.getProperty("dbname");
        try {

            if (AdminPanel.isMongoLocal()) {
                m = new MongoClient("localhost", 27017);
                morphia = new Morphia();

                dsTiles = morphia.createDatastore(m, "tiles");
                morphia.map(TilePersist.class);
                morphia.map(User.class);
                user = new User();
                user.setScreenName("seinecle");
                user.setName("Clement");
//                dsProjects.findAndDelete(dsProjects.createQuery(Project.class).field("title").exists());

            } else {
                
                MongoClientURI uri = new MongoClientURI("mongodb://"+userName+":"+password+"@"+dbName);
                m = new MongoClient(uri);
                MongoDatabase db = m.getDatabase(uri.getDatabase());
                System.out.println(db.getName());
                morphia = new Morphia();

                morphia.map(User.class);
                morphia.map(TilePersist.class);

                dsTiles = morphia.createDatastore(m, "tiles");
                user = new User();
                user.setScreenName("seinecle");
                user.setName("Clement");

            }
        } catch (MongoException ex) {
            Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Datastore getDsTiles() {
        return dsTiles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, RequestToken> getRequestTokens() {
        return requestTokens;
    }

    public void setRequestTokens(Map<String, RequestToken> requestTokens) {
        this.requestTokens = requestTokens;
    }

}
