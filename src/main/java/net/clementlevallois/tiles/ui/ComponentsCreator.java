/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.tiles.ui;

import java.util.Iterator;
import net.clementlevallois.tiles.model.TilePersist;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import org.primefaces.component.inputtext.InputText;

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

        HtmlOutputText labelOutputNameProject = new HtmlOutputText();
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
        inputWaitNextStep.setValue("");

        HtmlInputText inputIShouldDo = new HtmlInputText();
        inputIShouldDo.setValueExpression("value", createValueExpression("#{" + beanName + ".projectUpdate}", String.class));
        inputIShouldDo.setId("inputIShouldDo_" + uuid);
        inputIShouldDo.setValue("");

        HtmlOutputText labelWaitingForNextStep = new HtmlOutputText();
        labelWaitingForNextStep.setId("labelDone_" + uuid);
        labelWaitingForNextStep.setValue("a. Waiting for this input:");
        labelWaitingForNextStep.setEscape(false);

        HtmlOutputText labelUpdateInstruction = new HtmlOutputText();
        labelUpdateInstruction.setId("labelUpdateInstructions_" + uuid);
        labelUpdateInstruction.setValue("Choose one of these 2 updates.");
        labelUpdateInstruction.setEscape(false);

        HtmlOutputText labelOr = new HtmlOutputText();
        labelOr.setValue("or:");
        labelOr.setEscape(false);

        HtmlOutputText labelToDo = new HtmlOutputText();
        labelToDo.setId("labelToDo_" + uuid);
        labelToDo.setValue("b. I must do:");
        labelToDo.setEscape(false);

        HtmlCommandButton okUpdate = new HtmlCommandButton();
        okUpdate.setLabel("ok");
        okUpdate.setTitle("ok");
        okUpdate.setValue("ok");
        okUpdate.setId("okUpdate_" + uuid);
        okUpdate.setActionExpression(createMethodExpression(String.format("#{" + beanName + ".updateToDoOrDone('" + uuid + "')}", "ok"), null, String.class));

        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(labelUpdateInstruction);
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(labelWaitingForNextStep);
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(inputWaitNextStep);
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        //weird space missing on desktop.
        if (!mobile) {
            formUpdate.getChildren().add(LineBreakGenerator.generate());
        }
        formUpdate.getChildren().add(labelOr);
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(labelToDo);
        formUpdate.getChildren().add(LineBreakGenerator.generate());
        formUpdate.getChildren().add(inputIShouldDo);
        formUpdate.getChildren().add(okUpdate);

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

    public static UIComponent addTitle(UIComponent tile, String title) {
        if (title == null) {
            title = "";
        }

        String uuid = tile.getId().split("_")[1];
        title = "Task: " + title;

        Iterator<UIComponent> it = tile.getChildren().iterator();
        boolean titleFound = false;
        while (it.hasNext()) {
            UIComponent comp = it.next();
            if (comp.getId() != null && comp.getId().equals("title_" + uuid)) {
                HtmlOutputText titleComp = (HtmlOutputText) comp;
                titleComp.setValue(title);
                titleFound = true;
            }
        }
        if (titleFound) {
            return tile;
        }

        HtmlOutputText titleOfTheTile = new HtmlOutputText();
        titleOfTheTile.setId("title_" + uuid);
        titleOfTheTile.setStyle("font-weight: bold;");
        titleOfTheTile.setValue(title);
        tile.getChildren().add(titleOfTheTile);
        titleOfTheTile.setEscape(false);
        return tile;

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

        HtmlOutputText titleOfTheTile = new HtmlOutputText();
        titleOfTheTile.setId("title_" + uuid);
        titleOfTheTile.setStyle("font-weight: bold;");

        HtmlOutputText currentStatus;

        if (tilePersist != null) {
            currentStatus = createStatusComponent(uuid, tilePersist.isGood(), tilePersist.getCurrentStatus());
            tile = addTitle(tile, tilePersist.getTitle());

        } else {
            currentStatus = createStatusComponent(uuid, null, null);
            tile = addTitle(tile, "");
        }

        tile.getChildren().add(LineBreakGenerator.generate());
        tile.getChildren().add(currentStatus);
        tile.getChildren().add(LineBreakGenerator.generate());
        tile.getChildren().add(LineBreakGenerator.generate());

        return tile;

    }

    public static HtmlOutputText createStatusComponent(String uuid, Boolean good, String status) {
        HtmlOutputText currentStatus = new HtmlOutputText();
        currentStatus.setId("currentStatus_" + uuid);
        if (status != null && !status.isEmpty()) {
            String prefix;
            if (good != null) {
                if (good) {
                    prefix = "@nextstep: waiting for \"";
                } else {
                    prefix = "@nextstep: I must do \"";
                }
                currentStatus.setValue(prefix + status + "\"");
            } else {
                currentStatus.setValue("");
            }
        } else {
            currentStatus.setValue("");
        }

        currentStatus.setEscape(false);
        return currentStatus;

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
