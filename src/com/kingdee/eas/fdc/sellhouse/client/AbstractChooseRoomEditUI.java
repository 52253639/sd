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
public abstract class AbstractChooseRoomEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChooseRoomEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerityPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChooser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesMan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnButtonCol;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAffirm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChooseNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomerEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSincerityPurchase;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChooser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSalesMan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChooseNo;
    protected com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo editData = null;
    protected ActionCancelChooseRoom actionCancelChooseRoom = null;
    protected ActionAffirm actionAffirm = null;
    protected ActionPrintChooseRoomInform actionPrintChooseRoomInform = null;
    protected ActionTransPrePurchase actionTransPrePurchase = null;
    protected ActionTransPurchase actionTransPurchase = null;
    protected ActionTransSign actionTransSign = null;
    /**
     * output class constructor
     */
    public AbstractChooseRoomEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChooseRoomEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCancelChooseRoom
        this.actionCancelChooseRoom = new ActionCancelChooseRoom(this);
        getActionManager().registerAction("actionCancelChooseRoom", actionCancelChooseRoom);
         this.actionCancelChooseRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAffirm
        this.actionAffirm = new ActionAffirm(this);
        getActionManager().registerAction("actionAffirm", actionAffirm);
         this.actionAffirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintChooseRoomInform
        this.actionPrintChooseRoomInform = new ActionPrintChooseRoomInform(this);
        getActionManager().registerAction("actionPrintChooseRoomInform", actionPrintChooseRoomInform);
         this.actionPrintChooseRoomInform.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransPrePurchase
        this.actionTransPrePurchase = new ActionTransPrePurchase(this);
        getActionManager().registerAction("actionTransPrePurchase", actionTransPrePurchase);
         this.actionTransPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransPurchase
        this.actionTransPurchase = new ActionTransPurchase(this);
        getActionManager().registerAction("actionTransPurchase", actionTransPurchase);
         this.actionTransPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransSign
        this.actionTransSign = new ActionTransSign(this);
        getActionManager().registerAction("actionTransSign", actionTransSign);
         this.actionTransSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerityPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChooser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnButtonCol = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelChooseRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAffirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contChooseNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCustomerEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSincerityPurchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtChooser = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSalesMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtChooseNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDContainer1.setName("kDContainer1");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contCustomerEntry.setName("contCustomerEntry");
        this.contSincerityPurchase.setName("contSincerityPurchase");
        this.contChooser.setName("contChooser");
        this.contLinkPhone.setName("contLinkPhone");
        this.contNo.setName("contNo");
        this.contSalesMan.setName("contSalesMan");
        this.btnButtonCol.setName("btnButtonCol");
        this.btnCancelChooseRoom.setName("btnCancelChooseRoom");
        this.btnAffirm.setName("btnAffirm");
        this.contChooseNo.setName("contChooseNo");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtCustomerEntry.setName("prmtCustomerEntry");
        this.prmtSincerityPurchase.setName("prmtSincerityPurchase");
        this.txtChooser.setName("txtChooser");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.txtNo.setName("txtNo");
        this.prmtSalesMan.setName("prmtSalesMan");
        this.txtChooseNo.setName("txtChooseNo");
        // CoreUI
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setPreferredSize(new Dimension(540,400));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(50);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);		
        this.contNumber.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(50);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contCustomerEntry		
        this.contCustomerEntry.setBoundLabelText(resHelper.getString("contCustomerEntry.boundLabelText"));		
        this.contCustomerEntry.setBoundLabelLength(50);		
        this.contCustomerEntry.setBoundLabelUnderline(true);		
        this.contCustomerEntry.setEnabled(false);		
        this.contCustomerEntry.setVisible(false);
        // contSincerityPurchase		
        this.contSincerityPurchase.setBoundLabelText(resHelper.getString("contSincerityPurchase.boundLabelText"));		
        this.contSincerityPurchase.setBoundLabelLength(50);		
        this.contSincerityPurchase.setBoundLabelUnderline(true);		
        this.contSincerityPurchase.setEnabled(false);		
        this.contSincerityPurchase.setVisible(false);
        // contChooser		
        this.contChooser.setBoundLabelText(resHelper.getString("contChooser.boundLabelText"));		
        this.contChooser.setBoundLabelLength(50);		
        this.contChooser.setBoundLabelUnderline(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(50);		
        this.contLinkPhone.setBoundLabelUnderline(true);
        // contNo		
        this.contNo.setBoundLabelText(resHelper.getString("contNo.boundLabelText"));		
        this.contNo.setBoundLabelLength(100);		
        this.contNo.setBoundLabelUnderline(true);		
        this.contNo.setEnabled(false);		
        this.contNo.setVisible(false);
        // contSalesMan		
        this.contSalesMan.setBoundLabelText(resHelper.getString("contSalesMan.boundLabelText"));		
        this.contSalesMan.setBoundLabelLength(50);		
        this.contSalesMan.setBoundLabelUnderline(true);
        // btnButtonCol		
        this.btnButtonCol.setText(resHelper.getString("btnButtonCol.text"));
        // btnCancelChooseRoom
        this.btnCancelChooseRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelChooseRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelChooseRoom.setText(resHelper.getString("btnCancelChooseRoom.text"));
        // btnAffirm
        this.btnAffirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAffirm.setText(resHelper.getString("btnAffirm.text"));
        this.btnAffirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAffirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contChooseNo		
        this.contChooseNo.setBoundLabelText(resHelper.getString("contChooseNo.boundLabelText"));		
        this.contChooseNo.setBoundLabelLength(50);		
        this.contChooseNo.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setRequired(true);		
        this.pkBizDate.setEnabled(false);
        // prmtCustomerEntry		
        this.prmtCustomerEntry.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtCustomerEntry.setEnabledMultiSelection(true);		
        this.prmtCustomerEntry.setDisplayFormat("$name$");		
        this.prmtCustomerEntry.setEditFormat("$number$");		
        this.prmtCustomerEntry.setCommitFormat("$number$");
        this.prmtCustomerEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCustomerEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSincerityPurchase		
        this.prmtSincerityPurchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");		
        this.prmtSincerityPurchase.setDisplayFormat("$number$");		
        this.prmtSincerityPurchase.setEditFormat("$number$");		
        this.prmtSincerityPurchase.setCommitFormat("$name$");
        this.prmtSincerityPurchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSincerityPurchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtChooser		
        this.txtChooser.setMaxLength(80);
        // txtLinkPhone		
        this.txtLinkPhone.setMaxLength(80);
        // txtNo		
        this.txtNo.setRequired(true);		
        this.txtNo.setEnabled(false);
        // prmtSalesMan		
        this.prmtSalesMan.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.prmtSalesMan.setDisplayFormat("$name$");		
        this.prmtSalesMan.setEditFormat("$number$");		
        this.prmtSalesMan.setCommitFormat("$name$");
        // txtChooseNo
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
        this.setBounds(new Rectangle(10, 10, 400, 200));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 200));
        kDContainer1.setBounds(new Rectangle(4, 5, 394, 192));
        this.add(kDContainer1, new KDLayout.Constraints(4, 5, 394, 192, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(4, 5, 394, 192));        contNumber.setBounds(new Rectangle(196, 140, 179, 19));
        kDContainer1.getContentPane().add(contNumber, new KDLayout.Constraints(196, 140, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(3, 101, 179, 19));
        kDContainer1.getContentPane().add(contBizDate, new KDLayout.Constraints(3, 101, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustomerEntry.setBounds(new Rectangle(8, 146, 200, 19));
        kDContainer1.getContentPane().add(contCustomerEntry, new KDLayout.Constraints(8, 146, 200, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSincerityPurchase.setBounds(new Rectangle(18, 123, 200, 19));
        kDContainer1.getContentPane().add(contSincerityPurchase, new KDLayout.Constraints(18, 123, 200, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChooser.setBounds(new Rectangle(3, 71, 179, 19));
        kDContainer1.getContentPane().add(contChooser, new KDLayout.Constraints(3, 71, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPhone.setBounds(new Rectangle(207, 71, 179, 19));
        kDContainer1.getContentPane().add(contLinkPhone, new KDLayout.Constraints(207, 71, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNo.setBounds(new Rectangle(232, 372, 240, 19));
        kDContainer1.getContentPane().add(contNo, new KDLayout.Constraints(232, 372, 240, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSalesMan.setBounds(new Rectangle(207, 101, 179, 19));
        kDContainer1.getContentPane().add(contSalesMan, new KDLayout.Constraints(207, 101, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnButtonCol.setBounds(new Rectangle(97, 9, 83, 19));
        kDContainer1.getContentPane().add(btnButtonCol, new KDLayout.Constraints(97, 9, 83, 19, KDLayout.Constraints.ANCHOR_LEFT));
        btnCancelChooseRoom.setBounds(new Rectangle(5, 9, 83, 19));
        kDContainer1.getContentPane().add(btnCancelChooseRoom, new KDLayout.Constraints(5, 9, 83, 19, KDLayout.Constraints.ANCHOR_LEFT));
        btnAffirm.setBounds(new Rectangle(5, 9, 83, 19));
        kDContainer1.getContentPane().add(btnAffirm, new KDLayout.Constraints(5, 9, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contChooseNo.setBounds(new Rectangle(3, 42, 179, 19));
        kDContainer1.getContentPane().add(contChooseNo, new KDLayout.Constraints(3, 42, 179, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contCustomerEntry
        contCustomerEntry.setBoundEditor(prmtCustomerEntry);
        //contSincerityPurchase
        contSincerityPurchase.setBoundEditor(prmtSincerityPurchase);
        //contChooser
        contChooser.setBoundEditor(txtChooser);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contNo
        contNo.setBoundEditor(txtNo);
        //contSalesMan
        contSalesMan.setBoundEditor(prmtSalesMan);
        //contChooseNo
        contChooseNo.setBoundEditor(txtChooseNo);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("sincerityPurchase", com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo.class, this.prmtSincerityPurchase, "data");
		dataBinder.registerBinding("chooser", String.class, this.txtChooser, "text");
		dataBinder.registerBinding("linkPhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("salesMan", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSalesMan, "data");
		dataBinder.registerBinding("chooseNo", String.class, this.txtChooseNo, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChooseRoomEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sincerityPurchase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chooser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("salesMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chooseNo", ValidateHelper.ON_SAVE);    		
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
     * output btnAffirm_actionPerformed method
     */
    protected void btnAffirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtCustomerEntry_dataChanged method
     */
    protected void prmtCustomerEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSincerityPurchase_dataChanged method
     */
    protected void prmtSincerityPurchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("sincerityPurchase.*"));
        sic.add(new SelectorItemInfo("chooser"));
        sic.add(new SelectorItemInfo("linkPhone"));
        sic.add(new SelectorItemInfo("salesMan.*"));
        sic.add(new SelectorItemInfo("chooseNo"));
        return sic;
    }        
    	

    /**
     * output actionCancelChooseRoom_actionPerformed method
     */
    public void actionCancelChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAffirm_actionPerformed method
     */
    public void actionAffirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintChooseRoomInform_actionPerformed method
     */
    public void actionPrintChooseRoomInform_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransPrePurchase_actionPerformed method
     */
    public void actionTransPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransPurchase_actionPerformed method
     */
    public void actionTransPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransSign_actionPerformed method
     */
    public void actionTransSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCancelChooseRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelChooseRoom() {
    	return false;
    }
	public RequestContext prepareActionAffirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAffirm() {
    	return false;
    }
	public RequestContext prepareActionPrintChooseRoomInform(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintChooseRoomInform() {
    	return false;
    }
	public RequestContext prepareActionTransPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionTransPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransPurchase() {
    	return false;
    }
	public RequestContext prepareActionTransSign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransSign() {
    	return false;
    }

    /**
     * output ActionCancelChooseRoom class
     */     
    protected class ActionCancelChooseRoom extends ItemAction {     
    
        public ActionCancelChooseRoom()
        {
            this(null);
        }

        public ActionCancelChooseRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelChooseRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelChooseRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelChooseRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionCancelChooseRoom", "actionCancelChooseRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionAffirm class
     */     
    protected class ActionAffirm extends ItemAction {     
    
        public ActionAffirm()
        {
            this(null);
        }

        public ActionAffirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAffirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAffirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAffirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionAffirm", "actionAffirm_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintChooseRoomInform class
     */     
    protected class ActionPrintChooseRoomInform extends ItemAction {     
    
        public ActionPrintChooseRoomInform()
        {
            this(null);
        }

        public ActionPrintChooseRoomInform(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrintChooseRoomInform.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintChooseRoomInform.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintChooseRoomInform.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionPrintChooseRoomInform", "actionPrintChooseRoomInform_actionPerformed", e);
        }
    }

    /**
     * output ActionTransPrePurchase class
     */     
    protected class ActionTransPrePurchase extends ItemAction {     
    
        public ActionTransPrePurchase()
        {
            this(null);
        }

        public ActionTransPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTransPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionTransPrePurchase", "actionTransPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionTransPurchase class
     */     
    protected class ActionTransPurchase extends ItemAction {     
    
        public ActionTransPurchase()
        {
            this(null);
        }

        public ActionTransPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTransPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionTransPurchase", "actionTransPurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionTransSign class
     */     
    protected class ActionTransSign extends ItemAction {     
    
        public ActionTransSign()
        {
            this(null);
        }

        public ActionTransSign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTransSign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransSign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransSign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChooseRoomEditUI.this, "ActionTransSign", "actionTransSign_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChooseRoomEditUI");
    }




}