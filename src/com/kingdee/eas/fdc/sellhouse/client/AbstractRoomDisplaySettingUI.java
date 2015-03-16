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
public abstract class AbstractRoomDisplaySettingUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomDisplaySettingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomWidth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomField;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachDis;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboFrontColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelFont;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomWidth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRoomField;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAttachDis;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboInitColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboKeepDownColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboPurColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboOnShowColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboSignColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboPrePurColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboNoSellColor;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboSincerPurColor;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeRoomMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeBalanceObject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerMoneyType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isQuitUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isChangeUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox idChangeRoomUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isChangeObjectUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSincerUpdate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7QuitMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ChangeMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ChangeRoomMoney;
    protected com.kingdee.bos.ctrl.swing.KDComboBox f7ChangeBalanceObject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SincerMoneyType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isPreToOtherMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboSinReColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel11;
    /**
     * output class constructor
     */
    public AbstractRoomDisplaySettingUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomDisplaySettingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contRoomHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomWidth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomField = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachDis = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboFrontColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.labelFont = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtRoomHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomWidth = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboRoomField = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboAttachDis = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboInitColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboKeepDownColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboPurColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboOnShowColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboSignColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboPrePurColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboNoSellColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.cbIsAuditDate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboSincerPurColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contQuitMoneyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeMoneyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeRoomMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeBalanceObject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerMoneyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isQuitUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isChangeUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.idChangeRoomUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isChangeObjectUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isSincerUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.f7QuitMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeRoomMoney = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeBalanceObject = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7SincerMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.isPreToOtherMoney = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboSinReColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel11 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel4.setName("kDPanel4");
        this.contRoomHeight.setName("contRoomHeight");
        this.contRoomWidth.setName("contRoomWidth");
        this.contRoomField.setName("contRoomField");
        this.contAttachDis.setName("contAttachDis");
        this.kDLabel1.setName("kDLabel1");
        this.comboFrontColor.setName("comboFrontColor");
        this.labelFont.setName("labelFont");
        this.txtRoomHeight.setName("txtRoomHeight");
        this.txtRoomWidth.setName("txtRoomWidth");
        this.comboRoomField.setName("comboRoomField");
        this.comboAttachDis.setName("comboAttachDis");
        this.comboInitColor.setName("comboInitColor");
        this.kDLabel7.setName("kDLabel7");
        this.comboKeepDownColor.setName("comboKeepDownColor");
        this.comboPurColor.setName("comboPurColor");
        this.comboOnShowColor.setName("comboOnShowColor");
        this.comboSignColor.setName("comboSignColor");
        this.comboPrePurColor.setName("comboPrePurColor");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel8.setName("kDLabel8");
        this.comboNoSellColor.setName("comboNoSellColor");
        this.cbIsAuditDate.setName("cbIsAuditDate");
        this.kDLabel9.setName("kDLabel9");
        this.comboSincerPurColor.setName("comboSincerPurColor");
        this.kDTable1.setName("kDTable1");
        this.contQuitMoneyType.setName("contQuitMoneyType");
        this.contChangeMoneyType.setName("contChangeMoneyType");
        this.contChangeRoomMoney.setName("contChangeRoomMoney");
        this.contChangeBalanceObject.setName("contChangeBalanceObject");
        this.contSincerMoneyType.setName("contSincerMoneyType");
        this.isQuitUpdate.setName("isQuitUpdate");
        this.isChangeUpdate.setName("isChangeUpdate");
        this.idChangeRoomUpdate.setName("idChangeRoomUpdate");
        this.isChangeObjectUpdate.setName("isChangeObjectUpdate");
        this.isSincerUpdate.setName("isSincerUpdate");
        this.f7QuitMoneyType.setName("f7QuitMoneyType");
        this.f7ChangeMoneyType.setName("f7ChangeMoneyType");
        this.f7ChangeRoomMoney.setName("f7ChangeRoomMoney");
        this.f7ChangeBalanceObject.setName("f7ChangeBalanceObject");
        this.f7SincerMoneyType.setName("f7SincerMoneyType");
        this.kDTable2.setName("kDTable2");
        this.kDTable3.setName("kDTable3");
        this.isPreToOtherMoney.setName("isPreToOtherMoney");
        this.kDLabel10.setName("kDLabel10");
        this.comboSinReColor.setName("comboSinReColor");
        this.kDLabel11.setName("kDLabel11");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,400));
        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTabbedPane1
        // kDPanel1
        // kDPanel3
        // kDPanel5
        // kDPanel2
        // kDPanel6
        // kDPanel4
        // contRoomHeight		
        this.contRoomHeight.setBoundLabelText(resHelper.getString("contRoomHeight.boundLabelText"));		
        this.contRoomHeight.setBoundLabelLength(100);		
        this.contRoomHeight.setBoundLabelUnderline(true);
        // contRoomWidth		
        this.contRoomWidth.setBoundLabelText(resHelper.getString("contRoomWidth.boundLabelText"));		
        this.contRoomWidth.setBoundLabelLength(100);		
        this.contRoomWidth.setBoundLabelUnderline(true);
        // contRoomField		
        this.contRoomField.setBoundLabelText(resHelper.getString("contRoomField.boundLabelText"));		
        this.contRoomField.setBoundLabelLength(100);		
        this.contRoomField.setBoundLabelUnderline(true);
        // contAttachDis		
        this.contAttachDis.setBoundLabelText(resHelper.getString("contAttachDis.boundLabelText"));		
        this.contAttachDis.setBoundLabelLength(100);		
        this.contAttachDis.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // comboFrontColor
        // labelFont		
        this.labelFont.setText(resHelper.getString("labelFont.text"));
        // txtRoomHeight
        // txtRoomWidth
        // comboRoomField
        // comboAttachDis
        // comboInitColor
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // comboKeepDownColor
        // comboPurColor
        // comboOnShowColor
        // comboSignColor
        // comboPrePurColor
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // comboNoSellColor
        // cbIsAuditDate		
        this.cbIsAuditDate.setText(resHelper.getString("cbIsAuditDate.text"));
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // comboSincerPurColor
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actGathering\" t:width=\"170\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"signGathering\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"isHouseMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"isSincerPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isSincerSellOrder\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"isEditPurAndSignDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isAdjustPrices\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"isLoanReceiving\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"basePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"mortagage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{actGathering}</t:Cell><t:Cell>$Resource{signGathering}</t:Cell><t:Cell>$Resource{isHouseMoney}</t:Cell><t:Cell>$Resource{isSincerPrice}</t:Cell><t:Cell>$Resource{isSincerSellOrder}</t:Cell><t:Cell>$Resource{isEditPurAndSignDate}</t:Cell><t:Cell>$Resource{isAdjustPrices}</t:Cell><t:Cell>$Resource{isLoanReceiving}</t:Cell><t:Cell>$Resource{basePrice}</t:Cell><t:Cell>$Resource{mortagage}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kDTable1_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contQuitMoneyType		
        this.contQuitMoneyType.setBoundLabelText(resHelper.getString("contQuitMoneyType.boundLabelText"));		
        this.contQuitMoneyType.setBoundLabelLength(100);		
        this.contQuitMoneyType.setBoundLabelUnderline(true);
        // contChangeMoneyType		
        this.contChangeMoneyType.setBoundLabelText(resHelper.getString("contChangeMoneyType.boundLabelText"));		
        this.contChangeMoneyType.setBoundLabelUnderline(true);		
        this.contChangeMoneyType.setBoundLabelLength(100);
        // contChangeRoomMoney		
        this.contChangeRoomMoney.setBoundLabelText(resHelper.getString("contChangeRoomMoney.boundLabelText"));		
        this.contChangeRoomMoney.setBoundLabelLength(100);		
        this.contChangeRoomMoney.setBoundLabelUnderline(true);
        // contChangeBalanceObject		
        this.contChangeBalanceObject.setBoundLabelText(resHelper.getString("contChangeBalanceObject.boundLabelText"));		
        this.contChangeBalanceObject.setBoundLabelLength(100);		
        this.contChangeBalanceObject.setBoundLabelUnderline(true);
        // contSincerMoneyType		
        this.contSincerMoneyType.setBoundLabelText(resHelper.getString("contSincerMoneyType.boundLabelText"));		
        this.contSincerMoneyType.setBoundLabelLength(100);		
        this.contSincerMoneyType.setBoundLabelUnderline(true);
        // isQuitUpdate		
        this.isQuitUpdate.setText(resHelper.getString("isQuitUpdate.text"));
        this.isQuitUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isQuitUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isChangeUpdate		
        this.isChangeUpdate.setText(resHelper.getString("isChangeUpdate.text"));
        this.isChangeUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isChangeUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // idChangeRoomUpdate		
        this.idChangeRoomUpdate.setText(resHelper.getString("idChangeRoomUpdate.text"));
        this.idChangeRoomUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    idChangeRoomUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isChangeObjectUpdate		
        this.isChangeObjectUpdate.setText(resHelper.getString("isChangeObjectUpdate.text"));
        this.isChangeObjectUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isChangeObjectUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isSincerUpdate		
        this.isSincerUpdate.setText(resHelper.getString("isSincerUpdate.text"));
        this.isSincerUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isSincerUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7QuitMoneyType		
        this.f7QuitMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7QuitMoneyType.setDisplayFormat("$name$");		
        this.f7QuitMoneyType.setEditFormat("$number$");		
        this.f7QuitMoneyType.setCommitFormat("$number$");
        // f7ChangeMoneyType		
        this.f7ChangeMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7ChangeMoneyType.setEditFormat("$number$");		
        this.f7ChangeMoneyType.setDisplayFormat("$name$");		
        this.f7ChangeMoneyType.setCommitFormat("$number$");
        // f7ChangeRoomMoney		
        this.f7ChangeRoomMoney.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7ChangeRoomMoney.setCommitFormat("$number$");		
        this.f7ChangeRoomMoney.setEditFormat("$number$");		
        this.f7ChangeRoomMoney.setDisplayFormat("$name$");
        // f7ChangeBalanceObject		
        this.f7ChangeBalanceObject.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum").toArray());
        // f7SincerMoneyType		
        this.f7SincerMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7SincerMoneyType.setDisplayFormat("$name$");		
        this.f7SincerMoneyType.setEditFormat("$number$");		
        this.f7SincerMoneyType.setCommitFormat("$number$");
        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"preLevelAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"isLevelModify\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"preStandAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"isStandModify\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{preLevelAmount}</t:Cell><t:Cell>$Resource{isLevelModify}</t:Cell><t:Cell>$Resource{preStandAmount}</t:Cell><t:Cell>$Resource{isStandModify}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));

        

        // kDTable3
		String kDTable3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"faithAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{faithAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable3.setFormatXml(resHelper.translateString("kDTable3",kDTable3StrXML));

        

        // isPreToOtherMoney		
        this.isPreToOtherMoney.setText(resHelper.getString("isPreToOtherMoney.text"));		
        this.isPreToOtherMoney.setSelected(true);
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
        // comboSinReColor
        // kDLabel11		
        this.kDLabel11.setText(resHelper.getString("kDLabel11.text"));		
        this.kDLabel11.setForeground(new java.awt.Color(255,0,0));
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
        this.setBounds(new Rectangle(10, 10, 650, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 400));
        btnYes.setBounds(new Rectangle(442, 369, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(442, 369, 73, 21, 0));
        btnNo.setBounds(new Rectangle(548, 368, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(548, 368, 73, 21, 0));
        kDTabbedPane1.setBounds(new Rectangle(6, 7, 638, 353));
        this.add(kDTabbedPane1, new KDLayout.Constraints(6, 7, 638, 353, 0));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        //kDPanel1
        kDPanel1.setLayout(null);        contRoomHeight.setBounds(new Rectangle(13, 17, 270, 19));
        kDPanel1.add(contRoomHeight, null);
        contRoomWidth.setBounds(new Rectangle(333, 16, 270, 19));
        kDPanel1.add(contRoomWidth, null);
        contRoomField.setBounds(new Rectangle(13, 57, 270, 19));
        kDPanel1.add(contRoomField, null);
        contAttachDis.setBounds(new Rectangle(333, 55, 270, 19));
        kDPanel1.add(contAttachDis, null);
        kDLabel1.setBounds(new Rectangle(13, 129, 62, 19));
        kDPanel1.add(kDLabel1, null);
        comboFrontColor.setBounds(new Rectangle(111, 129, 171, 19));
        kDPanel1.add(comboFrontColor, null);
        labelFont.setBounds(new Rectangle(13, 96, 84, 19));
        kDPanel1.add(labelFont, null);
        //contRoomHeight
        contRoomHeight.setBoundEditor(txtRoomHeight);
        //contRoomWidth
        contRoomWidth.setBoundEditor(txtRoomWidth);
        //contRoomField
        contRoomField.setBoundEditor(comboRoomField);
        //contAttachDis
        contAttachDis.setBoundEditor(comboAttachDis);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 637, 320));        comboInitColor.setBounds(new Rectangle(90, 16, 170, 19));
        kDPanel3.add(comboInitColor, new KDLayout.Constraints(90, 16, 170, 19, 0));
        kDLabel7.setBounds(new Rectangle(19, 16, 62, 19));
        kDPanel3.add(kDLabel7, new KDLayout.Constraints(19, 16, 62, 19, 0));
        comboKeepDownColor.setBounds(new Rectangle(403, 78, 170, 19));
        kDPanel3.add(comboKeepDownColor, new KDLayout.Constraints(403, 78, 170, 19, 0));
        comboPurColor.setBounds(new Rectangle(402, 46, 170, 19));
        kDPanel3.add(comboPurColor, new KDLayout.Constraints(402, 46, 170, 19, 0));
        comboOnShowColor.setBounds(new Rectangle(401, 15, 170, 19));
        kDPanel3.add(comboOnShowColor, new KDLayout.Constraints(401, 15, 170, 19, 0));
        comboSignColor.setBounds(new Rectangle(90, 80, 170, 19));
        kDPanel3.add(comboSignColor, new KDLayout.Constraints(90, 80, 170, 19, 0));
        comboPrePurColor.setBounds(new Rectangle(90, 47, 170, 19));
        kDPanel3.add(comboPrePurColor, new KDLayout.Constraints(90, 47, 170, 19, 0));
        kDLabel6.setBounds(new Rectangle(316, 80, 62, 19));
        kDPanel3.add(kDLabel6, new KDLayout.Constraints(316, 80, 62, 19, 0));
        kDLabel5.setBounds(new Rectangle(315, 48, 62, 19));
        kDPanel3.add(kDLabel5, new KDLayout.Constraints(315, 48, 62, 19, 0));
        kDLabel4.setBounds(new Rectangle(313, 16, 61, 19));
        kDPanel3.add(kDLabel4, new KDLayout.Constraints(313, 16, 61, 19, 0));
        kDLabel3.setBounds(new Rectangle(19, 79, 62, 19));
        kDPanel3.add(kDLabel3, new KDLayout.Constraints(19, 79, 62, 19, 0));
        kDLabel2.setBounds(new Rectangle(19, 47, 62, 19));
        kDPanel3.add(kDLabel2, new KDLayout.Constraints(19, 47, 62, 19, 0));
        kDLabel8.setBounds(new Rectangle(11, 111, 76, 19));
        kDPanel3.add(kDLabel8, new KDLayout.Constraints(11, 111, 76, 19, 0));
        comboNoSellColor.setBounds(new Rectangle(90, 109, 170, 19));
        kDPanel3.add(comboNoSellColor, new KDLayout.Constraints(90, 109, 170, 19, 0));
        cbIsAuditDate.setBounds(new Rectangle(17, 194, 213, 19));
        kDPanel3.add(cbIsAuditDate, new KDLayout.Constraints(17, 194, 213, 19, 0));
        kDLabel9.setBounds(new Rectangle(313, 111, 86, 19));
        kDPanel3.add(kDLabel9, new KDLayout.Constraints(313, 111, 86, 19, 0));
        comboSincerPurColor.setBounds(new Rectangle(404, 109, 170, 19));
        kDPanel3.add(comboSincerPurColor, new KDLayout.Constraints(404, 109, 170, 19, 0));
        isPreToOtherMoney.setBounds(new Rectangle(17, 219, 332, 20));
        kDPanel3.add(isPreToOtherMoney, new KDLayout.Constraints(17, 219, 332, 20, 0));
        kDLabel10.setBounds(new Rectangle(8, 139, 80, 19));
        kDPanel3.add(kDLabel10, new KDLayout.Constraints(8, 139, 80, 19, 0));
        comboSinReColor.setBounds(new Rectangle(90, 139, 170, 19));
        kDPanel3.add(comboSinReColor, new KDLayout.Constraints(90, 139, 170, 19, 0));
        kDLabel11.setBounds(new Rectangle(17, 166, 568, 19));
        kDPanel3.add(kDLabel11, new KDLayout.Constraints(17, 166, 568, 19, 0));
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 637, 320));        kDTable1.setBounds(new Rectangle(3, 4, 628, 314));
        kDPanel5.add(kDTable1, new KDLayout.Constraints(3, 4, 628, 314, 0));
        //kDPanel2
        kDPanel2.setLayout(null);        contQuitMoneyType.setBounds(new Rectangle(19, 40, 270, 19));
        kDPanel2.add(contQuitMoneyType, null);
        contChangeMoneyType.setBounds(new Rectangle(19, 88, 270, 19));
        kDPanel2.add(contChangeMoneyType, null);
        contChangeRoomMoney.setBounds(new Rectangle(19, 141, 270, 19));
        kDPanel2.add(contChangeRoomMoney, null);
        contChangeBalanceObject.setBounds(new Rectangle(18, 193, 270, 19));
        kDPanel2.add(contChangeBalanceObject, null);
        contSincerMoneyType.setBounds(new Rectangle(19, 245, 270, 19));
        kDPanel2.add(contSincerMoneyType, null);
        isQuitUpdate.setBounds(new Rectangle(378, 40, 110, 19));
        kDPanel2.add(isQuitUpdate, null);
        isChangeUpdate.setBounds(new Rectangle(378, 87, 110, 19));
        kDPanel2.add(isChangeUpdate, null);
        idChangeRoomUpdate.setBounds(new Rectangle(378, 140, 110, 19));
        kDPanel2.add(idChangeRoomUpdate, null);
        isChangeObjectUpdate.setBounds(new Rectangle(378, 192, 110, 19));
        kDPanel2.add(isChangeObjectUpdate, null);
        isSincerUpdate.setBounds(new Rectangle(378, 245, 110, 19));
        kDPanel2.add(isSincerUpdate, null);
        //contQuitMoneyType
        contQuitMoneyType.setBoundEditor(f7QuitMoneyType);
        //contChangeMoneyType
        contChangeMoneyType.setBoundEditor(f7ChangeMoneyType);
        //contChangeRoomMoney
        contChangeRoomMoney.setBoundEditor(f7ChangeRoomMoney);
        //contChangeBalanceObject
        contChangeBalanceObject.setBoundEditor(f7ChangeBalanceObject);
        //contSincerMoneyType
        contSincerMoneyType.setBoundEditor(f7SincerMoneyType);
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(0, 0, 637, 320));        kDTable2.setBounds(new Rectangle(3, 4, 629, 315));
        kDPanel6.add(kDTable2, new KDLayout.Constraints(3, 4, 629, 315, 0));
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 637, 320));        kDTable3.setBounds(new Rectangle(0, 1, 629, 318));
        kDPanel4.add(kDTable3, new KDLayout.Constraints(0, 1, 629, 318, 0));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomDisplaySettingUIHandler";
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
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kDTable1_activeCellChanged method
     */
    protected void kDTable1_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output isQuitUpdate_stateChanged method
     */
    protected void isQuitUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isChangeUpdate_stateChanged method
     */
    protected void isChangeUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output idChangeRoomUpdate_stateChanged method
     */
    protected void idChangeRoomUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isChangeObjectUpdate_stateChanged method
     */
    protected void isChangeObjectUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isSincerUpdate_stateChanged method
     */
    protected void isSincerUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomDisplaySettingUI");
    }




}