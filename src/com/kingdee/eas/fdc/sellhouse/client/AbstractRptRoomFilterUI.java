/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractRptRoomFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptRoomFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbSellState;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbOutLook;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbFace;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbFloor;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker orderDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker orderDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton roomArea;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton buildArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton buildPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton dealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer priceToLabel;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField roomAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField rommAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField priceFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Building;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField priceTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton roomPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton dealPrice;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton standardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbRoomModelType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton jrbRoomForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ModelType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7EyeSight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Direction;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7roomForm;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSellState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingFloor;
    /**
     * output class constructor
     */
    public AbstractRptRoomFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRptRoomFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.jrbSellState = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbBuildingProperty = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbRoomModel = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbOutLook = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbFace = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbFloor = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.orderDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.orderDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.buildArea = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.buildPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.dealTotalAmount = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.priceToLabel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.roomAreaTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.rommAreaFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.priceFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Building = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.priceTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.roomPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.dealPrice = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.standardTotalAmount = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbRoomModelType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.jrbRoomForm = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ModelType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7EyeSight = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Direction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7roomForm = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboSellState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7BuildingFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1.setName("kDPanel1");
        this.jrbSellState.setName("jrbSellState");
        this.jrbBuildingProperty.setName("jrbBuildingProperty");
        this.jrbRoomModel.setName("jrbRoomModel");
        this.jrbOutLook.setName("jrbOutLook");
        this.jrbFace.setName("jrbFace");
        this.jrbFloor.setName("jrbFloor");
        this.kDPanel2.setName("kDPanel2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.orderDateFrom.setName("orderDateFrom");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.orderDateTo.setName("orderDateTo");
        this.kDPanel3.setName("kDPanel3");
        this.roomArea.setName("roomArea");
        this.buildArea.setName("buildArea");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDPanel4.setName("kDPanel4");
        this.buildPrice.setName("buildPrice");
        this.dealTotalAmount.setName("dealTotalAmount");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.priceToLabel.setName("priceToLabel");
        this.roomAreaTo.setName("roomAreaTo");
        this.rommAreaFrom.setName("rommAreaFrom");
        this.priceFrom.setName("priceFrom");
        this.f7Building.setName("f7Building");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.priceTo.setName("priceTo");
        this.roomPrice.setName("roomPrice");
        this.dealPrice.setName("dealPrice");
        this.standardTotalAmount.setName("standardTotalAmount");
        this.jrbRoomModelType.setName("jrbRoomModelType");
        this.jrbRoomForm.setName("jrbRoomForm");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.contSellState.setName("contSellState");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.f7ModelType.setName("f7ModelType");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.f7RoomModel.setName("f7RoomModel");
        this.f7EyeSight.setName("f7EyeSight");
        this.f7Direction.setName("f7Direction");
        this.f7roomForm.setName("f7roomForm");
        this.comboSellState.setName("comboSellState");
        this.f7BuildingFloor.setName("f7BuildingFloor");
        // CustomerQueryPanel		
        this.setBorder(null);		
        this.setPreferredSize(new Dimension(515,360));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // jrbSellState		
        this.jrbSellState.setText(resHelper.getString("jrbSellState.text"));
        this.jrbSellState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    jrbSellState_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // jrbBuildingProperty		
        this.jrbBuildingProperty.setText(resHelper.getString("jrbBuildingProperty.text"));
        // jrbRoomModel		
        this.jrbRoomModel.setText(resHelper.getString("jrbRoomModel.text"));
        // jrbOutLook		
        this.jrbOutLook.setText(resHelper.getString("jrbOutLook.text"));
        // jrbFace		
        this.jrbFace.setText(resHelper.getString("jrbFace.text"));
        // jrbFloor		
        this.jrbFloor.setText(resHelper.getString("jrbFloor.text"));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(50);
        // orderDateFrom
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(50);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // orderDateTo
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // roomArea		
        this.roomArea.setText(resHelper.getString("roomArea.text"));		
        this.roomArea.setSelected(true);
        // buildArea		
        this.buildArea.setText(resHelper.getString("buildArea.text"));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(50);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(50);
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
        // buildPrice		
        this.buildPrice.setText(resHelper.getString("buildPrice.text"));		
        this.buildPrice.setSelected(true);
        this.buildPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    buildPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // dealTotalAmount		
        this.dealTotalAmount.setText(resHelper.getString("dealTotalAmount.text"));
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(50);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // priceToLabel		
        this.priceToLabel.setBoundLabelText(resHelper.getString("priceToLabel.boundLabelText"));		
        this.priceToLabel.setBoundLabelUnderline(true);		
        this.priceToLabel.setBoundLabelLength(50);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.buildPrice);
        this.kDButtonGroup1.add(this.dealTotalAmount);
        this.kDButtonGroup1.add(this.roomPrice);
        this.kDButtonGroup1.add(this.dealPrice);
        this.kDButtonGroup1.add(this.standardTotalAmount);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.roomArea);
        this.kDButtonGroup2.add(this.buildArea);
        // roomAreaTo		
        this.roomAreaTo.setDataType(1);
        // rommAreaFrom		
        this.rommAreaFrom.setDataType(1);
        this.rommAreaFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    rommAreaFrom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // priceFrom		
        this.priceFrom.setDataType(1);
        // f7Building		
        this.f7Building.setDisplayFormat("$name$");		
        this.f7Building.setEditFormat("$number$");		
        this.f7Building.setCommitFormat("$number$");		
        this.f7Building.setEnabledMultiSelection(true);		
        this.f7Building.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");
        this.f7Building.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    f7Building_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Building.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    f7Building_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);
        // priceTo		
        this.priceTo.setDataType(1);
        // roomPrice		
        this.roomPrice.setText(resHelper.getString("roomPrice.text"));
        // dealPrice		
        this.dealPrice.setText(resHelper.getString("dealPrice.text"));
        // standardTotalAmount		
        this.standardTotalAmount.setText(resHelper.getString("standardTotalAmount.text"));
        // jrbRoomModelType		
        this.jrbRoomModelType.setText(resHelper.getString("jrbRoomModelType.text"));		
        this.jrbRoomModelType.setSelected(true);
        // jrbRoomForm		
        this.jrbRoomForm.setText(resHelper.getString("jrbRoomForm.text"));
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(80);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(80);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(80);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(80);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(80);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(80);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setEnabled(false);		
        this.kDLabelContainer12.setVisible(false);
        // contSellState		
        this.contSellState.setBoundLabelText(resHelper.getString("contSellState.boundLabelText"));		
        this.contSellState.setBoundLabelLength(80);		
        this.contSellState.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(80);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // f7ModelType		
        this.f7ModelType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.f7ModelType.setCommitFormat("$number$");		
        this.f7ModelType.setEditFormat("$number$");		
        this.f7ModelType.setDisplayFormat("$name$");
        // f7BuildingProperty		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // f7RoomModel		
        this.f7RoomModel.setCommitFormat("$number$");		
        this.f7RoomModel.setEditFormat("$number$");		
        this.f7RoomModel.setDisplayFormat("$name$");		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
        // f7EyeSight		
        this.f7EyeSight.setCommitFormat("$number$");		
        this.f7EyeSight.setEditFormat("$number$");		
        this.f7EyeSight.setDisplayFormat("$name$");		
        this.f7EyeSight.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery");
        // f7Direction		
        this.f7Direction.setCommitFormat("$number$");		
        this.f7Direction.setEditFormat("$number$");		
        this.f7Direction.setDisplayFormat("$name$");		
        this.f7Direction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // f7roomForm		
        this.f7roomForm.setCommitFormat("$number$");		
        this.f7roomForm.setEditFormat("$number$");		
        this.f7roomForm.setDisplayFormat("$name$");		
        this.f7roomForm.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery");
        // comboSellState		
        this.comboSellState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());
        // f7BuildingFloor		
        this.f7BuildingFloor.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingFloorEntryQuery");		
        this.f7BuildingFloor.setDisplayFormat("$floorAlias$");		
        this.f7BuildingFloor.setEditFormat("$floor$");		
        this.f7BuildingFloor.setCommitFormat("$floor$");		
        this.f7BuildingFloor.setEnabled(false);		
        this.f7BuildingFloor.setVisible(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 515, 360));
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(3, 248, 508, 107));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(5, 38, 505, 61));
        this.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(4, 99, 507, 69));
        this.add(kDPanel3, null);
        kDPanel4.setBounds(new Rectangle(3, 169, 508, 81));
        this.add(kDPanel4, null);
        f7Building.setBounds(new Rectangle(79, 14, 170, 19));
        this.add(f7Building, null);
        kDLabelContainer7.setBounds(new Rectangle(27, 14, 202, 19));
        this.add(kDLabelContainer7, null);
        kDLabelContainer6.setBounds(new Rectangle(10, 360, 200, 19));
        this.add(kDLabelContainer6, null);
        kDLabelContainer8.setBounds(new Rectangle(300, 359, 200, 19));
        this.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(40, 370, 200, 19));
        this.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(315, 367, 200, 19));
        this.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(58, 381, 200, 19));
        this.add(kDLabelContainer11, null);
        kDLabelContainer12.setBounds(new Rectangle(324, 381, 200, 19));
        this.add(kDLabelContainer12, null);
        contSellState.setBounds(new Rectangle(296, 15, 200, 19));
        this.add(contSellState, null);
        kDLabelContainer14.setBounds(new Rectangle(205, 362, 200, 19));
        this.add(kDLabelContainer14, null);
        //kDPanel1
        kDPanel1.setLayout(null);        jrbSellState.setBounds(new Rectangle(64, 70, 80, 19));
        kDPanel1.add(jrbSellState, null);
        jrbBuildingProperty.setBounds(new Rectangle(213, 17, 84, 19));
        kDPanel1.add(jrbBuildingProperty, null);
        jrbRoomModel.setBounds(new Rectangle(362, 17, 57, 19));
        kDPanel1.add(jrbRoomModel, null);
        jrbOutLook.setBounds(new Rectangle(64, 43, 49, 19));
        kDPanel1.add(jrbOutLook, null);
        jrbFace.setBounds(new Rectangle(213, 43, 49, 19));
        kDPanel1.add(jrbFace, null);
        jrbFloor.setBounds(new Rectangle(362, 43, 49, 19));
        kDPanel1.add(jrbFloor, null);
        jrbRoomModelType.setBounds(new Rectangle(64, 17, 79, 19));
        kDPanel1.add(jrbRoomModelType, null);
        jrbRoomForm.setBounds(new Rectangle(213, 70, 87, 19));
        kDPanel1.add(jrbRoomForm, null);
        //kDPanel2
        kDPanel2.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(22, 20, 186, 19));
        kDPanel2.add(kDLabelContainer1, null);
        orderDateFrom.setBounds(new Rectangle(73, 20, 170, 19));
        kDPanel2.add(orderDateFrom, null);
        kDLabelContainer2.setBounds(new Rectangle(260, 20, 200, 19));
        kDPanel2.add(kDLabelContainer2, null);
        orderDateTo.setBounds(new Rectangle(311, 20, 170, 19));
        kDPanel2.add(orderDateTo, null);
        //kDPanel3
        kDPanel3.setLayout(null);        roomArea.setBounds(new Rectangle(102, 11, 140, 19));
        kDPanel3.add(roomArea, null);
        buildArea.setBounds(new Rectangle(271, 11, 140, 19));
        kDPanel3.add(buildArea, null);
        kDLabelContainer3.setBounds(new Rectangle(19, 36, 162, 19));
        kDPanel3.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(261, 37, 49, 19));
        kDPanel3.add(kDLabelContainer4, null);
        roomAreaTo.setBounds(new Rectangle(311, 37, 170, 19));
        kDPanel3.add(roomAreaTo, null);
        rommAreaFrom.setBounds(new Rectangle(70, 36, 170, 19));
        kDPanel3.add(rommAreaFrom, null);
        //kDPanel4
        kDPanel4.setLayout(null);        buildPrice.setBounds(new Rectangle(51, 18, 70, 19));
        kDPanel4.add(buildPrice, null);
        dealTotalAmount.setBounds(new Rectangle(403, 16, 71, 19));
        kDPanel4.add(dealTotalAmount, null);
        kDLabelContainer5.setBounds(new Rectangle(22, 43, 90, 19));
        kDPanel4.add(kDLabelContainer5, null);
        priceToLabel.setBounds(new Rectangle(263, 43, 77, 19));
        kDPanel4.add(priceToLabel, null);
        priceFrom.setBounds(new Rectangle(73, 43, 170, 19));
        kDPanel4.add(priceFrom, null);
        priceTo.setBounds(new Rectangle(314, 43, 170, 19));
        kDPanel4.add(priceTo, null);
        roomPrice.setBounds(new Rectangle(137, 17, 68, 19));
        kDPanel4.add(roomPrice, null);
        dealPrice.setBounds(new Rectangle(227, 17, 70, 19));
        kDPanel4.add(dealPrice, null);
        standardTotalAmount.setBounds(new Rectangle(317, 16, 74, 19));
        kDPanel4.add(standardTotalAmount, null);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(f7ModelType);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(f7BuildingProperty);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(f7RoomModel);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(f7EyeSight);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(f7Direction);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(f7BuildingFloor);
        //contSellState
        contSellState.setBoundEditor(comboSellState);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(f7roomForm);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RptRoomFilterUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output jrbSellState_actionPerformed method
     */
    protected void jrbSellState_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output buildPrice_actionPerformed method
     */
    protected void buildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output rommAreaFrom_actionPerformed method
     */
    protected void rommAreaFrom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7Building_willCommit method
     */
    protected void f7Building_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
        EntityViewInfo entityViewInfo = f7Building.getEntityViewInfo();
	if(entityViewInfo==null)
		entityViewInfo = new EntityViewInfo();
	
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("floorCount",new Integer(3).toString());
		
		entityViewInfo.setFilter(filterInfo);
		f7Building.setEntityViewInfo(entityViewInfo);
    }

    /**
     * output f7Building_willShow method
     */
    protected void f7Building_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
        EntityViewInfo entityViewInfo = f7Building.getEntityViewInfo();
	if(entityViewInfo==null)
		entityViewInfo = new EntityViewInfo();
	
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("floorCount",new Integer(3).toString());
		
		entityViewInfo.setFilter(filterInfo);
		f7Building.setEntityViewInfo(entityViewInfo);
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RptRoomFilterUI");
    }




}