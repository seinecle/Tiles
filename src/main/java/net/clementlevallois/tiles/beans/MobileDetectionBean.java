/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
public class MobileDetectionBean implements Serializable{

    public MobileDetectionBean() {
    }
    
    public void detectDevice(){
        String renderKitId = FacesContext.getCurrentInstance().getViewRoot().getRenderKitId();        
        if(renderKitId.equalsIgnoreCase("PRIMEFACES_MOBILE")){
            //REDIRECT TO YOUR MOBILE PAGE
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "index_mobile.xhtml");
        }
    }
    
}
