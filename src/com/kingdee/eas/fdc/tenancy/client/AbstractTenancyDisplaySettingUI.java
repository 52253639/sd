/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyDisplaySettingUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyDisplaySettingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlTenColor;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOther;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBizFlow;
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
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboUnTenColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboKeepDownColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboContinueTenColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboWaitTenColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboEnlargeTenColor;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboNewTenColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboNoTenancyColor;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDComboColor comboSincerObliColor;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton isReceiving;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton isMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlMoney;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMoneyModel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddMoney;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelMoney;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAuditRef;
    /**
     * output class constructor
     */
    public AbstractTenancyDisplaySettingUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyDisplaySettingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlTenColor = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlOther = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlBizFlow = new com.kingdee.bos.ctrl.swing.KDPanel();
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
        this.comboUnTenColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboKeepDownColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboContinueTenColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboWaitTenColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboEnlargeTenColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.comboNewTenColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboNoTenancyColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comboSincerObliColor = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.isReceiving = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.isMoneyDefine = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.pnlMoney = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblMoneyModel = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddMoney = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelMoney = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsAuditRef = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.pnlMain.setName("pnlMain");
        this.pnlRoom.setName("pnlRoom");
        this.pnlTenColor.setName("pnlTenColor");
        this.pnlOther.setName("pnlOther");
        this.pnlBizFlow.setName("pnlBizFlow");
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
        this.comboUnTenColor.setName("comboUnTenColor");
        this.kDLabel7.setName("kDLabel7");
        this.comboKeepDownColor.setName("comboKeepDownColor");
        this.comboContinueTenColor.setName("comboContinueTenColor");
        this.comboWaitTenColor.setName("comboWaitTenColor");
        this.comboEnlargeTenColor.setName("comboEnlargeTenColor");
        this.comboNewTenColor.setName("comboNewTenColor");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel8.setName("kDLabel8");
        this.comboNoTenancyColor.setName("comboNoTenancyColor");
        this.kDLabel9.setName("kDLabel9");
        this.comboSincerObliColor.setName("comboSincerObliColor");
        this.isReceiving.setName("isReceiving");
        this.isMoneyDefine.setName("isMoneyDefine");
        this.pnlMoney.setName("pnlMoney");
        this.tblMoneyModel.setName("tblMoneyModel");
        this.btnAddMoney.setName("btnAddMoney");
        this.btnDelMoney.setName("btnDelMoney");
        this.chkIsAuditRef.setName("chkIsAuditRef");
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
        // pnlMain
        // pnlRoom
        // pnlTenColor
        // pnlOther		
        this.pnlOther.setBorder(null);
        // pnlBizFlow		
        this.pnlBizFlow.setBorder(BorderFactory.createLineBorder(Color.black));
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
        // comboUnTenColor
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // comboKeepDownColor
        // comboContinueTenColor
        // comboWaitTenColor
        // comboEnlargeTenColor
        // comboNewTenColor
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
        // comboNoTenancyColor
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // comboSincerObliColor
        // isReceiving		
        this.isReceiving.setText(resHelper.getString("isReceiving.text"));		
        this.isReceiving.setSelected(true);
        // isMoneyDefine		
        this.isMoneyDefine.setText(resHelper.getString("isMoneyDefine.text"));
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.isReceiving);
        this.kDButtonGroup1.add(this.isMoneyDefine);
        // pnlMoney		
        this.pnlMoney.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlMoney.border.title")));
        // tblMoneyModel
		String tblMoneyModelStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"printPath\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{printPath}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMoneyModel.setFormatXml(resHelper.translateString("tblMoneyModel",tblMoneyModelStrXML));

        

        // btnAddMoney		
        this.btnAddMoney.setText(resHelper.getString("btnAddMoney.text"));
        this.btnAddMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddMoney_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelMoney		
        this.btnDelMoney.setText(resHelper.getString("btnDelMoney.text"));
        this.btnDelMoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelMoney_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsAuditRef		
        this.chkIsAuditRef.setText(resHelper.getString("chkIsAuditRef.text"));
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
        btnYes.setBounds(new Rectangle(442, 368, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(442, 368, 73, 21, 0));
        btnNo.setBounds(new Rectangle(548, 368, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(548, 368, 73, 21, 0));
        pnlMain.setBounds(new Rectangle(6, 7, 638, 353));
        this.add(pnlMain, new KDLayout.Constraints(6, 7, 638, 353, 0));
        //pnlMain
        pnlMain.add(pnlRoom, resHelper.getString("pnlRoom.constraints"));
        pnlMain.add(pnlTenColor, resHelper.getString("pnlTenColor.constraints"));
        pnlMain.add(pnlOther, resHelper.getString("pnlOther.constraints"));
        pnlMain.add(pnlBizFlow, resHelper.getString("pnlBizFlow.constraints"));
        //pnlRoom
        pnlRoom.setLayout(null);        contRoomHeight.setBounds(new Rectangle(13, 17, 270, 19));
        pnlRoom.add(contRoomHeight, null);
        contRoomWidth.setBounds(new Rectangle(333, 16, 270, 19));
        pnlRoom.add(contRoomWidth, null);
        contRoomField.setBounds(new Rectangle(13, 57, 270, 19));
        pnlRoom.add(contRoomField, null);
        contAttachDis.setBounds(new Rectangle(333, 55, 270, 19));
        pnlRoom.add(contAttachDis, null);
        kDLabel1.setBounds(new Rectangle(13, 129, 62, 19));
        pnlRoom.add(kDLabel1, null);
        comboFrontColor.setBounds(new Rectangle(111, 129, 171, 19));
        pnlRoom.add(comboFrontColor, null);
        labelFont.setBounds(new Rectangle(13, 96, 84, 19));
        pnlRoom.add(labelFont, null);
        //contRoomHeight
        contRoomHeight.setBoundEditor(txtRoomHeight);
        //contRoomWidth
        contRoomWidth.setBoundEditor(txtRoomWidth);
        //contRoomField
        contRoomField.setBoundEditor(comboRoomField);
        //contAttachDis
        contAttachDis.setBoundEditor(comboAttachDis);
        //pnlTenColor
        pnlTenColor.setLayout(new KDLayout());
        pnlTenColor.putClientProperty("OriginalBounds", new Rectangle(0, 0, 637, 320));        comboUnTenColor.setBounds(new Rectangle(93, 16, 170, 19));
        pnlTenColor.add(comboUnTenColor, new KDLayout.Constraints(93, 16, 170, 19, 0));
        kDLabel7.setBounds(new Rectangle(19, 16, 71, 19));
        pnlTenColor.add(kDLabel7, new KDLayout.Constraints(19, 16, 71, 19, 0));
        comboKeepDownColor.setBounds(new Rectangle(362, 80, 170, 19));
        pnlTenColor.add(comboKeepDownColor, new KDLayout.Constraints(362, 80, 170, 19, 0));
        comboContinueTenColor.setBounds(new Rectangle(362, 48, 170, 19));
        pnlTenColor.add(comboContinueTenColor, new KDLayout.Constraints(362, 48, 170, 19, 0));
        comboWaitTenColor.setBounds(new Rectangle(362, 16, 170, 19));
        pnlTenColor.add(comboWaitTenColor, new KDLayout.Constraints(362, 16, 170, 19, 0));
        comboEnlargeTenColor.setBounds(new Rectangle(93, 80, 170, 19));
        pnlTenColor.add(comboEnlargeTenColor, new KDLayout.Constraints(93, 80, 170, 19, 0));
        comboNewTenColor.setBounds(new Rectangle(93, 47, 170, 19));
        pnlTenColor.add(comboNewTenColor, new KDLayout.Constraints(93, 47, 170, 19, 0));
        kDLabel6.setBounds(new Rectangle(287, 80, 62, 19));
        pnlTenColor.add(kDLabel6, new KDLayout.Constraints(287, 80, 62, 19, 0));
        kDLabel5.setBounds(new Rectangle(287, 48, 62, 19));
        pnlTenColor.add(kDLabel5, new KDLayout.Constraints(287, 48, 62, 19, 0));
        kDLabel4.setBounds(new Rectangle(287, 16, 61, 19));
        pnlTenColor.add(kDLabel4, new KDLayout.Constraints(287, 16, 61, 19, 0));
        kDLabel3.setBounds(new Rectangle(19, 79, 60, 19));
        pnlTenColor.add(kDLabel3, new KDLayout.Constraints(19, 79, 60, 19, 0));
        kDLabel2.setBounds(new Rectangle(19, 47, 61, 19));
        pnlTenColor.add(kDLabel2, new KDLayout.Constraints(19, 47, 61, 19, 0));
        kDLabel8.setBounds(new Rectangle(18, 111, 68, 19));
        pnlTenColor.add(kDLabel8, new KDLayout.Constraints(18, 111, 68, 19, 0));
        comboNoTenancyColor.setBounds(new Rectangle(93, 110, 170, 19));
        pnlTenColor.add(comboNoTenancyColor, new KDLayout.Constraints(93, 110, 170, 19, 0));
        kDLabel9.setBounds(new Rectangle(287, 109, 59, 19));
        pnlTenColor.add(kDLabel9, new KDLayout.Constraints(287, 109, 59, 19, 0));
        comboSincerObliColor.setBounds(new Rectangle(363, 110, 170, 19));
        pnlTenColor.add(comboSincerObliColor, new KDLayout.Constraints(363, 110, 170, 19, 0));
        //pnlOther
        pnlOther.setLayout(null);        isReceiving.setBounds(new Rectangle(9, 13, 109, 19));
        pnlOther.add(isReceiving, null);
        isMoneyDefine.setBounds(new Rectangle(9, 44, 127, 19));
        pnlOther.add(isMoneyDefine, null);
        pnlMoney.setBounds(new Rectangle(11, 68, 606, 177));
        pnlOther.add(pnlMoney, null);
        //pnlMoney
        pnlMoney.setLayout(null);        tblMoneyModel.setBounds(new Rectangle(12, 36, 582, 136));
        pnlMoney.add(tblMoneyModel, null);
        btnAddMoney.setBounds(new Rectangle(465, 13, 65, 19));
        pnlMoney.add(btnAddMoney, null);
        btnDelMoney.setBounds(new Rectangle(534, 13, 65, 19));
        pnlMoney.add(btnDelMoney, null);
        //pnlBizFlow
        pnlBizFlow.setLayout(null);        chkIsAuditRef.setBounds(new Rectangle(11, 19, 234, 19));
        pnlBizFlow.add(chkIsAuditRef, null);

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
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyDisplaySettingUIHandler";
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
     * output btnAddMoney_actionPerformed method
     */
    protected void btnAddMoney_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelMoney_actionPerformed method
     */
    protected void btnDelMoney_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyDisplaySettingUI");
    }




}