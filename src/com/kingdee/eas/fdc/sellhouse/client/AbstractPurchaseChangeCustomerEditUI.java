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
public abstract class AbstractPurchaseChangeCustomerEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseChangeCustomerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUp;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDown;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRenameReason;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearchOld;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSearchNew;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubarea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtOldTable;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtNewTable;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRenameReason;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelationPurchase;
    protected com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo editData = null;
    protected ActionRelationPurchase actionRelationPurchase = null;
    /**
     * output class constructor
     */
    public AbstractPurchaseChangeCustomerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseChangeCustomerEditUI.class.getName());
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
        //actionRelationPurchase
        this.actionRelationPurchase = new ActionRelationPurchase(this);
        getActionManager().registerAction("actionRelationPurchase", actionRelationPurchase);
         this.actionRelationPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contRenameReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearchOld = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnSearchNew = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSubarea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.spiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtBuilding = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7RoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSellOrder = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtOldTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtNewTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtRenameReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRelationPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contName.setName("contName");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnAdd.setName("btnAdd");
        this.btnDelete.setName("btnDelete");
        this.btnUp.setName("btnUp");
        this.btnDown.setName("btnDown");
        this.contRenameReason.setName("contRenameReason");
        this.btnSearchOld.setName("btnSearchOld");
        this.btnSearchNew.setName("btnSearchNew");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtName.setName("txtName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.txtProjectName.setName("txtProjectName");
        this.txtSubarea.setName("txtSubarea");
        this.spiUnit.setName("spiUnit");
        this.txtBuilding.setName("txtBuilding");
        this.f7RoomModel.setName("f7RoomModel");
        this.txtSellOrder.setName("txtSellOrder");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.kdtOldTable.setName("kdtOldTable");
        this.kdtNewTable.setName("kdtNewTable");
        this.prmtRenameReason.setName("prmtRenameReason");
        this.btnRelationPurchase.setName("btnRelationPurchase");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,600));		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(true);		
        this.btnAuditResult.setEnabled(true);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemMultiapprove.setEnabled(false);		
        this.menuItemNextPerson.setVisible(false);		
        this.menuItemNextPerson.setEnabled(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel3.border.title")));
        // btnAddCustomer		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));
        this.btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAdd		
        this.btnAdd.setText(resHelper.getString("btnAdd.text"));
        this.btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAdd_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelete		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        this.btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnUp		
        this.btnUp.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_movetop"));		
        this.btnUp.setText(resHelper.getString("btnUp.text"));
        this.btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUp_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDown		
        this.btnDown.setText(resHelper.getString("btnDown.text"));		
        this.btnDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_movedown"));
        this.btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDown_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contRenameReason		
        this.contRenameReason.setBoundLabelText(resHelper.getString("contRenameReason.boundLabelText"));		
        this.contRenameReason.setBoundLabelLength(100);		
        this.contRenameReason.setBoundLabelUnderline(true);
        // btnSearchOld		
        this.btnSearchOld.setText(resHelper.getString("btnSearchOld.text"));
        this.btnSearchOld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSearchOld_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnSearchNew		
        this.btnSearchNew.setText(resHelper.getString("btnSearchNew.text"));
        this.btnSearchNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSearchNew_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDatePicker1		
        this.kDDatePicker1.setRequired(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtDescription
        // txtName		
        this.txtName.setRequired(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
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
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // txtProjectName		
        this.txtProjectName.setEnabled(false);
        // txtSubarea		
        this.txtSubarea.setEnabled(false);
        // spiUnit		
        this.spiUnit.setEnabled(false);
        // txtBuilding		
        this.txtBuilding.setEnabled(false);
        // f7RoomModel		
        this.f7RoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");		
        this.f7RoomModel.setEnabled(false);
        // txtSellOrder		
        this.txtSellOrder.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setEnabled(false);
        // txtRoomArea		
        this.txtRoomArea.setEnabled(false);
        // kdtOldTable
		String kdtOldTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"certificateNumber\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"mailaddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailaddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtOldTable.setFormatXml(resHelper.translateString("kdtOldTable",kdtOldTableStrXML));

        

        // kdtNewTable
		String kdtNewTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"certificateNumber\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"mailaddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailaddress}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{des}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtNewTable.setFormatXml(resHelper.translateString("kdtNewTable",kdtNewTableStrXML));
        this.kdtNewTable.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCustomerInfo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // prmtRenameReason		
        this.prmtRenameReason.setDisplayFormat("$name$");		
        this.prmtRenameReason.setEditFormat("$name$");		
        this.prmtRenameReason.setCommitFormat("$name$");		
        this.prmtRenameReason.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RenameRoomReasonQuery");
        // btnRelationPurchase
        this.btnRelationPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelationPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelationPurchase.setText(resHelper.getString("btnRelationPurchase.text"));		
        this.btnRelationPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(null);
        contLastUpdateUser.setBounds(new Rectangle(355, 11, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(694, 11, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(24, 11, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(355, 50, 611, 48));
        this.add(contDescription, null);
        contName.setBounds(new Rectangle(23, 46, 270, 19));
        this.add(contName, null);
        kDPanel1.setBounds(new Rectangle(6, 109, 970, 144));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(6, 278, 970, 155));
        this.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(9, 461, 970, 160));
        this.add(kDPanel3, null);
        btnAddCustomer.setBounds(new Rectangle(749, 437, 90, 19));
        this.add(btnAddCustomer, null);
        btnAdd.setBounds(new Rectangle(845, 437, 60, 19));
        this.add(btnAdd, null);
        btnDelete.setBounds(new Rectangle(911, 437, 60, 19));
        this.add(btnDelete, null);
        btnUp.setBounds(new Rectangle(564, 437, 78, 19));
        this.add(btnUp, null);
        btnDown.setBounds(new Rectangle(657, 437, 78, 19));
        this.add(btnDown, null);
        contRenameReason.setBounds(new Rectangle(23, 80, 270, 19));
        this.add(contRenameReason, null);
        btnSearchOld.setBounds(new Rectangle(872, 255, 66, 20));
        this.add(btnSearchOld, null);
        btnSearchNew.setBounds(new Rectangle(480, 437, 69, 19));
        this.add(btnSearchNew, null);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDatePicker1);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contName
        contName.setBoundEditor(txtName);
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(16, 23, 270, 19));
        kDPanel1.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(686, 22, 270, 19));
        kDPanel1.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(16, 54, 270, 19));
        kDPanel1.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(16, 81, 270, 19));
        kDPanel1.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(347, 53, 270, 19));
        kDPanel1.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(687, 53, 270, 19));
        kDPanel1.add(kDLabelContainer6, null);
        kDLabelContainer7.setBounds(new Rectangle(16, 110, 270, 19));
        kDPanel1.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(347, 85, 270, 19));
        kDPanel1.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(687, 85, 270, 19));
        kDPanel1.add(kDLabelContainer9, null);
        btnSelectRoom.setBounds(new Rectangle(347, 23, 107, 21));
        kDPanel1.add(btnSelectRoom, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtRoomNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtProjectName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSubarea);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(spiUnit);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtBuilding);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(f7RoomModel);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtSellOrder);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtBuildingArea);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtRoomArea);
        //kDPanel2
        kDPanel2.setLayout(null);        kdtOldTable.setBounds(new Rectangle(15, 19, 940, 120));
        kDPanel2.add(kdtOldTable, null);
        //kDPanel3
        kDPanel3.setLayout(null);        kdtNewTable.setBounds(new Rectangle(15, 19, 940, 120));
        kDPanel3.add(kdtNewTable, null);
        //contRenameReason
        contRenameReason.setBoundEditor(prmtRenameReason);

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
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnRelationPurchase);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.kDDatePicker1, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("renameReason", com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo.class, this.prmtRenameReason, "data");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnAddCustomer, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnAdd, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnDelete, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnAddCustomer, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnAdd, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnDelete, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnAddCustomer, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnAdd, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnDelete, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnAddCustomer, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnAdd, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnDelete, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseChangeCustomerEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("renameReason", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.btnAddCustomer.setEnabled(true);
		            this.btnAdd.setEnabled(true);
		            this.btnDelete.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.btnAddCustomer.setEnabled(true);
		            this.btnAdd.setEnabled(true);
		            this.btnDelete.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.btnAddCustomer.setEnabled(false);
		            this.btnAdd.setEnabled(false);
		            this.btnDelete.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.btnAddCustomer.setEnabled(false);
		            this.btnAdd.setEnabled(false);
		            this.btnDelete.setEnabled(false);
        }
    }

    /**
     * output btnAddCustomer_actionPerformed method
     */
    protected void btnAddCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAdd_actionPerformed method
     */
    protected void btnAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelete_actionPerformed method
     */
    protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnUp_actionPerformed method
     */
    protected void btnUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDown_actionPerformed method
     */
    protected void btnDown_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnSearchOld_actionPerformed method
     */
    protected void btnSearchOld_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnSearchNew_actionPerformed method
     */
    protected void btnSearchNew_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblCustomerInfo_editStopped method
     */
    protected void tblCustomerInfo_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("renameReason.*"));
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
     * output actionRelationPurchase_actionPerformed method
     */
    public void actionRelationPurchase_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionRelationPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelationPurchase() {
    	return false;
    }

    /**
     * output ActionRelationPurchase class
     */     
    protected class ActionRelationPurchase extends ItemAction {     
    
        public ActionRelationPurchase()
        {
            this(null);
        }

        public ActionRelationPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRelationPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelationPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelationPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseChangeCustomerEditUI.this, "ActionRelationPurchase", "actionRelationPurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseChangeCustomerEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}