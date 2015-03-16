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
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractTenancyReceiveBillEidtUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyReceiveBillEidtUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Purchase;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7MoneyName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReceiveAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBillDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Auditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLoanData;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheque;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayeeAccountBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7LoanData;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayeeBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayQuomodo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPayQuomodo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Cheque;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiptNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAdd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionRec actionRec = null;
    /**
     * output class constructor
     */
    public AbstractTenancyReceiveBillEidtUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyReceiveBillEidtUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiveAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Purchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7MoneyName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReceiveAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnChooseRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBillDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Auditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contApAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtApAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLoanData = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayeeBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheque = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7PayeeAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7PayeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7LoanData = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPayeeBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPayQuomodo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboPayQuomodo = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contReceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtReceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAdd = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contPurchase.setName("contPurchase");
        this.contMoneyName.setName("contMoneyName");
        this.contReceiveAmount.setName("contReceiveAmount");
        this.contCurrency.setName("contCurrency");
        this.f7Purchase.setName("f7Purchase");
        this.f7MoneyName.setName("f7MoneyName");
        this.txtReceiveAmount.setName("txtReceiveAmount");
        this.f7Currency.setName("f7Currency");
        this.contRoom.setName("contRoom");
        this.txtRoom.setName("txtRoom");
        this.btnChooseRoom.setName("btnChooseRoom");
        this.contNumber.setName("contNumber");
        this.contCustomer.setName("contCustomer");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contBillDate.setName("contBillDate");
        this.txtNumber.setName("txtNumber");
        this.pkBillDate.setName("pkBillDate");
        this.f7Customer.setName("f7Customer");
        this.contCreator.setName("contCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.f7Creator.setName("f7Creator");
        this.pkAuditTime.setName("pkAuditTime");
        this.f7Auditor.setName("f7Auditor");
        this.contApAmount.setName("contApAmount");
        this.contActAmount.setName("contActAmount");
        this.txtApAmount.setName("txtApAmount");
        this.txtActAmount.setName("txtActAmount");
        this.contSellProject.setName("contSellProject");
        this.contSellOrder.setName("contSellOrder");
        this.contSalesman.setName("contSalesman");
        this.contLoanData.setName("contLoanData");
        this.contPayeeAccountBank.setName("contPayeeAccountBank");
        this.contPayeeAccount.setName("contPayeeAccount");
        this.contPayeeBank.setName("contPayeeBank");
        this.contProject.setName("contProject");
        this.contDes.setName("contDes");
        this.contCheque.setName("contCheque");
        this.f7Project.setName("f7Project");
        this.f7Salesman.setName("f7Salesman");
        this.f7PayeeAccountBank.setName("f7PayeeAccountBank");
        this.f7PayeeAccount.setName("f7PayeeAccount");
        this.txtDes.setName("txtDes");
        this.f7LoanData.setName("f7LoanData");
        this.f7SellProject.setName("f7SellProject");
        this.f7SellOrder.setName("f7SellOrder");
        this.txtPayeeBank.setName("txtPayeeBank");
        this.contPayQuomodo.setName("contPayQuomodo");
        this.comboPayQuomodo.setName("comboPayQuomodo");
        this.f7Cheque.setName("f7Cheque");
        this.btnRec.setName("btnRec");
        this.contReceiptNumber.setName("contReceiptNumber");
        this.txtReceiptNumber.setName("txtReceiptNumber");
        this.kDTable1.setName("kDTable1");
        this.btnAdd.setName("btnAdd");
        this.btnDelete.setName("btnDelete");
        // CoreUI		
        this.setPreferredSize(new Dimension(960,500));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // contPurchase		
        this.contPurchase.setBoundLabelText(resHelper.getString("contPurchase.boundLabelText"));		
        this.contPurchase.setBoundLabelLength(100);		
        this.contPurchase.setBoundLabelUnderline(true);
        // contMoneyName		
        this.contMoneyName.setBoundLabelText(resHelper.getString("contMoneyName.boundLabelText"));		
        this.contMoneyName.setBoundLabelUnderline(true);		
        this.contMoneyName.setBoundLabelLength(100);
        // contReceiveAmount		
        this.contReceiveAmount.setBoundLabelText(resHelper.getString("contReceiveAmount.boundLabelText"));		
        this.contReceiveAmount.setBoundLabelLength(100);		
        this.contReceiveAmount.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // f7Purchase		
        this.f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");		
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
        // f7MoneyName		
        this.f7MoneyName.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7MoneyName.setCommitFormat("$number$");		
        this.f7MoneyName.setEditFormat("$number$");		
        this.f7MoneyName.setDisplayFormat("$name$");
        this.f7MoneyName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7MoneyName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtReceiveAmount		
        this.txtReceiveAmount.setDataType(1);
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setCommitFormat("$number$");
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelUnderline(true);		
        this.contRoom.setBoundLabelLength(100);
        // txtRoom		
        this.txtRoom.setEnabled(false);
        // btnChooseRoom		
        this.btnChooseRoom.setText(resHelper.getString("btnChooseRoom.text"));
        this.btnChooseRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contBillDate		
        this.contBillDate.setBoundLabelText(resHelper.getString("contBillDate.boundLabelText"));		
        this.contBillDate.setBoundLabelLength(100);		
        this.contBillDate.setBoundLabelUnderline(true);
        // txtNumber
        // pkBillDate
        // f7Customer		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");		
        this.f7Customer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");
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
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // f7Creator		
        this.f7Creator.setEnabled(false);		
        this.f7Creator.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // f7Auditor		
        this.f7Auditor.setEnabled(false);		
        this.f7Auditor.setDisplayFormat("$name$");
        // contApAmount		
        this.contApAmount.setBoundLabelText(resHelper.getString("contApAmount.boundLabelText"));		
        this.contApAmount.setBoundLabelLength(100);		
        this.contApAmount.setBoundLabelUnderline(true);
        // contActAmount		
        this.contActAmount.setBoundLabelText(resHelper.getString("contActAmount.boundLabelText"));		
        this.contActAmount.setBoundLabelLength(100);		
        this.contActAmount.setBoundLabelUnderline(true);
        // txtApAmount		
        this.txtApAmount.setEnabled(false);		
        this.txtApAmount.setDataType(1);
        // txtActAmount		
        this.txtActAmount.setEnabled(false);		
        this.txtActAmount.setDataType(1);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(100);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(100);		
        this.contSalesman.setBoundLabelUnderline(true);
        // contLoanData		
        this.contLoanData.setBoundLabelText(resHelper.getString("contLoanData.boundLabelText"));		
        this.contLoanData.setBoundLabelUnderline(true);		
        this.contLoanData.setBoundLabelLength(100);
        // contPayeeAccountBank		
        this.contPayeeAccountBank.setBoundLabelText(resHelper.getString("contPayeeAccountBank.boundLabelText"));		
        this.contPayeeAccountBank.setBoundLabelUnderline(true);		
        this.contPayeeAccountBank.setBoundLabelLength(100);
        // contPayeeAccount		
        this.contPayeeAccount.setBoundLabelText(resHelper.getString("contPayeeAccount.boundLabelText"));		
        this.contPayeeAccount.setBoundLabelLength(100);		
        this.contPayeeAccount.setBoundLabelUnderline(true);
        // contPayeeBank		
        this.contPayeeBank.setBoundLabelText(resHelper.getString("contPayeeBank.boundLabelText"));		
        this.contPayeeBank.setBoundLabelUnderline(true);		
        this.contPayeeBank.setBoundLabelLength(100);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelUnderline(true);		
        this.contDes.setBoundLabelLength(100);
        // contCheque		
        this.contCheque.setBoundLabelText(resHelper.getString("contCheque.boundLabelText"));		
        this.contCheque.setBoundLabelUnderline(true);		
        this.contCheque.setBoundLabelLength(100);
        // f7Project		
        this.f7Project.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProjectQuery");		
        this.f7Project.setCommitFormat("$number$");		
        this.f7Project.setEditFormat("$number$");		
        this.f7Project.setDisplayFormat("$name$");
        // f7Salesman		
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Salesman.setDisplayFormat("$name$");		
        this.f7Salesman.setEditFormat("$number$");		
        this.f7Salesman.setCommitFormat("$number$");
        // f7PayeeAccountBank		
        this.f7PayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");		
        this.f7PayeeAccountBank.setCommitFormat("$number$");		
        this.f7PayeeAccountBank.setDisplayFormat("$name$");		
        this.f7PayeeAccountBank.setEditFormat("$number$");
        this.f7PayeeAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7PayeeAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7PayeeAccount		
        this.f7PayeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.f7PayeeAccount.setEditFormat("$number$");		
        this.f7PayeeAccount.setDisplayFormat("$name$");		
        this.f7PayeeAccount.setCommitFormat("$number$");		
        this.f7PayeeAccount.setRequired(true);
        // txtDes		
        this.txtDes.setMaxLength(80);
        // f7LoanData		
        this.f7LoanData.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.LoanDataF7Query");		
        this.f7LoanData.setCommitFormat("$number$");		
        this.f7LoanData.setEditFormat("$number$");		
        this.f7LoanData.setDisplayFormat("$name$");
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setEnabled(false);
        // f7SellOrder		
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");
        // txtPayeeBank
        // contPayQuomodo		
        this.contPayQuomodo.setBoundLabelText(resHelper.getString("contPayQuomodo.boundLabelText"));		
        this.contPayQuomodo.setBoundLabelLength(100);		
        this.contPayQuomodo.setBoundLabelUnderline(true);
        // comboPayQuomodo		
        this.comboPayQuomodo.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum").toArray());
        this.comboPayQuomodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPayQuomodo_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Cheque		
        this.f7Cheque.setQueryInfo("com.kingdee.eas.fm.nt.app.ChequeQuery");		
        this.f7Cheque.setCommitFormat("$number$");		
        this.f7Cheque.setDisplayFormat("$number$");		
        this.f7Cheque.setEditFormat("$number$");
        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setToolTipText(resHelper.getString("btnRec.toolTipText"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // contReceiptNumber		
        this.contReceiptNumber.setBoundLabelText(resHelper.getString("contReceiptNumber.boundLabelText"));		
        this.contReceiptNumber.setBoundLabelLength(100);		
        this.contReceiptNumber.setBoundLabelUnderline(true);
        // txtReceiptNumber		
        this.txtReceiptNumber.setMaxLength(80);
        // kDTable1		
        this.kDTable1.setFormatXml(resHelper.getString("kDTable1.formatXml"));

        

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
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 960, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 960, 500));
        contPurchase.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(contPurchase, new KDLayout.Constraints(10, 110, 270, 19, 0));
        contMoneyName.setBounds(new Rectangle(10, 147, 270, 19));
        this.add(contMoneyName, new KDLayout.Constraints(10, 147, 270, 19, 0));
        contReceiveAmount.setBounds(new Rectangle(10, 180, 270, 19));
        this.add(contReceiveAmount, new KDLayout.Constraints(10, 180, 270, 19, 0));
        contCurrency.setBounds(new Rectangle(381, 180, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(381, 180, 270, 19, 0));
        contRoom.setBounds(new Rectangle(10, 45, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(10, 45, 270, 19, 0));
        btnChooseRoom.setBounds(new Rectangle(288, 45, 83, 19));
        this.add(btnChooseRoom, new KDLayout.Constraints(288, 45, 83, 19, 0));
        contNumber.setBounds(new Rectangle(10, 13, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 13, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(381, 45, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(381, 45, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(376, 273, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(376, 273, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(9, 271, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(9, 271, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(10, 304, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 304, 270, 19, 0));
        contBillDate.setBounds(new Rectangle(381, 13, 270, 19));
        this.add(contBillDate, new KDLayout.Constraints(381, 13, 270, 19, 0));
        contCreator.setBounds(new Rectangle(375, 307, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(375, 307, 270, 19, 0));
        contApAmount.setBounds(new Rectangle(381, 147, 270, 19));
        this.add(contApAmount, new KDLayout.Constraints(381, 147, 270, 19, 0));
        contActAmount.setBounds(new Rectangle(676, 147, 270, 19));
        this.add(contActAmount, new KDLayout.Constraints(676, 147, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(381, 74, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(381, 74, 270, 19, 0));
        contSellOrder.setBounds(new Rectangle(10, 74, 270, 19));
        this.add(contSellOrder, new KDLayout.Constraints(10, 74, 270, 19, 0));
        contSalesman.setBounds(new Rectangle(381, 110, 270, 19));
        this.add(contSalesman, new KDLayout.Constraints(381, 110, 270, 19, 0));
        contLoanData.setBounds(new Rectangle(676, 110, 270, 19));
        this.add(contLoanData, new KDLayout.Constraints(676, 110, 270, 19, 0));
        contPayeeAccountBank.setBounds(new Rectangle(10, 212, 270, 19));
        this.add(contPayeeAccountBank, new KDLayout.Constraints(10, 212, 270, 19, 0));
        contPayeeAccount.setBounds(new Rectangle(676, 212, 270, 19));
        this.add(contPayeeAccount, new KDLayout.Constraints(676, 212, 270, 19, 0));
        contPayeeBank.setBounds(new Rectangle(381, 212, 270, 19));
        this.add(contPayeeBank, new KDLayout.Constraints(381, 212, 270, 19, 0));
        contProject.setBounds(new Rectangle(676, 74, 270, 19));
        this.add(contProject, new KDLayout.Constraints(676, 74, 270, 19, 0));
        contDes.setBounds(new Rectangle(9, 240, 938, 19));
        this.add(contDes, new KDLayout.Constraints(9, 240, 938, 19, 0));
        contCheque.setBounds(new Rectangle(676, 13, 270, 19));
        this.add(contCheque, new KDLayout.Constraints(676, 13, 270, 19, 0));
        contPayQuomodo.setBounds(new Rectangle(675, 273, 270, 19));
        this.add(contPayQuomodo, new KDLayout.Constraints(675, 273, 270, 19, 0));
        contReceiptNumber.setBounds(new Rectangle(676, 13, 270, 19));
        this.add(contReceiptNumber, new KDLayout.Constraints(676, 13, 270, 19, 0));
        kDTable1.setBounds(new Rectangle(12, 332, 932, 164));
        this.add(kDTable1, new KDLayout.Constraints(12, 332, 932, 164, 0));
        btnAdd.setBounds(new Rectangle(793, 307, 70, 19));
        this.add(btnAdd, new KDLayout.Constraints(793, 307, 70, 19, 0));
        btnDelete.setBounds(new Rectangle(873, 307, 70, 19));
        this.add(btnDelete, new KDLayout.Constraints(873, 307, 70, 19, 0));
        //contPurchase
        contPurchase.setBoundEditor(f7Purchase);
        //contMoneyName
        contMoneyName.setBoundEditor(f7MoneyName);
        //contReceiveAmount
        contReceiveAmount.setBoundEditor(txtReceiveAmount);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contRoom
        contRoom.setBoundEditor(txtRoom);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contBillDate
        contBillDate.setBoundEditor(pkBillDate);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contApAmount
        contApAmount.setBoundEditor(txtApAmount);
        //contActAmount
        contActAmount.setBoundEditor(txtActAmount);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contLoanData
        contLoanData.setBoundEditor(f7LoanData);
        //contPayeeAccountBank
        contPayeeAccountBank.setBoundEditor(f7PayeeAccountBank);
        //contPayeeAccount
        contPayeeAccount.setBoundEditor(f7PayeeAccount);
        //contPayeeBank
        contPayeeBank.setBoundEditor(txtPayeeBank);
        //contProject
        contProject.setBoundEditor(f7Project);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contCheque
        contCheque.setBoundEditor(f7Cheque);
        //contPayQuomodo
        contPayQuomodo.setBoundEditor(comboPayQuomodo);
        //contReceiptNumber
        contReceiptNumber.setBoundEditor(txtReceiptNumber);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCalculator);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnAdd, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnDelete, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnAdd, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnDelete, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnAdd, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnDelete, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnAdd, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnDelete, ActionStateConst.DISABLED);		
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
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.btnAdd.setEnabled(true);
		            this.btnDelete.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.btnAdd.setEnabled(true);
		            this.btnDelete.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.btnAdd.setEnabled(false);
		            this.btnDelete.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.btnAdd.setEnabled(false);
		            this.btnDelete.setEnabled(false);
        }
    }

    /**
     * output f7Purchase_dataChanged method
     */
    protected void f7Purchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7MoneyName_dataChanged method
     */
    protected void f7MoneyName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnChooseRoom_actionPerformed method
     */
    protected void btnChooseRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Customer_dataChanged method
     */
    protected void f7Customer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7PayeeAccountBank_dataChanged method
     */
    protected void f7PayeeAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboPayQuomodo_itemStateChanged method
     */
    protected void comboPayQuomodo_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output actionRec_actionPerformed method
     */
    public void actionRec_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionRec class
     */
    protected class ActionRec extends ItemAction
    {
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyReceiveBillEidtUI.this, "ActionRec", "actionRec_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyReceiveBillEidtUI");
    }




}