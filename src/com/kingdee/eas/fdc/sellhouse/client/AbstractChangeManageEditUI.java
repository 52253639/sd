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
public abstract class AbstractChangeManageEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNUmber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHandler;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabInformation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModifier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModifyDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ChangeReson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Handler;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelChangeName;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelPriceChange;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane panelSourceBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelSoureCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRecebill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCNBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFeeAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7MoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labSourceCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSourceTel;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSourceCustomer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSourceCustomer2;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSourceCustomer3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSourceCustomer4;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator13;
    protected com.kingdee.bos.ctrl.swing.KDLabel labSourceCustomer5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator14;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSourceTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer2;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator11;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer4;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator15;
    protected com.kingdee.bos.ctrl.swing.KDLabel labCustomer5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator16;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayList;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCNBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQRMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQRFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQRActAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelQRPayList;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsSignChangeName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboQuitRoomType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtQuitAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7QRMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtQRFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtQRActAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQRPayList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCRMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCRFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCRActAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane panelNewBill;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChangeRoomType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7CRMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCRFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCRActAmount;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPriceChangeInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPCPayList;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panelPCBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPCChooseAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanningCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCashSalesCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanningArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPreArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValuationType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AgioScheme;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgio;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSellAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanningCompensate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCashSalesCompensate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAreaCompensate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractBuildPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanningArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboValuationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPCAddPayline;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPCInsertPayLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPCRemovePayLine;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPCPayList;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAttachPropertyInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelFitInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachPropertyTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSellType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAttachPropertyTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAPLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveAPLine;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsFitmentToContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFitmentAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FitmentStandard;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFitmentAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtModifier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkModifyDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea details;
    protected com.kingdee.eas.fdc.sellhouse.ChangeManageInfo editData = null;
    protected ActionSelectCustomer actionSelectCustomer = null;
    protected ActionChooseAgio actionChooseAgio = null;
    /**
     * output class constructor
     */
    public AbstractChangeManageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeManageEditUI.class.getName());
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
        //actionSelectCustomer
        this.actionSelectCustomer = new ActionSelectCustomer(this);
        getActionManager().registerAction("actionSelectCustomer", actionSelectCustomer);
         this.actionSelectCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChooseAgio
        this.actionChooseAgio = new ActionChooseAgio(this);
        getActionManager().registerAction("actionChooseAgio", actionChooseAgio);
         this.actionChooseAgio.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNUmber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHandler = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tabInformation = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModifier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModifyDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkChangeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBizType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7ChangeReson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Handler = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.panelChangeName = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelQuitRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelChangeRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelPriceChange = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelSourceBill = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panelSoureCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelRecebill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCNBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7MoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPaneRemark = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.labSourceCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSourceTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labSourceCustomer1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labSourceCustomer2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labSourceCustomer3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labSourceCustomer4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator13 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labSourceCustomer5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator14 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtSourceTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.labCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.labCustomer1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labCustomer2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labCustomer3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator11 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator15 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.labCustomer5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator16 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pkCNBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contQuitRoomType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuitAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQRMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQRFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQRActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panelQRPayList = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.cbIsSignChangeName = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.comboQuitRoomType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtQuitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7QRMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtQRFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtQRActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblQRPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contChangeRoomType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCRMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCRFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCRActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panelNewBill = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.comboChangeRoomType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7CRMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCRFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCRActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contPriceChangeInfo = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contPCPayList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.panelPCBaseInfo = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgioDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnPCChooseAgio = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanningCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCashSalesCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAreaCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanningArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPreArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValuationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizAdscriptionDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7AgioScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAgioDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSellAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanningCompensate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCashSalesCompensate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAreaCompensate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractBuildPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanningArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboValuationType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnPCAddPayline = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPCInsertPayLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPCRemovePayLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblPCPayList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtAFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.panelRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelAttachPropertyInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelFitInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSellType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachPropertyTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSellType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomModel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttachPropertyTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount1 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblAttachProperty = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddAPLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveAPLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsFitmentToContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contFitmentStandard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFitmentAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7FitmentStandard = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFitmentPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFitmentAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtModifier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkModifyDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.details = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contNUmber.setName("contNUmber");
        this.contChangeDate.setName("contChangeDate");
        this.contRoom.setName("contRoom");
        this.contBizType.setName("contBizType");
        this.contChangeReson.setName("contChangeReson");
        this.contHandler.setName("contHandler");
        this.tabInformation.setName("tabInformation");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditDate.setName("contAuditDate");
        this.contAuditor.setName("contAuditor");
        this.contModifier.setName("contModifier");
        this.contModifyDate.setName("contModifyDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.pkChangeDate.setName("pkChangeDate");
        this.f7Room.setName("f7Room");
        this.comboBizType.setName("comboBizType");
        this.f7ChangeReson.setName("f7ChangeReson");
        this.f7Handler.setName("f7Handler");
        this.panelChangeName.setName("panelChangeName");
        this.panelQuitRoom.setName("panelQuitRoom");
        this.panelChangeRoom.setName("panelChangeRoom");
        this.panelPriceChange.setName("panelPriceChange");
        this.panelSourceBill.setName("panelSourceBill");
        this.contActAmount.setName("contActAmount");
        this.contFeeAmount.setName("contFeeAmount");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contRemark.setName("contRemark");
        this.panelSoureCustomer.setName("panelSoureCustomer");
        this.panelCustomer.setName("panelCustomer");
        this.panelRecebill.setName("panelRecebill");
        this.contCNBizAdscriptionDate.setName("contCNBizAdscriptionDate");
        this.txtActAmount.setName("txtActAmount");
        this.txtFeeAmount.setName("txtFeeAmount");
        this.f7MoneyDefine.setName("f7MoneyDefine");
        this.scrollPaneRemark.setName("scrollPaneRemark");
        this.txtRemark.setName("txtRemark");
        this.labSourceCustomer.setName("labSourceCustomer");
        this.contSourceTel.setName("contSourceTel");
        this.labSourceCustomer1.setName("labSourceCustomer1");
        this.labSourceCustomer2.setName("labSourceCustomer2");
        this.labSourceCustomer3.setName("labSourceCustomer3");
        this.kDSeparator8.setName("kDSeparator8");
        this.labSourceCustomer4.setName("labSourceCustomer4");
        this.kDSeparator13.setName("kDSeparator13");
        this.labSourceCustomer5.setName("labSourceCustomer5");
        this.kDSeparator14.setName("kDSeparator14");
        this.txtSourceTel.setName("txtSourceTel");
        this.labCustomer.setName("labCustomer");
        this.contTel.setName("contTel");
        this.btnSelectCustomer.setName("btnSelectCustomer");
        this.labCustomer1.setName("labCustomer1");
        this.labCustomer2.setName("labCustomer2");
        this.labCustomer3.setName("labCustomer3");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDSeparator11.setName("kDSeparator11");
        this.labCustomer4.setName("labCustomer4");
        this.kDSeparator15.setName("kDSeparator15");
        this.labCustomer5.setName("labCustomer5");
        this.kDSeparator16.setName("kDSeparator16");
        this.txtTel.setName("txtTel");
        this.tblPayList.setName("tblPayList");
        this.pkCNBizAdscriptionDate.setName("pkCNBizAdscriptionDate");
        this.contQuitRoomType.setName("contQuitRoomType");
        this.contQuitAmount.setName("contQuitAmount");
        this.contQRMoneyDefine.setName("contQRMoneyDefine");
        this.contQRFeeAmount.setName("contQRFeeAmount");
        this.contQRActAmount.setName("contQRActAmount");
        this.panelQRPayList.setName("panelQRPayList");
        this.cbIsSignChangeName.setName("cbIsSignChangeName");
        this.comboQuitRoomType.setName("comboQuitRoomType");
        this.txtQuitAmount.setName("txtQuitAmount");
        this.f7QRMoneyDefine.setName("f7QRMoneyDefine");
        this.txtQRFeeAmount.setName("txtQRFeeAmount");
        this.txtQRActAmount.setName("txtQRActAmount");
        this.tblQRPayList.setName("tblQRPayList");
        this.contChangeRoomType.setName("contChangeRoomType");
        this.contCRMoneyDefine.setName("contCRMoneyDefine");
        this.contCRFeeAmount.setName("contCRFeeAmount");
        this.contCRActAmount.setName("contCRActAmount");
        this.panelNewBill.setName("panelNewBill");
        this.comboChangeRoomType.setName("comboChangeRoomType");
        this.f7CRMoneyDefine.setName("f7CRMoneyDefine");
        this.txtCRFeeAmount.setName("txtCRFeeAmount");
        this.txtCRActAmount.setName("txtCRActAmount");
        this.contPriceChangeInfo.setName("contPriceChangeInfo");
        this.contPCPayList.setName("contPCPayList");
        this.panelPCBaseInfo.setName("panelPCBaseInfo");
        this.contBizAdscriptionDate.setName("contBizAdscriptionDate");
        this.contBizDate.setName("contBizDate");
        this.contPayType.setName("contPayType");
        this.contAgioScheme.setName("contAgioScheme");
        this.contAgioDes.setName("contAgioDes");
        this.btnPCChooseAgio.setName("btnPCChooseAgio");
        this.contAgio.setName("contAgio");
        this.contDealTotalAmount.setName("contDealTotalAmount");
        this.contContractTotalAmount.setName("contContractTotalAmount");
        this.contSellAmount.setName("contSellAmount");
        this.contPlanningCompensate.setName("contPlanningCompensate");
        this.contCashSalesCompensate.setName("contCashSalesCompensate");
        this.contAreaCompensate.setName("contAreaCompensate");
        this.contDealBuildPrice.setName("contDealBuildPrice");
        this.contContractBuildPrice.setName("contContractBuildPrice");
        this.contPlanningArea.setName("contPlanningArea");
        this.contPreArea.setName("contPreArea");
        this.contActualArea.setName("contActualArea");
        this.contContractRoomPrice.setName("contContractRoomPrice");
        this.contDealRoomPrice.setName("contDealRoomPrice");
        this.contValuationType.setName("contValuationType");
        this.pkBizAdscriptionDate.setName("pkBizAdscriptionDate");
        this.pkBizDate.setName("pkBizDate");
        this.f7PayType.setName("f7PayType");
        this.f7AgioScheme.setName("f7AgioScheme");
        this.txtAgioDes.setName("txtAgioDes");
        this.txtAgio.setName("txtAgio");
        this.txtDealTotalAmount.setName("txtDealTotalAmount");
        this.txtContractTotalAmount.setName("txtContractTotalAmount");
        this.txtSellAmount.setName("txtSellAmount");
        this.txtPlanningCompensate.setName("txtPlanningCompensate");
        this.txtCashSalesCompensate.setName("txtCashSalesCompensate");
        this.txtAreaCompensate.setName("txtAreaCompensate");
        this.txtDealBuildPrice.setName("txtDealBuildPrice");
        this.txtContractBuildPrice.setName("txtContractBuildPrice");
        this.txtPlanningArea.setName("txtPlanningArea");
        this.txtPreArea.setName("txtPreArea");
        this.txtActualArea.setName("txtActualArea");
        this.txtContractRoomPrice.setName("txtContractRoomPrice");
        this.txtDealRoomPrice.setName("txtDealRoomPrice");
        this.comboValuationType.setName("comboValuationType");
        this.contAFundAmount.setName("contAFundAmount");
        this.contLoanAmount.setName("contLoanAmount");
        this.btnPCAddPayline.setName("btnPCAddPayline");
        this.btnPCInsertPayLine.setName("btnPCInsertPayLine");
        this.btnPCRemovePayLine.setName("btnPCRemovePayLine");
        this.tblPCPayList.setName("tblPCPayList");
        this.txtAFundAmount.setName("txtAFundAmount");
        this.txtLoanAmount.setName("txtLoanAmount");
        this.panelRoom.setName("panelRoom");
        this.panelAttachPropertyInfo.setName("panelAttachPropertyInfo");
        this.panelFitInfo.setName("panelFitInfo");
        this.contSellType.setName("contSellType");
        this.contBuildingArea.setName("contBuildingArea");
        this.contBuildingPrice.setName("contBuildingPrice");
        this.contRoomPrice.setName("contRoomPrice");
        this.contRoomArea.setName("contRoomArea");
        this.contRoomModel.setName("contRoomModel");
        this.contStandardTotalAmount.setName("contStandardTotalAmount");
        this.contAttachPropertyTotalAmount.setName("contAttachPropertyTotalAmount");
        this.contFitmentAmount1.setName("contFitmentAmount1");
        this.comboSellType.setName("comboSellType");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtBuildingPrice.setName("txtBuildingPrice");
        this.txtRoomPrice.setName("txtRoomPrice");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtRoomModel.setName("txtRoomModel");
        this.txtStandardTotalAmount.setName("txtStandardTotalAmount");
        this.txtAttachPropertyTotalAmount.setName("txtAttachPropertyTotalAmount");
        this.txtFitmentAmount1.setName("txtFitmentAmount1");
        this.tblAttachProperty.setName("tblAttachProperty");
        this.btnAddAPLine.setName("btnAddAPLine");
        this.btnRemoveAPLine.setName("btnRemoveAPLine");
        this.chkIsFitmentToContract.setName("chkIsFitmentToContract");
        this.contFitmentStandard.setName("contFitmentStandard");
        this.contFitmentPrice.setName("contFitmentPrice");
        this.contFitmentAmount.setName("contFitmentAmount");
        this.f7FitmentStandard.setName("f7FitmentStandard");
        this.txtFitmentPrice.setName("txtFitmentPrice");
        this.txtFitmentAmount.setName("txtFitmentAmount");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditDate.setName("pkAuditDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtModifier.setName("prmtModifier");
        this.pkModifyDate.setName("pkModifyDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.details.setName("details");
        // CoreUI		
        this.setPreferredSize(new Dimension(1200,800));
        // contNUmber		
        this.contNUmber.setBoundLabelText(resHelper.getString("contNUmber.boundLabelText"));		
        this.contNUmber.setBoundLabelLength(100);		
        this.contNUmber.setBoundLabelUnderline(true);
        // contChangeDate		
        this.contChangeDate.setBoundLabelText(resHelper.getString("contChangeDate.boundLabelText"));		
        this.contChangeDate.setBoundLabelLength(100);		
        this.contChangeDate.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contBizType		
        this.contBizType.setBoundLabelText(resHelper.getString("contBizType.boundLabelText"));		
        this.contBizType.setBoundLabelUnderline(true);		
        this.contBizType.setBoundLabelLength(100);
        // contChangeReson		
        this.contChangeReson.setBoundLabelText(resHelper.getString("contChangeReson.boundLabelText"));		
        this.contChangeReson.setBoundLabelLength(100);		
        this.contChangeReson.setBoundLabelUnderline(true);
        // contHandler		
        this.contHandler.setBoundLabelText(resHelper.getString("contHandler.boundLabelText"));		
        this.contHandler.setBoundLabelLength(100);		
        this.contHandler.setBoundLabelUnderline(true);		
        this.contHandler.setEnabled(false);
        // tabInformation
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(70);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(70);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(70);		
        this.contAuditDate.setBoundLabelUnderline(true);		
        this.contAuditDate.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(70);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contModifier		
        this.contModifier.setBoundLabelText(resHelper.getString("contModifier.boundLabelText"));		
        this.contModifier.setBoundLabelLength(70);		
        this.contModifier.setBoundLabelUnderline(true);		
        this.contModifier.setEnabled(false);
        // contModifyDate		
        this.contModifyDate.setBoundLabelText(resHelper.getString("contModifyDate.boundLabelText"));		
        this.contModifyDate.setBoundLabelLength(70);		
        this.contModifyDate.setBoundLabelUnderline(true);		
        this.contModifyDate.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkChangeDate		
        this.pkChangeDate.setRequired(true);
        // f7Room		
        this.f7Room.setRequired(true);		
        this.f7Room.setDisplayFormat("$name$");		
        this.f7Room.setEditFormat("$name$");		
        this.f7Room.setCommitFormat("$name$");		
        this.f7Room.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectQuery");
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
        // comboBizType		
        this.comboBizType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum").toArray());		
        this.comboBizType.setRequired(true);
        this.comboBizType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBizType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7ChangeReson		
        this.f7ChangeReson.setRequired(true);		
        this.f7ChangeReson.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChangeReasonQuery");		
        this.f7ChangeReson.setCommitFormat("$number$");		
        this.f7ChangeReson.setEditFormat("$number$");		
        this.f7ChangeReson.setDisplayFormat("$name$");
        // f7Handler		
        this.f7Handler.setEnabled(false);
        this.f7Handler.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Handler_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // panelChangeName
        // panelQuitRoom
        // panelChangeRoom
        // panelPriceChange		
        this.panelPriceChange.setAutoscrolls(true);		
        this.panelPriceChange.setPreferredSize(new Dimension(957,521));
        // panelSourceBill		
        this.panelSourceBill.setMinimumSize(new Dimension(10,10));		
        this.panelSourceBill.setPreferredSize(new Dimension(10,10));
        // contActAmount		
        this.contActAmount.setBoundLabelText(resHelper.getString("contActAmount.boundLabelText"));		
        this.contActAmount.setBoundLabelLength(100);		
        this.contActAmount.setBoundLabelUnderline(true);		
        this.contActAmount.setEnabled(false);
        // contFeeAmount		
        this.contFeeAmount.setBoundLabelText(resHelper.getString("contFeeAmount.boundLabelText"));		
        this.contFeeAmount.setBoundLabelLength(100);		
        this.contFeeAmount.setBoundLabelUnderline(true);		
        this.contFeeAmount.setEnabled(false);		
        this.contFeeAmount.setVisible(false);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);		
        this.contMoneyDefine.setVisible(false);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // panelSoureCustomer		
        this.panelSoureCustomer.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelSoureCustomer.border.title")));
        // panelCustomer		
        this.panelCustomer.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelCustomer.border.title")));
        // panelRecebill		
        this.panelRecebill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelRecebill.border.title")));
        // contCNBizAdscriptionDate		
        this.contCNBizAdscriptionDate.setBoundLabelText(resHelper.getString("contCNBizAdscriptionDate.boundLabelText"));		
        this.contCNBizAdscriptionDate.setBoundLabelLength(100);		
        this.contCNBizAdscriptionDate.setBoundLabelUnderline(true);
        // txtActAmount		
        this.txtActAmount.setDataType(1);		
        this.txtActAmount.setEnabled(false);
        // txtFeeAmount		
        this.txtFeeAmount.setDataType(1);
        // f7MoneyDefine		
        this.f7MoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7MoneyDefine.setCommitFormat("$number$");		
        this.f7MoneyDefine.setEditFormat("$number$");		
        this.f7MoneyDefine.setDisplayFormat("$name$");
        // scrollPaneRemark
        // txtRemark		
        this.txtRemark.setMaxLength(255);
        // labSourceCustomer		
        this.labSourceCustomer.setBoundLabelText(resHelper.getString("labSourceCustomer.boundLabelText"));		
        this.labSourceCustomer.setBoundLabelUnderline(true);		
        this.labSourceCustomer.setBoundLabelLength(30);
        // contSourceTel		
        this.contSourceTel.setBoundLabelText(resHelper.getString("contSourceTel.boundLabelText"));		
        this.contSourceTel.setBoundLabelLength(100);		
        this.contSourceTel.setBoundLabelUnderline(true);		
        this.contSourceTel.setEnabled(false);
        // labSourceCustomer1		
        this.labSourceCustomer1.setOpaque(true);
        this.labSourceCustomer1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labSourceCustomer1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // labSourceCustomer2		
        this.labSourceCustomer2.setOpaque(true);
        this.labSourceCustomer2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labSourceCustomer2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // labSourceCustomer3		
        this.labSourceCustomer3.setOpaque(true);
        this.labSourceCustomer3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labSourceCustomer3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator8
        // labSourceCustomer4		
        this.labSourceCustomer4.setOpaque(true);
        this.labSourceCustomer4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labSourceCustomer4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator13
        // labSourceCustomer5		
        this.labSourceCustomer5.setOpaque(true);
        this.labSourceCustomer5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    labSourceCustomer5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator14
        // txtSourceTel		
        this.txtSourceTel.setEnabled(false);
        // labCustomer		
        this.labCustomer.setBoundLabelText(resHelper.getString("labCustomer.boundLabelText"));		
        this.labCustomer.setBoundLabelUnderline(true);		
        this.labCustomer.setBoundLabelLength(30);
        // contTel		
        this.contTel.setBoundLabelText(resHelper.getString("contTel.boundLabelText"));		
        this.contTel.setBoundLabelLength(100);		
        this.contTel.setBoundLabelUnderline(true);		
        this.contTel.setEnabled(false);
        // btnSelectCustomer
        this.btnSelectCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectCustomer.setText(resHelper.getString("btnSelectCustomer.text"));
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
        // kDSeparator9
        // kDSeparator10
        // kDSeparator11
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
        // kDSeparator15
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
        // kDSeparator16
        // txtTel		
        this.txtTel.setEnabled(false);
        // tblPayList
		String tblPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPayList.setFormatXml(resHelper.translateString("tblPayList",tblPayListStrXML));

        

        this.tblPayList.checkParsed();
        // pkCNBizAdscriptionDate		
        this.pkCNBizAdscriptionDate.setRequired(true);
        // contQuitRoomType		
        this.contQuitRoomType.setBoundLabelText(resHelper.getString("contQuitRoomType.boundLabelText"));		
        this.contQuitRoomType.setBoundLabelUnderline(true);		
        this.contQuitRoomType.setBoundLabelLength(100);
        // contQuitAmount		
        this.contQuitAmount.setBoundLabelText(resHelper.getString("contQuitAmount.boundLabelText"));		
        this.contQuitAmount.setBoundLabelLength(100);		
        this.contQuitAmount.setBoundLabelUnderline(true);		
        this.contQuitAmount.setEnabled(false);
        // contQRMoneyDefine		
        this.contQRMoneyDefine.setBoundLabelText(resHelper.getString("contQRMoneyDefine.boundLabelText"));		
        this.contQRMoneyDefine.setBoundLabelLength(100);		
        this.contQRMoneyDefine.setBoundLabelUnderline(true);		
        this.contQRMoneyDefine.setVisible(false);
        // contQRFeeAmount		
        this.contQRFeeAmount.setBoundLabelText(resHelper.getString("contQRFeeAmount.boundLabelText"));		
        this.contQRFeeAmount.setBoundLabelLength(100);		
        this.contQRFeeAmount.setBoundLabelUnderline(true);		
        this.contQRFeeAmount.setEnabled(false);		
        this.contQRFeeAmount.setVisible(false);
        // contQRActAmount		
        this.contQRActAmount.setBoundLabelText(resHelper.getString("contQRActAmount.boundLabelText"));		
        this.contQRActAmount.setBoundLabelLength(100);		
        this.contQRActAmount.setBoundLabelUnderline(true);		
        this.contQRActAmount.setEnabled(false);
        // panelQRPayList		
        this.panelQRPayList.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelQRPayList.border.title")));
        // cbIsSignChangeName		
        this.cbIsSignChangeName.setText(resHelper.getString("cbIsSignChangeName.text"));
        // comboQuitRoomType		
        this.comboQuitRoomType.setRequired(true);		
        this.comboQuitRoomType.setEnabled(false);
        // txtQuitAmount		
        this.txtQuitAmount.setDataType(1);		
        this.txtQuitAmount.setEnabled(false);
        // f7QRMoneyDefine		
        this.f7QRMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7QRMoneyDefine.setCommitFormat("$number$");		
        this.f7QRMoneyDefine.setEditFormat("$number$");		
        this.f7QRMoneyDefine.setDisplayFormat("$name$");
        // txtQRFeeAmount		
        this.txtQRFeeAmount.setDataType(1);
        this.txtQRFeeAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtQRFeeAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtQRActAmount		
        this.txtQRActAmount.setDataType(1);		
        this.txtQRActAmount.setEnabled(false);
        // tblQRPayList
		String tblQRPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblQRPayList.setFormatXml(resHelper.translateString("tblQRPayList",tblQRPayListStrXML));

        

        this.tblQRPayList.checkParsed();
        // contChangeRoomType		
        this.contChangeRoomType.setBoundLabelText(resHelper.getString("contChangeRoomType.boundLabelText"));		
        this.contChangeRoomType.setBoundLabelUnderline(true);		
        this.contChangeRoomType.setBoundLabelLength(100);
        // contCRMoneyDefine		
        this.contCRMoneyDefine.setBoundLabelText(resHelper.getString("contCRMoneyDefine.boundLabelText"));		
        this.contCRMoneyDefine.setBoundLabelLength(100);		
        this.contCRMoneyDefine.setBoundLabelUnderline(true);		
        this.contCRMoneyDefine.setVisible(false);
        // contCRFeeAmount		
        this.contCRFeeAmount.setBoundLabelText(resHelper.getString("contCRFeeAmount.boundLabelText"));		
        this.contCRFeeAmount.setBoundLabelLength(100);		
        this.contCRFeeAmount.setBoundLabelUnderline(true);		
        this.contCRFeeAmount.setEnabled(false);		
        this.contCRFeeAmount.setVisible(false);
        // contCRActAmount		
        this.contCRActAmount.setBoundLabelText(resHelper.getString("contCRActAmount.boundLabelText"));		
        this.contCRActAmount.setBoundLabelLength(100);		
        this.contCRActAmount.setBoundLabelUnderline(true);		
        this.contCRActAmount.setEnabled(false);
        // panelNewBill
        // comboChangeRoomType		
        this.comboChangeRoomType.setRequired(true);		
        this.comboChangeRoomType.setEnabled(false);
        // f7CRMoneyDefine		
        this.f7CRMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7CRMoneyDefine.setCommitFormat("$number$");		
        this.f7CRMoneyDefine.setEditFormat("$number$");		
        this.f7CRMoneyDefine.setDisplayFormat("$name$");
        // txtCRFeeAmount		
        this.txtCRFeeAmount.setDataType(1);
        // txtCRActAmount		
        this.txtCRActAmount.setDataType(1);		
        this.txtCRActAmount.setEnabled(false);
        // contPriceChangeInfo		
        this.contPriceChangeInfo.setTitle(resHelper.getString("contPriceChangeInfo.title"));
        // contPCPayList		
        this.contPCPayList.setTitle(resHelper.getString("contPCPayList.title"));
        // panelPCBaseInfo
        // contBizAdscriptionDate		
        this.contBizAdscriptionDate.setBoundLabelText(resHelper.getString("contBizAdscriptionDate.boundLabelText"));		
        this.contBizAdscriptionDate.setBoundLabelLength(100);		
        this.contBizAdscriptionDate.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelLength(100);		
        this.contPayType.setBoundLabelUnderline(true);
        // contAgioScheme		
        this.contAgioScheme.setBoundLabelText(resHelper.getString("contAgioScheme.boundLabelText"));		
        this.contAgioScheme.setBoundLabelLength(100);		
        this.contAgioScheme.setBoundLabelUnderline(true);
        // contAgioDes		
        this.contAgioDes.setBoundLabelText(resHelper.getString("contAgioDes.boundLabelText"));		
        this.contAgioDes.setBoundLabelLength(100);		
        this.contAgioDes.setBoundLabelUnderline(true);		
        this.contAgioDes.setEnabled(false);
        // btnPCChooseAgio
        this.btnPCChooseAgio.setAction((IItemAction)ActionProxyFactory.getProxy(actionChooseAgio, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPCChooseAgio.setText(resHelper.getString("btnPCChooseAgio.text"));
        // contAgio		
        this.contAgio.setBoundLabelText(resHelper.getString("contAgio.boundLabelText"));		
        this.contAgio.setBoundLabelLength(100);		
        this.contAgio.setBoundLabelUnderline(true);		
        this.contAgio.setEnabled(false);
        // contDealTotalAmount		
        this.contDealTotalAmount.setBoundLabelText(resHelper.getString("contDealTotalAmount.boundLabelText"));		
        this.contDealTotalAmount.setBoundLabelLength(100);		
        this.contDealTotalAmount.setBoundLabelUnderline(true);		
        this.contDealTotalAmount.setEnabled(false);
        // contContractTotalAmount		
        this.contContractTotalAmount.setBoundLabelText(resHelper.getString("contContractTotalAmount.boundLabelText"));		
        this.contContractTotalAmount.setBoundLabelLength(100);		
        this.contContractTotalAmount.setBoundLabelUnderline(true);		
        this.contContractTotalAmount.setEnabled(false);
        // contSellAmount		
        this.contSellAmount.setBoundLabelText(resHelper.getString("contSellAmount.boundLabelText"));		
        this.contSellAmount.setBoundLabelLength(resHelper.getInt("contSellAmount.boundLabelLength"));		
        this.contSellAmount.setBoundLabelUnderline(resHelper.getBoolean("contSellAmount.boundLabelUnderline"));
        // contPlanningCompensate		
        this.contPlanningCompensate.setBoundLabelText(resHelper.getString("contPlanningCompensate.boundLabelText"));		
        this.contPlanningCompensate.setBoundLabelLength(resHelper.getInt("contPlanningCompensate.boundLabelLength"));		
        this.contPlanningCompensate.setBoundLabelUnderline(resHelper.getBoolean("contPlanningCompensate.boundLabelUnderline"));
        // contCashSalesCompensate		
        this.contCashSalesCompensate.setBoundLabelText(resHelper.getString("contCashSalesCompensate.boundLabelText"));		
        this.contCashSalesCompensate.setBoundLabelLength(resHelper.getInt("contCashSalesCompensate.boundLabelLength"));		
        this.contCashSalesCompensate.setBoundLabelUnderline(resHelper.getBoolean("contCashSalesCompensate.boundLabelUnderline"));
        // contAreaCompensate		
        this.contAreaCompensate.setBoundLabelText(resHelper.getString("contAreaCompensate.boundLabelText"));		
        this.contAreaCompensate.setBoundLabelLength(resHelper.getInt("contAreaCompensate.boundLabelLength"));		
        this.contAreaCompensate.setBoundLabelUnderline(resHelper.getBoolean("contAreaCompensate.boundLabelUnderline"));
        // contDealBuildPrice		
        this.contDealBuildPrice.setBoundLabelText(resHelper.getString("contDealBuildPrice.boundLabelText"));		
        this.contDealBuildPrice.setBoundLabelLength(100);		
        this.contDealBuildPrice.setBoundLabelUnderline(true);		
        this.contDealBuildPrice.setEnabled(false);		
        this.contDealBuildPrice.setVisible(false);
        // contContractBuildPrice		
        this.contContractBuildPrice.setBoundLabelText(resHelper.getString("contContractBuildPrice.boundLabelText"));		
        this.contContractBuildPrice.setBoundLabelLength(100);		
        this.contContractBuildPrice.setBoundLabelUnderline(true);		
        this.contContractBuildPrice.setEnabled(false);		
        this.contContractBuildPrice.setVisible(false);
        // contPlanningArea		
        this.contPlanningArea.setBoundLabelText(resHelper.getString("contPlanningArea.boundLabelText"));		
        this.contPlanningArea.setBoundLabelLength(resHelper.getInt("contPlanningArea.boundLabelLength"));		
        this.contPlanningArea.setBoundLabelUnderline(resHelper.getBoolean("contPlanningArea.boundLabelUnderline"));		
        this.contPlanningArea.setEnabled(false);		
        this.contPlanningArea.setVisible(false);
        // contPreArea		
        this.contPreArea.setBoundLabelText(resHelper.getString("contPreArea.boundLabelText"));		
        this.contPreArea.setBoundLabelLength(resHelper.getInt("contPreArea.boundLabelLength"));		
        this.contPreArea.setBoundLabelUnderline(resHelper.getBoolean("contPreArea.boundLabelUnderline"));		
        this.contPreArea.setVisible(false);		
        this.contPreArea.setEnabled(false);
        // contActualArea		
        this.contActualArea.setBoundLabelText(resHelper.getString("contActualArea.boundLabelText"));		
        this.contActualArea.setBoundLabelLength(resHelper.getInt("contActualArea.boundLabelLength"));		
        this.contActualArea.setBoundLabelUnderline(resHelper.getBoolean("contActualArea.boundLabelUnderline"));		
        this.contActualArea.setEnabled(false);		
        this.contActualArea.setVisible(false);
        // contContractRoomPrice		
        this.contContractRoomPrice.setBoundLabelText(resHelper.getString("contContractRoomPrice.boundLabelText"));		
        this.contContractRoomPrice.setBoundLabelLength(100);		
        this.contContractRoomPrice.setBoundLabelUnderline(true);		
        this.contContractRoomPrice.setEnabled(false);		
        this.contContractRoomPrice.setVisible(false);
        // contDealRoomPrice		
        this.contDealRoomPrice.setBoundLabelText(resHelper.getString("contDealRoomPrice.boundLabelText"));		
        this.contDealRoomPrice.setBoundLabelLength(100);		
        this.contDealRoomPrice.setBoundLabelUnderline(true);		
        this.contDealRoomPrice.setEnabled(false);		
        this.contDealRoomPrice.setVisible(false);
        // contValuationType		
        this.contValuationType.setBoundLabelText(resHelper.getString("contValuationType.boundLabelText"));		
        this.contValuationType.setBoundLabelLength(100);		
        this.contValuationType.setBoundLabelUnderline(true);		
        this.contValuationType.setVisible(false);		
        this.contValuationType.setEnabled(false);
        // pkBizAdscriptionDate		
        this.pkBizAdscriptionDate.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // f7PayType		
        this.f7PayType.setDisplayFormat("$name$");		
        this.f7PayType.setEditFormat("$number$");		
        this.f7PayType.setCommitFormat("$number$");		
        this.f7PayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");
        this.f7PayType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7PayType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7AgioScheme		
        this.f7AgioScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AgioSchemeQuery");		
        this.f7AgioScheme.setCommitFormat("$number$");		
        this.f7AgioScheme.setEditFormat("$number$");		
        this.f7AgioScheme.setDisplayFormat("$name$");
        this.f7AgioScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7AgioScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAgioDes		
        this.txtAgioDes.setEnabled(false);
        // txtAgio		
        this.txtAgio.setDataType(1);		
        this.txtAgio.setEnabled(false);
        // txtDealTotalAmount		
        this.txtDealTotalAmount.setDataType(1);		
        this.txtDealTotalAmount.setEnabled(false);		
        this.txtDealTotalAmount.setRequired(true);
        this.txtDealTotalAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtDealTotalAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtContractTotalAmount		
        this.txtContractTotalAmount.setDataType(1);		
        this.txtContractTotalAmount.setEnabled(false);
        // txtSellAmount		
        this.txtSellAmount.setDataType(resHelper.getInt("txtSellAmount.dataType"));		
        this.txtSellAmount.setEnabled(false);
        // txtPlanningCompensate		
        this.txtPlanningCompensate.setDataType(resHelper.getInt("txtPlanningCompensate.dataType"));		
        this.txtPlanningCompensate.setEnabled(false);
        // txtCashSalesCompensate		
        this.txtCashSalesCompensate.setDataType(resHelper.getInt("txtCashSalesCompensate.dataType"));		
        this.txtCashSalesCompensate.setEnabled(false);
        // txtAreaCompensate		
        this.txtAreaCompensate.setDataType(resHelper.getInt("txtAreaCompensate.dataType"));		
        this.txtAreaCompensate.setEnabled(false);
        // txtDealBuildPrice		
        this.txtDealBuildPrice.setDataType(1);		
        this.txtDealBuildPrice.setSupportedEmpty(true);		
        this.txtDealBuildPrice.setEnabled(false);		
        this.txtDealBuildPrice.setVisible(false);
        // txtContractBuildPrice		
        this.txtContractBuildPrice.setDataType(1);		
        this.txtContractBuildPrice.setEnabled(false);		
        this.txtContractBuildPrice.setVisible(false);
        // txtPlanningArea		
        this.txtPlanningArea.setDataType(resHelper.getInt("txtPlanningArea.dataType"));		
        this.txtPlanningArea.setEnabled(false);		
        this.txtPlanningArea.setVisible(false);
        // txtPreArea		
        this.txtPreArea.setDataType(resHelper.getInt("txtPreArea.dataType"));		
        this.txtPreArea.setEnabled(false);		
        this.txtPreArea.setVisible(false);
        // txtActualArea		
        this.txtActualArea.setEnabled(false);		
        this.txtActualArea.setVisible(false);
        // txtContractRoomPrice		
        this.txtContractRoomPrice.setDataType(1);		
        this.txtContractRoomPrice.setEnabled(false);		
        this.txtContractRoomPrice.setVisible(false);
        // txtDealRoomPrice		
        this.txtDealRoomPrice.setDataType(1);		
        this.txtDealRoomPrice.setEnabled(false);		
        this.txtDealRoomPrice.setVisible(false);
        // comboValuationType		
        this.comboValuationType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalcTypeEnum").toArray());		
        this.comboValuationType.setRequired(true);		
        this.comboValuationType.setEnabled(false);		
        this.comboValuationType.setVisible(false);
        // contAFundAmount		
        this.contAFundAmount.setBoundLabelText(resHelper.getString("contAFundAmount.boundLabelText"));		
        this.contAFundAmount.setBoundLabelLength(100);		
        this.contAFundAmount.setBoundLabelUnderline(true);		
        this.contAFundAmount.setEnabled(false);
        // contLoanAmount		
        this.contLoanAmount.setBoundLabelText(resHelper.getString("contLoanAmount.boundLabelText"));		
        this.contLoanAmount.setBoundLabelLength(100);		
        this.contLoanAmount.setBoundLabelUnderline(true);		
        this.contLoanAmount.setEnabled(false);
        // btnPCAddPayline
        this.btnPCAddPayline.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPCAddPayline.setText(resHelper.getString("btnPCAddPayline.text"));
        // btnPCInsertPayLine
        this.btnPCInsertPayLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionInsertLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPCInsertPayLine.setText(resHelper.getString("btnPCInsertPayLine.text"));
        // btnPCRemovePayLine
        this.btnPCRemovePayLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPCRemovePayLine.setText(resHelper.getString("btnPCRemovePayLine.text"));
        // tblPCPayList
		String tblPCPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblPCPayList.setFormatXml(resHelper.translateString("tblPCPayList",tblPCPayListStrXML));
        this.tblPCPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblPCPayList_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblPCPayList.checkParsed();
        // txtAFundAmount		
        this.txtAFundAmount.setDataType(1);		
        this.txtAFundAmount.setEnabled(false);
        // txtLoanAmount		
        this.txtLoanAmount.setDataType(1);		
        this.txtLoanAmount.setEnabled(false);
        // panelRoom
        // panelAttachPropertyInfo
        // panelFitInfo
        // contSellType		
        this.contSellType.setBoundLabelText(resHelper.getString("contSellType.boundLabelText"));		
        this.contSellType.setBoundLabelUnderline(true);		
        this.contSellType.setBoundLabelLength(100);		
        this.contSellType.setEnabled(false);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setEnabled(false);
        // contBuildingPrice		
        this.contBuildingPrice.setBoundLabelText(resHelper.getString("contBuildingPrice.boundLabelText"));		
        this.contBuildingPrice.setBoundLabelLength(100);		
        this.contBuildingPrice.setBoundLabelUnderline(true);		
        this.contBuildingPrice.setEnabled(false);
        // contRoomPrice		
        this.contRoomPrice.setBoundLabelText(resHelper.getString("contRoomPrice.boundLabelText"));		
        this.contRoomPrice.setBoundLabelLength(100);		
        this.contRoomPrice.setBoundLabelUnderline(true);		
        this.contRoomPrice.setEnabled(false);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelLength(100);		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setEnabled(false);
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);		
        this.contRoomModel.setEnabled(false);
        // contStandardTotalAmount		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contStandardTotalAmount.setBoundLabelLength(100);		
        this.contStandardTotalAmount.setBoundLabelUnderline(true);		
        this.contStandardTotalAmount.setEnabled(false);
        // contAttachPropertyTotalAmount		
        this.contAttachPropertyTotalAmount.setBoundLabelText(resHelper.getString("contAttachPropertyTotalAmount.boundLabelText"));		
        this.contAttachPropertyTotalAmount.setBoundLabelLength(100);		
        this.contAttachPropertyTotalAmount.setBoundLabelUnderline(true);		
        this.contAttachPropertyTotalAmount.setEnabled(false);
        // contFitmentAmount1		
        this.contFitmentAmount1.setBoundLabelText(resHelper.getString("contFitmentAmount1.boundLabelText"));		
        this.contFitmentAmount1.setBoundLabelLength(100);		
        this.contFitmentAmount1.setBoundLabelUnderline(true);
        // comboSellType		
        this.comboSellType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellTypeEnum").toArray());		
        this.comboSellType.setRequired(true);		
        this.comboSellType.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setDataType(1);		
        this.txtBuildingArea.setEnabled(false);
        // txtBuildingPrice		
        this.txtBuildingPrice.setEnabled(false);
        // txtRoomPrice		
        this.txtRoomPrice.setEnabled(false);
        // txtRoomArea		
        this.txtRoomArea.setDataType(1);		
        this.txtRoomArea.setEnabled(false);
        // txtRoomModel		
        this.txtRoomModel.setEnabled(false);
        // txtStandardTotalAmount		
        this.txtStandardTotalAmount.setDataType(1);		
        this.txtStandardTotalAmount.setEnabled(false);
        // txtAttachPropertyTotalAmount		
        this.txtAttachPropertyTotalAmount.setEnabled(false);
        // txtFitmentAmount1		
        this.txtFitmentAmount1.setDataType(1);		
        this.txtFitmentAmount1.setEnabled(false);
        // tblAttachProperty
		String tblAttachPropertyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"agio\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isMergeTocontract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"mergeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{agio}</t:Cell><t:Cell>$Resource{isMergeTocontract}</t:Cell><t:Cell>$Resource{mergeAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAttachProperty.setFormatXml(resHelper.translateString("tblAttachProperty",tblAttachPropertyStrXML));
        this.tblAttachProperty.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAttachProperty_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblAttachProperty.checkParsed();
        // btnAddAPLine
        this.btnAddAPLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddAPLine.setText(resHelper.getString("btnAddAPLine.text"));
        // btnRemoveAPLine
        this.btnRemoveAPLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveAPLine.setText(resHelper.getString("btnRemoveAPLine.text"));
        // chkIsFitmentToContract		
        this.chkIsFitmentToContract.setText(resHelper.getString("chkIsFitmentToContract.text"));
        this.chkIsFitmentToContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsFitmentToContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFitmentStandard		
        this.contFitmentStandard.setBoundLabelText(resHelper.getString("contFitmentStandard.boundLabelText"));		
        this.contFitmentStandard.setBoundLabelUnderline(true);		
        this.contFitmentStandard.setBoundLabelLength(100);
        // contFitmentPrice		
        this.contFitmentPrice.setBoundLabelText(resHelper.getString("contFitmentPrice.boundLabelText"));		
        this.contFitmentPrice.setBoundLabelLength(100);		
        this.contFitmentPrice.setBoundLabelUnderline(true);		
        this.contFitmentPrice.setEnabled(false);
        // contFitmentAmount		
        this.contFitmentAmount.setBoundLabelText(resHelper.getString("contFitmentAmount.boundLabelText"));		
        this.contFitmentAmount.setBoundLabelLength(100);		
        this.contFitmentAmount.setBoundLabelUnderline(true);
        // f7FitmentStandard		
        this.f7FitmentStandard.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.DecorationStandardQuery");		
        this.f7FitmentStandard.setCommitFormat("$number$");		
        this.f7FitmentStandard.setDisplayFormat("$name$");		
        this.f7FitmentStandard.setEditFormat("$number$");
        // txtFitmentPrice		
        this.txtFitmentPrice.setDataType(1);		
        this.txtFitmentPrice.setEnabled(false);
        // txtFitmentAmount		
        this.txtFitmentAmount.setDataType(1);
        this.txtFitmentAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtFitmentAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCreator		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setRequired(true);		
        this.pkCreateTime.setEnabled(false);
        // pkAuditDate		
        this.pkAuditDate.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setRequired(true);		
        this.prmtAuditor.setEnabled(false);
        // prmtModifier		
        this.prmtModifier.setRequired(true);		
        this.prmtModifier.setEnabled(false);
        // pkModifyDate		
        this.pkModifyDate.setEnabled(false);
        // kDScrollPane1
        // details
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
        contNUmber.setBounds(new Rectangle(20, 17, 270, 19));
        this.add(contNUmber, new KDLayout.Constraints(20, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeDate.setBounds(new Rectangle(370, 17, 270, 19));
        this.add(contChangeDate, new KDLayout.Constraints(370, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(20, 61, 620, 19));
        this.add(contRoom, new KDLayout.Constraints(20, 61, 620, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizType.setBounds(new Rectangle(690, 17, 270, 19));
        this.add(contBizType, new KDLayout.Constraints(690, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeReson.setBounds(new Rectangle(370, 39, 270, 19));
        this.add(contChangeReson, new KDLayout.Constraints(370, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHandler.setBounds(new Rectangle(20, 39, 270, 19));
        this.add(contHandler, new KDLayout.Constraints(20, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tabInformation.setBounds(new Rectangle(17, 101, 977, 533));
        this.add(tabInformation, new KDLayout.Constraints(17, 101, 977, 533, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(17, 645, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(17, 645, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(17, 668, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(17, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditDate.setBounds(new Rectangle(370, 668, 270, 19));
        this.add(contAuditDate, new KDLayout.Constraints(370, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(370, 645, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(370, 645, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModifier.setBounds(new Rectangle(724, 645, 270, 19));
        this.add(contModifier, new KDLayout.Constraints(724, 645, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contModifyDate.setBounds(new Rectangle(724, 668, 270, 19));
        this.add(contModifyDate, new KDLayout.Constraints(724, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(691, 39, 270, 41));
        this.add(kDLabelContainer1, new KDLayout.Constraints(691, 39, 270, 41, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNUmber
        contNUmber.setBoundEditor(txtNumber);
        //contChangeDate
        contChangeDate.setBoundEditor(pkChangeDate);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contBizType
        contBizType.setBoundEditor(comboBizType);
        //contChangeReson
        contChangeReson.setBoundEditor(f7ChangeReson);
        //contHandler
        contHandler.setBoundEditor(f7Handler);
        //tabInformation
        tabInformation.add(panelChangeName, resHelper.getString("panelChangeName.constraints"));
        tabInformation.add(panelQuitRoom, resHelper.getString("panelQuitRoom.constraints"));
        tabInformation.add(panelChangeRoom, resHelper.getString("panelChangeRoom.constraints"));
        tabInformation.add(panelPriceChange, resHelper.getString("panelPriceChange.constraints"));
        tabInformation.add(panelSourceBill, resHelper.getString("panelSourceBill.constraints"));
        //panelChangeName
        panelChangeName.setLayout(new KDLayout());
        panelChangeName.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 500));        contActAmount.setBounds(new Rectangle(16, 210, 270, 19));
        panelChangeName.add(contActAmount, new KDLayout.Constraints(16, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeAmount.setBounds(new Rectangle(688, 210, 270, 19));
        panelChangeName.add(contFeeAmount, new KDLayout.Constraints(688, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMoneyDefine.setBounds(new Rectangle(661, 210, 270, 19));
        panelChangeName.add(contMoneyDefine, new KDLayout.Constraints(661, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRemark.setBounds(new Rectangle(16, 240, 942, 42));
        panelChangeName.add(contRemark, new KDLayout.Constraints(16, 240, 942, 42, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelSoureCustomer.setBounds(new Rectangle(8, 8, 958, 88));
        panelChangeName.add(panelSoureCustomer, new KDLayout.Constraints(8, 8, 958, 88, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelCustomer.setBounds(new Rectangle(8, 112, 958, 88));
        panelChangeName.add(panelCustomer, new KDLayout.Constraints(8, 112, 958, 88, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelRecebill.setBounds(new Rectangle(9, 292, 958, 205));
        panelChangeName.add(panelRecebill, new KDLayout.Constraints(9, 292, 958, 205, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCNBizAdscriptionDate.setBounds(new Rectangle(352, 210, 270, 19));
        panelChangeName.add(contCNBizAdscriptionDate, new KDLayout.Constraints(352, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contActAmount
        contActAmount.setBoundEditor(txtActAmount);
        //contFeeAmount
        contFeeAmount.setBoundEditor(txtFeeAmount);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(f7MoneyDefine);
        //contRemark
        contRemark.setBoundEditor(scrollPaneRemark);
        //scrollPaneRemark
        scrollPaneRemark.getViewport().add(txtRemark, null);
        //panelSoureCustomer
        panelSoureCustomer.setLayout(new KDLayout());
        panelSoureCustomer.putClientProperty("OriginalBounds", new Rectangle(8, 8, 958, 88));        kDSeparator6.setBounds(new Rectangle(94, 38, 90, 10));
        panelSoureCustomer.add(kDSeparator6, new KDLayout.Constraints(94, 38, 90, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator7.setBounds(new Rectangle(206, 38, 90, 10));
        panelSoureCustomer.add(kDSeparator7, new KDLayout.Constraints(206, 38, 90, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSourceCustomer.setBounds(new Rectangle(21, 21, 30, 19));
        panelSoureCustomer.add(labSourceCustomer, new KDLayout.Constraints(21, 21, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSourceTel.setBounds(new Rectangle(21, 49, 612, 19));
        panelSoureCustomer.add(contSourceTel, new KDLayout.Constraints(21, 49, 612, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSourceCustomer1.setBounds(new Rectangle(94, 18, 90, 19));
        panelSoureCustomer.add(labSourceCustomer1, new KDLayout.Constraints(94, 18, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSourceCustomer2.setBounds(new Rectangle(206, 18, 90, 19));
        panelSoureCustomer.add(labSourceCustomer2, new KDLayout.Constraints(206, 18, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSourceCustomer3.setBounds(new Rectangle(318, 18, 90, 19));
        panelSoureCustomer.add(labSourceCustomer3, new KDLayout.Constraints(318, 18, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(318, 38, 90, 10));
        panelSoureCustomer.add(kDSeparator8, new KDLayout.Constraints(318, 38, 90, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSourceCustomer4.setBounds(new Rectangle(430, 18, 90, 19));
        panelSoureCustomer.add(labSourceCustomer4, new KDLayout.Constraints(430, 18, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator13.setBounds(new Rectangle(430, 38, 90, 10));
        panelSoureCustomer.add(kDSeparator13, new KDLayout.Constraints(430, 38, 90, 10, 0));
        labSourceCustomer5.setBounds(new Rectangle(543, 18, 90, 19));
        panelSoureCustomer.add(labSourceCustomer5, new KDLayout.Constraints(543, 18, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator14.setBounds(new Rectangle(543, 38, 90, 10));
        panelSoureCustomer.add(kDSeparator14, new KDLayout.Constraints(543, 38, 90, 10, 0));
        //contSourceTel
        contSourceTel.setBoundEditor(txtSourceTel);
        //panelCustomer
        panelCustomer.setLayout(new KDLayout());
        panelCustomer.putClientProperty("OriginalBounds", new Rectangle(8, 112, 958, 88));        labCustomer.setBounds(new Rectangle(21, 20, 30, 19));
        panelCustomer.add(labCustomer, new KDLayout.Constraints(21, 20, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTel.setBounds(new Rectangle(21, 48, 612, 19));
        panelCustomer.add(contTel, new KDLayout.Constraints(21, 48, 612, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSelectCustomer.setBounds(new Rectangle(723, 17, 85, 19));
        panelCustomer.add(btnSelectCustomer, new KDLayout.Constraints(723, 17, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        labCustomer1.setBounds(new Rectangle(95, 17, 90, 19));
        panelCustomer.add(labCustomer1, new KDLayout.Constraints(95, 17, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer2.setBounds(new Rectangle(207, 17, 90, 19));
        panelCustomer.add(labCustomer2, new KDLayout.Constraints(207, 17, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer3.setBounds(new Rectangle(319, 17, 90, 19));
        panelCustomer.add(labCustomer3, new KDLayout.Constraints(319, 17, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator9.setBounds(new Rectangle(95, 37, 90, 10));
        panelCustomer.add(kDSeparator9, new KDLayout.Constraints(95, 37, 90, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator10.setBounds(new Rectangle(207, 37, 90, 10));
        panelCustomer.add(kDSeparator10, new KDLayout.Constraints(207, 37, 90, 10, 0));
        kDSeparator11.setBounds(new Rectangle(319, 37, 90, 10));
        panelCustomer.add(kDSeparator11, new KDLayout.Constraints(319, 37, 90, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labCustomer4.setBounds(new Rectangle(430, 17, 90, 19));
        panelCustomer.add(labCustomer4, new KDLayout.Constraints(430, 17, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator15.setBounds(new Rectangle(430, 37, 90, 10));
        panelCustomer.add(kDSeparator15, new KDLayout.Constraints(430, 37, 90, 10, 0));
        labCustomer5.setBounds(new Rectangle(543, 17, 90, 19));
        panelCustomer.add(labCustomer5, new KDLayout.Constraints(543, 17, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator16.setBounds(new Rectangle(543, 37, 90, 10));
        panelCustomer.add(kDSeparator16, new KDLayout.Constraints(543, 37, 90, 10, 0));
        //contTel
        contTel.setBoundEditor(txtTel);
        //panelRecebill
        panelRecebill.setLayout(new KDLayout());
        panelRecebill.putClientProperty("OriginalBounds", new Rectangle(9, 292, 958, 205));        tblPayList.setBounds(new Rectangle(19, 18, 916, 167));
        panelRecebill.add(tblPayList, new KDLayout.Constraints(19, 18, 916, 167, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCNBizAdscriptionDate
        contCNBizAdscriptionDate.setBoundEditor(pkCNBizAdscriptionDate);
        //panelQuitRoom
        panelQuitRoom.setLayout(new KDLayout());
        panelQuitRoom.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 500));        contQuitRoomType.setBounds(new Rectangle(18, 27, 270, 19));
        panelQuitRoom.add(contQuitRoomType, new KDLayout.Constraints(18, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQuitAmount.setBounds(new Rectangle(687, 27, 270, 19));
        panelQuitRoom.add(contQuitAmount, new KDLayout.Constraints(687, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQRMoneyDefine.setBounds(new Rectangle(671, 47, 270, 19));
        panelQuitRoom.add(contQRMoneyDefine, new KDLayout.Constraints(671, 47, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQRFeeAmount.setBounds(new Rectangle(681, 45, 270, 19));
        panelQuitRoom.add(contQRFeeAmount, new KDLayout.Constraints(681, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQRActAmount.setBounds(new Rectangle(352, 27, 270, 19));
        panelQuitRoom.add(contQRActAmount, new KDLayout.Constraints(352, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelQRPayList.setBounds(new Rectangle(16, 69, 946, 421));
        panelQuitRoom.add(panelQRPayList, new KDLayout.Constraints(16, 69, 946, 421, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsSignChangeName.setBounds(new Rectangle(18, 5, 140, 19));
        panelQuitRoom.add(cbIsSignChangeName, new KDLayout.Constraints(18, 5, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contQuitRoomType
        contQuitRoomType.setBoundEditor(comboQuitRoomType);
        //contQuitAmount
        contQuitAmount.setBoundEditor(txtQuitAmount);
        //contQRMoneyDefine
        contQRMoneyDefine.setBoundEditor(f7QRMoneyDefine);
        //contQRFeeAmount
        contQRFeeAmount.setBoundEditor(txtQRFeeAmount);
        //contQRActAmount
        contQRActAmount.setBoundEditor(txtQRActAmount);
        //panelQRPayList
        panelQRPayList.setLayout(new KDLayout());
        panelQRPayList.putClientProperty("OriginalBounds", new Rectangle(16, 69, 946, 421));        tblQRPayList.setBounds(new Rectangle(17, 19, 908, 382));
        panelQRPayList.add(tblQRPayList, new KDLayout.Constraints(17, 19, 908, 382, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelChangeRoom
        panelChangeRoom.setLayout(new KDLayout());
        panelChangeRoom.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 500));        contChangeRoomType.setBounds(new Rectangle(28, 10, 270, 19));
        panelChangeRoom.add(contChangeRoomType, new KDLayout.Constraints(28, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCRMoneyDefine.setBounds(new Rectangle(615, 9, 270, 19));
        panelChangeRoom.add(contCRMoneyDefine, new KDLayout.Constraints(615, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCRFeeAmount.setBounds(new Rectangle(886, 10, 270, 19));
        panelChangeRoom.add(contCRFeeAmount, new KDLayout.Constraints(886, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCRActAmount.setBounds(new Rectangle(334, 10, 270, 19));
        panelChangeRoom.add(contCRActAmount, new KDLayout.Constraints(334, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelNewBill.setBounds(new Rectangle(3, 41, 966, 456));
        panelChangeRoom.add(panelNewBill, new KDLayout.Constraints(3, 41, 966, 456, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contChangeRoomType
        contChangeRoomType.setBoundEditor(comboChangeRoomType);
        //contCRMoneyDefine
        contCRMoneyDefine.setBoundEditor(f7CRMoneyDefine);
        //contCRFeeAmount
        contCRFeeAmount.setBoundEditor(txtCRFeeAmount);
        //contCRActAmount
        contCRActAmount.setBoundEditor(txtCRActAmount);
        //panelPriceChange
        panelPriceChange.setLayout(new KDLayout());
        panelPriceChange.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 500));        contPriceChangeInfo.setBounds(new Rectangle(12, 157, 945, 148));
        panelPriceChange.add(contPriceChangeInfo, new KDLayout.Constraints(12, 157, 945, 148, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPCPayList.setBounds(new Rectangle(12, 314, 945, 215));
        panelPriceChange.add(contPCPayList, new KDLayout.Constraints(12, 314, 945, 215, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panelPCBaseInfo.setBounds(new Rectangle(12, 6, 945, 145));
        panelPriceChange.add(panelPCBaseInfo, new KDLayout.Constraints(12, 6, 945, 145, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contPriceChangeInfo
        contPriceChangeInfo.getContentPane().setLayout(new KDLayout());
        contPriceChangeInfo.getContentPane().putClientProperty("OriginalBounds", new Rectangle(12, 157, 945, 148));        contBizAdscriptionDate.setBounds(new Rectangle(335, 12, 270, 19));
        contPriceChangeInfo.getContentPane().add(contBizAdscriptionDate, new KDLayout.Constraints(335, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(14, 12, 270, 19));
        contPriceChangeInfo.getContentPane().add(contBizDate, new KDLayout.Constraints(14, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayType.setBounds(new Rectangle(657, 12, 270, 19));
        contPriceChangeInfo.getContentPane().add(contPayType, new KDLayout.Constraints(657, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAgioScheme.setBounds(new Rectangle(14, 34, 270, 19));
        contPriceChangeInfo.getContentPane().add(contAgioScheme, new KDLayout.Constraints(14, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgioDes.setBounds(new Rectangle(14, 56, 483, 19));
        contPriceChangeInfo.getContentPane().add(contAgioDes, new KDLayout.Constraints(14, 56, 483, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPCChooseAgio.setBounds(new Rectangle(508, 56, 97, 19));
        contPriceChangeInfo.getContentPane().add(btnPCChooseAgio, new KDLayout.Constraints(508, 56, 97, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgio.setBounds(new Rectangle(657, 56, 270, 19));
        contPriceChangeInfo.getContentPane().add(contAgio, new KDLayout.Constraints(657, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDealTotalAmount.setBounds(new Rectangle(14, 77, 270, 19));
        contPriceChangeInfo.getContentPane().add(contDealTotalAmount, new KDLayout.Constraints(14, 77, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractTotalAmount.setBounds(new Rectangle(335, 77, 270, 19));
        contPriceChangeInfo.getContentPane().add(contContractTotalAmount, new KDLayout.Constraints(335, 77, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellAmount.setBounds(new Rectangle(657, 77, 270, 19));
        contPriceChangeInfo.getContentPane().add(contSellAmount, new KDLayout.Constraints(657, 77, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanningCompensate.setBounds(new Rectangle(14, 99, 270, 19));
        contPriceChangeInfo.getContentPane().add(contPlanningCompensate, new KDLayout.Constraints(14, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCashSalesCompensate.setBounds(new Rectangle(335, 99, 270, 19));
        contPriceChangeInfo.getContentPane().add(contCashSalesCompensate, new KDLayout.Constraints(335, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAreaCompensate.setBounds(new Rectangle(657, 99, 270, 19));
        contPriceChangeInfo.getContentPane().add(contAreaCompensate, new KDLayout.Constraints(657, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDealBuildPrice.setBounds(new Rectangle(186, 120, 270, 19));
        contPriceChangeInfo.getContentPane().add(contDealBuildPrice, new KDLayout.Constraints(186, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBuildPrice.setBounds(new Rectangle(0, 122, 270, 19));
        contPriceChangeInfo.getContentPane().add(contContractBuildPrice, new KDLayout.Constraints(0, 122, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanningArea.setBounds(new Rectangle(455, 121, 270, 19));
        contPriceChangeInfo.getContentPane().add(contPlanningArea, new KDLayout.Constraints(455, 121, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPreArea.setBounds(new Rectangle(505, 121, 270, 19));
        contPriceChangeInfo.getContentPane().add(contPreArea, new KDLayout.Constraints(505, 121, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActualArea.setBounds(new Rectangle(777, 120, 270, 19));
        contPriceChangeInfo.getContentPane().add(contActualArea, new KDLayout.Constraints(777, 120, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractRoomPrice.setBounds(new Rectangle(634, 37, 270, 19));
        contPriceChangeInfo.getContentPane().add(contContractRoomPrice, new KDLayout.Constraints(634, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDealRoomPrice.setBounds(new Rectangle(631, 35, 270, 19));
        contPriceChangeInfo.getContentPane().add(contDealRoomPrice, new KDLayout.Constraints(631, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contValuationType.setBounds(new Rectangle(615, 34, 270, 19));
        contPriceChangeInfo.getContentPane().add(contValuationType, new KDLayout.Constraints(615, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBizAdscriptionDate
        contBizAdscriptionDate.setBoundEditor(pkBizAdscriptionDate);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contAgioScheme
        contAgioScheme.setBoundEditor(f7AgioScheme);
        //contAgioDes
        contAgioDes.setBoundEditor(txtAgioDes);
        //contAgio
        contAgio.setBoundEditor(txtAgio);
        //contDealTotalAmount
        contDealTotalAmount.setBoundEditor(txtDealTotalAmount);
        //contContractTotalAmount
        contContractTotalAmount.setBoundEditor(txtContractTotalAmount);
        //contSellAmount
        contSellAmount.setBoundEditor(txtSellAmount);
        txtSellAmount.setLayout(null);        //contPlanningCompensate
        contPlanningCompensate.setBoundEditor(txtPlanningCompensate);
        txtPlanningCompensate.setLayout(null);        //contCashSalesCompensate
        contCashSalesCompensate.setBoundEditor(txtCashSalesCompensate);
        txtCashSalesCompensate.setLayout(null);        //contAreaCompensate
        contAreaCompensate.setBoundEditor(txtAreaCompensate);
        txtAreaCompensate.setLayout(null);        //contDealBuildPrice
        contDealBuildPrice.setBoundEditor(txtDealBuildPrice);
        //contContractBuildPrice
        contContractBuildPrice.setBoundEditor(txtContractBuildPrice);
        //contPlanningArea
        contPlanningArea.setBoundEditor(txtPlanningArea);
        txtPlanningArea.setLayout(null);        //contPreArea
        contPreArea.setBoundEditor(txtPreArea);
        txtPreArea.setLayout(null);        //contActualArea
        contActualArea.setBoundEditor(txtActualArea);
        txtActualArea.setLayout(null);        //contContractRoomPrice
        contContractRoomPrice.setBoundEditor(txtContractRoomPrice);
        //contDealRoomPrice
        contDealRoomPrice.setBoundEditor(txtDealRoomPrice);
        //contValuationType
        contValuationType.setBoundEditor(comboValuationType);
        //contPCPayList
        contPCPayList.getContentPane().setLayout(new KDLayout());
        contPCPayList.getContentPane().putClientProperty("OriginalBounds", new Rectangle(12, 314, 945, 215));        contAFundAmount.setBounds(new Rectangle(336, 6, 270, 19));
        contPCPayList.getContentPane().add(contAFundAmount, new KDLayout.Constraints(336, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLoanAmount.setBounds(new Rectangle(15, 6, 270, 19));
        contPCPayList.getContentPane().add(contLoanAmount, new KDLayout.Constraints(15, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPCAddPayline.setBounds(new Rectangle(660, 6, 80, 19));
        contPCPayList.getContentPane().add(btnPCAddPayline, new KDLayout.Constraints(660, 6, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPCInsertPayLine.setBounds(new Rectangle(753, 6, 80, 19));
        contPCPayList.getContentPane().add(btnPCInsertPayLine, new KDLayout.Constraints(753, 6, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPCRemovePayLine.setBounds(new Rectangle(847, 6, 80, 19));
        contPCPayList.getContentPane().add(btnPCRemovePayLine, new KDLayout.Constraints(847, 6, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblPCPayList.setBounds(new Rectangle(7, 33, 920, 157));
        contPCPayList.getContentPane().add(tblPCPayList, new KDLayout.Constraints(7, 33, 920, 157, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contAFundAmount
        contAFundAmount.setBoundEditor(txtAFundAmount);
        //contLoanAmount
        contLoanAmount.setBoundEditor(txtLoanAmount);
        //panelPCBaseInfo
        panelPCBaseInfo.add(panelRoom, resHelper.getString("panelRoom.constraints"));
        panelPCBaseInfo.add(panelAttachPropertyInfo, resHelper.getString("panelAttachPropertyInfo.constraints"));
        panelPCBaseInfo.add(panelFitInfo, resHelper.getString("panelFitInfo.constraints"));
        //panelRoom
        panelRoom.setLayout(new KDLayout());
        panelRoom.putClientProperty("OriginalBounds", new Rectangle(0, 0, 944, 112));        contSellType.setBounds(new Rectangle(12, 15, 270, 19));
        panelRoom.add(contSellType, new KDLayout.Constraints(12, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingArea.setBounds(new Rectangle(12, 37, 270, 19));
        panelRoom.add(contBuildingArea, new KDLayout.Constraints(12, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingPrice.setBounds(new Rectangle(12, 59, 270, 19));
        panelRoom.add(contBuildingPrice, new KDLayout.Constraints(12, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomPrice.setBounds(new Rectangle(333, 59, 270, 19));
        panelRoom.add(contRoomPrice, new KDLayout.Constraints(333, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(333, 37, 270, 19));
        panelRoom.add(contRoomArea, new KDLayout.Constraints(333, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(333, 15, 270, 19));
        panelRoom.add(contRoomModel, new KDLayout.Constraints(333, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardTotalAmount.setBounds(new Rectangle(655, 15, 270, 19));
        panelRoom.add(contStandardTotalAmount, new KDLayout.Constraints(655, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAttachPropertyTotalAmount.setBounds(new Rectangle(655, 37, 270, 19));
        panelRoom.add(contAttachPropertyTotalAmount, new KDLayout.Constraints(655, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFitmentAmount1.setBounds(new Rectangle(655, 59, 270, 19));
        panelRoom.add(contFitmentAmount1, new KDLayout.Constraints(655, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSellType
        contSellType.setBoundEditor(comboSellType);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contBuildingPrice
        contBuildingPrice.setBoundEditor(txtBuildingPrice);
        //contRoomPrice
        contRoomPrice.setBoundEditor(txtRoomPrice);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contRoomModel
        contRoomModel.setBoundEditor(txtRoomModel);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contAttachPropertyTotalAmount
        contAttachPropertyTotalAmount.setBoundEditor(txtAttachPropertyTotalAmount);
        //contFitmentAmount1
        contFitmentAmount1.setBoundEditor(txtFitmentAmount1);
        //panelAttachPropertyInfo
        panelAttachPropertyInfo.setLayout(new KDLayout());
        panelAttachPropertyInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 944, 112));        tblAttachProperty.setBounds(new Rectangle(2, 2, 933, 108));
        panelAttachPropertyInfo.add(tblAttachProperty, new KDLayout.Constraints(2, 2, 933, 108, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddAPLine.setBounds(new Rectangle(755, 6, 73, 19));
        panelAttachPropertyInfo.add(btnAddAPLine, new KDLayout.Constraints(755, 6, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveAPLine.setBounds(new Rectangle(856, 6, 75, 19));
        panelAttachPropertyInfo.add(btnRemoveAPLine, new KDLayout.Constraints(856, 6, 75, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelFitInfo
        panelFitInfo.setLayout(new KDLayout());
        panelFitInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 944, 112));        chkIsFitmentToContract.setBounds(new Rectangle(12, 54, 169, 19));
        panelFitInfo.add(chkIsFitmentToContract, new KDLayout.Constraints(12, 54, 169, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentStandard.setBounds(new Rectangle(12, 16, 270, 19));
        panelFitInfo.add(contFitmentStandard, new KDLayout.Constraints(12, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentPrice.setBounds(new Rectangle(354, 16, 270, 19));
        panelFitInfo.add(contFitmentPrice, new KDLayout.Constraints(354, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentAmount.setBounds(new Rectangle(653, 16, 270, 19));
        panelFitInfo.add(contFitmentAmount, new KDLayout.Constraints(653, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(f7FitmentStandard);
        //contFitmentPrice
        contFitmentPrice.setBoundEditor(txtFitmentPrice);
        //contFitmentAmount
        contFitmentAmount.setBoundEditor(txtFitmentAmount);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditDate
        contAuditDate.setBoundEditor(pkAuditDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contModifier
        contModifier.setBoundEditor(prmtModifier);
        //contModifyDate
        contModifyDate.setBoundEditor(pkModifyDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(details, null);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
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
		dataBinder.registerBinding("changeDate", java.util.Date.class, this.pkChangeDate, "value");
		dataBinder.registerBinding("srcRoom", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.f7Room, "data");
		dataBinder.registerBinding("bizType", com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum.class, this.comboBizType, "selectedItem");
		dataBinder.registerBinding("changeReason", com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo.class, this.f7ChangeReson, "data");
		dataBinder.registerBinding("handler", com.kingdee.eas.base.permission.UserInfo.class, this.f7Handler, "data");
		dataBinder.registerBinding("description", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("customerPhone", String.class, this.txtTel, "text");
		dataBinder.registerBinding("isSignChangeName", boolean.class, this.cbIsSignChangeName, "selected");
		dataBinder.registerBinding("quitAmount", java.math.BigDecimal.class, this.txtQuitAmount, "value");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("payType", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.f7PayType, "data");
		dataBinder.registerBinding("agioScheme", com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo.class, this.f7AgioScheme, "data");
		dataBinder.registerBinding("agioDesc", String.class, this.txtAgioDes, "text");
		dataBinder.registerBinding("lastAgio", java.math.BigDecimal.class, this.txtAgio, "value");
		dataBinder.registerBinding("dealTotalAmount", java.math.BigDecimal.class, this.txtDealTotalAmount, "value");
		dataBinder.registerBinding("contractTotalAmount", java.math.BigDecimal.class, this.txtContractTotalAmount, "value");
		dataBinder.registerBinding("sellAmount", java.math.BigDecimal.class, this.txtSellAmount, "value");
		dataBinder.registerBinding("planningCompensate", java.math.BigDecimal.class, this.txtPlanningCompensate, "value");
		dataBinder.registerBinding("cashSalesCompensate", java.math.BigDecimal.class, this.txtCashSalesCompensate, "value");
		dataBinder.registerBinding("areaCompensate", java.math.BigDecimal.class, this.txtAreaCompensate, "value");
		dataBinder.registerBinding("dealBuildPrice", java.math.BigDecimal.class, this.txtDealBuildPrice, "value");
		dataBinder.registerBinding("contractBuildPrice", java.math.BigDecimal.class, this.txtContractBuildPrice, "value");
		dataBinder.registerBinding("planningArea", java.math.BigDecimal.class, this.txtPlanningArea, "value");
		dataBinder.registerBinding("preArea", java.math.BigDecimal.class, this.txtPreArea, "value");
		dataBinder.registerBinding("actualArea", java.math.BigDecimal.class, this.txtActualArea, "value");
		dataBinder.registerBinding("contractRoomPrice", java.math.BigDecimal.class, this.txtContractRoomPrice, "value");
		dataBinder.registerBinding("dealRoomPrice", java.math.BigDecimal.class, this.txtDealRoomPrice, "value");
		dataBinder.registerBinding("valuationType", com.kingdee.eas.fdc.sellhouse.CalcTypeEnum.class, this.comboValuationType, "selectedItem");
		dataBinder.registerBinding("accFundAmount", java.math.BigDecimal.class, this.txtAFundAmount, "value");
		dataBinder.registerBinding("loanAmount", java.math.BigDecimal.class, this.txtLoanAmount, "value");
		dataBinder.registerBinding("sellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.comboSellType, "selectedItem");
		dataBinder.registerBinding("strdBuildingPrice", java.math.BigDecimal.class, this.txtBuildingPrice, "value");
		dataBinder.registerBinding("strdRoomPrice", java.math.BigDecimal.class, this.txtRoomPrice, "value");
		dataBinder.registerBinding("srcRoom.roomModel.name", String.class, this.txtRoomModel, "text");
		dataBinder.registerBinding("strdTotalAmount", java.math.BigDecimal.class, this.txtStandardTotalAmount, "value");
		dataBinder.registerBinding("attachmentAmount", java.math.BigDecimal.class, this.txtAttachPropertyTotalAmount, "value");
		dataBinder.registerBinding("fitmentTotalAmount", java.math.BigDecimal.class, this.txtFitmentAmount1, "value");
		dataBinder.registerBinding("isFitmentToContract", boolean.class, this.chkIsFitmentToContract, "selected");
		dataBinder.registerBinding("fitmentStandard", com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo.class, this.f7FitmentStandard, "data");
		dataBinder.registerBinding("fitmentPrice", java.math.BigDecimal.class, this.txtFitmentPrice, "value");
		dataBinder.registerBinding("fitmentTotalAmount", java.math.BigDecimal.class, this.txtFitmentAmount, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtModifier, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkModifyDate, "value");
		dataBinder.registerBinding("details", String.class, this.details, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChangeManageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChangeManageInfo)ov;
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
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
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
		getValidateHelper().registerBindProperty("changeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcRoom", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handler", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSignChangeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quitAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agioScheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agioDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastAgio", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planningCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cashSalesCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("areaCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planningArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valuationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accFundAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdBuildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcRoom.roomModel.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFitmentToContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("details", ValidateHelper.ON_SAVE);    		
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
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboBizType_itemStateChanged method
     */
    protected void comboBizType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7Handler_dataChanged method
     */
    protected void f7Handler_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output labSourceCustomer1_mouseClicked method
     */
    protected void labSourceCustomer1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labSourceCustomer2_mouseClicked method
     */
    protected void labSourceCustomer2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labSourceCustomer3_mouseClicked method
     */
    protected void labSourceCustomer3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labSourceCustomer4_mouseClicked method
     */
    protected void labSourceCustomer4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output labSourceCustomer5_mouseClicked method
     */
    protected void labSourceCustomer5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
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
     * output txtQRFeeAmount_dataChanged method
     */
    protected void txtQRFeeAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7PayType_dataChanged method
     */
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7AgioScheme_dataChanged method
     */
    protected void f7AgioScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealTotalAmount_dataChanged method
     */
    protected void txtDealTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealBuildPrice_actionPerformed method
     */
    protected void txtDealBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtContractBuildPrice_actionPerformed method
     */
    protected void txtContractBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboValuationType_itemStateChanged method
     */
    protected void comboValuationType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblPCPayList_editStopped method
     */
    protected void tblPCPayList_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblAttachProperty_editStopped method
     */
    protected void tblAttachProperty_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output chkIsFitmentToContract_actionPerformed method
     */
    protected void chkIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtFitmentAmount_dataChanged method
     */
    protected void txtFitmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("changeDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("srcRoom.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("srcRoom.id"));
        	sic.add(new SelectorItemInfo("srcRoom.number"));
        	sic.add(new SelectorItemInfo("srcRoom.name"));
		}
        sic.add(new SelectorItemInfo("bizType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("changeReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("changeReason.id"));
        	sic.add(new SelectorItemInfo("changeReason.number"));
        	sic.add(new SelectorItemInfo("changeReason.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("handler.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("handler.id"));
        	sic.add(new SelectorItemInfo("handler.number"));
        	sic.add(new SelectorItemInfo("handler.name"));
		}
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("customerPhone"));
        sic.add(new SelectorItemInfo("isSignChangeName"));
        sic.add(new SelectorItemInfo("quitAmount"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payType.id"));
        	sic.add(new SelectorItemInfo("payType.number"));
        	sic.add(new SelectorItemInfo("payType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("agioScheme.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("agioScheme.id"));
        	sic.add(new SelectorItemInfo("agioScheme.number"));
        	sic.add(new SelectorItemInfo("agioScheme.name"));
		}
        sic.add(new SelectorItemInfo("agioDesc"));
        sic.add(new SelectorItemInfo("lastAgio"));
        sic.add(new SelectorItemInfo("dealTotalAmount"));
        sic.add(new SelectorItemInfo("contractTotalAmount"));
        sic.add(new SelectorItemInfo("sellAmount"));
        sic.add(new SelectorItemInfo("planningCompensate"));
        sic.add(new SelectorItemInfo("cashSalesCompensate"));
        sic.add(new SelectorItemInfo("areaCompensate"));
        sic.add(new SelectorItemInfo("dealBuildPrice"));
        sic.add(new SelectorItemInfo("contractBuildPrice"));
        sic.add(new SelectorItemInfo("planningArea"));
        sic.add(new SelectorItemInfo("preArea"));
        sic.add(new SelectorItemInfo("actualArea"));
        sic.add(new SelectorItemInfo("contractRoomPrice"));
        sic.add(new SelectorItemInfo("dealRoomPrice"));
        sic.add(new SelectorItemInfo("valuationType"));
        sic.add(new SelectorItemInfo("accFundAmount"));
        sic.add(new SelectorItemInfo("loanAmount"));
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("strdBuildingPrice"));
        sic.add(new SelectorItemInfo("strdRoomPrice"));
        sic.add(new SelectorItemInfo("srcRoom.roomModel.name"));
        sic.add(new SelectorItemInfo("strdTotalAmount"));
        sic.add(new SelectorItemInfo("attachmentAmount"));
        sic.add(new SelectorItemInfo("fitmentTotalAmount"));
        sic.add(new SelectorItemInfo("isFitmentToContract"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fitmentStandard.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fitmentStandard.id"));
        	sic.add(new SelectorItemInfo("fitmentStandard.number"));
        	sic.add(new SelectorItemInfo("fitmentStandard.name"));
		}
        sic.add(new SelectorItemInfo("fitmentPrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("details"));
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
     * output actionSelectCustomer_actionPerformed method
     */
    public void actionSelectCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChooseAgio_actionPerformed method
     */
    public void actionChooseAgio_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionSelectCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelectCustomer() {
    	return false;
    }
	public RequestContext prepareActionChooseAgio(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChooseAgio() {
    	return false;
    }

    /**
     * output ActionSelectCustomer class
     */     
    protected class ActionSelectCustomer extends ItemAction {     
    
        public ActionSelectCustomer()
        {
            this(null);
        }

        public ActionSelectCustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeManageEditUI.this, "ActionSelectCustomer", "actionSelectCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionChooseAgio class
     */     
    protected class ActionChooseAgio extends ItemAction {     
    
        public ActionChooseAgio()
        {
            this(null);
        }

        public ActionChooseAgio(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChooseAgio.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseAgio.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseAgio.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeManageEditUI.this, "ActionChooseAgio", "actionChooseAgio_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChangeManageEditUI");
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
        return com.kingdee.eas.fdc.sellhouse.client.ChangeManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.ChangeManageInfo objectValue = new com.kingdee.eas.fdc.sellhouse.ChangeManageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/ChangeManage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ChangeManageQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblPayList;
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