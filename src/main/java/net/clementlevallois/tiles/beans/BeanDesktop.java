/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.beans;

import net.clementlevallois.tiles.admin.AdminPanel;
import net.clementlevallois.tiles.model.TilePersist;
import net.clementlevallois.tiles.utils.FindComponent;
import com.google.code.morphia.query.Query;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import net.clementlevallois.tiles.model.User;
import net.clementlevallois.tiles.persistence.TileSavingOps;
import net.clementlevallois.tiles.ui.ComponentsCreator;
import net.clementlevallois.tiles.ui.ComponentsRemover;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;

/*
 Copyright 2008-2013 Clement Levallois
 Authors : Clement Levallois <clementlevallois@gmail.com>
 Website : http://www.clementlevallois.net


 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Clement Levallois. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s): Clement Levallois

 */
@ManagedBean
@SessionScoped
public class BeanDesktop implements Serializable {

    private String projectUpdate = "";
    private String newProjectName;
    boolean tilesRestored = false;
    private User userIdentified;

    public BeanDesktop() {
        System.out.println("new bean 2 instantiated");
    }

    public String getNewProjectName() {
        return newProjectName;
    }

    public void setNewProjectName(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    public void createNewTile() {

        PanelGrid tiles;

        HtmlPanelGroup tile = new HtmlPanelGroup();
        String uuid = UUID.randomUUID().toString().substring(0, 10);
        tile.setId("tile_" + uuid);
        tile.setStyleClass("tileDesktop");

        tile = (HtmlPanelGroup) ComponentsCreator.createBasicComponents(tile, null, false);
        tile = (HtmlPanelGroup) ComponentsCreator.createTitleChooserComponents(tile, false);

        tiles = (PanelGrid) FindComponent.doFind(FacesContext.getCurrentInstance(), "tiles");

        tiles.getChildren().add(0, tile);
        RequestContext.getCurrentInstance().update("tiles");

    }

    public void validatingTileTitle(String uuid) {

        UIComponent newTile = FindComponent.doFind(FacesContext.getCurrentInstance(), "tile_" + uuid);

        Iterator<UIComponent> it = newTile.getChildren().iterator();
        while (it.hasNext()) {
            UIComponent comp = it.next();
            //removing the form containing components specific to tile creation
            if (comp.getId() != null && comp.getId().equals("formCreationTile_" + uuid)) {
                comp.getChildren().clear();
                it.remove();
            }
        }
        //add title
        newTile = ComponentsCreator.addTitle(newTile, newProjectName);

        // adding the button "update" so that the user can update the tile later:
        HtmlForm formUpdate = ComponentsCreator.createFormUpdate(uuid, false);
        newTile.getChildren().add(formUpdate);

        TilePersist t = new TilePersist();
        t.setTitle(newProjectName);
        t.setScreenName(userIdentified.getScreenName());
        t.setUuid(uuid);

        ControllerBean.dsTiles.save(t);
    }

    public void restoreSavedTiles() {
        PanelGrid tiles = (PanelGrid) FindComponent.doFind(FacesContext.getCurrentInstance(), "tiles");
        tiles.setColumnClasses("thirty-percent, thirty-percent, thirty-percent");

        if (tilesRestored & tiles.getChildCount() > 0) {
            System.out.println("tiles already restored");
            return;
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String token = request.getParameter("type");

        if (token == null || token.isEmpty()) {
            System.out.println("no token in the url");
            return;
        }

        Query<User> query = ControllerBean.getDsTiles().createQuery(User.class).field("accessToken").equal(token);
        userIdentified = query.get();
        System.out.println("user identified in restoreSavedTiles: " + userIdentified.getScreenName());

        tiles.getChildren().clear();

        System.out.println("creation of tiles stored in db for this user");
        List<TilePersist> tilesPersisted = TileSavingOps.retrieveTilesOfAUser(userIdentified.getScreenName());

        for (TilePersist t : tilesPersisted) {

            HtmlPanelGroup tile = new HtmlPanelGroup();
            tile.setId("tile_" + t.getUuid());
            tile.setStyleClass("tileDesktop");

            tile = (HtmlPanelGroup) ComponentsCreator.createBasicComponents(tile, t, false);

            if (t.isGood()) {
                tile.setStyle(AdminPanel.styleGood);
            } else {
                tile.setStyle(AdminPanel.styleBad);
            }

            HtmlForm createFormUpdate = ComponentsCreator.createButtonToDisplayFormUpdate(t.getUuid(), false);
            tile.getChildren().add(createFormUpdate);
            tiles.getChildren().add(tile);
        }
        tilesRestored = true;
        RequestContext.getCurrentInstance().update("tiles");

    }

    public String getProjectUpdate() {
        return projectUpdate;
    }

    public void setProjectUpdate(String projectUpdate) {
        this.projectUpdate = projectUpdate;
    }

    public void deleteTile(String uuid) {
        UIComponent tile = FindComponent.doFind(FacesContext.getCurrentInstance(), "tile_" + uuid);

        UIComponent tiles = FindComponent.doFind(FacesContext.getCurrentInstance(), "tiles");
        tiles.getChildren().remove(tile);

        Query<TilePersist> queryProject = ControllerBean.getDsTiles().createQuery(TilePersist.class).field("uuid").equal(uuid);
        ControllerBean.getDsTiles().findAndDelete(queryProject);

    }

    public void updateToDoOrDone(String uuid) {
        HtmlPanelGroup tile = (HtmlPanelGroup) FindComponent.doFind(FacesContext.getCurrentInstance(), "tile_" + uuid);

        long currentTimeMillis = System.currentTimeMillis();

        TilePersist tilePersist = TileSavingOps.retrieveGivenTileOfAUser(userIdentified.getScreenName(), uuid);

        if (tilePersist == null) {
            tilePersist = new TilePersist();
            tilePersist.setUuid(uuid);
            tilePersist.setScreenName(userIdentified.getScreenName());
            HtmlOutputText title = (HtmlOutputText) FindComponent.doFind(FacesContext.getCurrentInstance(), "title_" + uuid);
            tilePersist.setTitle((String) title.getValue());
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String inputWaitNextStep = ec.getRequestParameterMap().get("inputWaitNextStep_" + uuid);
        String inputIShouldDo = ec.getRequestParameterMap().get("inputIShouldDo_" + uuid);

        if ((inputWaitNextStep != null && !inputWaitNextStep.isEmpty()) & (inputIShouldDo != null && !inputIShouldDo.isEmpty())) {
            System.out.println("the 2 fields should not be filled together");
            return;
        }

        boolean waitNextStepFilled = inputWaitNextStep != null && !inputWaitNextStep.isEmpty();
        boolean iShouldDoFilled = inputIShouldDo != null && !inputIShouldDo.isEmpty();

        // if at least one field is filled:
        if (waitNextStepFilled | iShouldDoFilled) {

            String input;
            if (iShouldDoFilled) {
                input = inputIShouldDo;
                tile.setStyle(AdminPanel.styleBad);
                tilePersist.setGood(false);
                tilePersist.getToDos().put(currentTimeMillis, input);
            } else {
                input = inputWaitNextStep;
                tile.setStyle(AdminPanel.styleGood);
                tilePersist.setGood(true);
                tilePersist.getDones().put(currentTimeMillis, input);
            }

            UIComponent comp;
            Iterator<UIComponent> it = tile.getChildren().iterator();
            while (it.hasNext()) {
                comp = it.next();
                // registering the latest update (wait or to do) as the current status of the tile
                if (comp.getId() != null && comp.getId().equals("currentStatus_" + uuid)) {
                    HtmlOutputText createStatusComponent = ComponentsCreator.createStatusComponent(uuid, waitNextStepFilled, input);
                    HtmlOutputText currentStatus = (HtmlOutputText) comp;
                    currentStatus.setValue(createStatusComponent.getValue());
                }
            }
            tilePersist.setCurrentStatus(input);

        }
        //UI Updates:

        HtmlInputText input1 = (HtmlInputText) FindComponent.doFind(FacesContext.getCurrentInstance(), "inputWaitNextStep_" + uuid);
        input1.setValue("");
        HtmlInputText input2 = (HtmlInputText) FindComponent.doFind(FacesContext.getCurrentInstance(), "inputIShouldDo_" + uuid);
        input2.setValue("");

        //removing the form containing components specific to field updates
        tile = (HtmlPanelGroup) ComponentsRemover.removeComponentByIdFromTile(tile, "formUpdate_" + uuid);

        //adding the UPDATE button so that the UI for update can be shown again by the user:
        HtmlForm createFormUpdate = ComponentsCreator.createButtonToDisplayFormUpdate(uuid, false);
        tile.getChildren().add(createFormUpdate);

        // save the tile
        TileSavingOps.saveTile(tilePersist);

        //update the page
        RequestContext.getCurrentInstance().update(tile.getClientId());

    }

    public void showUpdateUI(String uuid) {
        FacesContext context = FacesContext.getCurrentInstance();
        HtmlPanelGroup tile = (HtmlPanelGroup) FindComponent.doFind(context, "tile_" + uuid);

        UIComponent doFind = FindComponent.doFind(context, "showUpdateForm_" + uuid);

        tile.getChildren().remove(doFind);
        HtmlForm formUpdate = ComponentsCreator.createFormUpdate(uuid, false);
        tile.getChildren().add(formUpdate);

    }

    public User getUserIdentified() {
        return userIdentified;
    }

    public void setUserIdentified(User userIdentified) {
        this.userIdentified = userIdentified;
    }

}
