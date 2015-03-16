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
public abstract class AbstractRoomKeepDownBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomKeepDownBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeepDownCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeepDownDay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdBizTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtKeepdownCustomer;
    protected com.kingdee.bos.ctrl.swing.KDSpinner ksKeepDownDay;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextArea ktDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbReason;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelKeepDown;
    protected com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo editData = null;
    protected ActionKeepDown actionKeepDown = null;
    protected ActionCancelKeepDown actionCancelKeepDown = null;
    /**
     * output class constructor
     */
    public AbstractRoomKeepDownBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomKeepDownBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
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
        //actionAudit
        actionAudit.setEnabled(false);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionKeepDown
        this.actionKeepDown = new ActionKeepDown(this);
        getActionManager().registerAction("actionKeepDown", actionKeepDown);
         this.actionKeepDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelKeepDown
        this.actionCancelKeepDown = new ActionCancelKeepDown(this);
        getActionManager().registerAction("actionCancelKeepDown", actionCancelKeepDown);
         this.actionCancelKeepDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contKeepDownCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contKeepDownDay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdBizTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtKeepdownCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ksKeepDownDay = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.ktDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.cbReason = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnCancelKeepDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contRoom.setName("contRoom");
        this.contBizTime.setName("contBizTime");
        this.contKeepDownCustomer.setName("contKeepDownCustomer");
        this.contKeepDownDay.setName("contKeepDownDay");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contDescription.setName("contDescription");
        this.contReason.setName("contReason");
        this.txtNumber.setName("txtNumber");
        this.prmtRoom.setName("prmtRoom");
        this.kdBizTime.setName("kdBizTime");
        this.prmtKeepdownCustomer.setName("prmtKeepdownCustomer");
        this.ksKeepDownDay.setName("ksKeepDownDay");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.kdAuditTime.setName("kdAuditTime");
        this.ktDescription.setName("ktDescription");
        this.cbReason.setName("cbReason");
        this.btnCancelKeepDown.setName("btnCancelKeepDown");
        // CoreUI		
        this.setPreferredSize(new Dimension(600,250));		
        this.btnPageSetup.setEnabled(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.btnUnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);		
        this.btnCalculator.setEnabled(false);		
        this.btnCalculator.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contBizTime		
        this.contBizTime.setBoundLabelText(resHelper.getString("contBizTime.boundLabelText"));		
        this.contBizTime.setBoundLabelLength(100);		
        this.contBizTime.setBoundLabelUnderline(true);
        // contKeepDownCustomer		
        this.contKeepDownCustomer.setBoundLabelText(resHelper.getString("contKeepDownCustomer.boundLabelText"));		
        this.contKeepDownCustomer.setBoundLabelLength(100);		
        this.contKeepDownCustomer.setBoundLabelUnderline(true);
        // contKeepDownDay		
        this.contKeepDownDay.setBoundLabelText(resHelper.getString("contKeepDownDay.boundLabelText"));		
        this.contKeepDownDay.setBoundLabelLength(100);		
        this.contKeepDownDay.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelLength(100);
        // contReason		
        this.contReason.setBoundLabelText(resHelper.getString("contReason.boundLabelText"));		
        this.contReason.setBoundLabelLength(100);		
        this.contReason.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        this.txtNumber.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtNumber_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRoom		
        this.prmtRoom.setDefaultF7UIName("com.kingdee.eas.fdc.achievementmgmt.app.RoomF7Query");		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setEditFormat("$number$");		
        this.prmtRoom.setRequired(true);
        this.prmtRoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    prmtRoom_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdBizTime		
        this.kdBizTime.setRequired(true);
        // prmtKeepdownCustomer		
        this.prmtKeepdownCustomer.setDefaultF7UIName("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtKeepdownCustomer.setEnabledMultiSelection(true);		
        this.prmtKeepdownCustomer.setDisplayFormat("$name$");		
        this.prmtKeepdownCustomer.setEditFormat("$number$");		
        this.prmtKeepdownCustomer.setCommitFormat("$number$");
        this.prmtKeepdownCustomer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtKeepdownCustomer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // ksKeepDownDay
        this.ksKeepDownDay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    ksKeepDownDay_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");
        // kdAuditTime		
        this.kdAuditTime.setEnabled(false);
        // ktDescription		
        this.ktDescription.setMaxLength(255);
        // cbReason		
        this.cbReason.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum").toArray());
        // btnCancelKeepDown
        this.btnCancelKeepDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelKeepDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelKeepDown.setText(resHelper.getString("btnCancelKeepDown.text"));		
        this.btnCancelKeepDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelverification"));
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
        this.setBounds(new Rectangle(10, 10, 600, 250));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 250));
        contNumber.setBounds(new Rectangle(25, 7, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(25, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(312, 7, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(312, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizTime.setBounds(new Rectangle(312, 37, 270, 19));
        this.add(contBizTime, new KDLayout.Constraints(312, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contKeepDownCustomer.setBounds(new Rectangle(25, 37, 270, 19));
        this.add(contKeepDownCustomer, new KDLayout.Constraints(25, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contKeepDownDay.setBounds(new Rectangle(25, 67, 270, 19));
        this.add(contKeepDownDay, new KDLayout.Constraints(25, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(312, 67, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(312, 67, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(25, 97, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(25, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(312, 97, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(312, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(25, 158, 562, 69));
        this.add(contDescription, new KDLayout.Constraints(25, 158, 562, 69, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contReason.setBounds(new Rectangle(25, 127, 270, 19));
        this.add(contReason, new KDLayout.Constraints(25, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contBizTime
        contBizTime.setBoundEditor(kdBizTime);
        //contKeepDownCustomer
        contKeepDownCustomer.setBoundEditor(prmtKeepdownCustomer);
        //contKeepDownDay
        contKeepDownDay.setBoundEditor(ksKeepDownDay);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(kdAuditTime);
        //contDescription
        contDescription.setBoundEditor(ktDescription);
        //contReason
        contReason.setBoundEditor(cbReason);

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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnCancelKeepDown);
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
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtRoom, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.kdBizTime, "value");
		dataBinder.registerBinding("customerEntry", com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection.class, this.prmtKeepdownCustomer, "data");
		dataBinder.registerBinding("keepDate", int.class, this.ksKeepDownDay, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.kdAuditTime, "value");
		dataBinder.registerBinding("description", String.class, this.ktDescription, "text");
		dataBinder.registerBinding("reason", com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum.class, this.cbReason, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomKeepDownBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo)ov;
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
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keepDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reason", ValidateHelper.ON_SAVE);    		
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
     * output txtNumber_propertyChange method
     */
    protected void txtNumber_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRoom_stateChanged method
     */
    protected void prmtRoom_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtKeepdownCustomer_dataChanged method
     */
    protected void prmtKeepdownCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output ksKeepDownDay_stateChanged method
     */
    protected void ksKeepDownDay_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("room.*"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("customerEntry.*"));
        sic.add(new SelectorItemInfo("keepDate"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("reason"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionKeepDown_actionPerformed method
     */
    public void actionKeepDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelKeepDown_actionPerformed method
     */
    public void actionCancelKeepDown_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionKeepDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionKeepDown() {
    	return false;
    }
	public RequestContext prepareActionCancelKeepDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelKeepDown() {
    	return false;
    }

    /**
     * output ActionKeepDown class
     */     
    protected class ActionKeepDown extends ItemAction {     
    
        public ActionKeepDown()
        {
            this(null);
        }

        public ActionKeepDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionKeepDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionKeepDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomKeepDownBillEditUI.this, "ActionKeepDown", "actionKeepDown_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelKeepDown class
     */     
    protected class ActionCancelKeepDown extends ItemAction {     
    
        public ActionCancelKeepDown()
        {
            this(null);
        }

        public ActionCancelKeepDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCancelKeepDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelKeepDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelKeepDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomKeepDownBillEditUI.this, "ActionCancelKeepDown", "actionCancelKeepDown_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomKeepDownBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}