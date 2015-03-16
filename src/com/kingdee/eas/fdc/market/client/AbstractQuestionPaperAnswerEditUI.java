/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractQuestionPaperAnswerEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuestionPaperAnswerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer titleSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continputDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquestionPaper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continsiderCard;
    protected com.kingdee.bos.ctrl.swing.KDPanel docRootPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpersion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinputDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtquestionPaper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Governor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSex;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIdNo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Purchase;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomStyle;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtZip;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtStandardPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddCustomer;
    protected com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo editData = null;
    protected ActionAddCustomer actionAddCustomer = null;
    /**
     * output class constructor
     */
    public AbstractQuestionPaperAnswerEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractQuestionPaperAnswerEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddCustomer
        this.actionAddCustomer = new ActionAddCustomer(this);
        getActionManager().registerAction("actionAddCustomer", actionAddCustomer);
         this.actionAddCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.titleSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continputDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contquestionPaper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continsiderCard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.docRootPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtpersionNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conCommerceChance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpersion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkinputDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtquestionPaper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.f7Governor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSex = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtIdNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Purchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomStyle = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtZip = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPayType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCommerceChance = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAddCustomer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contBizDate.setName("contBizDate");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.contpersion.setName("contpersion");
        this.titleSellProject.setName("titleSellProject");
        this.continputDate.setName("continputDate");
        this.contquestionPaper.setName("contquestionPaper");
        this.continsiderCard.setName("continsiderCard");
        this.docRootPanel.setName("docRootPanel");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.txtpersionNumber.setName("txtpersionNumber");
        this.conCommerceChance.setName("conCommerceChance");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtCompany.setName("txtCompany");
        this.dateCreateTime.setName("dateCreateTime");
        this.txtDescription.setName("txtDescription");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.prmtpersion.setName("prmtpersion");
        this.f7SellProject.setName("f7SellProject");
        this.pkinputDate.setName("pkinputDate");
        this.txtquestionPaper.setName("txtquestionPaper");
        this.f7Customer.setName("f7Customer");
        this.kdtEntrys.setName("kdtEntrys");
        this.f7Governor.setName("f7Governor");
        this.txtPhone.setName("txtPhone");
        this.txtSex.setName("txtSex");
        this.txtIdNo.setName("txtIdNo");
        this.f7Purchase.setName("f7Purchase");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.txtRoomStyle.setName("txtRoomStyle");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.txtAddress.setName("txtAddress");
        this.txtZip.setName("txtZip");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtBuildingPrice.setName("txtBuildingPrice");
        this.txtStandardPrice.setName("txtStandardPrice");
        this.txtPayType.setName("txtPayType");
        this.prmtCommerceChance.setName("prmtCommerceChance");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.menuItemAddCustomer.setName("menuItemAddCustomer");
        // CoreUI
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setVisible(true);		
        this.kDLabelContainer6.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);		
        this.kDLabelContainer7.setVisible(true);
        // contpersion		
        this.contpersion.setBoundLabelText(resHelper.getString("contpersion.boundLabelText"));		
        this.contpersion.setBoundLabelLength(100);		
        this.contpersion.setBoundLabelUnderline(true);		
        this.contpersion.setVisible(true);		
        this.contpersion.setBoundLabelAlignment(7);		
        this.contpersion.setForeground(new java.awt.Color(0,0,0));
        // titleSellProject		
        this.titleSellProject.setBoundLabelText(resHelper.getString("titleSellProject.boundLabelText"));		
        this.titleSellProject.setBoundLabelLength(100);		
        this.titleSellProject.setBoundLabelUnderline(true);		
        this.titleSellProject.setBoundLabelAlignment(7);		
        this.titleSellProject.setForeground(new java.awt.Color(0,0,0));
        // continputDate		
        this.continputDate.setBoundLabelText(resHelper.getString("continputDate.boundLabelText"));		
        this.continputDate.setBoundLabelLength(100);		
        this.continputDate.setBoundLabelUnderline(true);		
        this.continputDate.setVisible(true);		
        this.continputDate.setBoundLabelAlignment(7);		
        this.continputDate.setForeground(new java.awt.Color(0,0,0));
        // contquestionPaper		
        this.contquestionPaper.setBoundLabelText(resHelper.getString("contquestionPaper.boundLabelText"));		
        this.contquestionPaper.setBoundLabelLength(100);		
        this.contquestionPaper.setBoundLabelUnderline(true);		
        this.contquestionPaper.setVisible(true);		
        this.contquestionPaper.setBoundLabelAlignment(7);		
        this.contquestionPaper.setForeground(new java.awt.Color(0,0,0));
        // continsiderCard		
        this.continsiderCard.setBoundLabelText(resHelper.getString("continsiderCard.boundLabelText"));		
        this.continsiderCard.setBoundLabelLength(100);		
        this.continsiderCard.setBoundLabelUnderline(true);		
        this.continsiderCard.setBoundLabelAlignment(7);		
        this.continsiderCard.setForeground(new java.awt.Color(0,0,0));
        // docRootPanel
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);
        // txtpersionNumber		
        this.txtpersionNumber.setVisible(false);
        // conCommerceChance		
        this.conCommerceChance.setBoundLabelText(resHelper.getString("conCommerceChance.boundLabelText"));		
        this.conCommerceChance.setBoundLabelLength(100);		
        this.conCommerceChance.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));		
        this.pkBizDate.setRequired(true);
        // txtCompany		
        this.txtCompany.setText(resHelper.getString("txtCompany.text"));		
        this.txtCompany.setEnabled(false);		
        this.txtCompany.setVisible(true);		
        this.txtCompany.setHorizontalAlignment(2);		
        this.txtCompany.setForeground(new java.awt.Color(0,0,0));		
        this.txtCompany.setRequired(false);
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);		
        this.dateCreateTime.setRequired(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);
        // prmtpersion		
        this.prmtpersion.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtpersion.setVisible(true);		
        this.prmtpersion.setEditable(true);		
        this.prmtpersion.setDisplayFormat("$name$");		
        this.prmtpersion.setEditFormat("$number$");		
        this.prmtpersion.setCommitFormat("$number$");		
        this.prmtpersion.setRequired(true);		
        this.prmtpersion.setEnabled(true);		
        this.prmtpersion.setForeground(new java.awt.Color(0,0,0));
        prmtpersion.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtpersion_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtpersion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtpersion_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellProject		
        this.f7SellProject.setDisplayFormat("$name$");		
        this.f7SellProject.setEditFormat("$number$");		
        this.f7SellProject.setCommitFormat("$number$");		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setEditable(true);
        this.f7SellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkinputDate		
        this.pkinputDate.setVisible(true);		
        this.pkinputDate.setRequired(true);		
        this.pkinputDate.setEnabled(true);		
        this.pkinputDate.setForeground(new java.awt.Color(0,0,0));
        // txtquestionPaper		
        this.txtquestionPaper.setEditFormat("$topric$");		
        this.txtquestionPaper.setDisplayFormat("$topric$");		
        this.txtquestionPaper.setQueryInfo("com.kingdee.eas.fdc.market.app.QuestionPaperDefineQuery");		
        this.txtquestionPaper.setCommitFormat("$id$");		
        this.txtquestionPaper.setRequired(true);		
        this.txtquestionPaper.setDefaultF7UIName("µ÷²éÎÊ¾í");
        this.txtquestionPaper.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtquestionPaper_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Customer		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
        this.f7Customer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Customer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>0.0000000000</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"option\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"inputStr\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"inputNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{option}</t:Cell><t:Cell>$Resource{inputStr}</t:Cell><t:Cell>$Resource{inputNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));		
        this.kdtEntrys.setVisible(false);

                this.kdtEntrys.putBindContents("editData",new String[] {"id","option","inputStr","inputNumber"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_option_TextField = new KDTextField();
        kdtEntrys_option_TextField.setName("kdtEntrys_option_TextField");
        kdtEntrys_option_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_option_CellEditor = new KDTDefaultCellEditor(kdtEntrys_option_TextField);
        this.kdtEntrys.getColumn("option").setEditor(kdtEntrys_option_CellEditor);
        KDTextField kdtEntrys_inputStr_TextField = new KDTextField();
        kdtEntrys_inputStr_TextField.setName("kdtEntrys_inputStr_TextField");
        kdtEntrys_inputStr_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_inputStr_CellEditor = new KDTDefaultCellEditor(kdtEntrys_inputStr_TextField);
        this.kdtEntrys.getColumn("inputStr").setEditor(kdtEntrys_inputStr_CellEditor);
        KDFormattedTextField kdtEntrys_inputNumber_TextField = new KDFormattedTextField();
        kdtEntrys_inputNumber_TextField.setName("kdtEntrys_inputNumber_TextField");
        kdtEntrys_inputNumber_TextField.setVisible(true);
        kdtEntrys_inputNumber_TextField.setEditable(true);
        kdtEntrys_inputNumber_TextField.setHorizontalAlignment(2);
        kdtEntrys_inputNumber_TextField.setDataType(1);
        	kdtEntrys_inputNumber_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_inputNumber_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_inputNumber_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_inputNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_inputNumber_TextField);
        this.kdtEntrys.getColumn("inputNumber").setEditor(kdtEntrys_inputNumber_CellEditor);
        // f7Governor		
        this.f7Governor.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.f7Governor.setDisplayFormat("$name$");		
        this.f7Governor.setEditFormat("$number$");		
        this.f7Governor.setCommitFormat("$number$");
        // txtPhone		
        this.txtPhone.setEnabled(false);
        // txtSex		
        this.txtSex.setEnabled(false);
        // txtIdNo		
        this.txtIdNo.setEnabled(false);
        // f7Purchase		
        this.f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQueryForMarket");		
        this.f7Purchase.setDisplayFormat("$number$");		
        this.f7Purchase.setEditFormat("$number$");		
        this.f7Purchase.setCommitFormat("$number$");
        this.f7Purchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Purchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // txtRoomStyle		
        this.txtRoomStyle.setEnabled(false);
        // kDDatePicker1		
        this.kDDatePicker1.setEnabled(false);
        // txtAddress		
        this.txtAddress.setEnabled(false);
        // txtZip		
        this.txtZip.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setEnabled(false);
        // txtBuildingPrice		
        this.txtBuildingPrice.setEnabled(false);
        // txtStandardPrice		
        this.txtStandardPrice.setEnabled(false);
        // txtPayType		
        this.txtPayType.setEnabled(false);
        // prmtCommerceChance		
        this.prmtCommerceChance.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7CommerceChanceQuery");
        this.prmtCommerceChance.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCommerceChance_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAddCustomer
        this.btnAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));		
        this.btnAddCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addmapping"));
        // menuItemAddCustomer
        this.menuItemAddCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddCustomer.setText(resHelper.getString("menuItemAddCustomer.text"));		
        this.menuItemAddCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addmapping"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtCompany,dateCreateTime,bizPromptCreator,txtDescription,pkBizDate,txtNumber,kdtEntrys,prmtpersion,pkinputDate}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(17, 12, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(17, 12, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(17, 41, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(17, 41, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(17, 572, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(17, 572, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer4.setBounds(new Rectangle(17, 531, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(17, 531, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(372, 75, 625, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(372, 75, 625, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer7.setBounds(new Rectangle(17, 504, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(17, 504, 270, 19, 0));
        contpersion.setBounds(new Rectangle(727, 44, 270, 19));
        this.add(contpersion, new KDLayout.Constraints(727, 44, 270, 19, 0));
        titleSellProject.setBounds(new Rectangle(17, 70, 270, 19));
        this.add(titleSellProject, new KDLayout.Constraints(17, 70, 270, 19, 0));
        continputDate.setBounds(new Rectangle(372, 44, 270, 19));
        this.add(continputDate, new KDLayout.Constraints(372, 44, 270, 19, 0));
        contquestionPaper.setBounds(new Rectangle(372, 11, 270, 19));
        this.add(contquestionPaper, new KDLayout.Constraints(372, 11, 270, 19, 0));
        continsiderCard.setBounds(new Rectangle(17, 99, 270, 19));
        this.add(continsiderCard, new KDLayout.Constraints(17, 99, 270, 19, 0));
        docRootPanel.setBounds(new Rectangle(299, 101, 709, 491));
        this.add(docRootPanel, new KDLayout.Constraints(299, 101, 709, 491, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(727, 9, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(727, 9, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(17, 126, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(17, 126, 270, 19, 0));
        kDLabelContainer11.setBounds(new Rectangle(17, 153, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(17, 153, 270, 19, 0));
        kDLabelContainer12.setBounds(new Rectangle(17, 180, 270, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(17, 180, 270, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(16, 288, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(16, 288, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(16, 315, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(16, 315, 270, 19, 0));
        kDLabelContainer15.setBounds(new Rectangle(17, 342, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(17, 342, 270, 19, 0));
        kDLabelContainer16.setBounds(new Rectangle(17, 369, 270, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(17, 369, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(17, 207, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(17, 207, 270, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(17, 234, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(17, 234, 270, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(17, 396, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(17, 396, 270, 19, 0));
        kDLabelContainer17.setBounds(new Rectangle(17, 423, 270, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(17, 423, 270, 19, 0));
        kDLabelContainer18.setBounds(new Rectangle(17, 450, 270, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(17, 450, 270, 19, 0));
        kDLabelContainer19.setBounds(new Rectangle(17, 477, 270, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(17, 477, 270, 19, 0));
        txtpersionNumber.setBounds(new Rectangle(270, 587, 170, 19));
        this.add(txtpersionNumber, new KDLayout.Constraints(270, 587, 170, 19, 0));
        conCommerceChance.setBounds(new Rectangle(17, 261, 270, 19));
        this.add(conCommerceChance, new KDLayout.Constraints(17, 261, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCompany);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //contpersion
        contpersion.setBoundEditor(prmtpersion);
        //titleSellProject
        titleSellProject.setBoundEditor(f7SellProject);
        //continputDate
        continputDate.setBoundEditor(pkinputDate);
        //contquestionPaper
        contquestionPaper.setBoundEditor(txtquestionPaper);
        //continsiderCard
        continsiderCard.setBoundEditor(f7Customer);
        //docRootPanel
docRootPanel.setLayout(new BorderLayout(0, 0));        docRootPanel.add(kdtEntrys, BorderLayout.SOUTH);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(f7Governor);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtPhone);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtSex);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtIdNo);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(f7Purchase);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtRoomNumber);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtRoomStyle);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(kDDatePicker1);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtAddress);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtZip);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtBuildingArea);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtBuildingPrice);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtStandardPrice);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtPayType);
        //conCommerceChance
        conCommerceChance.setBoundEditor(prmtCommerceChance);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAddCustomer);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAddCustomer);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("company.name", String.class, this.txtCompany, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("persion", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtpersion, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.f7SellProject, "data");
		dataBinder.registerBinding("inputDate", java.util.Date.class, this.pkinputDate, "value");
		dataBinder.registerBinding("questionPaper", com.kingdee.eas.fdc.market.QuestionPaperDefineInfo.class, this.txtquestionPaper, "data");
		dataBinder.registerBinding("customer", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.f7Customer, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.option", String.class, this.kdtEntrys, "option.text");
		dataBinder.registerBinding("entrys.inputStr", String.class, this.kdtEntrys, "inputStr.text");
		dataBinder.registerBinding("entrys.inputNumber", java.math.BigDecimal.class, this.kdtEntrys, "inputNumber.text");
		dataBinder.registerBinding("governer", com.kingdee.eas.basedata.person.PersonInfo.class, this.f7Governor, "data");
		dataBinder.registerBinding("purchase", com.kingdee.eas.fdc.sellhouse.PurchaseInfo.class, this.f7Purchase, "data");
		dataBinder.registerBinding("commerceChance", com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo.class, this.prmtCommerceChance, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.QuestionPaperAnswerEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtCompany.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("persion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inputDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("questionPaper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.option", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.inputStr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.inputNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("governer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purchase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChance", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtpersion_dataChanged method
     */
    protected void prmtpersion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtquestionPaper_dataChanged method
     */
    protected void txtquestionPaper_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Customer_dataChanged method
     */
    protected void f7Customer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Purchase_dataChanged method
     */
    protected void f7Purchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCommerceChance_dataChanged method
     */
    protected void prmtCommerceChance_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmtpersion_Changed() method
     */
    public void prmtpersion_Changed() throws Exception
    {
        System.out.println("prmtpersion_Changed() Function is executed!");
            txtpersionNumber.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtpersion.getData(),"number")));


    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("persion.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("inputDate"));
        sic.add(new SelectorItemInfo("questionPaper.*"));
        sic.add(new SelectorItemInfo("customer.*"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.option"));
    sic.add(new SelectorItemInfo("entrys.inputStr"));
    sic.add(new SelectorItemInfo("entrys.inputNumber"));
        sic.add(new SelectorItemInfo("governer.*"));
        sic.add(new SelectorItemInfo("purchase.*"));
        sic.add(new SelectorItemInfo("commerceChance.*"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionAddCustomer_actionPerformed method
     */
    public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionAddCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddCustomer() {
    	return false;
    }

    /**
     * output ActionAddCustomer class
     */     
    protected class ActionAddCustomer extends ItemAction {     
    
        public ActionAddCustomer()
        {
            this(null);
        }

        public ActionAddCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperAnswerEditUI.this, "ActionAddCustomer", "actionAddCustomer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "QuestionPaperAnswerEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo();
        objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/market/QuestionPaperAnswer";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.market.app.QuestionPaperAnswerQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}