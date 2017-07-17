/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.bson.types.ObjectId;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
@Entity
public class User implements Serializable {

    @Id
    private ObjectId id;
    private String screenName;
    private String name;
    private long userId;
    private String location;
    private String description;
    private String followers_count;
    private String profile_image_url;
    private String url;
    private String created_at;
    private String lang;
    private String accessToken;
    private String accessTokenSecret;

    public User() {
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        if (name == null) {
            return "no name detected";
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        if (location == null) {
            return "no location available";
        } else {
            return location;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        if (description == null){
            return "no description available";
        }
        else{
        return description;}
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
    
    

    @Override
    public String toString() {


        return screenName + " (" + location + ")";

    }
}
