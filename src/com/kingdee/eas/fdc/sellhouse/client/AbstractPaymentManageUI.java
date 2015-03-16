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
public abstract class AbstractPaymentManageUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentManageUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelTabPayment;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelChangeManage;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPrePayment;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelEarnestMoney;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRoomAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBankAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelActBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelActAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelTotalPayment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCMFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCMSelete;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFinDeal;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMark;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCMBill;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCMFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCMFilter;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPPFilter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPPFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPSelect;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPPActBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPPActAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPPFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPPFilter;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPPActBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPTranAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPRecycleInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPPReceiveAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPPActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEMFilter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEMFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMSelect;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelEMBill;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelTabEMActBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMTranAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMRecycleInvoice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDUnGathering;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDHasGathering;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDHasRefundment;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDHasTransfer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEMReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboEMFilter;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEMBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelEMActBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelEMActAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEMActBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEMActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRAFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRASelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contf7Room;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelateBaseTran;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelateSaleBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelTabRAActBill;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRAFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRAQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRATranAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRAInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRARecycleInvoice;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer4;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRAReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRAFilter;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRelateBaseTran;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRABill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRAActBill;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRaActAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRAActBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRAActAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBASelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBAFilter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBAFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBankAmountBill;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBAFilter;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBankAmountBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAuditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAuditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contABFilter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtABFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelABActBill;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkABFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboABFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABTranAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnABRecycleInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnGenTotalFin;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblABActBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAAFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAASelect;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAAActAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAAFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAAFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAAFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAAFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAAQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAATranAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAAInvoice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAARecycleInvoice;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAAActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTPFilter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTPFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPSelect;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRevGather;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPAddBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPEditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPRemoveBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPAuditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTPUnAuditBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVoucher;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelVoucher;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTPFilterValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTPFilter;
    protected ActionSelect actionSelect = null;
    protected ActionReceiveAmount actionReceiveAmount = null;
    protected ActionQuitAmount actionQuitAmount = null;
    protected ActionTranAmount actionTranAmount = null;
    protected ActionInvoice actionInvoice = null;
    protected ActionRecycleInvoice actionRecycleInvoice = null;
    protected ActionFinDeal actionFinDeal = null;
    protected ActionMark actionMark = null;
    protected ActionRelateSaleBill actionRelateSaleBill = null;
    protected ActionAddBill actionAddBill = null;
    protected ActionEditBill actionEditBill = null;
    protected ActionRemoveBill actionRemoveBill = null;
    protected ActionAuditBill actionAuditBill = null;
    protected ActionUnAuditBill actionUnAuditBill = null;
    protected ActionGenTotalFin actionGenTotalFin = null;
    protected ActionVoucher actionVoucher = null;
    protected ActionDelVoucher actionDelVoucher = null;
    /**
     * output class constructor
     */
    public AbstractPaymentManageUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPaymentManageUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionSelect
        this.actionSelect = new ActionSelect(this);
        getActionManager().registerAction("actionSelect", actionSelect);
         this.actionSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveAmount
        this.actionReceiveAmount = new ActionReceiveAmount(this);
        getActionManager().registerAction("actionReceiveAmount", actionReceiveAmount);
         this.actionReceiveAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitAmount
        this.actionQuitAmount = new ActionQuitAmount(this);
        getActionManager().registerAction("actionQuitAmount", actionQuitAmount);
         this.actionQuitAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTranAmount
        this.actionTranAmount = new ActionTranAmount(this);
        getActionManager().registerAction("actionTranAmount", actionTranAmount);
         this.actionTranAmount.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvoice
        this.actionInvoice = new ActionInvoice(this);
        getActionManager().registerAction("actionInvoice", actionInvoice);
         this.actionInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRecycleInvoice
        this.actionRecycleInvoice = new ActionRecycleInvoice(this);
        getActionManager().registerAction("actionRecycleInvoice", actionRecycleInvoice);
         this.actionRecycleInvoice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFinDeal
        this.actionFinDeal = new ActionFinDeal(this);
        getActionManager().registerAction("actionFinDeal", actionFinDeal);
         this.actionFinDeal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMark
        this.actionMark = new ActionMark(this);
        getActionManager().registerAction("actionMark", actionMark);
         this.actionMark.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelateSaleBill
        this.actionRelateSaleBill = new ActionRelateSaleBill(this);
        getActionManager().registerAction("actionRelateSaleBill", actionRelateSaleBill);
         this.actionRelateSaleBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddBill
        this.actionAddBill = new ActionAddBill(this);
        getActionManager().registerAction("actionAddBill", actionAddBill);
         this.actionAddBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditBill
        this.actionEditBill = new ActionEditBill(this);
        getActionManager().registerAction("actionEditBill", actionEditBill);
         this.actionEditBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveBill
        this.actionRemoveBill = new ActionRemoveBill(this);
        getActionManager().registerAction("actionRemoveBill", actionRemoveBill);
         this.actionRemoveBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAuditBill
        this.actionAuditBill = new ActionAuditBill(this);
        getActionManager().registerAction("actionAuditBill", actionAuditBill);
         this.actionAuditBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAuditBill
        this.actionUnAuditBill = new ActionUnAuditBill(this);
        getActionManager().registerAction("actionUnAuditBill", actionUnAuditBill);
         this.actionUnAuditBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGenTotalFin
        this.actionGenTotalFin = new ActionGenTotalFin(this);
        getActionManager().registerAction("actionGenTotalFin", actionGenTotalFin);
         this.actionGenTotalFin.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVoucher
        this.actionVoucher = new ActionVoucher(this);
        getActionManager().registerAction("actionVoucher", actionVoucher);
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelVoucher
        this.actionDelVoucher = new ActionDelVoucher(this);
        getActionManager().registerAction("actionDelVoucher", actionDelVoucher);
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.panelTabPayment = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.panelChangeManage = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPrePayment = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelEarnestMoney = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelRoomAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelBankAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelActBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelActAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelTotalPayment = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCMFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnCMSelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFinDeal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMark = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblCMBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.comboCMFilterValue = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboCMFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contPPFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPPFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnPPSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.panelPPActBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPPActAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pkPPFilterValue = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboPPFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblPPActBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnPPQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPPTranAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPPInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPPRecycleInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPPReceiveAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblPPActAmount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contEMFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtEMFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnEMSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.panelEMBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelTabEMActBill = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnEMQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEMTranAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEMInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEMRecycleInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDUnGathering = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDHasGathering = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDHasRefundment = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDHasTransfer = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnEMReceiveAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboEMFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblEMBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelEMActBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelEMActAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblEMActBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblEMActAmount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRAFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRASelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contf7Room = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelateBaseTran = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRelateSaleBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.panelReceiveAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelTabRAActBill = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.txtRAFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnRAQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRATranAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRAInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRARecycleInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.labCustomer1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnRAReceiveAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboRAFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboRelateBaseTran = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblRABill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.panelRAActBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelRaActAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblRAActBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRAActAmount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnBASelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contBAFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBAFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.panelBankAmountBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.comboBAFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblBankAmountBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAuditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAuditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnABSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contABFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtABFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.panelABActBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pkABFilterValue = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboABFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnABReceiveAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnABQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnABTranAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnABInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnABRecycleInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnGenTotalFin = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblABActBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contAAFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAASelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.panelAAActAmount = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.comboAAFilterValue = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAAFilterValue = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAAFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboAAFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnAAQuitAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAATranAmount = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAAInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAARecycleInvoice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblAAActAmount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTPFilter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTPFilterValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnTPSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblRevGather = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnTPAddBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTPEditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTPRemoveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTPAuditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTPUnAuditBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVoucher = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelVoucher = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.comboTPFilterValue = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboTPFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.panelTabPayment.setName("panelTabPayment");
        this.treeMain.setName("treeMain");
        this.panelChangeManage.setName("panelChangeManage");
        this.panelPrePayment.setName("panelPrePayment");
        this.panelEarnestMoney.setName("panelEarnestMoney");
        this.panelRoomAmount.setName("panelRoomAmount");
        this.panelBankAmount.setName("panelBankAmount");
        this.panelActBill.setName("panelActBill");
        this.panelActAmount.setName("panelActAmount");
        this.panelTotalPayment.setName("panelTotalPayment");
        this.contCMFilter.setName("contCMFilter");
        this.btnCMSelete.setName("btnCMSelete");
        this.btnFinDeal.setName("btnFinDeal");
        this.btnMark.setName("btnMark");
        this.tblCMBill.setName("tblCMBill");
        this.comboCMFilterValue.setName("comboCMFilterValue");
        this.comboCMFilter.setName("comboCMFilter");
        this.contPPFilter.setName("contPPFilter");
        this.txtPPFilterValue.setName("txtPPFilterValue");
        this.btnPPSelect.setName("btnPPSelect");
        this.panelPPActBill.setName("panelPPActBill");
        this.panelPPActAmount.setName("panelPPActAmount");
        this.pkPPFilterValue.setName("pkPPFilterValue");
        this.comboPPFilter.setName("comboPPFilter");
        this.tblPPActBill.setName("tblPPActBill");
        this.btnPPQuitAmount.setName("btnPPQuitAmount");
        this.btnPPTranAmount.setName("btnPPTranAmount");
        this.btnPPInvoice.setName("btnPPInvoice");
        this.btnPPRecycleInvoice.setName("btnPPRecycleInvoice");
        this.btnPPReceiveAmount.setName("btnPPReceiveAmount");
        this.tblPPActAmount.setName("tblPPActAmount");
        this.contEMFilter.setName("contEMFilter");
        this.txtEMFilterValue.setName("txtEMFilterValue");
        this.btnEMSelect.setName("btnEMSelect");
        this.panelEMBill.setName("panelEMBill");
        this.panelTabEMActBill.setName("panelTabEMActBill");
        this.btnEMQuitAmount.setName("btnEMQuitAmount");
        this.btnEMTranAmount.setName("btnEMTranAmount");
        this.btnEMInvoice.setName("btnEMInvoice");
        this.btnEMRecycleInvoice.setName("btnEMRecycleInvoice");
        this.kDUnGathering.setName("kDUnGathering");
        this.kDHasGathering.setName("kDHasGathering");
        this.kDHasRefundment.setName("kDHasRefundment");
        this.kDHasTransfer.setName("kDHasTransfer");
        this.btnEMReceiveAmount.setName("btnEMReceiveAmount");
        this.comboEMFilter.setName("comboEMFilter");
        this.tblEMBill.setName("tblEMBill");
        this.panelEMActBill.setName("panelEMActBill");
        this.panelEMActAmount.setName("panelEMActAmount");
        this.tblEMActBill.setName("tblEMActBill");
        this.tblEMActAmount.setName("tblEMActAmount");
        this.contRAFilter.setName("contRAFilter");
        this.btnRASelect.setName("btnRASelect");
        this.contf7Room.setName("contf7Room");
        this.contRelateBaseTran.setName("contRelateBaseTran");
        this.btnRelateSaleBill.setName("btnRelateSaleBill");
        this.panelReceiveAmount.setName("panelReceiveAmount");
        this.panelTabRAActBill.setName("panelTabRAActBill");
        this.txtRAFilterValue.setName("txtRAFilterValue");
        this.btnRAQuitAmount.setName("btnRAQuitAmount");
        this.btnRATranAmount.setName("btnRATranAmount");
        this.btnRAInvoice.setName("btnRAInvoice");
        this.btnRARecycleInvoice.setName("btnRARecycleInvoice");
        this.labCustomer1.setName("labCustomer1");
        this.kDSeparator3.setName("kDSeparator3");
        this.labCustomer2.setName("labCustomer2");
        this.kDSeparator4.setName("kDSeparator4");
        this.labCustomer3.setName("labCustomer3");
        this.kDSeparator5.setName("kDSeparator5");
        this.labCustomer4.setName("labCustomer4");
        this.kDSeparator6.setName("kDSeparator6");
        this.labCustomer5.setName("labCustomer5");
        this.kDSeparator7.setName("kDSeparator7");
        this.btnRAReceiveAmount.setName("btnRAReceiveAmount");
        this.comboRAFilter.setName("comboRAFilter");
        this.f7Room.setName("f7Room");
        this.comboRelateBaseTran.setName("comboRelateBaseTran");
        this.tblRABill.setName("tblRABill");
        this.panelRAActBill.setName("panelRAActBill");
        this.panelRaActAmount.setName("panelRaActAmount");
        this.tblRAActBill.setName("tblRAActBill");
        this.tblRAActAmount.setName("tblRAActAmount");
        this.btnBASelect.setName("btnBASelect");
        this.contBAFilter.setName("contBAFilter");
        this.txtBAFilterValue.setName("txtBAFilterValue");
        this.panelBankAmountBill.setName("panelBankAmountBill");
        this.comboBAFilter.setName("comboBAFilter");
        this.tblBankAmountBill.setName("tblBankAmountBill");
        this.btnAddBill.setName("btnAddBill");
        this.btnEditBill.setName("btnEditBill");
        this.btnRemoveBill.setName("btnRemoveBill");
        this.btnAuditBill.setName("btnAuditBill");
        this.btnUnAuditBill.setName("btnUnAuditBill");
        this.btnABSelect.setName("btnABSelect");
        this.contABFilter.setName("contABFilter");
        this.txtABFilterValue.setName("txtABFilterValue");
        this.panelABActBill.setName("panelABActBill");
        this.pkABFilterValue.setName("pkABFilterValue");
        this.comboABFilter.setName("comboABFilter");
        this.btnABReceiveAmount.setName("btnABReceiveAmount");
        this.btnABQuitAmount.setName("btnABQuitAmount");
        this.btnABTranAmount.setName("btnABTranAmount");
        this.btnABInvoice.setName("btnABInvoice");
        this.btnABRecycleInvoice.setName("btnABRecycleInvoice");
        this.btnGenTotalFin.setName("btnGenTotalFin");
        this.tblABActBill.setName("tblABActBill");
        this.contAAFilter.setName("contAAFilter");
        this.btnAASelect.setName("btnAASelect");
        this.panelAAActAmount.setName("panelAAActAmount");
        this.comboAAFilterValue.setName("comboAAFilterValue");
        this.pkAAFilterValue.setName("pkAAFilterValue");
        this.txtAAFilterValue.setName("txtAAFilterValue");
        this.comboAAFilter.setName("comboAAFilter");
        this.btnAAQuitAmount.setName("btnAAQuitAmount");
        this.btnAATranAmount.setName("btnAATranAmount");
        this.btnAAInvoice.setName("btnAAInvoice");
        this.btnAARecycleInvoice.setName("btnAARecycleInvoice");
        this.tblAAActAmount.setName("tblAAActAmount");
        this.contTPFilter.setName("contTPFilter");
        this.txtTPFilterValue.setName("txtTPFilterValue");
        this.btnTPSelect.setName("btnTPSelect");
        this.tblRevGather.setName("tblRevGather");
        this.btnTPAddBill.setName("btnTPAddBill");
        this.btnTPEditBill.setName("btnTPEditBill");
        this.btnTPRemoveBill.setName("btnTPRemoveBill");
        this.btnTPAuditBill.setName("btnTPAuditBill");
        this.btnTPUnAuditBill.setName("btnTPUnAuditBill");
        this.btnVoucher.setName("btnVoucher");
        this.btnDelVoucher.setName("btnDelVoucher");
        this.comboTPFilterValue.setName("comboTPFilterValue");
        this.comboTPFilter.setName("comboTPFilter");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));		
        this.tblMain.setVisible(false);
                this.tblMain.putBindContents("mainQuery",new String[] {""});


        // pnlMain		
        this.pnlMain.setDividerLocation(200);
        // treeView
        // panelTabPayment
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // panelChangeManage
        // panelPrePayment
        // panelEarnestMoney
        // panelRoomAmount
        // panelBankAmount
        // panelActBill
        // panelActAmount
        // panelTotalPayment
        // contCMFilter		
        this.contCMFilter.setBoundLabelText(resHelper.getString("contCMFilter.boundLabelText"));		
        this.contCMFilter.setBoundLabelLength(100);		
        this.contCMFilter.setBoundLabelUnderline(true);
        // btnCMSelete
        this.btnCMSelete.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCMSelete.setText(resHelper.getString("btnCMSelete.text"));
        // btnFinDeal
        this.btnFinDeal.setAction((IItemAction)ActionProxyFactory.getProxy(actionFinDeal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFinDeal.setText(resHelper.getString("btnFinDeal.text"));
        // btnMark
        this.btnMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMark.setText(resHelper.getString("btnMark.text"));
        // tblCMBill
		String tblCMBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dealState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"srcRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"srcCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"handler\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{dealState}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{srcRoom}</t:Cell><t:Cell>$Resource{srcCustomer}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{handler}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCMBill.setFormatXml(resHelper.translateString("tblCMBill",tblCMBillStrXML));
        this.tblCMBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCMBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // comboCMFilterValue
        // comboCMFilter
        this.comboCMFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCMFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contPPFilter		
        this.contPPFilter.setBoundLabelText(resHelper.getString("contPPFilter.boundLabelText"));		
        this.contPPFilter.setBoundLabelLength(100);		
        this.contPPFilter.setBoundLabelUnderline(true);
        // txtPPFilterValue
        // btnPPSelect
        this.btnPPSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPSelect.setText(resHelper.getString("btnPPSelect.text"));
        // panelPPActBill		
        this.panelPPActBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelPPActBill.border.title")));
        // panelPPActAmount		
        this.panelPPActAmount.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelPPActAmount.border.title")));
        // pkPPFilterValue
        // comboPPFilter
        this.comboPPFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPPFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblPPActBill
		String tblPPActBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"payPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{payPerson}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPPActBill.setFormatXml(resHelper.translateString("tblPPActBill",tblPPActBillStrXML));
        this.tblPPActBill.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblPPActBill_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblPPActBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPPActBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnPPQuitAmount
        this.btnPPQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPQuitAmount.setText(resHelper.getString("btnPPQuitAmount.text"));
        // btnPPTranAmount
        this.btnPPTranAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionTranAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPTranAmount.setText(resHelper.getString("btnPPTranAmount.text"));
        // btnPPInvoice
        this.btnPPInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPInvoice.setText(resHelper.getString("btnPPInvoice.text"));
        // btnPPRecycleInvoice
        this.btnPPRecycleInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycleInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPRecycleInvoice.setText(resHelper.getString("btnPPRecycleInvoice.text"));
        // btnPPReceiveAmount
        this.btnPPReceiveAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPPReceiveAmount.setText(resHelper.getString("btnPPReceiveAmount.text"));
        // tblPPActAmount
		String tblPPActAmountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"head\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"hasTransferredAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{head}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{hasTransferredAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPPActAmount.setFormatXml(resHelper.translateString("tblPPActAmount",tblPPActAmountStrXML));
        this.tblPPActAmount.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPPActAmount_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contEMFilter		
        this.contEMFilter.setBoundLabelText(resHelper.getString("contEMFilter.boundLabelText"));		
        this.contEMFilter.setBoundLabelLength(100);		
        this.contEMFilter.setBoundLabelUnderline(true);
        // txtEMFilterValue
        // btnEMSelect
        this.btnEMSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMSelect.setText(resHelper.getString("btnEMSelect.text"));
        // panelEMBill		
        this.panelEMBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelEMBill.border.title")));
        // panelTabEMActBill
        // btnEMQuitAmount
        this.btnEMQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMQuitAmount.setText(resHelper.getString("btnEMQuitAmount.text"));
        // btnEMTranAmount
        this.btnEMTranAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionTranAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMTranAmount.setText(resHelper.getString("btnEMTranAmount.text"));
        // btnEMInvoice
        this.btnEMInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMInvoice.setText(resHelper.getString("btnEMInvoice.text"));
        // btnEMRecycleInvoice
        this.btnEMRecycleInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycleInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMRecycleInvoice.setText(resHelper.getString("btnEMRecycleInvoice.text"));
        // kDUnGathering		
        this.kDUnGathering.setText(resHelper.getString("kDUnGathering.text"));
        // kDHasGathering		
        this.kDHasGathering.setText(resHelper.getString("kDHasGathering.text"));
        // kDHasRefundment		
        this.kDHasRefundment.setText(resHelper.getString("kDHasRefundment.text"));
        // kDHasTransfer		
        this.kDHasTransfer.setText(resHelper.getString("kDHasTransfer.text"));
        // btnEMReceiveAmount
        this.btnEMReceiveAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEMReceiveAmount.setText(resHelper.getString("btnEMReceiveAmount.text"));
        // comboEMFilter
        this.comboEMFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboEMFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblEMBill
		String tblEMBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"tranId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"revType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"tel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"invalidDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"projectNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{tranId}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{revType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{tel}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{invalidDate}</t:Cell><t:Cell>$Resource{projectNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEMBill.setFormatXml(resHelper.translateString("tblEMBill",tblEMBillStrXML));
        this.tblEMBill.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblEMBill_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblEMBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblEMBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // panelEMActBill
        // panelEMActAmount
        // tblEMActBill
		String tblEMActBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"payPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{payPerson}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblEMActBill.setFormatXml(resHelper.translateString("tblEMActBill",tblEMActBillStrXML));
        this.tblEMActBill.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblEMActBill_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblEMActBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblEMActBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblEMActAmount
		String tblEMActAmountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"head\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"hasTransferredAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{head}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{hasTransferredAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblEMActAmount.setFormatXml(resHelper.translateString("tblEMActAmount",tblEMActAmountStrXML));
        this.tblEMActAmount.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblEMActAmount_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contRAFilter		
        this.contRAFilter.setBoundLabelText(resHelper.getString("contRAFilter.boundLabelText"));		
        this.contRAFilter.setBoundLabelLength(100);		
        this.contRAFilter.setBoundLabelUnderline(true);
        // btnRASelect
        this.btnRASelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRASelect.setText(resHelper.getString("btnRASelect.text"));
        // contf7Room		
        this.contf7Room.setBoundLabelText(resHelper.getString("contf7Room.boundLabelText"));		
        this.contf7Room.setBoundLabelLength(100);		
        this.contf7Room.setBoundLabelUnderline(true);
        // contRelateBaseTran		
        this.contRelateBaseTran.setBoundLabelText(resHelper.getString("contRelateBaseTran.boundLabelText"));		
        this.contRelateBaseTran.setBoundLabelLength(100);		
        this.contRelateBaseTran.setBoundLabelUnderline(true);
        // btnRelateSaleBill
        this.btnRelateSaleBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelateSaleBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelateSaleBill.setText(resHelper.getString("btnRelateSaleBill.text"));
        // panelReceiveAmount		
        this.panelReceiveAmount.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelReceiveAmount.border.title")));
        // panelTabRAActBill
        // txtRAFilterValue
        // btnRAQuitAmount
        this.btnRAQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRAQuitAmount.setText(resHelper.getString("btnRAQuitAmount.text"));
        // btnRATranAmount
        this.btnRATranAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionTranAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRATranAmount.setText(resHelper.getString("btnRATranAmount.text"));
        // btnRAInvoice
        this.btnRAInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRAInvoice.setText(resHelper.getString("btnRAInvoice.text"));
        // btnRARecycleInvoice
        this.btnRARecycleInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycleInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRARecycleInvoice.setText(resHelper.getString("btnRARecycleInvoice.text"));
        // labCustomer1		
        this.labCustomer1.setOpaque(true);
        this.labCustomer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labCustomer1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator3
        // labCustomer2		
        this.labCustomer2.setOpaque(true);
        this.labCustomer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labCustomer2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator4
        // labCustomer3		
        this.labCustomer3.setOpaque(true);
        this.labCustomer3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labCustomer3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator5
        // labCustomer4		
        this.labCustomer4.setOpaque(true);
        this.labCustomer4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labCustomer4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator6
        // labCustomer5		
        this.labCustomer5.setOpaque(true);
        this.labCustomer5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labCustomer5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator7
        // btnRAReceiveAmount
        this.btnRAReceiveAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRAReceiveAmount.setText(resHelper.getString("btnRAReceiveAmount.text"));
        // comboRAFilter
        this.comboRAFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRAFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Room		
        this.f7Room.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");		
        this.f7Room.setCommitFormat("$number$");		
        this.f7Room.setEditFormat("$number$");		
        this.f7Room.setDisplayFormat("$name$");
        this.f7Room.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Room_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboRelateBaseTran
        this.comboRelateBaseTran.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRelateBaseTran_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblRABill
		String tblRABillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"mark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{mark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRABill.setFormatXml(resHelper.translateString("tblRABill",tblRABillStrXML));

        

        // panelRAActBill
        // panelRaActAmount
        // tblRAActBill
		String tblRAActBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"payPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{payPerson}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRAActBill.setFormatXml(resHelper.translateString("tblRAActBill",tblRAActBillStrXML));
        this.tblRAActBill.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblRAActBill_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblRAActBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRAActBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblRAActAmount
		String tblRAActAmountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"head\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"hasTransferredAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{head}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{hasTransferredAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRAActAmount.setFormatXml(resHelper.translateString("tblRAActAmount",tblRAActAmountStrXML));
        this.tblRAActAmount.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRAActAmount_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnBASelect
        this.btnBASelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBASelect.setText(resHelper.getString("btnBASelect.text"));
        // contBAFilter		
        this.contBAFilter.setBoundLabelText(resHelper.getString("contBAFilter.boundLabelText"));		
        this.contBAFilter.setBoundLabelLength(100);		
        this.contBAFilter.setBoundLabelUnderline(true);
        // txtBAFilterValue
        // panelBankAmountBill		
        this.panelBankAmountBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelBankAmountBill.border.title")));
        // comboBAFilter
        this.comboBAFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBAFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblBankAmountBill
		String tblBankAmountBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"bank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{bank}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBankAmountBill.setFormatXml(resHelper.translateString("tblBankAmountBill",tblBankAmountBillStrXML));
        this.tblBankAmountBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblBankAmountBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnAddBill
        this.btnAddBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddBill.setText(resHelper.getString("btnAddBill.text"));
        // btnEditBill
        this.btnEditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditBill.setText(resHelper.getString("btnEditBill.text"));
        // btnRemoveBill
        this.btnRemoveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveBill.setText(resHelper.getString("btnRemoveBill.text"));
        // btnAuditBill
        this.btnAuditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionAuditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAuditBill.setText(resHelper.getString("btnAuditBill.text"));
        // btnUnAuditBill
        this.btnUnAuditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAuditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAuditBill.setText(resHelper.getString("btnUnAuditBill.text"));
        // btnABSelect
        this.btnABSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABSelect.setText(resHelper.getString("btnABSelect.text"));
        // contABFilter		
        this.contABFilter.setBoundLabelText(resHelper.getString("contABFilter.boundLabelText"));		
        this.contABFilter.setBoundLabelLength(100);		
        this.contABFilter.setBoundLabelUnderline(true);
        // txtABFilterValue
        // panelABActBill		
        this.panelABActBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelABActBill.border.title")));
        // pkABFilterValue
        // comboABFilter		
        this.comboABFilter.setActionCommand("comboABFilter");
        this.comboABFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboABFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnABReceiveAmount
        this.btnABReceiveAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionReceiveAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABReceiveAmount.setText(resHelper.getString("btnABReceiveAmount.text"));
        // btnABQuitAmount
        this.btnABQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABQuitAmount.setText(resHelper.getString("btnABQuitAmount.text"));
        // btnABTranAmount
        this.btnABTranAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionTranAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABTranAmount.setText(resHelper.getString("btnABTranAmount.text"));
        // btnABInvoice
        this.btnABInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABInvoice.setText(resHelper.getString("btnABInvoice.text"));
        // btnABRecycleInvoice
        this.btnABRecycleInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycleInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnABRecycleInvoice.setText(resHelper.getString("btnABRecycleInvoice.text"));
        // btnGenTotalFin
        this.btnGenTotalFin.setAction((IItemAction)ActionProxyFactory.getProxy(actionGenTotalFin, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnGenTotalFin.setText(resHelper.getString("btnGenTotalFin.text"));
        // tblABActBill
		String tblABActBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"bizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"payPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{payPerson}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblABActBill.setFormatXml(resHelper.translateString("tblABActBill",tblABActBillStrXML));
        this.tblABActBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblABActBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contAAFilter		
        this.contAAFilter.setBoundLabelText(resHelper.getString("contAAFilter.boundLabelText"));		
        this.contAAFilter.setBoundLabelLength(100);		
        this.contAAFilter.setBoundLabelUnderline(true);
        // btnAASelect
        this.btnAASelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAASelect.setText(resHelper.getString("btnAASelect.text"));
        // panelAAActAmount		
        this.panelAAActAmount.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelAAActAmount.border.title")));
        // comboAAFilterValue
        // pkAAFilterValue
        // txtAAFilterValue
        // comboAAFilter
        this.comboAAFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboAAFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAAQuitAmount
        this.btnAAQuitAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAAQuitAmount.setText(resHelper.getString("btnAAQuitAmount.text"));
        // btnAATranAmount
        this.btnAATranAmount.setAction((IItemAction)ActionProxyFactory.getProxy(actionTranAmount, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAATranAmount.setText(resHelper.getString("btnAATranAmount.text"));
        // btnAAInvoice
        this.btnAAInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAAInvoice.setText(resHelper.getString("btnAAInvoice.text"));
        // btnAARecycleInvoice
        this.btnAARecycleInvoice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecycleInvoice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAARecycleInvoice.setText(resHelper.getString("btnAARecycleInvoice.text"));
        // tblAAActAmount
		String tblAAActAmountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"head\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"moneyDefineType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"appRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"actDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{head}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{moneyDefineType}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{appRevAmount}</t:Cell><t:Cell>$Resource{actDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAAActAmount.setFormatXml(resHelper.translateString("tblAAActAmount",tblAAActAmountStrXML));
        this.tblAAActAmount.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAAActAmount_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // contTPFilter		
        this.contTPFilter.setBoundLabelText(resHelper.getString("contTPFilter.boundLabelText"));		
        this.contTPFilter.setBoundLabelLength(100);		
        this.contTPFilter.setBoundLabelUnderline(true);
        // txtTPFilterValue
        // btnTPSelect
        this.btnTPSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPSelect.setText(resHelper.getString("btnTPSelect.text"));
        // tblRevGather
		String tblRevGatherStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"fiVouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"settlement\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"reAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{settlement}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell><t:Cell>$Resource{bank}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{reAccount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRevGather.setFormatXml(resHelper.translateString("tblRevGather",tblRevGatherStrXML));
        this.tblRevGather.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRevGather_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnTPAddBill
        this.btnTPAddBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPAddBill.setText(resHelper.getString("btnTPAddBill.text"));
        // btnTPEditBill
        this.btnTPEditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPEditBill.setText(resHelper.getString("btnTPEditBill.text"));
        // btnTPRemoveBill
        this.btnTPRemoveBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPRemoveBill.setText(resHelper.getString("btnTPRemoveBill.text"));
        // btnTPAuditBill
        this.btnTPAuditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionAuditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPAuditBill.setText(resHelper.getString("btnTPAuditBill.text"));
        // btnTPUnAuditBill
        this.btnTPUnAuditBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAuditBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTPUnAuditBill.setText(resHelper.getString("btnTPUnAuditBill.text"));
        // btnVoucher
        this.btnVoucher.setAction((IItemAction)ActionProxyFactory.getProxy(actionVoucher, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVoucher.setText(resHelper.getString("btnVoucher.text"));
        // btnDelVoucher
        this.btnDelVoucher.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelVoucher, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelVoucher.setText(resHelper.getString("btnDelVoucher.text"));
        // comboTPFilterValue
        // comboTPFilter
        this.comboTPFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboTPFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        pnlMain.setBounds(new Rectangle(7, 7, 997, 688));
        this.add(pnlMain, new KDLayout.Constraints(7, 7, 997, 688, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(panelTabPayment, "right");
        //treeView
        treeView.setTree(treeMain);
        //panelTabPayment
        panelTabPayment.add(panelChangeManage, resHelper.getString("panelChangeManage.constraints"));
        panelTabPayment.add(panelPrePayment, resHelper.getString("panelPrePayment.constraints"));
        panelTabPayment.add(panelEarnestMoney, resHelper.getString("panelEarnestMoney.constraints"));
        panelTabPayment.add(panelRoomAmount, resHelper.getString("panelRoomAmount.constraints"));
        panelTabPayment.add(panelBankAmount, resHelper.getString("panelBankAmount.constraints"));
        panelTabPayment.add(panelActBill, resHelper.getString("panelActBill.constraints"));
        panelTabPayment.add(panelActAmount, resHelper.getString("panelActAmount.constraints"));
        panelTabPayment.add(panelTotalPayment, resHelper.getString("panelTotalPayment.constraints"));
        //panelChangeManage
        panelChangeManage.setLayout(new KDLayout());
        panelChangeManage.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        tblMain.setBounds(new Rectangle(852, 607, 132, 36));
        panelChangeManage.add(tblMain, new KDLayout.Constraints(852, 607, 132, 36, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCMFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelChangeManage.add(contCMFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnCMSelete.setBounds(new Rectangle(400, 10, 120, 19));
        panelChangeManage.add(btnCMSelete, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnFinDeal.setBounds(new Rectangle(600, 10, 85, 19));
        panelChangeManage.add(btnFinDeal, new KDLayout.Constraints(600, 10, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnMark.setBounds(new Rectangle(693, 10, 85, 19));
        panelChangeManage.add(btnMark, new KDLayout.Constraints(693, 10, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblCMBill.setBounds(new Rectangle(10, 44, 768, 582));
        panelChangeManage.add(tblCMBill, new KDLayout.Constraints(10, 44, 768, 582, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        comboCMFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelChangeManage.add(comboCMFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCMFilter
        contCMFilter.setBoundEditor(comboCMFilter);
        //panelPrePayment
        panelPrePayment.setLayout(new KDLayout());
        panelPrePayment.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        contPPFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelPrePayment.add(contPPFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtPPFilterValue.setBounds(new Rectangle(220, 10, 168, 19));
        panelPrePayment.add(txtPPFilterValue, new KDLayout.Constraints(220, 10, 168, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPPSelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelPrePayment.add(btnPPSelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelPPActBill.setBounds(new Rectangle(10, 47, 769, 600));
        panelPrePayment.add(panelPPActBill, new KDLayout.Constraints(10, 47, 769, 600, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelPPActAmount.setBounds(new Rectangle(10, 292, 768, 319));
        panelPrePayment.add(panelPPActAmount, new KDLayout.Constraints(10, 292, 768, 319, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        pkPPFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelPrePayment.add(pkPPFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contPPFilter
        contPPFilter.setBoundEditor(comboPPFilter);
        //panelPPActBill
        panelPPActBill.setLayout(new KDLayout());
        panelPPActBill.putClientProperty("OriginalBounds", new Rectangle(10, 47, 769, 600));        tblPPActBill.setBounds(new Rectangle(12, 46, 742, 532));
        panelPPActBill.add(tblPPActBill, new KDLayout.Constraints(12, 46, 742, 532, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPPQuitAmount.setBounds(new Rectangle(669, 14, 85, 19));
        panelPPActBill.add(btnPPQuitAmount, new KDLayout.Constraints(669, 14, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPPTranAmount.setBounds(new Rectangle(479, 14, 85, 19));
        panelPPActBill.add(btnPPTranAmount, new KDLayout.Constraints(479, 14, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPPInvoice.setBounds(new Rectangle(574, 14, 85, 19));
        panelPPActBill.add(btnPPInvoice, new KDLayout.Constraints(574, 14, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnPPRecycleInvoice.setBounds(new Rectangle(669, 14, 85, 19));
        panelPPActBill.add(btnPPRecycleInvoice, new KDLayout.Constraints(669, 14, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnPPReceiveAmount.setBounds(new Rectangle(574, 14, 85, 19));
        panelPPActBill.add(btnPPReceiveAmount, new KDLayout.Constraints(574, 14, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelPPActAmount
        panelPPActAmount.setLayout(new KDLayout());
        panelPPActAmount.putClientProperty("OriginalBounds", new Rectangle(10, 292, 768, 319));        tblPPActAmount.setBounds(new Rectangle(12, 18, 742, 281));
        panelPPActAmount.add(tblPPActAmount, new KDLayout.Constraints(12, 18, 742, 281, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelEarnestMoney
        panelEarnestMoney.setLayout(new KDLayout());
        panelEarnestMoney.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        contEMFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelEarnestMoney.add(contEMFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtEMFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelEarnestMoney.add(txtEMFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMSelect.setBounds(new Rectangle(657, 10, 120, 19));
        panelEarnestMoney.add(btnEMSelect, new KDLayout.Constraints(657, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        panelEMBill.setBounds(new Rectangle(10, 48, 768, 221));
        panelEarnestMoney.add(panelEMBill, new KDLayout.Constraints(10, 48, 768, 221, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelTabEMActBill.setBounds(new Rectangle(10, 305, 768, 332));
        panelEarnestMoney.add(panelTabEMActBill, new KDLayout.Constraints(10, 305, 768, 332, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMQuitAmount.setBounds(new Rectangle(680, 276, 85, 19));
        panelEarnestMoney.add(btnEMQuitAmount, new KDLayout.Constraints(680, 276, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMTranAmount.setBounds(new Rectangle(500, 276, 85, 19));
        panelEarnestMoney.add(btnEMTranAmount, new KDLayout.Constraints(500, 276, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMInvoice.setBounds(new Rectangle(597, 276, 85, 19));
        panelEarnestMoney.add(btnEMInvoice, new KDLayout.Constraints(597, 276, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMRecycleInvoice.setBounds(new Rectangle(694, 276, 85, 19));
        panelEarnestMoney.add(btnEMRecycleInvoice, new KDLayout.Constraints(694, 276, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDUnGathering.setBounds(new Rectangle(397, 10, 60, 19));
        panelEarnestMoney.add(kDUnGathering, new KDLayout.Constraints(397, 10, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDHasGathering.setBounds(new Rectangle(462, 10, 60, 19));
        panelEarnestMoney.add(kDHasGathering, new KDLayout.Constraints(462, 10, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDHasRefundment.setBounds(new Rectangle(527, 10, 60, 19));
        panelEarnestMoney.add(kDHasRefundment, new KDLayout.Constraints(527, 10, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDHasTransfer.setBounds(new Rectangle(593, 10, 60, 19));
        panelEarnestMoney.add(kDHasTransfer, new KDLayout.Constraints(593, 10, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEMReceiveAmount.setBounds(new Rectangle(585, 276, 85, 19));
        panelEarnestMoney.add(btnEMReceiveAmount, new KDLayout.Constraints(585, 276, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contEMFilter
        contEMFilter.setBoundEditor(comboEMFilter);
        //panelEMBill
        panelEMBill.setLayout(new KDLayout());
        panelEMBill.putClientProperty("OriginalBounds", new Rectangle(10, 48, 768, 221));        tblEMBill.setBounds(new Rectangle(12, 18, 742, 188));
        panelEMBill.add(tblEMBill, new KDLayout.Constraints(12, 18, 742, 188, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelTabEMActBill
        panelTabEMActBill.add(panelEMActBill, resHelper.getString("panelEMActBill.constraints"));
        panelTabEMActBill.add(panelEMActAmount, resHelper.getString("panelEMActAmount.constraints"));
        //panelEMActBill
        panelEMActBill.setLayout(new KDLayout());
        panelEMActBill.putClientProperty("OriginalBounds", new Rectangle(0, 0, 767, 299));        tblEMActBill.setBounds(new Rectangle(6, 7, 742, 280));
        panelEMActBill.add(tblEMActBill, new KDLayout.Constraints(6, 7, 742, 280, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelEMActAmount
        panelEMActAmount.setLayout(new KDLayout());
        panelEMActAmount.putClientProperty("OriginalBounds", new Rectangle(0, 0, 767, 299));        tblEMActAmount.setBounds(new Rectangle(6, 7, 742, 281));
        panelEMActAmount.add(tblEMActAmount, new KDLayout.Constraints(6, 7, 742, 281, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelRoomAmount
        panelRoomAmount.setLayout(new KDLayout());
        panelRoomAmount.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        contRAFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelRoomAmount.add(contRAFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRASelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelRoomAmount.add(btnRASelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contf7Room.setBounds(new Rectangle(10, 37, 270, 19));
        panelRoomAmount.add(contf7Room, new KDLayout.Constraints(10, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRelateBaseTran.setBounds(new Rectangle(578, 10, 200, 19));
        panelRoomAmount.add(contRelateBaseTran, new KDLayout.Constraints(578, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnRelateSaleBill.setBounds(new Rectangle(658, 36, 120, 19));
        panelRoomAmount.add(btnRelateSaleBill, new KDLayout.Constraints(658, 36, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        panelReceiveAmount.setBounds(new Rectangle(10, 72, 768, 222));
        panelRoomAmount.add(panelReceiveAmount, new KDLayout.Constraints(10, 72, 768, 222, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelTabRAActBill.setBounds(new Rectangle(10, 344, 768, 288));
        panelRoomAmount.add(panelTabRAActBill, new KDLayout.Constraints(10, 344, 768, 288, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtRAFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelRoomAmount.add(txtRAFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRAQuitAmount.setBounds(new Rectangle(680, 303, 85, 19));
        panelRoomAmount.add(btnRAQuitAmount, new KDLayout.Constraints(680, 303, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRATranAmount.setBounds(new Rectangle(487, 303, 85, 19));
        panelRoomAmount.add(btnRATranAmount, new KDLayout.Constraints(487, 303, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRAInvoice.setBounds(new Rectangle(590, 303, 85, 19));
        panelRoomAmount.add(btnRAInvoice, new KDLayout.Constraints(590, 303, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRARecycleInvoice.setBounds(new Rectangle(693, 303, 85, 19));
        panelRoomAmount.add(btnRARecycleInvoice, new KDLayout.Constraints(693, 303, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer1.setBounds(new Rectangle(299, 37, 56, 19));
        panelRoomAmount.add(labCustomer1, new KDLayout.Constraints(299, 37, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator3.setBounds(new Rectangle(299, 56, 56, 10));
        panelRoomAmount.add(kDSeparator3, new KDLayout.Constraints(299, 56, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer2.setBounds(new Rectangle(360, 37, 56, 19));
        panelRoomAmount.add(labCustomer2, new KDLayout.Constraints(360, 37, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator4.setBounds(new Rectangle(360, 56, 56, 10));
        panelRoomAmount.add(kDSeparator4, new KDLayout.Constraints(360, 56, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer3.setBounds(new Rectangle(421, 37, 56, 19));
        panelRoomAmount.add(labCustomer3, new KDLayout.Constraints(421, 37, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator5.setBounds(new Rectangle(421, 56, 56, 10));
        panelRoomAmount.add(kDSeparator5, new KDLayout.Constraints(421, 56, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer4.setBounds(new Rectangle(482, 37, 56, 19));
        panelRoomAmount.add(labCustomer4, new KDLayout.Constraints(482, 37, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator6.setBounds(new Rectangle(482, 56, 56, 10));
        panelRoomAmount.add(kDSeparator6, new KDLayout.Constraints(482, 56, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer5.setBounds(new Rectangle(543, 37, 56, 19));
        panelRoomAmount.add(labCustomer5, new KDLayout.Constraints(543, 37, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator7.setBounds(new Rectangle(543, 56, 56, 10));
        panelRoomAmount.add(kDSeparator7, new KDLayout.Constraints(543, 56, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRAReceiveAmount.setBounds(new Rectangle(585, 303, 85, 19));
        panelRoomAmount.add(btnRAReceiveAmount, new KDLayout.Constraints(585, 303, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contRAFilter
        contRAFilter.setBoundEditor(comboRAFilter);
        //contf7Room
        contf7Room.setBoundEditor(f7Room);
        //contRelateBaseTran
        contRelateBaseTran.setBoundEditor(comboRelateBaseTran);
        //panelReceiveAmount
        panelReceiveAmount.setLayout(new KDLayout());
        panelReceiveAmount.putClientProperty("OriginalBounds", new Rectangle(10, 72, 768, 222));        tblRABill.setBounds(new Rectangle(12, 18, 743, 190));
        panelReceiveAmount.add(tblRABill, new KDLayout.Constraints(12, 18, 743, 190, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelTabRAActBill
        panelTabRAActBill.add(panelRAActBill, resHelper.getString("panelRAActBill.constraints"));
        panelTabRAActBill.add(panelRaActAmount, resHelper.getString("panelRaActAmount.constraints"));
        //panelRAActBill
        panelRAActBill.setLayout(new KDLayout());
        panelRAActBill.putClientProperty("OriginalBounds", new Rectangle(0, 0, 767, 255));        tblRAActBill.setBounds(new Rectangle(12, 12, 742, 234));
        panelRAActBill.add(tblRAActBill, new KDLayout.Constraints(12, 12, 742, 234, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelRaActAmount
        panelRaActAmount.setLayout(new KDLayout());
        panelRaActAmount.putClientProperty("OriginalBounds", new Rectangle(0, 0, 767, 255));        tblRAActAmount.setBounds(new Rectangle(12, 14, 742, 235));
        panelRaActAmount.add(tblRAActAmount, new KDLayout.Constraints(12, 14, 742, 235, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelBankAmount
        panelBankAmount.setLayout(new KDLayout());
        panelBankAmount.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        btnBASelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelBankAmount.add(btnBASelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBAFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelBankAmount.add(contBAFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtBAFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelBankAmount.add(txtBAFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelBankAmountBill.setBounds(new Rectangle(3, 46, 768, 583));
        panelBankAmount.add(panelBankAmountBill, new KDLayout.Constraints(3, 46, 768, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contBAFilter
        contBAFilter.setBoundEditor(comboBAFilter);
        //panelBankAmountBill
        panelBankAmountBill.setLayout(new KDLayout());
        panelBankAmountBill.putClientProperty("OriginalBounds", new Rectangle(3, 46, 768, 583));        tblBankAmountBill.setBounds(new Rectangle(12, 18, 740, 550));
        panelBankAmountBill.add(tblBankAmountBill, new KDLayout.Constraints(12, 18, 740, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddBill.setBounds(new Rectangle(286, 19, 85, 19));
        panelBankAmountBill.add(btnAddBill, new KDLayout.Constraints(286, 19, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnEditBill.setBounds(new Rectangle(381, 19, 85, 19));
        panelBankAmountBill.add(btnEditBill, new KDLayout.Constraints(381, 19, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveBill.setBounds(new Rectangle(476, 19, 85, 19));
        panelBankAmountBill.add(btnRemoveBill, new KDLayout.Constraints(476, 19, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAuditBill.setBounds(new Rectangle(571, 19, 85, 19));
        panelBankAmountBill.add(btnAuditBill, new KDLayout.Constraints(571, 19, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnUnAuditBill.setBounds(new Rectangle(669, 19, 85, 19));
        panelBankAmountBill.add(btnUnAuditBill, new KDLayout.Constraints(669, 19, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelActBill
        panelActBill.setLayout(new KDLayout());
        panelActBill.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        btnABSelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelActBill.add(btnABSelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contABFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelActBill.add(contABFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtABFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelActBill.add(txtABFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelABActBill.setBounds(new Rectangle(3, 46, 768, 583));
        panelActBill.add(panelABActBill, new KDLayout.Constraints(3, 46, 768, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        pkABFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelActBill.add(pkABFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contABFilter
        contABFilter.setBoundEditor(comboABFilter);
        //panelABActBill
        panelABActBill.setLayout(new KDLayout());
        panelABActBill.putClientProperty("OriginalBounds", new Rectangle(3, 46, 768, 583));        btnABReceiveAmount.setBounds(new Rectangle(168, 26, 85, 19));
        panelABActBill.add(btnABReceiveAmount, new KDLayout.Constraints(168, 26, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnABQuitAmount.setBounds(new Rectangle(261, 26, 85, 19));
        panelABActBill.add(btnABQuitAmount, new KDLayout.Constraints(261, 26, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnABTranAmount.setBounds(new Rectangle(354, 26, 85, 19));
        panelABActBill.add(btnABTranAmount, new KDLayout.Constraints(354, 26, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnABInvoice.setBounds(new Rectangle(447, 26, 85, 19));
        panelABActBill.add(btnABInvoice, new KDLayout.Constraints(447, 26, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnABRecycleInvoice.setBounds(new Rectangle(540, 26, 85, 19));
        panelABActBill.add(btnABRecycleInvoice, new KDLayout.Constraints(540, 26, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnGenTotalFin.setBounds(new Rectangle(634, 26, 120, 19));
        panelABActBill.add(btnGenTotalFin, new KDLayout.Constraints(634, 26, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblABActBill.setBounds(new Rectangle(12, 18, 740, 550));
        panelABActBill.add(tblABActBill, new KDLayout.Constraints(12, 18, 740, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelActAmount
        panelActAmount.setLayout(new KDLayout());
        panelActAmount.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        contAAFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelActAmount.add(contAAFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAASelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelActAmount.add(btnAASelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelAAActAmount.setBounds(new Rectangle(3, 46, 768, 583));
        panelActAmount.add(panelAAActAmount, new KDLayout.Constraints(3, 46, 768, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        comboAAFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelActAmount.add(comboAAFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        pkAAFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelActAmount.add(pkAAFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtAAFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelActAmount.add(txtAAFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contAAFilter
        contAAFilter.setBoundEditor(comboAAFilter);
        //panelAAActAmount
        panelAAActAmount.setLayout(new KDLayout());
        panelAAActAmount.putClientProperty("OriginalBounds", new Rectangle(3, 46, 768, 583));        btnAAQuitAmount.setBounds(new Rectangle(371, 22, 85, 19));
        panelAAActAmount.add(btnAAQuitAmount, new KDLayout.Constraints(371, 22, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAATranAmount.setBounds(new Rectangle(470, 22, 85, 19));
        panelAAActAmount.add(btnAATranAmount, new KDLayout.Constraints(470, 22, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAAInvoice.setBounds(new Rectangle(569, 22, 85, 19));
        panelAAActAmount.add(btnAAInvoice, new KDLayout.Constraints(569, 22, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAARecycleInvoice.setBounds(new Rectangle(669, 22, 85, 19));
        panelAAActAmount.add(btnAARecycleInvoice, new KDLayout.Constraints(669, 22, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblAAActAmount.setBounds(new Rectangle(12, 18, 740, 550));
        panelAAActAmount.add(tblAAActAmount, new KDLayout.Constraints(12, 18, 740, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelTotalPayment
        panelTotalPayment.setLayout(new KDLayout());
        panelTotalPayment.putClientProperty("OriginalBounds", new Rectangle(0, 0, 785, 654));        contTPFilter.setBounds(new Rectangle(10, 10, 200, 19));
        panelTotalPayment.add(contTPFilter, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtTPFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelTotalPayment.add(txtTPFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPSelect.setBounds(new Rectangle(400, 10, 120, 19));
        panelTotalPayment.add(btnTPSelect, new KDLayout.Constraints(400, 10, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblRevGather.setBounds(new Rectangle(5, 81, 768, 543));
        panelTotalPayment.add(tblRevGather, new KDLayout.Constraints(5, 81, 768, 543, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPAddBill.setBounds(new Rectangle(129, 46, 85, 19));
        panelTotalPayment.add(btnTPAddBill, new KDLayout.Constraints(129, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPEditBill.setBounds(new Rectangle(222, 46, 85, 19));
        panelTotalPayment.add(btnTPEditBill, new KDLayout.Constraints(222, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPRemoveBill.setBounds(new Rectangle(315, 46, 85, 19));
        panelTotalPayment.add(btnTPRemoveBill, new KDLayout.Constraints(315, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPAuditBill.setBounds(new Rectangle(408, 46, 85, 19));
        panelTotalPayment.add(btnTPAuditBill, new KDLayout.Constraints(408, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnTPUnAuditBill.setBounds(new Rectangle(501, 46, 85, 19));
        panelTotalPayment.add(btnTPUnAuditBill, new KDLayout.Constraints(501, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnVoucher.setBounds(new Rectangle(594, 46, 85, 19));
        panelTotalPayment.add(btnVoucher, new KDLayout.Constraints(594, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelVoucher.setBounds(new Rectangle(688, 46, 85, 19));
        panelTotalPayment.add(btnDelVoucher, new KDLayout.Constraints(688, 46, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        comboTPFilterValue.setBounds(new Rectangle(220, 10, 170, 19));
        panelTotalPayment.add(comboTPFilterValue, new KDLayout.Constraints(220, 10, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contTPFilter
        contTPFilter.setBoundEditor(comboTPFilter);

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PaymentManageUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblCMBill_tableClicked method
     */
    protected void tblCMBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboCMFilter_itemStateChanged method
     */
    protected void comboCMFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboPPFilter_itemStateChanged method
     */
    protected void comboPPFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblPPActBill_tableSelectChanged method
     */
    protected void tblPPActBill_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblPPActBill_tableClicked method
     */
    protected void tblPPActBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblPPActAmount_tableClicked method
     */
    protected void tblPPActAmount_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboEMFilter_itemStateChanged method
     */
    protected void comboEMFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblEMBill_tableSelectChanged method
     */
    protected void tblEMBill_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblEMBill_tableClicked method
     */
    protected void tblEMBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblEMActBill_tableSelectChanged method
     */
    protected void tblEMActBill_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblEMActBill_tableClicked method
     */
    protected void tblEMActBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblEMActAmount_tableClicked method
     */
    protected void tblEMActAmount_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output labCustomer1_mouseClicked method
     */
    protected void labCustomer1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labCustomer2_mouseClicked method
     */
    protected void labCustomer2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labCustomer3_mouseClicked method
     */
    protected void labCustomer3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labCustomer4_mouseClicked method
     */
    protected void labCustomer4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labCustomer5_mouseClicked method
     */
    protected void labCustomer5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output comboRAFilter_itemStateChanged method
     */
    protected void comboRAFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboRelateBaseTran_itemStateChanged method
     */
    protected void comboRelateBaseTran_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblRAActBill_tableSelectChanged method
     */
    protected void tblRAActBill_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblRAActBill_tableClicked method
     */
    protected void tblRAActBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblRAActAmount_tableClicked method
     */
    protected void tblRAActAmount_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboBAFilter_itemStateChanged method
     */
    protected void comboBAFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblBankAmountBill_tableClicked method
     */
    protected void tblBankAmountBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboABFilter_itemStateChanged method
     */
    protected void comboABFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblABActBill_tableClicked method
     */
    protected void tblABActBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboAAFilter_itemStateChanged method
     */
    protected void comboAAFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblAAActAmount_tableClicked method
     */
    protected void tblAAActAmount_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblRevGather_tableClicked method
     */
    protected void tblRevGather_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboTPFilter_itemStateChanged method
     */
    protected void comboTPFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionSelect_actionPerformed method
     */
    public void actionSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveAmount_actionPerformed method
     */
    public void actionReceiveAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitAmount_actionPerformed method
     */
    public void actionQuitAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTranAmount_actionPerformed method
     */
    public void actionTranAmount_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvoice_actionPerformed method
     */
    public void actionInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRecycleInvoice_actionPerformed method
     */
    public void actionRecycleInvoice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFinDeal_actionPerformed method
     */
    public void actionFinDeal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMark_actionPerformed method
     */
    public void actionMark_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRelateSaleBill_actionPerformed method
     */
    public void actionRelateSaleBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddBill_actionPerformed method
     */
    public void actionAddBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditBill_actionPerformed method
     */
    public void actionEditBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveBill_actionPerformed method
     */
    public void actionRemoveBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAuditBill_actionPerformed method
     */
    public void actionAuditBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAuditBill_actionPerformed method
     */
    public void actionUnAuditBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGenTotalFin_actionPerformed method
     */
    public void actionGenTotalFin_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVoucher_actionPerformed method
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelVoucher_actionPerformed method
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelect() {
    	return false;
    }
	public RequestContext prepareActionReceiveAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReceiveAmount() {
    	return false;
    }
	public RequestContext prepareActionQuitAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitAmount() {
    	return false;
    }
	public RequestContext prepareActionTranAmount(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTranAmount() {
    	return false;
    }
	public RequestContext prepareActionInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInvoice() {
    	return false;
    }
	public RequestContext prepareActionRecycleInvoice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRecycleInvoice() {
    	return false;
    }
	public RequestContext prepareActionFinDeal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFinDeal() {
    	return false;
    }
	public RequestContext prepareActionMark(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMark() {
    	return false;
    }
	public RequestContext prepareActionRelateSaleBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelateSaleBill() {
    	return false;
    }
	public RequestContext prepareActionAddBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddBill() {
    	return false;
    }
	public RequestContext prepareActionEditBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditBill() {
    	return false;
    }
	public RequestContext prepareActionRemoveBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveBill() {
    	return false;
    }
	public RequestContext prepareActionAuditBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAuditBill() {
    	return false;
    }
	public RequestContext prepareActionUnAuditBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAuditBill() {
    	return false;
    }
	public RequestContext prepareActionGenTotalFin(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGenTotalFin() {
    	return false;
    }
	public RequestContext prepareActionVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVoucher() {
    	return false;
    }
	public RequestContext prepareActionDelVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelVoucher() {
    	return false;
    }

    /**
     * output ActionSelect class
     */     
    protected class ActionSelect extends ItemAction {     
    
        public ActionSelect()
        {
            this(null);
        }

        public ActionSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionSelect", "actionSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveAmount class
     */     
    protected class ActionReceiveAmount extends ItemAction {     
    
        public ActionReceiveAmount()
        {
            this(null);
        }

        public ActionReceiveAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionReceiveAmount", "actionReceiveAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitAmount class
     */     
    protected class ActionQuitAmount extends ItemAction {     
    
        public ActionQuitAmount()
        {
            this(null);
        }

        public ActionQuitAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionQuitAmount", "actionQuitAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionTranAmount class
     */     
    protected class ActionTranAmount extends ItemAction {     
    
        public ActionTranAmount()
        {
            this(null);
        }

        public ActionTranAmount(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTranAmount.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTranAmount.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTranAmount.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionTranAmount", "actionTranAmount_actionPerformed", e);
        }
    }

    /**
     * output ActionInvoice class
     */     
    protected class ActionInvoice extends ItemAction {     
    
        public ActionInvoice()
        {
            this(null);
        }

        public ActionInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionInvoice", "actionInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionRecycleInvoice class
     */     
    protected class ActionRecycleInvoice extends ItemAction {     
    
        public ActionRecycleInvoice()
        {
            this(null);
        }

        public ActionRecycleInvoice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRecycleInvoice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecycleInvoice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecycleInvoice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionRecycleInvoice", "actionRecycleInvoice_actionPerformed", e);
        }
    }

    /**
     * output ActionFinDeal class
     */     
    protected class ActionFinDeal extends ItemAction {     
    
        public ActionFinDeal()
        {
            this(null);
        }

        public ActionFinDeal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFinDeal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinDeal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinDeal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionFinDeal", "actionFinDeal_actionPerformed", e);
        }
    }

    /**
     * output ActionMark class
     */     
    protected class ActionMark extends ItemAction {     
    
        public ActionMark()
        {
            this(null);
        }

        public ActionMark(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMark.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMark.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMark.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionMark", "actionMark_actionPerformed", e);
        }
    }

    /**
     * output ActionRelateSaleBill class
     */     
    protected class ActionRelateSaleBill extends ItemAction {     
    
        public ActionRelateSaleBill()
        {
            this(null);
        }

        public ActionRelateSaleBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRelateSaleBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelateSaleBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelateSaleBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionRelateSaleBill", "actionRelateSaleBill_actionPerformed", e);
        }
    }

    /**
     * output ActionAddBill class
     */     
    protected class ActionAddBill extends ItemAction {     
    
        public ActionAddBill()
        {
            this(null);
        }

        public ActionAddBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionAddBill", "actionAddBill_actionPerformed", e);
        }
    }

    /**
     * output ActionEditBill class
     */     
    protected class ActionEditBill extends ItemAction {     
    
        public ActionEditBill()
        {
            this(null);
        }

        public ActionEditBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionEditBill", "actionEditBill_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveBill class
     */     
    protected class ActionRemoveBill extends ItemAction {     
    
        public ActionRemoveBill()
        {
            this(null);
        }

        public ActionRemoveBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionRemoveBill", "actionRemoveBill_actionPerformed", e);
        }
    }

    /**
     * output ActionAuditBill class
     */     
    protected class ActionAuditBill extends ItemAction {     
    
        public ActionAuditBill()
        {
            this(null);
        }

        public ActionAuditBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAuditBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAuditBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionAuditBill", "actionAuditBill_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAuditBill class
     */     
    protected class ActionUnAuditBill extends ItemAction {     
    
        public ActionUnAuditBill()
        {
            this(null);
        }

        public ActionUnAuditBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAuditBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAuditBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAuditBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionUnAuditBill", "actionUnAuditBill_actionPerformed", e);
        }
    }

    /**
     * output ActionGenTotalFin class
     */     
    protected class ActionGenTotalFin extends ItemAction {     
    
        public ActionGenTotalFin()
        {
            this(null);
        }

        public ActionGenTotalFin(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionGenTotalFin.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGenTotalFin.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGenTotalFin.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionGenTotalFin", "actionGenTotalFin_actionPerformed", e);
        }
    }

    /**
     * output ActionVoucher class
     */     
    protected class ActionVoucher extends ItemAction {     
    
        public ActionVoucher()
        {
            this(null);
        }

        public ActionVoucher(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVoucher.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionVoucher", "actionVoucher_actionPerformed", e);
        }
    }

    /**
     * output ActionDelVoucher class
     */     
    protected class ActionDelVoucher extends ItemAction {     
    
        public ActionDelVoucher()
        {
            this(null);
        }

        public ActionDelVoucher(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelVoucher.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentManageUI.this, "ActionDelVoucher", "actionDelVoucher_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PaymentManageUI");
    }




}