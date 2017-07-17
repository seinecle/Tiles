/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import net.clementlevallois.tiles.model.User;
import com.google.code.morphia.query.Query;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import net.clementlevallois.tiles.controller.ControllerBean;
import net.clementlevallois.tiles.mobile.UAgentInfo;
import net.clementlevallois.tiles.oauth.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * REST Web Service
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
@Path("login")
public class SigninResource implements Serializable {

    @Context
    private UriInfo context;

    @Context
    HttpServletRequest request;

    @Context
    HttpServletResponse response;

    User userIdentified;
    Twitter twitterSession;

    @Inject
    ControllerBean controllerBean;
    


    /**
     * Creates a new instance of SigninResource
     */
    public SigninResource() {
    }

    /**
     * Retrieves representation of an instance of REST.SigninResource
     *
     * @param request
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("twitterAuth")
    public String redirectToTwitter() throws ServletException, IOException {

        Twitter twitter = TwitterFactory.twitterMaker();

        // setup callback URL
        StringBuffer callbackURL = request.getRequestURL();
        System.out.println("callbackURL from request: " + callbackURL.toString());

        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");

        String urlCallBack = "http://www.exploreyourdata.com/tiles/webresources/login/callback";

//        twitter4j.User user = null;
//        // get request object and save to session
//        try {
//            user = twitter.verifyCredentials();
//        } catch (TwitterException te) {
//            if (401 == te.getStatusCode()) {
//                System.out.println("userIdentified not authenticated. let's continue.");
//            } else {
//                System.out.println("userIdentified already authenticated. let's move to tiles.xhtml");
//                System.out.println("user identified: " + user.getScreenName());
//                Query<User> query = ControllerBean.getDsTiles().createQuery(User.class).field("screenName").equal(user.getScreenName());
//                userIdentified = query.get();
//                response.sendRedirect("/tiles/tiles.xhtml?type=" + userIdentified.getAccessToken());
//            }
//        } catch (java.lang.IllegalStateException r) {
//            System.out.println("userIdentified not authenticated. let's continue.");
//        }
        try {

            RequestToken requestToken = twitter.getOAuthRequestToken(urlCallBack);

            request.getSession().setAttribute("requestToken", requestToken);
            controllerBean.getRequestTokens().put(requestToken.getToken(), requestToken);

            // redirect to twitter authentication URL
            response.sendRedirect(requestToken.getAuthenticationURL());

        } catch (IOException ex) {
            Logger.getLogger(SigninResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException | java.lang.IllegalStateException ex) {
            Logger.getLogger(SigninResource.class.getName()).log(Level.SEVERE, null, ex);

//            System.out.println("userIdentified already authenticated. let's move to tiles.xhtml");
//            System.out.println("user identified: " + user.getScreenName());
//            Query<User> query = ControllerBean.getDsTiles().createQuery(User.class).field("screenName").equal(user.getScreenName());
//            userIdentified = query.get();
//            response.sendRedirect("/tiles/tiles.xhtml?type=" + userIdentified.getAccessToken());
        }
        return null;
    }

    /**
     * Retrieves representation of an instance of REST.SigninResource
     *
     * @param request
     * @return an instance of java.lang.String
     */
    @GET
    @Path("callback")
    @Produces(MediaType.TEXT_PLAIN)
    public String callback() throws ServletException, TwitterException, IOException {
        Twitter twitter = TwitterFactory.twitterMaker();

// Get twitter object from session
//Get twitter request token object from session
        RequestToken requestToken;
        requestToken = controllerBean.getRequestTokens().get(request.getParameter("oauth_token"));
        String verifier = request.getParameter("oauth_verifier");
        try {
            // Get twitter access token object by verifying request token 
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");

            // Get userIdentified object from database with twitter userid
            Query<User> queryProject = ControllerBean.getDsTiles().createQuery(User.class).field("userId").equal(accessToken.getUserId());
            userIdentified = queryProject.get();

            if (userIdentified == null) {
                // if userIdentified is null, create new userIdentified with given twitter details 
                userIdentified = new User();
                userIdentified.setUserId(accessToken.getUserId());
                userIdentified.setScreenName(accessToken.getScreenName());
                userIdentified.setAccessToken(accessToken.getToken());
                userIdentified.setAccessTokenSecret(accessToken.getTokenSecret());
                ControllerBean.getDsTiles().save(userIdentified);
            } else {
                // if userIdentified already there in database, update access token
                userIdentified.setAccessToken(accessToken.getToken());
                userIdentified.setAccessTokenSecret(accessToken.getTokenSecret());
                ControllerBean.getDsTiles().save(userIdentified);
            }
        } catch (TwitterException e) {
            System.out.println(e.getErrorMessage());
        }
        System.out.println("user identified: " + userIdentified.getScreenName());

        String userAgent = request.getHeader("user-agent");
        System.out.println("user agent: "+ userAgent);
        String accept = request.getHeader("Accept");
        System.out.println("accept: "+ accept);


        if (userAgent != null && accept != null) {
            UAgentInfo agent = new UAgentInfo(userAgent, accept);
            if (agent.detectMobileQuick()) {
                response.sendRedirect("/tiles/tiles_mobile.xhtml?type=" + userIdentified.getAccessToken());
            } else {
                response.sendRedirect("/tiles/tiles.xhtml?type=" + userIdentified.getAccessToken());
            }
        } else {
            response.sendRedirect("/tiles/tiles.xhtml?type=" + userIdentified.getAccessToken());

        }
        return null;
    }

    public User getUserIdentified() {
        return userIdentified;
    }

    public void setUserIdentified(User userIdentified) {
        this.userIdentified = userIdentified;
    }

}
