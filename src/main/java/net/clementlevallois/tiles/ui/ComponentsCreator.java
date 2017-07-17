/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.ui;

import net.clementlevallois.tiles.model.TilePersist;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;

/**
 *
 * @author C. Levallois
 */
public class ComponentsCreator {

    public static UIComponent createTitleChooserComponents(UIComponent tile, boolean mobile) {
        String beanName;

        if (mobile) {
            beanName = "beanMobile";
        } else {
            beanName = "beanDesktop";
        }

        String uuid = tile.getId().split("_")[1];

        InputText inputTitleProject = new InputText();
        inputTitleProject.setValueExpression("value", createValueExpression("#{" + beanName + ".newProjectName}", String.class));
        inputTitleProject.setId("inputTitle_" + uuid);

        OutputLabel labelOutputNameProject = new OutputLabel();
        labelOutputNameProject.setId("labelTitle_" + uuid);
        labelOutputNameProject.setValue("Which task?<br>");
        labelOutputNameProject.setEscape(false);

        HtmlForm formProjectCreation = new HtmlForm();
        formProjectCreation.setId("formCreationTile_" + uuid);
        formProjectCreation.setPrependId(false);

        HtmlCommandButton okProjectName = new HtmlCommandButton();
        okProjectName.setLabel("ok");
        okProjectName.setValue("ok");
        okProjectName.setTitle("ok");
        okProjectName.setId("ok_" + uuid);
        okProjectName.setActionExpression(createMethodExpression(String.format("#{" + beanName + ".validatingTileTitle('" + uuid + "')}", "ok"), null, String.class));

        formProjectCreation.getChildren().add(labelOutputNameProject);
        formProjectCreation.getChildren().add(inputTitleProject);
        formProjectCreation.getChildren().add(okProjectName);

        tile.getChildren().add(formProjectCreation);

        return tile;

    }

    public static HtmlForm createFormUpdate(String uuid, boolean mobile) {
        String beanName;

        if (mobile) {
            beanName = "beanMobile";
        } else {
            beanName = "beanDesktop";
        }

        HtmlForm formUpdate = new HtmlForm();
        formUpdate.setId("formUpdate_" + uuid);
        formUpdate.setPrependId(false);
        formUpdate.setStyle("display: block;");

        HtmlInputText inputWaitNextStep = new HtmlInputText();
        inputWaitNextStep.setValueExpression("value", createValueExpression("#{" + beanName + ".projectUpdate}", String.class));
        inputWaitNextStep.setId("inputWaitNextStep_" + uuid);

        HtmlInputText inputIShouldDo = new HtmlInputText();
        inputIShouldDo.setValueExpression("value", createValueExpression("#{" + beanName + ".projectUpdate}", String.class));
        inputIShouldDo.setId("inputIShouldDo_" + uuid);

        OutputLabel labelWaitingForNextStep = new OutputLabel();
        labelWaitingForNextStep.setId("labelDone_" + uuid);
        labelWaitingForNextStep.setValue("Waiting for an action?<br/>");
        labelWaitingForNextStep.setEscape(false);

        OutputLabel labelOr = new OutputLabel();
        labelOr.setValue("or:<br>");
        labelOr.setEscape(false);

        OutputLabel labelToDo = new OutputLabel();
        labelToDo.setId("labelToDo_" + uuid);
        labelToDo.setValue("This is what I should do next -> <br>");
        labelToDo.setEscape(false);

        HtmlCommandButton okUpdate = new HtmlCommandButton();
        okUpdate.setLabel("ok");
        okUpdate.setTitle("ok");
        okUpdate.setValue("ok");
        okUpdate.setId("okUpdate_" + uuid);
        okUpdate.setActionExpression(createMethodExpression(String.format("#{" + beanName + ".updateToDoOrDone('" + uuid + "')}", "ok"), null, String.class));

        formUpdate.getChildren().add(0, labelWaitingForNextStep);
        formUpdate.getChildren().add(1, inputWaitNextStep);
        formUpdate.getChildren().add(2, labelOr);
        formUpdate.getChildren().add(3, labelToDo);
        formUpdate.getChildren().add(4, inputIShouldDo);
        formUpdate.getChildren().add(5, okUpdate);

        return formUpdate;

    }

    public static HtmlForm createButtonToDisplayFormUpdate(String uuid, boolean mobile) {
        String beanName;

        if (mobile) {
            beanName = "beanMobile";
        } else {
            beanName = "beanDesktop";
        }

        HtmlForm showUpdateForm = new HtmlForm();
        showUpdateForm.setId("showUpdateForm_" + uuid);
        showUpdateForm.setPrependId(false);
        showUpdateForm.setStyle("display: block;");

        HtmlCommandButton showUpdateButton = new HtmlCommandButton();

        showUpdateButton.setLabel("update");
        showUpdateButton.setValue("update task");
        showUpdateButton.setTitle("update");
        showUpdateButton.setId("okUpdateUI_" + uuid);
        showUpdateButton.setActionExpression(createMethodExpression(String.format("#{" + beanName + ".showUpdateUI('" + uuid + "')}", "ok"), null, String.class));

        showUpdateForm.getChildren().add(showUpdateButton);

        return showUpdateForm;

    }

    public static UIComponent createBasicComponents(UIComponent tile, TilePersist tilePersist, boolean mobile) {
        String beanName;

        if (mobile) {
            beanName = "beanMobile";
        } else {
            beanName = "beanDesktop";
        }

        String uuid = tile.getId().split("_")[1];

        HtmlForm formClose = new HtmlForm();
        formClose.setStyleClass("deleteButton");

        HtmlCommandButton deleteTileButton = new HtmlCommandButton();
        deleteTileButton.setValue("X");
        deleteTileButton.setTitle("X");
        deleteTileButton.setLabel("X");
        deleteTileButton.setId("deleteButton_" + uuid);
        deleteTileButton.setActionExpression(createMethodExpression(String.format("#{" + beanName + ".deleteTile('" + uuid + "')}", "X"), null, String.class));

        formClose.getChildren().add(deleteTileButton);
        tile.getChildren().add(formClose);

        OutputLabel titleOfTheTile = new OutputLabel();
        titleOfTheTile.setId("title_" + uuid);
        if (tilePersist != null) {
            titleOfTheTile.setValue("<b>task: </b>" + tilePersist.getTitle() + "<br/>");
        } else {
            titleOfTheTile.setValue("" + "<br/>");
        }
        titleOfTheTile.setEscape(false);
        tile.getChildren().add(titleOfTheTile);

        OutputLabel currentStatus = new OutputLabel();
        currentStatus.setId("currentStatus_" + uuid);
        if (tilePersist != null) {
            currentStatus.setValue("current status: " + tilePersist.getCurrentStatus());
        } else {
            currentStatus.setValue("");
        }
        currentStatus.setEscape(false);

        tile.getChildren().add(currentStatus);

        return tile;

    }

    static MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createMethodExpression(
                facesContext.getELContext(), expression, returnType, parameterTypes);
    }

    static ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
                facesContext.getELContext(), valueExpression, valueType);
    }

}
