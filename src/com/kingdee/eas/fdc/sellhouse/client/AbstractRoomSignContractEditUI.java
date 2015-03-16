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
public abstract class AbstractRoomSignContractEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomSignContractEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsOnRecord;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOnRecordDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSignJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSignDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkOnRecordDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSignJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNum;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox pkCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIntegral;
    protected com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo editData = null;
    protected ActionIntegral actionIntegral = null;
    /**
     * output class constructor
     */
    public AbstractRoomSignContractEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomSignContractEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setBindWorkFlow(true);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnAudit
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIntegral
        this.actionIntegral = new ActionIntegral(this);
        getActionManager().registerAction("actionIntegral", actionIntegral);
         this.actionIntegral.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsOnRecord = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contOnRecordDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSignJoinDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tabPurchase = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkOnRecordDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkSignJoinDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtContractNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnIntegral = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contContractName.setName("contContractName");
        this.contDescription.setName("contDescription");
        this.contRoom.setName("contRoom");
        this.contSignDate.setName("contSignDate");
        this.contContractNumber.setName("contContractNumber");
        this.chkIsOnRecord.setName("chkIsOnRecord");
        this.contOnRecordDate.setName("contOnRecordDate");
        this.contSignJoinDate.setName("contSignJoinDate");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.tabPurchase.setName("tabPurchase");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.txtContractName.setName("txtContractName");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.pkSignDate.setName("pkSignDate");
        this.txtContractNumber.setName("txtContractNumber");
        this.pkOnRecordDate.setName("pkOnRecordDate");
        this.pkSignJoinDate.setName("pkSignJoinDate");
        this.txtContractNum.setName("txtContractNum");
        this.pkCreator.setName("pkCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.btnIntegral.setName("btnIntegral");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,650));
        // contContractName		
        this.contContractName.setBoundLabelText(resHelper.getString("contContractName.boundLabelText"));		
        this.contContractName.setBoundLabelLength(100);		
        this.contContractName.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contSignDate		
        this.contSignDate.setBoundLabelText(resHelper.getString("contSignDate.boundLabelText"));		
        this.contSignDate.setBoundLabelLength(100);		
        this.contSignDate.setBoundLabelUnderline(true);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);
        // chkIsOnRecord		
        this.chkIsOnRecord.setText(resHelper.getString("chkIsOnRecord.text"));
        this.chkIsOnRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsOnRecord_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contOnRecordDate		
        this.contOnRecordDate.setBoundLabelText(resHelper.getString("contOnRecordDate.boundLabelText"));		
        this.contOnRecordDate.setBoundLabelLength(100);		
        this.contOnRecordDate.setBoundLabelUnderline(true);
        // contSignJoinDate		
        this.contSignJoinDate.setBoundLabelText(resHelper.getString("contSignJoinDate.boundLabelText"));		
        this.contSignJoinDate.setBoundLabelLength(100);		
        this.contSignJoinDate.setBoundLabelUnderline(true);
        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));
        this.btnSelectRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabPurchase
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelLength(100);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(100);
        // txtContractName
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(300);		
        this.txtDescription.setRows(3);
        // txtRoomNumber
        // pkSignDate
        this.pkSignDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    pkSignDate_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.pkSignDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkSignDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtContractNumber		
        this.txtContractNumber.setMaxLength(80);		
        this.txtContractNumber.setRequired(true);
        // pkOnRecordDate
        // pkSignJoinDate
        // txtContractNum
        // pkCreator		
        this.pkCreator.setDisplayFormat("$name$");		
        this.pkCreator.setEditFormat("$number$");		
        this.pkCreator.setCommitFormat("$number$");
        // pkCreateTime
        // btnIntegral
        this.btnIntegral.setAction((IItemAction)ActionProxyFactory.getProxy(actionIntegral, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnIntegral.setText(resHelper.getString("btnIntegral.text"));		
        this.btnIntegral.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_distributebatch"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 700));
        contContractName.setBounds(new Rectangle(677, 11, 270, 19));
        this.add(contContractName, new KDLayout.Constraints(677, 11, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 130, 936, 51));
        this.add(contDescription, new KDLayout.Constraints(10, 130, 936, 51, 0));
        contRoom.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contSignDate.setBounds(new Rectangle(677, 40, 270, 19));
        this.add(contSignDate, new KDLayout.Constraints(677, 40, 270, 19, 0));
        contContractNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contContractNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        chkIsOnRecord.setBounds(new Rectangle(541, 40, 102, 19));
        this.add(chkIsOnRecord, new KDLayout.Constraints(541, 40, 102, 19, 0));
        contOnRecordDate.setBounds(new Rectangle(11, 71, 270, 19));
        this.add(contOnRecordDate, new KDLayout.Constraints(11, 71, 270, 19, 0));
        contSignJoinDate.setBounds(new Rectangle(337, 70, 270, 19));
        this.add(contSignJoinDate, new KDLayout.Constraints(337, 70, 270, 19, 0));
        btnSelectRoom.setBounds(new Rectangle(343, 40, 88, 21));
        this.add(btnSelectRoom, new KDLayout.Constraints(343, 40, 88, 21, 0));
        tabPurchase.setBounds(new Rectangle(10, 189, 991, 506));
        this.add(tabPurchase, new KDLayout.Constraints(10, 189, 991, 506, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(342, 11, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(342, 11, 270, 19, 0));
        contCreator.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 100, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(338, 99, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(338, 99, 270, 19, 0));
        //contContractName
        contContractName.setBoundEditor(txtContractName);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contRoom
        contRoom.setBoundEditor(txtRoomNumber);
        //contSignDate
        contSignDate.setBoundEditor(pkSignDate);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contOnRecordDate
        contOnRecordDate.setBoundEditor(pkOnRecordDate);
        //contSignJoinDate
        contSignJoinDate.setBoundEditor(pkSignJoinDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtContractNum);
        //contCreator
        contCreator.setBoundEditor(pkCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);

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
        this.toolBar.add(btnIntegral);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isOnRecord", boolean.class, this.chkIsOnRecord, "selected");
		dataBinder.registerBinding("name", String.class, this.txtContractName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("signDate", java.util.Date.class, this.pkSignDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtContractNumber, "text");
		dataBinder.registerBinding("onRecordDate", java.util.Date.class, this.pkOnRecordDate, "value");
		dataBinder.registerBinding("signJoinDate", java.util.Date.class, this.pkSignJoinDate, "value");
		dataBinder.registerBinding("contractNumber", String.class, this.txtContractNum, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.pkCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomSignContractEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo)ov;
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
		getValidateHelper().registerBindProperty("isOnRecord", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("onRecordDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signJoinDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
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
     * output chkIsOnRecord_actionPerformed method
     */
    protected void chkIsOnRecord_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output pkSignDate_propertyChange method
     */
    protected void pkSignDate_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output pkSignDate_dataChanged method
     */
    protected void pkSignDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isOnRecord"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("onRecordDate"));
        sic.add(new SelectorItemInfo("signJoinDate"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionIntegral_actionPerformed method
     */
    public void actionIntegral_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionIntegral(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIntegral() {
    	return false;
    }

    /**
     * output ActionIntegral class
     */     
    protected class ActionIntegral extends ItemAction {     
    
        public ActionIntegral()
        {
            this(null);
        }

        public ActionIntegral(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIntegral.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntegral.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntegral.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomSignContractEditUI.this, "ActionIntegral", "actionIntegral_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomSignContractEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}